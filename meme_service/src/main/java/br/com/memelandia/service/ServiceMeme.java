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
import io.micrometer.core.instrument.*;

/**
 * @author fsdney
 */

@Service
public class ServiceMeme {

 private Logger logger = LoggerFactory.getLogger(ServiceMeme.class);
 
 @Autowired
 private MeterRegistry meterRegistry; // Registro de métricas

 @Autowired
 private RepositoriMeme repositoriMeme;

 @Autowired
 private RepositoriCategoria repositoriCategoria;

 @Autowired
 private RepositoriUsuario repositoriUsuario;

 public List<Meme> listarTodosMemes() {
	 logger.info("Recebida requisição para listar todos os memes.");
	 
	// Contador de chamadas ao método
     meterRegistry.counter("meme.listar.todas.chamadas").increment();
     
     // Medição de tempo de execução
     long startTime = System.currentTimeMillis();
     
     List<Meme> memes = repositoriMeme.findAll();
     
     long endTime = System.currentTimeMillis();
     meterRegistry.timer("meme.listar.todas.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
	 
	 if(memes.isEmpty()) {
		 logger.warn("A lista está vazia.");
         meterRegistry.counter("meme.listar.todas.vazio").increment();
	 } else {
		 logger.info("Total de memes encontrados: {}", memes.size());
     	 meterRegistry.counter("meme.listar.todas.sucesso").increment(); // Métrica para sucesso
         meterRegistry.gauge("meme.listar.todas.tamanho", memes, List::size); // Tamanho da lista retornada
	 }
	 
     return memes;
 }

 public Meme criarMeme(Meme meme) {
	 logger.info("Recebida requisição para listar todos os memes.");
	 
	 meterRegistry.counter("meme.criar.chamadas").increment();
	 
     long startTime = System.currentTimeMillis();

     // Valida categoria
     Optional<Categoria> categoria = repositoriCategoria.findById(meme.getCategoria().getId());
     if (categoria.isEmpty()) {
    	 logger.warn("Categoria não encontrada.");
         throw new RuntimeException("Categoria não encontrada.");
     }

     // Valida usuário
     Optional<Usuario> usuario = repositoriUsuario.findById(meme.getUsuario().getId());
     if (usuario.isEmpty()) {
    	 logger.warn("O usuario não encontrado.");
         throw new RuntimeException("Usuário não encontrado.");
     }

     // Configurações automáticas
     meme.setDataCadastro(new java.sql.Date(System.currentTimeMillis()));
     Meme salvo = repositoriMeme.save(meme);
     logger.info("Meme criado com sucesso: {}", salvo);
     meterRegistry.counter("meme.criar.sucesso").increment(); // Métrica para sucesso
		
		long endTime = System.currentTimeMillis();
		meterRegistry.timer("meme.criar.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
     
     return salvo;
 }

 public Optional<Meme> buscarMemePorId(Long id) {
	 logger.info("Recebida requisição para buscar meme com ID: {}", id);
	 
	// Contador de chamadas ao método
 	meterRegistry.counter("meme.buscarPorID.chamadas").increment();

 	// Medição de tempo de execução
 	long startTime = System.currentTimeMillis();
 	
 	 Optional<Meme> meme = repositoriMeme.findById(id);
     long endTime = System.currentTimeMillis();
		meterRegistry.timer("meme.buscarPorID.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
		
     if (meme.isPresent()) {
         logger.info("meme encontrado: {}", meme.get());
         meterRegistry.counter("meme.buscarPorID.sucesso").increment(); // Métrica para sucesso
     } else {
         logger.warn("meme com ID {} não encontrado.", id);
         meterRegistry.counter("meme.buscarPorID.naoEncontrada").increment(); // Métrica para falha
     }
	 
     return meme;
 }

 public void deletarMeme(Long id) {
	 logger.info("Recebida requisição para deletar meme com ID: {}", id);
	 
	 // Contador de chamadas ao método
	meterRegistry.counter("meme.deletar.chamadas").increment();
	// Medição de tempo de execução
     long startTime = System.currentTimeMillis();
     
     Optional<Meme> memeExistente = repositoriMeme.findById(id);
     if (memeExistente.isPresent()) {
     	repositoriMeme.deleteById(id);
     	logger.info("meme com ID {} deletada com sucesso.", id);
         meterRegistry.counter("meme.deletar.sucesso").increment(); // Métrica para sucesso
     } else {
     	logger.warn("meme com ID {} não encontrada.", id);
         meterRegistry.counter("meme.deletar.naoEncontrada").increment(); // Métrica para falha
     }
     
     long endTime = System.currentTimeMillis();
		meterRegistry.timer("meme.deletar.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
		
 }
     

 
 public Meme obterMemeDoDia() {
	    logger.info("Selecionando meme do dia.");
	    return Optional.ofNullable(repositoriMeme.findRandomMeme())
	                   .orElseThrow(() -> new RuntimeException("Nenhum meme encontrado no banco de dados."));
	}


}