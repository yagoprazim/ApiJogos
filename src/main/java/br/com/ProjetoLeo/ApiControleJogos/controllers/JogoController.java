package br.com.ProjetoLeo.ApiControleJogos.controllers;

import br.com.ProjetoLeo.ApiControleJogos.dtos.request.JogoRequestDto;
import br.com.ProjetoLeo.ApiControleJogos.dtos.response.JogoResponseDto;
import br.com.ProjetoLeo.ApiControleJogos.models.JogoModel;
import br.com.ProjetoLeo.ApiControleJogos.services.JogoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jogos")
public class JogoController {

    private final JogoService jogoService;

    @GetMapping
    public ResponseEntity<Page<JogoResponseDto>> listarTodosOsJogos(@PageableDefault @ParameterObject Pageable paginacao) {
        Page<JogoResponseDto> jogos = jogoService.listarTodosOsJogos(paginacao);

        return ResponseEntity.ok(jogos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogoResponseDto> listarUmJogo(@PathVariable Long id) {
        JogoResponseDto jogo = jogoService.listarUmJogo(id);

        return ResponseEntity.ok(jogo);
    }

    @PostMapping
    public ResponseEntity<JogoResponseDto> registrarJogo(@RequestBody @Valid JogoRequestDto jogoRequestDto) {
        JogoResponseDto jogo = jogoService.registrarJogo(jogoRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(jogo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JogoResponseDto> atualizarJogo(@PathVariable Long id,
                                                         @RequestBody @Valid JogoRequestDto jogoRequestDto) {
        JogoResponseDto jogo = jogoService.atualizarJogo(id, jogoRequestDto);

        return ResponseEntity.ok(jogo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogo(@PathVariable Long id) {
        jogoService.deletarJogo(id);

        return ResponseEntity.noContent().build();
    }
}
