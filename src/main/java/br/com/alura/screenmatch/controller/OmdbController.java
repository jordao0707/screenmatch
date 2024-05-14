package br.com.alura.screenmatch.controller;

import java.util.ArrayList;
import java.util.Scanner;

import br.com.alura.screenmatch.mapper.JsonMapper;
import br.com.alura.screenmatch.mapper.JsonMapperImpl;
import br.com.alura.screenmatch.model.dto.SerieDto;
import br.com.alura.screenmatch.model.dto.TemporadaDto;
import br.com.alura.screenmatch.service.ClientApi;

public class OmdbController {
    private Scanner inputLine = new Scanner(System.in);

    private ClientApi clientOmdb = new ClientApi();
    private JsonMapper jsonMapper = new JsonMapperImpl();

    public void exibirMenuEpisodio() {
        String serieName = inputLine.nextLine();
        Integer seasonNumber = inputLine.nextInt();
        Integer episodeNumber = inputLine.nextInt();
        var json = clientOmdb.getSerieDataOMDb(serieName, seasonNumber, episodeNumber);
        SerieDto series = jsonMapper.jsonToClass(json, SerieDto.class);
        System.out.println(series);
    }

    public void exibirMenuTemporada() {
        String serieName = inputLine.nextLine();

        var json = clientOmdb.getSerieDataOMDb(serieName);

        SerieDto serie = jsonMapper.jsonToClass(json, SerieDto.class);
        ArrayList<TemporadaDto> temporadas = new ArrayList<>();
        for (int i = 1; i < serie.totalTemporadas(); i++) {
            json = clientOmdb.getSerieDataOMDb(serieName, i);
            temporadas.add(jsonMapper.jsonToClass(json, TemporadaDto.class));
        }
        temporadas.forEach(System.out::println);
        temporadas.forEach(t -> t.episodio().forEach(e -> System.out.println(e.titulo())));

    }

}
