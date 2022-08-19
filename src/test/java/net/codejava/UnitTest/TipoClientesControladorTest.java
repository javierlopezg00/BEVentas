package net.codejava.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.codejava.Controlador.Tipo_clientesControlador;
import net.codejava.Entidad.Tipo_clientes;
import net.codejava.Entidad.Tipo_usuarios;
import net.codejava.Repositorio.RepositorioTipo_clientes;

@WebMvcTest(Tipo_clientesControlador.class)
public class TipoClientesControladorTest {
	@Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    RepositorioTipo_clientes repositorio;
	
	Tipo_clientes RECORD_1 = new Tipo_clientes(1,"Cliente Individual",0);
	Tipo_clientes RECORD_2 = new Tipo_clientes(2,"Gran Cliente",10);
	Tipo_clientes RECORD_3 = new Tipo_clientes(3,"Distribuidor Mayorista",15);
	
	@Test
	public void obtenerRegistros_success() throws Exception {
		
		List<Tipo_clientes> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
	    
	    
	    
	    Mockito.when(repositorio.findAll()).thenReturn(records);
	    
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/Tipo_clientes/ObtenerTodos")
	            .contentType(APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[2].descuento", is(15)));
	}
	
	@Test
	public void obtener_success() throws Exception {
		
		Mockito.when(repositorio.findById(RECORD_3.getId())).thenReturn(Optional.of(RECORD_3));
		
	    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
	            .get("/Tipo_clientes/Obtener")
	            .param("nIdTipoCliente", Integer.toString(RECORD_3.getId()))
	            .contentType(APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn();
	    
	    ObjectMapper mapperGet = new ObjectMapper();
		Tipo_clientes responseGet = mapperGet.readValue(mvcResult.getResponse().getContentAsString(), Tipo_clientes.class);
		
		assertThat(RECORD_3.getNombre()).isEqualTo(responseGet.getNombre());
	    
	    //System.out.println(mvcResult.getResponse().getContentAsString());
	    
	}
}
