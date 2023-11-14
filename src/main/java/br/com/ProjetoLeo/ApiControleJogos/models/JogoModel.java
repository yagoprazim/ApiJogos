package br.com.ProjetoLeo.ApiControleJogos.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_jogo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private String desenvolvedora;

    @Column(nullable = false)
    private String plataforma;

    @Column(nullable = false)
    private BigDecimal preco;
}
