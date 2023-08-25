package com.factory.contabancaria;

import com.factory.contabancaria.controller.ContasController;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.ContasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

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
	private ContasRepository contasRepository;

	@Test
	public void testListarTodasContas() throws Exception{
		mockMvc.perform(get("/api/contas"))
				.andExpect(status().isOk());
	}

	@Test
	public void testExibeUmaContaPeloIdInexistente() throws Exception{
		Long id = 1L;
		mockMvc.perform(get("/api/contas/{id}", id))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testExibeUmaContaPeloIdExistente() throws Exception{
		Long id = 1L;
		ContasModel contasMock = new ContasModel(id,"1","1","maria",new BigDecimal("1.0"),new BigDecimal("1.0"),"deposito",new BigDecimal("1.0"));
		when(contasService.exibeContaPorId(id)).thenReturn(Optional.of(contasMock));
		mockMvc.perform(get("/api/contas/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id));
	}

	@Test
	public void testCadastrarConta() throws Exception{

	}
}
