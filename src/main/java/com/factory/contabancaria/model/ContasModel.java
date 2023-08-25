package com.factory.contabancaria.model;

import com.factory.contabancaria.dto.ContasDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Table(name = "TB_CONTAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContasModel {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 6, nullable = false)
    private String numConta;
    @Column(length = 4, nullable = false)
    private String agencia;
    @Column(nullable = false)
    private String nomeDoUsuario;
    @Column(nullable = false)
    private BigDecimal valorAtualConta;
    @Column(nullable = false)
    private BigDecimal ValorFornecido;
    @Column(length = 20, nullable = false)
    private String tipoServico;
    private BigDecimal valorFinal;

    //Conversores
    public ContasDTO toDTOReqGet(){
        ContasDTO dto = new ContasDTO();
        dto.setNumConta(getNumConta());
        dto.setAgencia(getAgencia());
        dto.setNomeDoUsuario(getNomeDoUsuario());
        dto.setValorAtualConta(getValorAtualConta());
        return dto;
    }

    public ContasDTO toDTOReqPost(){
        ContasDTO dto = new ContasDTO();
        dto.setNomeDoUsuario(getNomeDoUsuario());
        dto.setValorAtualConta(getValorAtualConta());
        dto.setValorFornecido(getValorFornecido());
        dto.setTipoServico(getTipoServico());
        return dto;
    }

    public ContasDTO toDTOnome(){
        ContasDTO dto = new ContasDTO();
        dto.setNomeDoUsuario(getNomeDoUsuario());
        return dto;
    }

}
