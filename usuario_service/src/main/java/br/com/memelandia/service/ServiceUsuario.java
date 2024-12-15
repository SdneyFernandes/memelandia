package br.com.memelandia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.memelandia.entities.Usuario;
import br.com.memelandia.repositori.RepositoriUsuario;

/**
 * @author fsdney
 */

@Service
public class ServiceUsuario {

    private static final Logger logger = LoggerFactory.getLogger(ServiceUsuario.class);

    @Autowired
    private RepositoriUsuario repositoriUsuario;

    public List<Usuario> listarTodosUsuarios() {
        logger.info("Listando todos os usuários.");
        List<Usuario> usuarios = repositoriUsuario.findAll();
        logger.info("Total de usuários encontrados: {}", usuarios.size());
        return usuarios;
    }

    public Usuario criarUsuario(Usuario usuario) {
        logger.info("Criando novo usuário: {}", usuario);
        usuario.setDataCadastro(new java.sql.Date(System.currentTimeMillis()));
        Usuario salvo = repositoriUsuario.save(usuario);
        logger.info("Usuário criado com sucesso: {}", salvo);
        return salvo;
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        logger.info("Buscando usuário com ID: {}", id);
        Optional<Usuario> usuario = repositoriUsuario.findById(id);
        if (usuario.isPresent()) {
            logger.info("Usuário encontrado: {}", usuario.get());
        } else {
            logger.warn("Usuário com ID {} não encontrado.", id);
        }
        return usuario;
    }

    public void deletarUsuario(Long id) {
        logger.info("Deletando usuário com ID: {}", id);
        repositoriUsuario.deleteById(id);
        logger.info("Usuário com ID {} deletado com sucesso.", id);
    }
}
