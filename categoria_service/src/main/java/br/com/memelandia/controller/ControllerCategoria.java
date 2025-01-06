package br.com.memelandia.controller;

import java.util.List;
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
	
	@Autowired
	private ServiceCategoria serviceCategoria;
	
	@GetMapping 
	public ResponseEntity<List<Categoria>> listarTodasCategorias() {
		List<Categoria> categorias = serviceCategoria.listarTodasCategorias();
		return ResponseEntity.ok(categorias);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
		Categoria novaCategoria = serviceCategoria.criarCategoria(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
        return serviceCategoria.buscarCategoriaPorID(id)
                .map(categoria -> ResponseEntity.ok(categoria))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        serviceCategoria.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

}