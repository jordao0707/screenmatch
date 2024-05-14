package br.com.alura.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ClientApi {
    private static final String ENDERECO = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=6585022c";

    private HttpClient client = HttpClient.newHttpClient();

    public String getSerieDataOMDb(String serieName) {
        String uri = ENDERECO + serieName.replace(" ", "+") + API_KEY;
        return getDataOMDb(uri).body();
    }

    public String getSerieDataOMDb(String serieName, Integer season) {
        String uri = ENDERECO + serieName.replace(" ", "+") + "&season=" + season + API_KEY;
        return getDataOMDb(uri).body();
    }

    public String getSerieDataOMDb(String serieName, Integer season, Integer episode) {
        String uri = ENDERECO + serieName.replace(" ", "+") + "&season=" + season + "&episode=" + episode + API_KEY;
        return getDataOMDb(uri).body();
    }

    private HttpResponse<String> getDataOMDb(String uri) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).build();
        try {
            return Optional.ofNullable(client.send(request, HttpResponse.BodyHandlers.ofString())).orElseThrow();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
