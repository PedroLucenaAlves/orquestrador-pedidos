package br.com.orquestrador.pedidos.controller;

import br.com.orquestrador.pedidos.exception.AcessoNaoAutorizadoException;
import br.com.orquestrador.pedidos.request.dto.PedidoRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

    @PostMapping("/pedidos")
    public ResponseEntity<PedidoRequestDTO> criarPedido (@RequestBody PedidoRequestDTO pedidoRequestDTO){

        //captura o nome do usuario vindo do token jwt
        String usuarioAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();

        // nosso usuario mockado "fluxo-integracao" so pode criar pedidos para o cliente 123
        if (usuarioAutenticado.equals("fluxo-integracao") && !pedidoRequestDTO.clientId().equals(123L)){

            throw new AcessoNaoAutorizadoException("Usuário 'fluxo-integracao' não pode criar pedidos para este cliente.");

        }

        log.info("Recebendo pedido: {}", pedidoRequestDTO);
        return ResponseEntity.ok(pedidoRequestDTO);

    }

}
