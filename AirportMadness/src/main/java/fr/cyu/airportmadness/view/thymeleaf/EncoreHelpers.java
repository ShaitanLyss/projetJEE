package fr.cyu.airportmadness.view.thymeleaf;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EncoreHelpers {
    private static EncoreHelpers uniqueInstance;

    public static EncoreHelpers  getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new EncoreHelpers();
        return uniqueInstance;
    }

    public List<String> entryScriptUrls() {
        List<String> res = new ArrayList<>(3);
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/build/entrypoints.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            jsonNode.get("entrypoints").get("app").get("js").elements()
                    .forEachRemaining(e -> res.add(e.asText()));
//            map.get("entrypoints").get("map");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    public List<String> entryLinkUrls() {
        List<String> res = new ArrayList<>(3);
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("static/build/entrypoints.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            jsonNode.get("entrypoints").get("app").get("css").elements()
                    .forEachRemaining(e -> res.add(e.asText()));
//            map.get("entrypoints").get("map");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res;
    }
}
