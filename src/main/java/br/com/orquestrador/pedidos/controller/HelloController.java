package br.com.orquestrador.pedidos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello (){
        return "hello";
    }

    //Testando filtro de segurança Spring Security
    @GetMapping("/users")
    public List<String> users() {
        return List.of("João - CPF: 123.456.789-00", "Maria - CPF: 987.654.321-00");
    }

}
