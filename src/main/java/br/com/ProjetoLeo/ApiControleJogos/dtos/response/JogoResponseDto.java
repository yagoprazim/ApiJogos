package br.com.ProjetoLeo.ApiControleJogos.dtos.response;

import org.springframework.hateoas.Links;

import java.math.BigDecimal;

public record JogoResponseDto(

    Long id,

    String titulo,

    String genero,

    String desenvolvedora,

    String plataforma,

    BigDecimal preco,

    String descricao,

    Links links) {

}
