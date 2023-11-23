package br.com.ProjetoLeo.ApiControleJogos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiControleJogosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiControleJogosApplication.class, args);
	}

}
