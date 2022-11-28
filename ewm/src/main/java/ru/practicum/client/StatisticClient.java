package ru.practicum.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.practicum.client.model.EndpointHit;
import ru.practicum.client.model.ViewStat;
import ru.practicum.client.model.ViewStatShort;
import ru.practicum.exception.StatisticSendingClientException;
import ru.practicum.exception.ValidationException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Component
public class StatisticClient {
    @Autowired
    public StatisticClient(@Value("${stats-server.url}") String statServerUrl, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.statServerUrl = statServerUrl;
    }

    private final ObjectMapper objectMapper;
    private final String statServerUrl;

//    public static void main(String[] args) {
//        StatisticClient statisticClient = new StatisticClient("http://localhost:9090", new ObjectMapper());
//        LocalDateTime start = LocalDateTime.now().minusDays(5);
//        LocalDateTime end = LocalDateTime.now();
//        String[] uris = new String[]{"/events/12", "/events/30", "/event/949", "/events/1", "/events/5"};
//        Boolean unique = false;
//        List<ViewStat> result = statisticClient.getStatistic(start, end, uris, unique);
//        for (ViewStat v : result) {
//            System.out.println(v);
//        }
//    }

    public List<ViewStat> getStatistic(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        List<ViewStat> result;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String encodeStart = URLEncoder.encode(start.format(formatter), StandardCharsets.UTF_8);
        String encodeEnd = URLEncoder.encode(end.format(formatter), StandardCharsets.UTF_8);
        String encodeUris = URLEncoder.encode(convertArrayToStringForUrl(uris), StandardCharsets.UTF_8);
        String encodeUnique = URLEncoder.encode(String.valueOf(unique), StandardCharsets.UTF_8);
        URI uri = URI.create(statServerUrl + "/stats?start=" + encodeStart + "&end=" + encodeEnd +
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
            throw new StatisticSendingClientException("An error occurred when sending a request from a client");
        }
        log.info("Send request GET uri = {}. \n And get response: {}", uri, response);
        return result;
    }

    private String convertArrayToStringForUrl(String[] uris) {
        List<String> fields = new ArrayList<>(List.of(uris));
        return fields.stream().map(String::valueOf).collect(Collectors.joining(",", "", ""));
    }

    public Optional<Long> getViewsByUri(Long eventId) {
        Long result;
        URI uri = URI.create(statServerUrl + "/events/" + eventId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response;
        try {
            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            result = objectMapper.readValue(response.body(), Long.class);
        } catch (IOException | InterruptedException e) {
            log.warn("Error: " + e + " \n Cause = " + e.getCause() + " \n Message = " + e.getMessage());
            throw new StatisticSendingClientException("An error occurred when sending a request from a client 'GetViewsByUri'");
        }
        log.info("Send request GET uri = {}. \n And get response: {}", uri, response);
        return Optional.of(result);
    }

    public void saveCall(EndpointHit endpointHit) {
        URI uri = URI.create(statServerUrl + "/hit");
        String body;
        try {
            body = objectMapper.writeValueAsString(endpointHit);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Failed to serialize and save statistics by endpointHit = " + endpointHit);
        }
        HttpRequest request;
        request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .timeout(Duration.of(10, SECONDS))
                .headers("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();

        HttpClient.newBuilder().build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
        log.info("Send request POST uri = {} to save statistic with body: {}", uri, body);
    }

    //    public static void main(String[] args) {
//        StatisticClient statisticClient = new StatisticClient("http://localhost:9090", new ObjectMapper());
//        String[] uris = new String[]{"/events/12", "/events/30", "/event/538", "/events/1"};
//        List<ViewStatShort> result = statisticClient.getStatisticForCollect(uris);
//        for(ViewStatShort v : result){
//            System.out.println(v);
//        }
//    }
    public List<ViewStatShort> getStatisticForCollect(String[] uris) {
        List<ViewStatShort> result;
        String encodeUris = URLEncoder.encode(convertArrayToStringForUrl(uris), StandardCharsets.UTF_8);
        URI uri = URI.create(statServerUrl + "/stats/collect?uris=" + encodeUris);
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
                    .constructCollectionType(List.class, ViewStatShort.class));
        } catch (IOException | InterruptedException e) {
            throw new StatisticSendingClientException("An error occurred when sending a request from a client");
        }
        log.info("Send request GET uri = {}. \n And get response: {}", uri, response);
        return result;
    }
}