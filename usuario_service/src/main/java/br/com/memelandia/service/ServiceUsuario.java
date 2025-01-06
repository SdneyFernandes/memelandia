package br.com.memelandia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.memelandia.entities.Usuario;
import br.com.memelandia.repositori.RepositoriUsuario;
import io.micrometer.core.instrument.*;

import org.springframework.cloud.stream.function.StreamBridge;


/**
 * @author fsdney
 */

@Service
public class ServiceUsuario {

    private static final Logger logger = LoggerFactory.getLogger(ServiceUsuario.class);
    
    private final StreamBridge streamBridge;
    
    @Autowired
    private RepositoriUsuario repositoriUsuario;
    
    @Autowired
    private MeterRegistry meterRegistry; // Registro de métricas
    

    public ServiceUsuario(StreamBridge streamBridge, RepositoriUsuario repositoriUsuario) {
        this.streamBridge = streamBridge;
        this.repositoriUsuario = repositoriUsuario;
    }


    public List<Usuario> listarTodosUsuarios() {
    	logger.info("Recebida requisição para listar todos os usuários.");
    	
    	// Contador de chamadas ao método
        meterRegistry.counter("usuario.listar.todas.chamadas").increment();
        
        // Medição de tempo de execução
        long startTime = System.currentTimeMillis();
        
        List<Usuario> usuarios = repositoriUsuario.findAll();
        
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("usuario.listar.todas.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
        
        if (usuarios.isEmpty()) {
        	logger.warn("A lista está vazia.");
            meterRegistry.counter("usuario.listar.todas.vazio").increment();
        } else {
        	logger.info("Total de usuários encontrados: {}", usuarios.size());
        	meterRegistry.counter("usuario.listar.todas.sucesso").increment(); // Métrica para sucesso
            meterRegistry.gauge("usuario.listar.todas.tamanho", usuarios, List::size); // Tamanho da lista retornada
        }
        
        return usuarios;
    }

    public Usuario criarUsuario(Usuario usuario) {
        logger.info("Recebida requisição para criar novo usuário: {}", usuario);
        
        meterRegistry.counter("usuario.criar.chamadas").increment();
        
        long startTime = System.currentTimeMillis();
        
        Optional<Usuario> usuarioExistente = repositoriUsuario.findByName(usuario.getName());
        usuario.setDataCadastro(new java.sql.Date(System.currentTimeMillis()));
        
        if (usuarioExistente.isPresent()) {
            logger.warn("O usuário com o nome '{}' já existe.", usuario.getName());
            meterRegistry.counter("usuario.criar.existente").increment();
        }
        
        Usuario salvo = repositoriUsuario.save(usuario);
        logger.info("Usuário criado com sucesso: {}", salvo);
        meterRegistry.counter("usuario.criar.sucesso").increment();
        
        // Publicando evento de criação de usuário
        streamBridge.send("usuarioEventos-out-0", salvo);
        
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("usuario.criar.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
        
        return salvo;
    }


    public Optional<Usuario> buscarUsuarioPorId(Long id) {
    	logger.info("Recebida requisição para buscar usuário com ID: {}", id);
    	
    	// Contador de chamadas ao método
    	meterRegistry.counter("usuario.buscarPorID.chamadas").increment();

    	// Medição de tempo de execução
    	long startTime = System.currentTimeMillis();
    	
    	
        Optional<Usuario> usuario = repositoriUsuario.findById(id);
        long endTime = System.currentTimeMillis();
		meterRegistry.timer("usuario.buscarPorID.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
		
        if (usuario.isPresent()) {
            logger.info("Usuário encontrado: {}", usuario.get());
            meterRegistry.counter("usuario.buscarPorID.sucesso").increment(); // Métrica para sucesso
        } else {
            logger.warn("Usuário com ID {} não encontrado.", id);
            meterRegistry.counter("categoria.buscarPorID.naoEncontrada").increment(); // Métrica para falha
        }
        return usuario;
    }

    public void deletarUsuario(Long id) {
    	logger.info("Recebida requisição para deletar usuário com ID: {}", id);
    	
    	
        // Contador de chamadas ao método
		meterRegistry.counter("usuario.deletar.chamadas").increment();
		// Medição de tempo de execução
        long startTime = System.currentTimeMillis();
        
        Optional<Usuario> usuarioExistente = repositoriUsuario.findById(id);
        if (usuarioExistente.isPresent()) {
        	repositoriUsuario.deleteById(id);
        	logger.info("usuario com ID {} deletada com sucesso.", id);
            meterRegistry.counter("usuario.deletar.sucesso").increment(); // Métrica para sucesso
        } else {
        	logger.warn("usuario com ID {} não encontrada.", id);
            meterRegistry.counter("usuario.deletar.naoEncontrada").increment(); // Métrica para falha
        }
        
        long endTime = System.currentTimeMillis();
		meterRegistry.timer("categoria.deletar.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
		
    }
}