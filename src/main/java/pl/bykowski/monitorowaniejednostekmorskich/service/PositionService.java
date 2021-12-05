package pl.bykowski.monitorowaniejednostekmorskich.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.bykowski.monitorowaniejednostekmorskich.util.AccessToken;
import pl.bykowski.monitorowaniejednostekmorskich.model.Datum;
import pl.bykowski.monitorowaniejednostekmorskich.model.Track;
import pl.bykowski.monitorowaniejednostekmorskich.view.PositionView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PositionService {

    private final DestinationService destinationService;
    private final RestTemplate restTemplate;

    public PositionService(DestinationService destinationService) {
        this.destinationService = destinationService;
        this.restTemplate = new RestTemplate();
    }

    public List<PositionView> getPositions() {
        List<Track> positions = getActualPoints();

        return positions.stream()
                        .map(track -> getPosition(track))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
    }

    private PositionView getPosition(Track track) {

        Double lat = track.getGeometry().getCoordinates().get(0);
        Double lon = track.getGeometry().getCoordinates().get(1);

        if (lat == null || lon == null)
            return null;

        Datum destination = destinationService.getDestination(track.getDestination(), lat, lon);
        return new PositionView(lat,
                                lon,
                                track.getName(),
                                destination.getLongitude(),
                                destination.getLatitude(),
                                track.getMmsi().longValue());
    }

    public List<Track> getActualPoints() {

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
