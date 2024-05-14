package br.com.alura.screenmatch.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screenmatch.mapper.JsonMapper;
import br.com.alura.screenmatch.mapper.JsonMapperImpl;
import br.com.alura.screenmatch.model.dto.SerieDto;
import br.com.alura.screenmatch.model.dto.TemporadaDto;
import br.com.alura.screenmatch.model.entity.Episodio;
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
        System.out.println("Digite o nome de uma série: ");
        String serieName = inputLine.nextLine();

        var json = clientOmdb.getSerieDataOMDb(serieName);

        SerieDto serie = jsonMapper.jsonToClass(json, SerieDto.class);
        ArrayList<TemporadaDto> temporadas = new ArrayList<>();
        for (int i = 1; i < serie.totalTemporadas(); i++) {
            json = clientOmdb.getSerieDataOMDb(serieName, i);
            temporadas.add(jsonMapper.jsonToClass(json, TemporadaDto.class));
        }

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream().map(e -> new Episodio(t.numero(), e)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        DoubleSummaryStatistics statistics = episodios.stream().filter(e -> e.getAvaliacao() > 0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: " + statistics.getAverage());
        System.out.println("Melhor episódio: " + statistics.getMax());
        System.out.println("Pior episódio: " + statistics.getMin());
    }

}

// Map<Integer, Double> avaliacaoTemporada = episodios.stream().filter(e ->
// e.getAvaliacao() > 0).peek(e -> System.out.println("nova")).collect(
// Collectors.groupingBy(Episodio::getTemporada,
// Collectors.averagingDouble(Episodio::getAvaliacao)));
// System.out.println(avaliacaoTemporada);

// System.out.println("\n TOP 5 MELHORES EPISÓDIOS: \n");
// episodios.stream().sorted(Comparator.comparing(Episodio::getAvaliacao).reversed()).limit(5)
// .forEach(e -> System.out.println("Resultado:" + e));

// System.out.println("\n Adicione um filtro pelo ano de lançamento: \n");
// Integer anoLancamento = inputLine.nextInt();
// inputLine.nextLine();
// episodios.stream().filter(
// e -> e.getDataLancamento() != null &&
// e.getDataLancamento().isAfter(LocalDate.of(anoLancamento, 1, 1)))
// .forEach(System.out::println);

// System.out.println("\n Digite o nome de um episodio \n");
// String episodeName = inputLine.nextLine();

// Optional<Episodio> firstEpisodio = episodios.stream()
// .filter(e ->
// e.getTitulo().toLowerCase().contains(episodeName.toLowerCase())).findFirst();

// if (firstEpisodio.isPresent()) {
// System.out.println(firstEpisodio.get());
// } else {
// System.out.println("Episodio não encontrado");
// }