package ru.practicum.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
@RequiredArgsConstructor
public class StatisticClient {
    private final ObjectMapper objectMapper;
    public static void main(String[] args) throws JsonProcessingException {
        LocalDateTime start = LocalDateTime.now().minusDays(5);
        LocalDateTime end = LocalDateTime.now();
                StatisticClient statisticClient = new StatisticClient(new ObjectMapper());
        System.out.println(statisticClient.getStatistic(start, end, null, null));
    }

    public List<ViewStat> getStatistic(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique)
            throws JsonProcessingException {
        List<ViewStat> result = null;
        String encodeStart = URLEncoder.encode(String.valueOf(start), StandardCharsets.UTF_8);
        String encodeEnd = URLEncoder.encode(String.valueOf(end), StandardCharsets.UTF_8);

        URI uri = URI.create("http://localhost:9090/stats?start=" + encodeStart + "&end=" + encodeEnd);
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
            System.out.println("Something wrong in GetClient");
        }
        return result;
    }

    public Integer getViewsById(Integer eventId) {
        Integer result = null;
        URI uri = URI.create("http://localhost:9090/events/" + eventId);
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
            result = objectMapper.readValue(response.body(), Integer.class);
        } catch (IOException | InterruptedException e) {
            System.out.println("Something wrong in GetClient");
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
        CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
                .build()
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