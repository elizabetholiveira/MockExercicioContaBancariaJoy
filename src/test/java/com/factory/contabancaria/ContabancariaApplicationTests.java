package com.factory.contabancaria;

import com.factory.contabancaria.controller.ContasController;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.ContasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(controllers = ContasController.class)
class ContabancariaApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ContasService contasService;

	@MockBean
	private ContaFactory contaFactory;

	@MockBean
	private ContasRepository contasRepository;

	//Teste se está conseguindo listar todas as contas
	@Test
	public void testListarTodasContas() throws Exception{
		mockMvc.perform(get("/api/contas"))
				.andExpect(status().isOk());
	}

	//Teste se está avisando que uma conta procurada pelo ID não existe
	@Test
	public void testExibeUmaContaPeloIdInexistente() throws Exception{
		Long id = 1L;
		mockMvc.perform(get("/api/contas/{id}", id))
				.andExpect(status().isNotFound());
	}

	//Teste se uma procurada pelo ID existe
	@Test
	public void testExibeUmaContaPeloIdExistente() throws Exception{
		Long id = 1L;
		ContasModel contasMock = new ContasModel(id,"1","1","maria",new BigDecimal("1.0"),new BigDecimal("1.0"),"deposito",new BigDecimal("1.0"));
		when(contasService.exibeContaPorId(id)).thenReturn(Optional.of(contasMock));
		mockMvc.perform(get("/api/contas/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id));
	}

	//Teste se a conta está sendo cadastrada direito
	@Test
	public void testCadastrarConta() throws Exception{
		ContasModel contaTeste = new ContasModel(1L,"1","1","maria",new BigDecimal("1.0"),new BigDecimal("1.0"),"deposito",new BigDecimal("1.0"));
		when(contasService.cadastrar(contaTeste,contaFactory))
				.thenReturn(new ContasModel(1L,"1","1","maria",new BigDecimal("1.0"),new BigDecimal("1.0"),"deposito",new BigDecimal("1.0")));
		mockMvc.perform(MockMvcRequestBuilders.post("/api/contas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(contaTeste)))
				.andExpect(status().isCreated());
	}

	//Teste se a conta está sendo atualizada direito
	@Test
	public void testAtualizarConta() throws Exception{
		Long id = 1L;
		ContasModel updateTeste = new ContasModel(id,"1","1","maria",new BigDecimal("1.0"),new BigDecimal("1.0"),"deposito",new BigDecimal("1.0"));
		when(contasService.alterar(id,updateTeste))
				.thenReturn(new ContasModel(id,"1","1","maria",new BigDecimal("1.0"),new BigDecimal("1.0"),"deposito",new BigDecimal("1.0")));
		mockMvc.perform(put("/api/contas/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updateTeste)))
				.andExpect(status().isOk());

	}
}
