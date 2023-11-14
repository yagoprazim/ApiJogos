package br.com.ProjetoLeo.ApiControleJogos.mappers;

import br.com.ProjetoLeo.ApiControleJogos.dtos.request.JogoRequestDto;
import br.com.ProjetoLeo.ApiControleJogos.dtos.response.JogoResponseDto;
import br.com.ProjetoLeo.ApiControleJogos.models.JogoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface JogoMapper {

    JogoMapper INSTANCE = Mappers.getMapper(JogoMapper.class);

    JogoResponseDto converteParaResponseDto(JogoModel jogoModel);

    JogoModel converteParaModel(JogoRequestDto jogoRequestDto);

    @Mapping(target = "id", ignore = true)
    void atualizaModelAPartirDeDto(JogoRequestDto jogoRequestDto, @MappingTarget JogoModel jogoModel);
}
