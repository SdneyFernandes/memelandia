package br.com.memelandia.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.memelandia.entities.Meme;
import br.com.memelandia.service.ServiceMeme;

/**
 * @author fsdney
 */

@Tag(name = "Memes", description = "Endpoints para gerenciamento de memes")
@RestController
@RequestMapping("/meme_service")
public class ControllerMeme {

    private final ServiceMeme serviceMeme;

    public ControllerMeme(ServiceMeme serviceMeme) {
        this.serviceMeme = serviceMeme;
    }

    @Operation(summary = "Listar", description = "Listar todos os memes")
    @GetMapping
    public ResponseEntity<List<Meme>> listarTodosMemes() {
        return ResponseEntity.ok(serviceMeme.listarTodosMemes());
    }

    @Operation(summary = "Criar", description = "Criar um novo meme")
    @PostMapping
    public ResponseEntity<Meme> criarMeme(@RequestBody Meme meme) {
        Meme novoMeme = serviceMeme.criarMeme(meme).orElseThrow(() -> new RuntimeException("Erro ao criar meme."));
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMeme);
    }

    @Operation(summary = "Buscar Por Id", description = "Buscar um meme pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Meme> buscarMemePorId(@PathVariable Long id) {
        return serviceMeme.buscarMemePorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @Operation(summary = "Buscar Por Nome", description = "Buscar um meme pelo ID")
    @GetMapping("/name/{name}")
    public ResponseEntity<Meme> buscarMemePorNome(@PathVariable String name) {
        return serviceMeme.buscarMemePorNome(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Deletar Por Id", description = "Deletar um meme pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMemePorId(@PathVariable Long id) {
        boolean removido = serviceMeme.deletarMemePorId(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Meme não encontrado para exclusão.");
        }
    }
    
    @Operation(summary = "Deletar Por Nome", description = "Deletar um meme pelo Nome")
    @DeleteMapping("/name/{name}")
    public ResponseEntity<?> deletarMemePorNome(@PathVariable String name) {
        boolean removido = serviceMeme.deletarMemePorNome(name);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Meme não encontrado para exclusão.");
        }
    }

    @Operation(summary = "Meme do Dia", description = "Selecionar um meme aleatório do banco de dados")
    @GetMapping("/meme-do-dia")
    public ResponseEntity<Meme> obterMemeDoDia() {
        Meme meme = serviceMeme.obterMemeDoDia();
        return ResponseEntity.ok(meme);
    }
}
