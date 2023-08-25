package com.factory.contabancaria.model.factory;

import java.math.BigDecimal;

public class CalculoSaque implements CalculoConta{
    @Override
    public BigDecimal calcular(BigDecimal valorAtualConta, BigDecimal valorFornecido){
        BigDecimal resultado = valorAtualConta.subtract(valorFornecido);
        return resultado;
    }
}
