package br.com.orquestrador.pedidos.controller;

import br.com.orquestrador.pedidos.request.dto.PedidoRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

    @PostMapping("/pedidos")
    public ResponseEntity<PedidoRequestDTO> criarPedido (@RequestBody PedidoRequestDTO pedidoRequestDTO){
        log.info("Recebendo pedido: {}", pedidoRequestDTO);
        return ResponseEntity.ok(pedidoRequestDTO);

    }

}
