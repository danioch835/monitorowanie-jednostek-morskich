package pl.bykowski.monitorowaniejednostekmorskich.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.entity.DestinationEntity;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.entity.TrackPositionEntity;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.repository.DestinationRepository;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.repository.TrackPositonRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationDbService {

    @Autowired
    private DestinationRepository destinationRepository;

    public DestinationEntity save(DestinationEntity destinationEntity) {
        return destinationRepository.save(destinationEntity);
    }

    public DestinationEntity find(String name) {
        return destinationRepository.findByName(name);
    }



}
