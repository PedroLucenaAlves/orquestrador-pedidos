package br.com.orquestrador.pedidos.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    //exception personalizada para nossa validacao do @Valid do DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Cria um mapa para guardar os erros de "campo: mensagem"
        Map<String, String> fieldErrors = new HashMap<>();

        // Itera sobre todos os erros de campo encontrados
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        // Monta o corpo (body) da resposta final e intuitiva
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value()); // 400
        body.put("erro", "Erro de Validação");
        body.put("camposInvalidos", fieldErrors); // O mapa de erros
        body.put("caminho", request.getRequestURI());

        // Retorna um 400 Bad Request
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }



}
