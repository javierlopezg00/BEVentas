package net.codejava.UnitTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.hamcrest.core.Is.is;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.codejava.Controlador.Imagenes_dispositivosControlador;
import net.codejava.Entidad.Imagenes_dispositivos;
import net.codejava.Entidad.Tipo_usuarios;
import net.codejava.Repositorio.RepositorioImagenes_dispositivos;

@WebMvcTest(Imagenes_dispositivosControlador.class)
public class ImagenesDispositivosControladorTest {

	@Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    RepositorioImagenes_dispositivos repositorioImagenes_dispositivos;
	
	Imagenes_dispositivos RECORD_1 = new Imagenes_dispositivos(1,1,"imagen1-1.jpg");
	Imagenes_dispositivos RECORD_2= new Imagenes_dispositivos(2,4,"imagen1-4.jpg");
	Imagenes_dispositivos RECORD_3= new Imagenes_dispositivos(3,4,"imagen2-4.jpg");
	
	@Test
	public void obtenerRegistros_success() throws Exception {
	    List<Imagenes_dispositivos> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
	    
	    
	    
	    Mockito.when(repositorioImagenes_dispositivos.findAll()).thenReturn(records);
	    
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/Imagenes_dispositivos/ObtenerTodos")
	            .contentType(APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].id_imagen", is(1)))
	            .andExpect(jsonPath("$[1].id_inventario", is(4)))
	            .andExpect(jsonPath("$[2].imagen", is("imagen2-4.jpg")));
	}
	
	@Test
	public void insertarRegistro() throws Exception {
	    
	    LinkedMultiValueMap<String,String> params = new LinkedMultiValueMap<>();
		params.add("nIdInventario", "3");
		params.add("nImagen", "imagen3-1");
		
		
	    
	    mockMvc.perform(MockMvcRequestBuilders
	            .post("/Imagenes_dispositivos/Insertar")
	            .params(params)
	            .contentType(APPLICATION_JSON))
	            .andExpect(status().isOk());
	}
	
	@Test
	public void obtenerPorImagen_success() throws Exception {
		
		Mockito.when(repositorioImagenes_dispositivos.findById(RECORD_3.getId_imagen())).thenReturn(Optional.of(RECORD_3));
		
	    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
	            .get("/Imagenes_dispositivos/Obtener")
	            .param("nIdImagen", Integer.toString(RECORD_3.getId_imagen()))
	            .contentType(APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn();
	    
	    ObjectMapper mapperGet = new ObjectMapper();
	    Imagenes_dispositivos responseGet = mapperGet.readValue(mvcResult.getResponse().getContentAsString(), Imagenes_dispositivos.class);
		
		assertThat(RECORD_3.getImagen()).isEqualTo(responseGet.getImagen());
	    
	    //System.out.println(mvcResult.getResponse().getContentAsString());
	    
	}
	
	@Test
	public void obtenerPorInventario_success() throws Exception {
		List<Imagenes_dispositivos> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
		
		Mockito.when(repositorioImagenes_dispositivos.findByidInventario(RECORD_3.getId_inventario())).thenReturn(records);
		
	    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
	            .get("/Imagenes_dispositivos/ObtenerA")
	            .param("nIdInventario", Integer.toString(RECORD_3.getId_inventario()))
	            .contentType(APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn();
	    
	    ObjectMapper mapperGet = new ObjectMapper();
	    Imagenes_dispositivos[] responseGet = mapperGet.readValue(mvcResult.getResponse().getContentAsString(), Imagenes_dispositivos[].class);
		
		assertThat(RECORD_3.getImagen()).isEqualTo(responseGet[2].getImagen());
	    
	    System.out.println(mvcResult.getResponse().getContentAsString());
	    
	}
	
	
}
