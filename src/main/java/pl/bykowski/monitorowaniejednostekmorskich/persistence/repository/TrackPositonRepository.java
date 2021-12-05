package pl.bykowski.monitorowaniejednostekmorskich.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.entity.TrackPositionEntity;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TrackPositonRepository extends JpaRepository<TrackPositionEntity, Long> {

    List<TrackPositionEntity> findByMmsi(Integer mmsi);

    TrackPositionEntity findTopByMmsiOrderByDateDesc(Integer mmsi);

    List<TrackPositionEntity> findByMmsiAndDateGreaterThan(Integer mmsi, OffsetDateTime data);
}
