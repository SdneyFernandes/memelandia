package br.com.memelandia.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.memelandia.entities.Categoria;
import br.com.memelandia.service.ServiceCategoria;

/**
 * @author fsdney
 */

@RestController
@RequestMapping("/categoria_service")
public class ControllerCategoria {

	private static final Logger logger = LoggerFactory.getLogger(ControllerCategoria.class);
	
	@Autowired
	private ServiceCategoria serviceCategoria;
	
	@GetMapping 
	public ResponseEntity<List<Categoria>> listarTodasCategorias() {
		logger.info("Recebida requisição para listar todas as categorias. ");
		List<Categoria> categorias = serviceCategoria.listarTodasCategorias();
		logger.info("Total de caegorias encontradas: {}", categorias.size());
		return ResponseEntity.ok(categorias);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
		logger.info("Recebida requisição para criar nova categoria: {}", categoria);
		Categoria novaCategoria = serviceCategoria.criarCategoria(categoria);
		logger.info("Catgoria criada com sucesso: {}", novaCategoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
		logger.info("Recebida requisição para buscar categoria por ID: {}", id);
		return serviceCategoria.buscarCategoriaPorID(id)
				.map(categoria -> {
					logger.info("Categoria encontrada: {}", categoria);
					return ResponseEntity.ok(categoria);
				})
				.orElseGet(() -> {
					logger.warn("Categoria com ID {} não encontrada.", id);
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				});
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
		logger.info("Recebida requisição para deletar categoria com ID: {}", id);
		serviceCategoria.deletarCategoria(id);
		logger.info("Categoria com ID {} deletada com sucesso", id);
		return ResponseEntity.noContent().build();
	}
}
