package br.com.ProjetoLeo.ApiControleJogos.dtos.response;

import java.math.BigDecimal;

public record JogoResponseDto(

    Long id,

    String titulo,

    String genero,

    String desenvolvedora,

    String plataforma,

    BigDecimal preco,

    String descricao) {
}
