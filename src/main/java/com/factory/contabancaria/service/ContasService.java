package com.factory.contabancaria.service;

import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContasService {
    @Autowired
    ContasRepository contasRepository;

    //métodos
    public List<ContasModel> listarContas(){
        return contasRepository.findAll();
    }

    public Optional<ContasModel> exibeContaPorId(Long id){
        return contasRepository.findById(id);
    }

    public ContasModel cadastrar(ContasModel contasModel, ContaFactory contaFactory){
        BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
        contasModel.setValorFinal(resultado);
        contasModel.setValorAtualConta(contasModel.getValorFinal());
        return contasRepository.save(contasModel);
    }

    public ContasModel alterar(Long id, ContasModel contasModel) {

        ContasModel conta = exibeContaPorId(id).get();

        if (contasModel.getNumConta() != null) {
            conta.setNumConta(contasModel.getNumConta());
        }
        if (contasModel.getAgencia() != null) {
            conta.setAgencia(contasModel.getAgencia());
        }

        return contasRepository.save(conta);
    }

    public void deletarConta(Long id){
        contasRepository.deleteById(id);
    }

    //métodos Beth
    //Selecionar conta pelo nome do usuário
    public Optional<ContasModel> exibeContaPorNome(String nome){
        return contasRepository.findByNome(nome);
    }

    //Faça uma alteração específica
    public ContasModel alterarNome(Long id, ContasModel contasModel){
        ContasModel contas = exibeContaPorId(id).get();

        if (contasModel.getNomeDoUsuario() != null) {
            contas.setNomeDoUsuario(contasModel.getNomeDoUsuario());
        }
        return contasRepository.save(contas);
    }

}
