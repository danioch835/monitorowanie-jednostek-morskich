package pl.bykowski.monitorowaniejednostekmorskich.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bykowski.monitorowaniejednostekmorskich.model.Datum;
import pl.bykowski.monitorowaniejednostekmorskich.model.Track;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.entity.TrackPositionEntity;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.service.TrackPositionDbService;
import pl.bykowski.monitorowaniejednostekmorskich.view.TrackDestinationView;
import pl.bykowski.monitorowaniejednostekmorskich.view.TrackPointView;
import pl.bykowski.monitorowaniejednostekmorskich.view.TrackView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackService {

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private TrackPositionDbService trackPositionDbService;

    public TrackView getTrackByMmsi(Long mmsi) {
        List<TrackPositionEntity> intervalPoints = getTrackHistoryFromDb(mmsi);

        Track track = new Track();
//        track.setName("A");
//        track.setDestination("A");
        track.setMmsi(mmsi.intValue());
        return getTrackView(track, intervalPoints);
    }

    public List<TrackView> getTracks() {
        List<Track> actualPoints = positionService.getActualPoints();

        List<TrackView> tracksView = new ArrayList<>();
        for (Track actualPosition : actualPoints) {
            List<TrackPositionEntity> intervalPoints = getLastDayTrackFromDb(actualPosition.getMmsi().longValue());


            if (intervalPoints != null && !intervalPoints.isEmpty()) {
                TrackView trackView = getTrackView(actualPosition, intervalPoints);

                TrackPositionEntity lastPosition = intervalPoints.get(intervalPoints.size() - 1);
                if (actualPosition.getDestination() != null) {
                    Datum destination = destinationService.getDestination(actualPosition.getDestination(),
                                                                          lastPosition.getLat(),
                                                                          lastPosition.getLon());
                    if (destination != null)
                        trackView.setTrackDestination(getTrackDestinationView(destination));
                }

                tracksView.add(trackView);
            }

        }
        return tracksView;
    }

    private TrackDestinationView getTrackDestinationView(Datum destination) {
        TrackDestinationView trackDestinationView = new TrackDestinationView();

        TrackPointView trackPointView = new TrackPointView();
        trackPointView.setLon(destination.getLongitude());
        trackPointView.setLat(destination.getLatitude());
        trackDestinationView.setPoint(trackPointView);
        trackDestinationView.setName(destination.getName());

        return trackDestinationView;
    }

    private TrackView getTrackView(Track track, List<TrackPositionEntity> positions) {
        TrackView trackView = new TrackView();

        double distance = positions.stream().mapToDouble(TrackPositionEntity::getDistance).sum();

        trackView.setName(track.getName());
        trackView.setDestination(track.getDestination());
        trackView.setDistance((int) distance);
        trackView.setShipId(track.getMmsi().longValue());

        List<TrackPointView> trackPointView = positions.stream()
                                                       .map(this::toTrackView).collect(Collectors.toList());
        trackView.setPoints(trackPointView);

        return trackView;
    }

    private TrackPointView toTrackView(TrackPositionEntity trackPosition) {
        TrackPointView trackPointView = new TrackPointView();

        trackPointView.setLon(trackPosition.getLon());
        trackPointView.setLat(trackPosition.getLat());
        trackPointView.setDate(trackPosition.getDate());

        return trackPointView;
    }

    private List<TrackPositionEntity> getTrackHistoryFromDb(Long mmsi) {
        return trackPositionDbService.find(mmsi.intValue());
    }

    private List<TrackPositionEntity> getLastDayTrackFromDb(Long mmsi) {
        return trackPositionDbService.getLastDayTruck(mmsi.intValue());
    }

}
