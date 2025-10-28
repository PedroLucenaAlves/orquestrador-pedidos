package br.com.orquestrador.pedidos;

import br.com.orquestrador.pedidos.security.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoOrquestradorDePedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoOrquestradorDePedidosApplication.class, args);

        //testando nosso token da classe JwtUtil
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("admin");
        System.out.println("Token: " + token);
        System.out.println("Válido? " + jwtUtil.validateToken(token));

	}

}
