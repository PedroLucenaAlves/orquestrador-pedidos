package br.com.orquestrador.pedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Diz ao Spring para retornar 401 (Unauthorized) quando esta exceção for disparada
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AcessoNaoAutorizadoException extends RuntimeException {

    // Um construtor simples que aceita uma mensagem de erro
    public AcessoNaoAutorizadoException(String mensagem) {
        super(mensagem);
    }

}
