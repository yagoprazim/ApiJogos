package br.com.ProjetoLeo.ApiControleJogos.exceptions;

import br.com.ProjetoLeo.ApiControleJogos.dtos.ErrosListaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrosListaDto>> trataErro400(MethodArgumentNotValidException exception) {
        var errosListaDto = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errosListaDto.stream()
                .map(ErrosListaDto::new)
                .toList());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> trataErro404(ResourceNotFoundException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("erro", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(JogoAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> trataErroJogoExistente(JogoAlreadyExistsException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("erro", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
