package com.factory.contabancaria.controller;

import com.factory.contabancaria.dto.ContasDTO;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.ContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contas")
public class ContasController {

    @Autowired
    ContasService contasService;

    @Autowired
    ContasRepository contasRepository;

    //requisições
    //GET - Pegar as informações do nosso banco
    @GetMapping
    public ResponseEntity<List<ContasModel>> listarTodasContas(){
        return ResponseEntity.ok(contasService.listarContas());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> exibeUmaContaPeloId(@PathVariable Long id){
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);
        if (contaOpcional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }
        return ResponseEntity.ok(contaOpcional.get());
    }

    //POST - Cria uma nova conta dentro do banco
    @PostMapping
    public ResponseEntity<?> cadastrarConta(@RequestBody ContasModel contasModel, ContaFactory contaFactory){
        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);
        ContasDTO contasDTO = new ContasDTO();
        contasDTO.setNomeDoUsuario(novaConta.getNomeDoUsuario());
        contasDTO.setValorAtualConta(novaConta.getValorAtualConta());
        contasDTO.setValorFornecido(novaConta.getValorFornecido());
        contasDTO.setTipoServico(novaConta.getTipoServico());
        return new ResponseEntity<>(contasDTO, HttpStatus.CREATED);
    }

    //PUT - Alterar uma conta já existente dentro do banco
    @PutMapping(path = "/{id}")
    public ContasModel atualizarConta(@PathVariable Long id, @RequestBody ContasModel contasModel){
        return contasService.alterar(id, contasModel);
    }

    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id){
        contasService.deletarConta(id);
    }

    //Requisições Beth
    //GET - Seleciona conta pelo nome
    @GetMapping(path = "/nome/{nome}")
    public ContasDTO findByNome(@PathVariable String nome){
        contasRepository.findByNome(nome);
        ContasModel contasModel = new ContasModel();
        return contasModel.toDTOReqGet();
    }

    //PUT - Faça uma alteração específica
    @PutMapping(path = "/nome/{id}")
    public ContasDTO alterarNome(@PathVariable Long id, @RequestBody ContasModel contasModel){
        ContasModel contas = contasService.alterarNome(id, contasModel);
        return contas.toDTOnome();
    }
}
