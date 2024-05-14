package br.com.alura.screenmatch.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TemporadaDto(
        @JsonAlias("Season") Integer numero,
        @JsonAlias("Episodes") List<EpisodioDto> episodio) {
}
