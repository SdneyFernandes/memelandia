package br.com.memelandia.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.com.memelandia.entities.Categoria;
import br.com.memelandia.service.ServiceCategoria;

/**
 * @author fsdney
 */


@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias")
@RestController
@RequestMapping("/categoria_service")
public class ControllerCategoria {

    private final ServiceCategoria serviceCategoria;

    public ControllerCategoria(ServiceCategoria serviceCategoria) {
        this.serviceCategoria = serviceCategoria;
    }

    @Operation(summary = "Listar", description = "Método para listar todas as categorias")
    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodasCategorias() {
        List<Categoria> categorias = serviceCategoria.listarTodasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Criar", description = "Método para criar uma nova categoria")
    @PostMapping
    public ResponseEntity<?> criarCategoria(@RequestBody Categoria categoria) {
        return serviceCategoria.criarCategoria(categoria)
                .<ResponseEntity<?>>map(novaCategoria -> ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Categoria com este nome já existe."));
    }

    @Operation(summary = "Buscar Por Id ", description = "Método para buscar uma categoria pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
        return serviceCategoria.buscarCategoriaPorID(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @Operation(summary = "Buscar Por Nome ", description = "Método para buscar uma categoria pelo Nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Categoria> buscarCategoriaPorNome(@PathVariable String nome) {
        return serviceCategoria.buscarCategoriaPorNome(nome)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @Operation(summary = "Deletar Por Id", description = "Método para deletar uma categoria pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCategoriaPorId(@PathVariable Long id) {
        boolean removida = serviceCategoria.deletarCategoriaPorId(id);
        if (removida) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoria não encontrada para exclusão.");
        }
    }
    
    @Operation(summary = "Deletar Por Nome", description = "Método para deletar uma categoria pelo Nome")
    @DeleteMapping("/nome/{nome}")
    public ResponseEntity<?> deletarCategoriaPorNome(@PathVariable String nome) {
        boolean removida = serviceCategoria.deletarCategoriaPorNome(nome);
        if (removida) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoria não encontrada para exclusão.");
        }
    }
}