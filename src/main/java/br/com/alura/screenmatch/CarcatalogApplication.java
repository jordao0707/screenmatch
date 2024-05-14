package br.com.alura.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.screenmatch.controller.OmdbController;

@SpringBootApplication
public class CarcatalogApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CarcatalogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		OmdbController omdbController = new OmdbController();
		omdbController.exibirMenuTemporada();
	}

}
