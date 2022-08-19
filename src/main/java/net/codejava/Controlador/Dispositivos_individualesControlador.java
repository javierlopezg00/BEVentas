package net.codejava.Controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.codejava.Entidad.Dispositivos_individuales;
import net.codejava.Entidad.Ventas;
import net.codejava.Repositorio.RepositorioDispositivos_individuales;

/**
 * La siguiente clase es el controlador que conforma todos los metodos que iteractuan con la tabla Dispositivos_individuales en Oracle
 * Por medio de la entidad y el repositorio correspondiente podemos interactuar con la base de datos a traves de servicios RESTfull
 * Por lo que las respuestas otorgadas seran en un formato compatible siendo mayormente Json's
 *
 */
@CrossOrigin
@RestController
@RequestMapping(path="/Dispositivos_individuales")
public class Dispositivos_individualesControlador {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RepositorioDispositivos_individuales repositorioDispositivosIndividuales;
	
	/**
	 * Constructor de la clase Dispositivos_individualesControlador
	 */
	public Dispositivos_individualesControlador(){}
	
	/**
	 * Query a la tabla DISPOSITIVOS_INDIVIDUALES para obtener todos los datos de la tabla DISPOSITIVOS_INDIVIDUALES
	 * @return objeto JSON con todos los registros
	 */
	@GetMapping("/ObtenerTodos")
	public @ResponseBody Iterable<Dispositivos_individuales> getAll(){
		return repositorioDispositivosIndividuales.findByVendido(0);
	}
	
	/**
	 * Obtener un solo registro de la tabla DISPOSITIVOS_INDIVIDUALES con la ayuda de su identificador
	 * @param nSerial Numero Serial del DISPOSITIVOS_INDIVIDUALES el cual es el identificador de la tupla
	 * @return Un DISPOSITIVOS_INDIVIDUALES segun el nSerial
	 */
	@GetMapping("/Obtener")
	public @ResponseBody Optional<Dispositivos_individuales> getOne(@RequestParam String nSerial){
		return repositorioDispositivosIndividuales.findBySerie(nSerial);
	}
	
	
	/*@PostMapping("/Registrar")
	public @ResponseBody List<Dispositivos_individuales> registrar(@RequestParam int nIdInventario, @RequestParam int nCantidad) {
	
		List<Dispositivos_individuales> nList = new ArrayList<Dispositivos_individuales>();
		
		for(int i = 0; i < nCantidad; i++) {
			Dispositivos_individuales n = new Dispositivos_individuales("0",nIdInventario);
			nList.add(repositorioDispositivosIndividuales.save(n));	
		}
		
		return nList;
	}*/
	
	/**
	 * Creacion de un nuevo DISPOSITIVOS_INDIVIDUAL en la tabla en la base de datos
	 * @param nId Identificador del DISPOSITIVOS_INDIVIDUAL
	 * @param nSerie Numero de serie del  DISPOSITIVO_INDIVIDUAL
	 * @return
	 */
	
	@PostMapping("/Insertar")
	public @ResponseBody Dispositivos_individuales insertar(
			@RequestParam int nId,
			@RequestParam String nSerie
			) {
	
		Dispositivos_individuales n = new Dispositivos_individuales(nSerie,nId,3);
		
		return repositorioDispositivosIndividuales.save(n);
	}

	/**
	 * Creación de un nuevo DISPOSITIVOS_INDIVIDUAL en la tabla en la base de datos
	 * @param nSerie Número de serie del  DISPOSITIVO_INDIVIDUAL
	 * @param nId Identificador del DISPOSITIVO_INDIVIDUAL
	 * @param nVendido Identificador del estado del DISPOSITIVO_INDIVIDUAL
	 */
	@PostMapping("/Prueba")
	@ResponseBody
	public void prueba(@RequestParam String nSerie, @RequestParam String nId, @RequestParam int nVendido) {
		String Sql = "call insertdi(?,?,?)";
		jdbcTemplate.update(Sql, nSerie, nId, nVendido);
		
	}
	
	/**
	 * Obtención de las existencias de un DISPOSITIVO_INDIVIDUAL en el inventario
	 * @param nId Identificador del DISPOSITIVO_INDIVIDUAL
	 * @return Array de dispositivos
	 */
	@GetMapping("/ObtenerCantidad")
	public @ResponseBody int getCant(@RequestParam int nId){
		 String sql = "SELECT count(*) FROM dispositivos_individuales where vendido = 0 and id_inventario = ?";
		
		 return jdbcTemplate.queryForObject(
	                sql, new Object[]{nId}, int.class);
	}
	
	/**
	 * Obtener los números de serie de los DISPOSITIVOS_INDIVIDUALES de un tipo de producto que no han sido vendidos
	 * @param nId Identificador del Inventario
	 * @return  Array de dispositivos
	 */
	@GetMapping("/ObtenerSerie")
	public @ResponseBody String getSerie(@RequestParam int nId){
		 String sql = "SELECT serie_dispositivo FROM dispositivos_individuales where vendido = 0 and id_inventario = ? FETCH NEXT 1 ROWS ONLY";
		
		 return jdbcTemplate.queryForObject(
	                sql, new Object[]{nId}, String.class);
	}
	
	/**
	 * Obtener los números de serie de los DISPOSITIVOS_INDIVIDUALES de un tipo de producto que no han sido vendidos
	 * @param nId Identificador del Inventario
	 * @return  Array de dispositivos
	 */
	
	@GetMapping("/ObtenerSeries")
	public @ResponseBody Iterable<Dispositivos_individuales> getSeries(@RequestParam int nId){
		return repositorioDispositivosIndividuales.findByVendidoAndIdInventario(0,nId);
	}
	
	/**
	 * Obtener DISPOSITIVOS_INDIVIDUALES que han sido entregados a ventas desde fábrica
	 * @return Array de dispositivos
	 */
	@GetMapping("/ObtenerEntregados")
	public @ResponseBody Iterable<Dispositivos_individuales> getAllEntregados(){
		return repositorioDispositivosIndividuales.findByVendido(3);
	}
	
	
	/**
	 * Actualizar estado de un DISPOSITIVO_INDIVIDUAL
	 * @param nSerie Serie del dispositivo
	 */
	@PostMapping("/Update")
	@ResponseBody
	public void update(@RequestParam String nSerie) {
		String Sql = "call actualizardisp(?)";
		jdbcTemplate.update(Sql, nSerie);
		
	}
	
}
