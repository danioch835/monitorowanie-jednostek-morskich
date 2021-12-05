package pl.bykowski.monitorowaniejednostekmorskich.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.bykowski.monitorowaniejednostekmorskich.util.AccessToken;
import pl.bykowski.monitorowaniejednostekmorskich.model.*;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.entity.TrackPositionEntity;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.service.TrackPositionDbService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataLoaderService {

    private final RestTemplate restTemplate;
    private final TrackPositionDbService trackPositionDbService;

    public DataLoaderService(TrackPositionDbService trackPositionDbService) {
        this.trackPositionDbService = trackPositionDbService;
        this.restTemplate = new RestTemplate();
    }

    public void getTracks() {
        List<Track> tracks = getPositions();

        for (Track track : tracks) {
            Tracks points = getTrack(track.getMmsi().longValue());
            List<TrackPosition> intervalPoints = points.getAllIntervalPointsItem();
            trackPositionDbService.save(track.getMmsi(), prepareEntities(points, intervalPoints));
        }
    }

    private List<TrackPositionEntity> prepareEntities(Tracks tracks, List<TrackPosition> points) {

        List<TrackPositionEntity> entities = new ArrayList<>();

        for (int i = 1; i < points.size(); i++) {
            TrackPosition previousPosition = points.get(i-1);
            TrackPosition actualPosition = points.get(i);

            double distance = DistanceCalculator.distance(previousPosition.getLat(),
                                                          actualPosition.getLat(),
                                                          previousPosition.getLon(),
                                                          previousPosition.getLon());

            TrackPositionEntity entity = new TrackPositionEntity();
            entity.setMmsi(tracks.getMmsi());
            entity.setLat(actualPosition.getLat());
            entity.setLon(actualPosition.getLon());
            entity.setDate(actualPosition.getMsgt());
            entity.setDistance(distance);

            entities.add(entity);
        }

        return entities;
    }

    private Tracks getTrack(Long mmsi) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + AccessToken.get());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Tracks> exchange = restTemplate.exchange("https://www.barentswatch.no/bwapi/v1/geodata/ais/" + mmsi + "/opentracks",
                                                                HttpMethod.GET,
                                                                httpEntity,
                                                                Tracks.class);

        return exchange.getBody();
    }

    private List<Track> getPositions() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + AccessToken.get());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

            String xmin = "10.09094";
            String xmax = "10.67047";
            String ymin = "63.3989";
            String ymax = "63.58645";
            String urlPositions = "https://www.barentswatch.no/bwapi/v2/geodata/ais/openpositions";

            ResponseEntity<Track[]> exchange = restTemplate.exchange(urlPositions + "?Xmin=" + xmin + "&Xmax=" + xmax + "&Ymin=" + ymin + "&Ymax=" + ymax,
                                                                     HttpMethod.GET,
                                                                     httpEntity,
                                                                     Track[].class);

            return Arrays.asList(exchange.getBody());
    }

}
