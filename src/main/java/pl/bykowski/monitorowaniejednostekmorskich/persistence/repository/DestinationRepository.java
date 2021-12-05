package pl.bykowski.monitorowaniejednostekmorskich.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.entity.DestinationEntity;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.entity.TrackPositionEntity;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationEntity, Long> {

    DestinationEntity findByName(String name);

}
