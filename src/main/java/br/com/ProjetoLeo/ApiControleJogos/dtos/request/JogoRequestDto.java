package br.com.ProjetoLeo.ApiControleJogos.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record JogoRequestDto(

        @NotBlank(message = "Informe o título do jogo.")
        String titulo,

        @NotBlank(message = "Informe o gênero do jogo.")
        String genero,

        @NotBlank(message = "Informe a empresa desenvolvedora do jogo.")
        String desenvolvedora,

        @NotBlank(message = "Informe a plataforma para compra do jogo.")
        String plataforma,

        @NotNull(message = "Informe o preço do jogo.")
        BigDecimal preco
) { }
