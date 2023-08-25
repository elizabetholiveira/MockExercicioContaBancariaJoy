package com.factory.contabancaria.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ContasDTO {

    private String numConta;

    private String agencia;

    private String nomeDoUsuario;

    private BigDecimal valorAtualConta;

    private BigDecimal valorFornecido;

    private String tipoServico;

}
