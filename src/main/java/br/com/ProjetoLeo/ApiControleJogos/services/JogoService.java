package br.com.ProjetoLeo.ApiControleJogos.services;

import br.com.ProjetoLeo.ApiControleJogos.dtos.request.JogoRequestDto;
import br.com.ProjetoLeo.ApiControleJogos.dtos.response.JogoResponseDto;
import br.com.ProjetoLeo.ApiControleJogos.exceptions.JogoAlreadyExistsException;
import br.com.ProjetoLeo.ApiControleJogos.exceptions.ResourceNotFoundException;
import br.com.ProjetoLeo.ApiControleJogos.mappers.JogoMapper;
import br.com.ProjetoLeo.ApiControleJogos.models.JogoModel;
import br.com.ProjetoLeo.ApiControleJogos.repositories.JogoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JogoService {

    private final JogoRepository jogoRepository;

    public Page<JogoResponseDto> listarTodosOsJogos(Pageable paginacao) {
        Page<JogoModel> jogos = jogoRepository.findAll(paginacao);
        JogoMapper.INSTANCE.linkParaListarUmJogoLoop(jogos.getContent());

        return jogos.map(JogoMapper.INSTANCE::converteParaResponseDto);
    }


    public JogoResponseDto listarUmJogo(Long id) {
        JogoModel jogoModel = jogoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));

        JogoMapper.INSTANCE.linkParaListarTodosOsJogos(jogoModel);

        return JogoMapper.INSTANCE.converteParaResponseDto(jogoModel);
    }

    public JogoResponseDto registrarJogo(JogoRequestDto jogoRequestDto){
        if (jogoRepository.existsByTitulo(jogoRequestDto.titulo())) {
            throw new JogoAlreadyExistsException("Já existe um jogo registrado com esse nome.");
        }

        JogoModel jogoModel = JogoMapper.INSTANCE.converteParaModel(jogoRequestDto);
        JogoModel jogoRegistrado = jogoRepository.save(jogoModel);
        JogoMapper.INSTANCE.linkParaListarUmJogo(jogoRegistrado);

        return JogoMapper.INSTANCE.converteParaResponseDto(jogoRegistrado);
    }

    public JogoResponseDto atualizarJogo(Long id, JogoRequestDto jogoRequestDto){
        JogoModel jogoModel = jogoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));

        if (!jogoModel.getTitulo().equals(jogoRequestDto.titulo()) &&
                jogoRepository.existsByTitulo(jogoRequestDto.titulo())) {
            throw new JogoAlreadyExistsException("Já existe um jogo registrado com esse nome.");
        }

        JogoMapper.INSTANCE.atualizaModelAPartirDeDto(jogoRequestDto, jogoModel);
        JogoModel jogoAtualizado = jogoRepository.save(jogoModel);
        JogoMapper.INSTANCE.linkParaListarUmJogo(jogoAtualizado);

        return JogoMapper.INSTANCE.converteParaResponseDto(jogoAtualizado);
    }

    public void deletarJogo(Long id){
        JogoModel jogoModel = jogoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado."));

        jogoRepository.deleteById(id);
    }

}
