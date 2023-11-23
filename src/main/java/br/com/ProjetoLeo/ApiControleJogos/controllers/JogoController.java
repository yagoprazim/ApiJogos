package br.com.ProjetoLeo.ApiControleJogos.controllers;

import br.com.ProjetoLeo.ApiControleJogos.dtos.request.JogoRequestDto;
import br.com.ProjetoLeo.ApiControleJogos.dtos.response.JogoResponseDto;
import br.com.ProjetoLeo.ApiControleJogos.services.JogoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jogos")
public class JogoController {

    private final JogoService jogoService;

    @Cacheable("cacheListarTodosOsJogos")
    @Operation(summary = "Endpoint que lista todos os jogos, com paginação.")
    @GetMapping
    public ResponseEntity<Page<JogoResponseDto>> listarTodosOsJogos(@PageableDefault @ParameterObject Pageable paginacao) {
        Page<JogoResponseDto> jogos = jogoService.listarTodosOsJogos(paginacao);

        return ResponseEntity.ok(jogos);
    }

    @Cacheable(value = "cacheListarUmJogo", key = "#id")
    @Operation(summary = "Endpoint que lista um jogo específico, a partir do ID fornecido.")
    @GetMapping("/{id}")
    public ResponseEntity<JogoResponseDto> listarUmJogo(@PathVariable Long id) {
        JogoResponseDto jogo = jogoService.listarUmJogo(id);

        return ResponseEntity.ok(jogo);
    }

    @CacheEvict(value = {"cacheListarTodosOsJogos", "cacheListarUmJogo"}, allEntries = true)
    @Operation(summary = "Endpoint que cria um jogo.")
    @PostMapping
    public ResponseEntity<JogoResponseDto> registrarJogo(@RequestBody @Valid JogoRequestDto jogoRequestDto) {
        JogoResponseDto jogo = jogoService.registrarJogo(jogoRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(jogo);
    }

    @CacheEvict(value = {"cacheListarTodosOsJogos", "cacheListarUmJogo"}, allEntries = true)
    @Operation(summary = "Endpoint que modifica as informações de um jogo já existente, a partir do ID fornecido.")
    @PutMapping("/{id}")
    public ResponseEntity<JogoResponseDto> atualizarJogo(@PathVariable Long id,
                                                         @RequestBody @Valid JogoRequestDto jogoRequestDto) {
        JogoResponseDto jogo = jogoService.atualizarJogo(id, jogoRequestDto);

        return ResponseEntity.ok(jogo);
    }

    @CacheEvict(value = {"cacheListarTodosOsJogos", "cacheListarUmJogo"}, allEntries = true)
    @Operation(summary = "Endpoint que deleta um jogo, a partir do ID fornecido.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogo(@PathVariable Long id) {
        jogoService.deletarJogo(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Result", "Jogo deletado com sucesso.");

        return ResponseEntity.noContent().headers(headers).build();
    }
}
