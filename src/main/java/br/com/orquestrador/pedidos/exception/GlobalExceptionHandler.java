package br.com.orquestrador.pedidos.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//Comunica ao Spring que esta classe vai "ouvir" excecoes de todos os @RestControllers
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AcessoNaoAutorizadoException.class)
    public ResponseEntity<Map<String, Object>> handleAcessoNaoAutorizado(
            AcessoNaoAutorizadoException exception,  HttpServletRequest request){

        //MOntando o body de erro
        Map<String,Object> body = new HashMap<>();
        body.put("status", HttpStatus.UNAUTHORIZED.value()); // 401
        body.put("erro", "Não Autorizado (Lógica de Negócio)");
        body.put("mensagem", exception.getMessage()); // Pega a mensagem que passamos
        body.put("caminho", request.getRequestURI());

        //Response json personalizada
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }



}
