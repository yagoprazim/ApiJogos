package br.com.ProjetoLeo.ApiControleJogos.exceptions;

public class JogoAlreadyExistsException extends RuntimeException{

    public JogoAlreadyExistsException(String mensagem) {
        super(mensagem);
    }
}
