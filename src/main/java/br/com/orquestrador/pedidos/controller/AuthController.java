package br.com.orquestrador.pedidos.controller;

import br.com.orquestrador.pedidos.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(){

        //usuario mock chamado fluxo-integracao
        String token = jwtUtil.generateToken("fluxo-integracao"); //geramos um token aqui

        //retorna  o token e um json simples
        Map<String,String> response = Map.of("token", token);

        //aqui devolvemos o nosso token do tipo Bearer token para setar na autorizacao de /pedidos
        return ResponseEntity.ok(response);

    }

}
