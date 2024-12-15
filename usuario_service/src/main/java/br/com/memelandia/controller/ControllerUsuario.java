package br.com.memelandia.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.memelandia.entities.Usuario;
import br.com.memelandia.service.ServiceUsuario;

/**
 * @author fsdney
 */

@RestController
@RequestMapping("/usuario_service")
public class ControllerUsuario {

    private static final Logger logger = LoggerFactory.getLogger(ControllerUsuario.class);

    @Autowired
    private ServiceUsuario serviceUsuario;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        logger.info("Recebida requisição para listar todos os usuários.");
        List<Usuario> usuarios = serviceUsuario.listarTodosUsuarios();
        logger.info("Total de usuários encontrados: {}", usuarios.size());
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        logger.info("Recebida requisição para criar novo usuário: {}", usuario);
        Usuario novoUsuario = serviceUsuario.criarUsuario(usuario);
        logger.info("Usuário criado com sucesso: {}", novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        logger.info("Recebida requisição para buscar usuário por ID: {}", id);
        return serviceUsuario.buscarUsuarioPorId(id)
                .map(usuario -> {
                    logger.info("Usuário encontrado: {}", usuario);
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> {
                    logger.warn("Usuário com ID {} não encontrado.", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        logger.info("Recebida requisição para deletar usuário com ID: {}", id);
        serviceUsuario.deletarUsuario(id);
        logger.info("Usuário com ID {} deletado com sucesso.", id);
        return ResponseEntity.noContent().build();
    }
}
