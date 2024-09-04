package com.uniamerica.gestor.app.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ClienteConsumoDTO {
    private String nomeCliente;
    private BigDecimal totalGasto;

    public ClienteConsumoDTO(String nomeCliente, BigDecimal totalGasto) {
        this.nomeCliente = nomeCliente;
        this.totalGasto = totalGasto;
    }

}