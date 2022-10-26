package ru.practicum.statistic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.client.StatisticClient;
import ru.practicum.client.ViewStat;
import ru.practicum.exception.StatisticClientException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class StatisticClientTest {
    @InjectMocks
    private StatisticClient statisticClient;
    @Spy
    private ObjectMapper objectMapper;
    private final WireMockServer wireMockServer = new WireMockServer(9090);

    @BeforeEach
    public void shouldRun() {
        wireMockServer.start();
    }

    @AfterEach
    public void shouldClose() {
        wireMockServer.stop();
    }

    // getStatistic
    @Test
    public void shouldGetStatisticByUri() throws JsonProcessingException {
        String start = LocalDateTime.now().minusDays(5).toString();
        String end = LocalDateTime.now().toString();
        String[] uris = {"/event/367"};
        Boolean unique = false;

        String encodeStart = URLEncoder.encode(start, StandardCharsets.UTF_8);
        String encodeEnd = URLEncoder.encode(end, StandardCharsets.UTF_8);
        String encodeUris = URLEncoder.encode(convertArrayToStringForUrl(uris), StandardCharsets.UTF_8);
        String encodeUnique = URLEncoder.encode(String.valueOf(unique), StandardCharsets.UTF_8);

        ViewStat viewStat1 = ViewStat.builder().app("ewm-main-service").uri("/event/367").hits(15).build();
        List<ViewStat> listViewStat = List.of(viewStat1);

        configureFor("localhost", 9090);
        wireMockServer.stubFor(get(urlEqualTo("/stats?start=" + encodeStart + "&end=" + encodeEnd +
                "&uris=" + encodeUris + "&unique=" + encodeUnique))
                .willReturn(aResponse().withBody(objectMapper.writeValueAsString(listViewStat))));

        List<ViewStat> result = statisticClient.getStatistic(start, end, uris, unique);
        assertEquals(listViewStat, result);
        verify(getRequestedFor(urlEqualTo("/stats?start=" + encodeStart + "&end=" + encodeEnd +
                "&uris=" + encodeUris + "&unique=" + encodeUnique)));
    }

    private String convertArrayToStringForUrl(String[] uris) {
        List<String> fields = new ArrayList<>(List.of(uris));
        return fields.stream().map(String::valueOf).collect(Collectors.joining(",", "", ""));
    }

    // getViewsByUri
    @Test
    public void shouldGetViews() {
        configureFor("localhost", 9090);
        wireMockServer.stubFor(get(urlEqualTo("/events"))
                .willReturn(aResponse().withBody("100")));

        Integer result = statisticClient.getViewsByUri(1);
        assertEquals(100, result);
        verify(getRequestedFor(urlEqualTo("/events")));
    }

    @Test
    public void should() {
        configureFor("localhost", 9090);
        wireMockServer.stubFor(get(urlEqualTo("/eventts"))
                .willReturn(aResponse().withBody("100")));

        RuntimeException re = Assertions.assertThrows(StatisticClientException.class,
                () -> statisticClient.getViewsByUri(1));
        assertEquals("An error occurred when sending a request from a client", re.getMessage());
    }
}