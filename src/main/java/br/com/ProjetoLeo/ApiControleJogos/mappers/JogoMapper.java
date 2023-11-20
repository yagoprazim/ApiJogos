package br.com.ProjetoLeo.ApiControleJogos.mappers;

import br.com.ProjetoLeo.ApiControleJogos.controllers.JogoController;
import br.com.ProjetoLeo.ApiControleJogos.dtos.request.JogoRequestDto;
import br.com.ProjetoLeo.ApiControleJogos.dtos.response.JogoResponseDto;
import br.com.ProjetoLeo.ApiControleJogos.models.JogoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface JogoMapper {

    JogoMapper INSTANCE = Mappers.getMapper(JogoMapper.class);

    JogoResponseDto converteParaResponseDto(JogoModel jogoModel);

    JogoModel converteParaModel(JogoRequestDto jogoRequestDto);

    @Mapping(target = "id", ignore = true)
    void atualizaModelAPartirDeDto(JogoRequestDto jogoRequestDto, @MappingTarget JogoModel jogoModel);

    default JogoResponseDto listarUmJogoComHateoas(JogoModel jogoModel) {
        jogoModel.add(linkTo(methodOn(JogoController.class).listarTodosOsJogos(Pageable.unpaged())).withRel("Lista de Jogos"));
        return converteParaResponseDto(jogoModel);
    }

    default void listarTodosOsJogosComHateoas(List<JogoModel> jogos) {
        for (JogoModel jogo : jogos){
            Long jogoId = jogo.getId();
            jogo.add(linkTo(methodOn(JogoController.class).listarUmJogo(jogoId)).withSelfRel());
        }
    }
}
