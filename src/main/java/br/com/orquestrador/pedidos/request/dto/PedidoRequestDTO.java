package br.com.orquestrador.pedidos.request.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PedidoRequestDTO(

        @NotNull(message = "O campo 'clientId' não pode ser nulo.")
        @Min(value = 1, message = "O 'clientId' deve ser um número positivo.")
        Long clientId,

        @NotBlank(message = "O campo 'produto' não pode estar em branco.") // @NotBlank é para Strings
        @Size(min = 3, message = "O 'produto' deve ter no mínimo 3 caracteres.")
        String produto,

        @NotNull(message = "O campo 'quantidade' não pode ser nulo.")
        @Min(value = 1, message = "A 'quantidade' deve ser no mínimo 1.")
        Integer quantidade)
{}


