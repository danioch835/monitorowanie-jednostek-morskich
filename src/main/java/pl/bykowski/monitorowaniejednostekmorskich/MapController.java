package pl.bykowski.monitorowaniejednostekmorskich;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.bykowski.monitorowaniejednostekmorskich.service.PositionService;
import pl.bykowski.monitorowaniejednostekmorskich.service.TrackService;

@Controller
public class MapController {

    private final TrackService trackService;
    private final PositionService positionService;

    public MapController(TrackService trackService, PositionService positionService) {
        this.trackService = trackService;
        this.positionService = positionService;
    }

    @GetMapping("")
    public String getMapMain(Model model) {
//        model.addAttribute("tracks", trackService.getBestTracks());
        return "mapMain";
    }

    @GetMapping("/tracks")
    public String getTracks(Model model) {
        model.addAttribute("tracks", trackService.getTracks());
        return "mapTracks";
    }

    @GetMapping("/tracks/{mmsi}")
    public String getTrackOnMap(@PathVariable("mmsi") Long mmsi, Model model) {
        model.addAttribute("tracks", trackService.getTrackByMmsi(mmsi));
        return "mapTrack";
    }

    @GetMapping("/actual")
    public String getActualPositions(Model model) {
        model.addAttribute("tracks", positionService.getPositions());
        return "actualMap";
    }

}
