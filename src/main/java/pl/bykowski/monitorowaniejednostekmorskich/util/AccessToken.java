package pl.bykowski.monitorowaniejednostekmorskich.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AccessToken {

    private static String ACCESS_TOKEN;
    private static RestTemplate restTemplate = new RestTemplate();

    static synchronized public String get() {
        if (ACCESS_TOKEN == null) {
            ACCESS_TOKEN = getAccessToken();
        }
        return ACCESS_TOKEN;
    }

    private static String getAccessToken() {
        String url = "https://id.barentswatch.no/connect/token";

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

//        rootNode.put("client_id", "do.dev.api@gmail.com:dodevapi");
//        rootNode.put("client_secret", "dodevapisecret");
//        rootNode.put("scope", "api");
//        rootNode.put("grant_type", "client_credentials");
//
//        try {
//            System.out.println(mapper.writeValueAsString(rootNode));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("client_id", "do.dev.api@gmail.com:dodevapi");
        map.add("client_secret", "dodevapisecret");
        map.add("scope", "api");
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<JsonNode> responses = restTemplate.postForEntity(url, request, JsonNode.class);

        return responses.getBody().get("access_token").asText();
    }

}
