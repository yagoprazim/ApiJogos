package br.com.ProjetoLeo.ApiControleJogos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("basicScheme",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
				.info(new Info()
						.title("App Gerenciamento de Jogos")
						.description("Este aplicativo faz o gerenciamento de jogos que eu tenha interesse em comprar")
						.contact(new Contact().name("Projeto Sistemas Cliente/Servidor")
								.email("prazimyago@gmail.com")
								.url("https://github.com/yagoprazim/ApiJogos.git"))
						.version("Versão 0.0.1-SNAPSHOT"));
	}
}
