package br.com.orquestrador.pedidos.request.dto;

public record PedidoRequestDTO(Long clientId,
                               String produto,
                               Integer quantidade)
{}


