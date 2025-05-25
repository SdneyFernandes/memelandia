package br.com.memelandia.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.memelandia.entities.Usuario;
import br.com.memelandia.service.ServiceUsuario;
import br.com.memelandia.dto.UsuarioDTO;

/**
 * @author fsdney
 */


@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@RestController
@RequestMapping("/usuario_service")
public class ControllerUsuario {

    private final ServiceUsuario serviceUsuario;

    public ControllerUsuario(ServiceUsuario serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }

    @Operation(summary = "Listar", description = "Método para listar todos os usuários")
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = serviceUsuario.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Criar", description = "Método para criar um novo usuário")
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDTO dto) {
        return serviceUsuario.criarUsuario(dto)
                .<ResponseEntity<?>>map(novoUsuario -> ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Usuário com este nome já existe."));
    }

    @Operation(summary = "Buscar Por Id", description = "Método para buscar um usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        return serviceUsuario.buscarUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @Operation(summary = "Buscar Por Nome", description = "Método para buscar um usuário por Nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Usuario> buscarUsuarioPorNome(@PathVariable String nome) {
        return serviceUsuario.buscarUsuarioPorNome(nome)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Deletar Por Id", description = "Método para deletar um usuário por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuarioPorId(@PathVariable Long id) {
        boolean removido = serviceUsuario.deletarUsuarioPorId(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado para exclusão.");
        }
    }
    
    @Operation(summary = "Deletar Por Nome", description = "Método para deletar um usuário por Nome")
    @DeleteMapping("/nome/{nome}")
    public ResponseEntity<?> deletarUsuarioPorNome(@PathVariable String nome) {
        boolean removido = serviceUsuario.deletarUsuarioPorNome(nome);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado para exclusão.");
        }
    }
}
