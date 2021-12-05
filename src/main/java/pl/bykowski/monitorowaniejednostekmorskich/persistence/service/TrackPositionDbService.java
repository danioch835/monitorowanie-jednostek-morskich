package pl.bykowski.monitorowaniejednostekmorskich.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.entity.TrackPositionEntity;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.repository.TrackPositonRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrackPositionDbService {

    @Autowired
    private TrackPositonRepository trackPositonRepository;

    public List<TrackPositionEntity> save(Integer mmsi, List<TrackPositionEntity> positions) {
        TrackPositionEntity lastPositionDb = trackPositonRepository.findTopByMmsiOrderByDateDesc(mmsi);

        if (lastPositionDb != null) {
            positions = positions.stream()
                                 .filter(p -> p.getDate().isAfter(lastPositionDb.getDate()))
                                 .collect(Collectors.toList());

        }

        return trackPositonRepository.saveAll(positions);
    }

    public List<TrackPositionEntity> find(Integer mmsi) {
        return trackPositonRepository.findByMmsi(mmsi);
    }

    public List<TrackPositionEntity> getLastDayTruck(Integer mmsi) {
        OffsetDateTime lastDay = OffsetDateTime.now().minusDays(1);
        return trackPositonRepository.findByMmsiAndDateGreaterThan(mmsi, lastDay);
    }

}
