package ru.practicum.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.exception.StatisticClientException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
@RequiredArgsConstructor
public class StatisticClient {
    private final ObjectMapper objectMapper;

    public List<ViewStat> getStatistic(String start, String end, String[] uris, Boolean unique) {
        List<ViewStat> result;
        String encodeStart = URLEncoder.encode(start, StandardCharsets.UTF_8);
        String encodeEnd = URLEncoder.encode(end, StandardCharsets.UTF_8);
        String encodeUris = URLEncoder.encode(convertArrayToStringForUrl(uris), StandardCharsets.UTF_8);
        String encodeUnique = URLEncoder.encode(String.valueOf(unique), StandardCharsets.UTF_8);
        URI uri = URI.create("http://localhost:9090/stats?start=" + encodeStart + "&end=" + encodeEnd +
                "&uris=" + encodeUris + "&unique=" + encodeUnique);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .timeout(Duration.of(10, SECONDS))
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            result = objectMapper.readValue(response.body(), objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, ViewStat.class));
        } catch (IOException | InterruptedException e) {
            throw new StatisticClientException("An error occurred when sending a request from a client");
        }
        return result;
    }

    private String convertArrayToStringForUrl(String[] uris) {
        List<String> fields = new ArrayList<>(List.of(uris));
        return fields.stream().map(String::valueOf).collect(Collectors.joining(",", "", ""));
    }

    public Integer getViewsByUri(Integer eventId) {
        Integer result;
        URI uri = URI.create("http://localhost:9090/events");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .method("GET", HttpRequest.BodyPublishers.ofString("/events/" + eventId))
                .timeout(Duration.of(10, SECONDS))
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            result = objectMapper.readValue(response.body(), Integer.class);
        } catch (IOException | InterruptedException e) {
            throw new StatisticClientException("An error occurred when sending a request from a client");
        }
        return result;
    }

    public void saveCall(Map<String, String> endpointHit) {
        URI uri = URI.create("http://localhost:9090/hit");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(endpointHit)))
                .timeout(Duration.of(10, SECONDS))
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .header("Accept", "application/json")
                .build();
        HttpClient.newBuilder().build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }
}