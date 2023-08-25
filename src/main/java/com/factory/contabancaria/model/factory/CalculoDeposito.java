package com.factory.contabancaria.model.factory;

import java.math.BigDecimal;

public class CalculoDeposito implements CalculoConta{
    @Override
    public BigDecimal calcular(BigDecimal valorAtualConta, BigDecimal valorFornecido){
        BigDecimal resultado = valorAtualConta.add(valorFornecido);
        return resultado;
    }
}
