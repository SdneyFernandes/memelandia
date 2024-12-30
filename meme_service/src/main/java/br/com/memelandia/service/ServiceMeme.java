package br.com.memelandia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.memelandia.entities.Categoria;
import br.com.memelandia.entities.Meme;
import br.com.memelandia.entities.Usuario;
import br.com.memelandia.repositori.RepositoriMeme;
import br.com.memelandia.repositori.RepositoriCategoria;
import br.com.memelandia.repositori.RepositoriUsuario;

/**
 * @author fsdney
 */

@Service
public class ServiceMeme {

 private Logger logger = LoggerFactory.getLogger(ServiceMeme.class);

 @Autowired
 private RepositoriMeme repositoriMeme;

 @Autowired
 private RepositoriCategoria repositoriCategoria;

 @Autowired
 private RepositoriUsuario repositoriUsuario;

 public List<Meme> listarTodosMemes() {
     logger.info("Listando todos os memes.");
     return repositoriMeme.findAll();
 }

 public Meme criarMeme(Meme meme) {
     logger.info("Criando novo meme: {}", meme);

     // Valida categoria
     Optional<Categoria> categoria = repositoriCategoria.findById(meme.getCategoria().getId());
     if (categoria.isEmpty()) {
         throw new RuntimeException("Categoria não encontrada.");
     }

     // Valida usuário
     Optional<Usuario> usuario = repositoriUsuario.findById(meme.getUsuario().getId());
     if (usuario.isEmpty()) {
         throw new RuntimeException("Usuário não encontrado.");
     }

     // Configurações automáticas
     meme.setDataCadastro(new java.sql.Date(System.currentTimeMillis()));
     Meme salvo = repositoriMeme.save(meme);
     logger.info("Meme criado com sucesso: {}", salvo);
     return salvo;
 }

 public Optional<Meme> buscarMemePorId(Long id) {
     logger.info("Buscando meme com ID: {}", id);
     return repositoriMeme.findById(id);
 }

 public void deletarMeme(Long id) {
     logger.info("Deletando meme com ID: {}", id);
     repositoriMeme.deleteById(id);
 }

 
 public Meme obterMemeDoDia() {
	    logger.info("Selecionando meme do dia.");
	    return Optional.ofNullable(repositoriMeme.findRandomMeme())
	                   .orElseThrow(() -> new RuntimeException("Nenhum meme encontrado no banco de dados."));
	}


}
