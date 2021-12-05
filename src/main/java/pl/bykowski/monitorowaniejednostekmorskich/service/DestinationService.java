package pl.bykowski.monitorowaniejednostekmorskich.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.bykowski.monitorowaniejednostekmorskich.model.Coordinate;
import pl.bykowski.monitorowaniejednostekmorskich.model.Datum;
import pl.bykowski.monitorowaniejednostekmorskich.model.Track;
import pl.bykowski.monitorowaniejednostekmorskich.model.TrackPosition;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.entity.DestinationEntity;
import pl.bykowski.monitorowaniejednostekmorskich.persistence.service.DestinationDbService;
import pl.bykowski.monitorowaniejednostekmorskich.view.PositionView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationService {

    private final RestTemplate restTemplate;
    private final DestinationDbService destinationDbService;

    public DestinationService(DestinationDbService destinationDbService) {
        this.restTemplate = new RestTemplate();
        this.destinationDbService = destinationDbService;
    }

    public Datum getDestination(String destinationName, double lat, double lon) {
        DestinationEntity destinationEntity = destinationDbService.find(destinationName);

        if (destinationEntity != null) {
            Datum datum = new Datum(destinationEntity.getLat(), destinationEntity.getLon());
            datum.setName(destinationEntity.getName());
            return datum;
        } else {
            Datum datum = getDestinationFromApi(destinationName, lat, lon);
            if (datum != null) {
                destinationEntity = new DestinationEntity();
                destinationEntity.setName(destinationName);
                destinationEntity.setLat(datum.getLatitude());
                destinationEntity.setLon(datum.getLongitude());
                destinationDbService.save(destinationEntity);
            }
            return datum;
        }
    }

    private Datum getDestinationFromApi(String destinationName, double lat, double lon) {
        try {

            String key = "a3b8fd42d2ca398771d576b9312ba0d3";
            String url = "http://api.positionstack.com/v1/forward?access_key=" + key + "&query=" + destinationName;
            JsonNode data = restTemplate.getForObject(url, JsonNode.class).get("data").get(0);
            double latitude = data.get("latitude").asDouble();
            double longitude = data.get("longitude").asDouble();
            String name = data.get("name").asText();


            Datum datum = new Datum(latitude, longitude);
            datum.setName(name);
            return datum;

        } catch (Exception ex) {
            ex.printStackTrace();
        Datum datum = new Datum(lat, lon);
        return datum;
        }
    }

}
