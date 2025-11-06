package br.com.orquestrador.pedidos.controller;

import br.com.orquestrador.pedidos.exception.AcessoNaoAutorizadoException;
import br.com.orquestrador.pedidos.request.dto.PedidoRequestDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1")
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

    @PostMapping("/pedidos")
    public ResponseEntity<PedidoRequestDTO> criarPedido (@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO){

        log.info("Recebendo pedido: {}", pedidoRequestDTO);

        //mock para simular um id antes de implementar a jpa
        Long novoIdPedido = 999L;

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() //captura a url base
                        .path("{/id}")
                                .buildAndExpand(novoIdPedido) //substitui o id por 999l
                                        .toUri(); //converte para um objeto uri


        //define o status 201 e o header location, alem de devolver o body de resposta
        return ResponseEntity.created(location).body(pedidoRequestDTO);

    }

}
