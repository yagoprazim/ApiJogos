package br.com.ProjetoLeo.ApiControleJogos.services;

import br.com.ProjetoLeo.ApiControleJogos.dtos.request.JogoRequestDto;
import br.com.ProjetoLeo.ApiControleJogos.dtos.response.JogoResponseDto;
import br.com.ProjetoLeo.ApiControleJogos.exceptions.ResourceNotFoundException;
import br.com.ProjetoLeo.ApiControleJogos.mappers.JogoMapper;
import br.com.ProjetoLeo.ApiControleJogos.models.JogoModel;
import br.com.ProjetoLeo.ApiControleJogos.repositories.JogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class JogoService {

    private final JogoRepository jogoRepository;

    public Page<JogoResponseDto> listarTodosOsJogos(Pageable paginacao){
        return jogoRepository.findAll(paginacao)
                .map(JogoMapper.INSTANCE::converteParaResponseDto);
    }

    public JogoResponseDto listarUmJogo(Long id){
        JogoModel jogoModel = jogoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));

        return JogoMapper.INSTANCE.converteParaResponseDto(jogoModel);
    }

    public JogoResponseDto registrarJogo(JogoRequestDto jogoRequestDto){
        if (jogoRepository.existsByTitulo(jogoRequestDto.titulo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um jogo registrado com esse nome.");
        }

        JogoModel jogoModel = JogoMapper.INSTANCE.converteParaModel(jogoRequestDto);
        JogoModel jogoRegistrado = jogoRepository.save(jogoModel);

        return JogoMapper.INSTANCE.converteParaResponseDto(jogoRegistrado);
    }

    public JogoResponseDto atualizarJogo(Long id, JogoRequestDto jogoRequestDto){
        if (jogoRepository.existsByTitulo(jogoRequestDto.titulo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um jogo registrado com esse nome.");
        }

        JogoModel jogoModel = jogoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));

        JogoMapper.INSTANCE.atualizaModelAPartirDeDto(jogoRequestDto, jogoModel);
        JogoModel jogoAtualizado = jogoRepository.save(jogoModel);

        return JogoMapper.INSTANCE.converteParaResponseDto(jogoAtualizado);
    }

    public void deletarJogo(Long id){
        JogoModel jogoModel = jogoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));

        jogoRepository.deleteById(id);
    }

}
