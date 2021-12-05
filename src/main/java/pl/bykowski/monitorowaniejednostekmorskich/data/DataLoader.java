package pl.bykowski.monitorowaniejednostekmorskich.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Autowired
    private DataLoaderService dataLoaderService;

    @Scheduled(fixedDelay = 10000)
    public void loadTracks() {
        dataLoaderService.getTracks();
    }

}
