package br.com.memelandia.service;

import java.util. *;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;

import org.slf4j. *;
import org.springframework.stereotype.Service;

import br.com.memelandia.entities.Usuario;
import br.com.memelandia.repositori.RepositoriUsuario;
import br.com.memelandia.dto.UsuarioDTO;
import io.micrometer.core.instrument.*;

import org.springframework.cloud.stream.function.StreamBridge;


/**
 * @author fsdney
 */

@Service
public class ServiceUsuario {

	  private static final Logger logger = LoggerFactory.getLogger(ServiceUsuario.class);

	    private final StreamBridge streamBridge;
	    private final RepositoriUsuario repositoriUsuario;
	    private final MeterRegistry meterRegistry;

	    public ServiceUsuario(StreamBridge streamBridge, RepositoriUsuario repositoriUsuario, MeterRegistry meterRegistry) {
	        this.streamBridge = streamBridge;
	        this.repositoriUsuario = repositoriUsuario;
	        this.meterRegistry = meterRegistry;
	    
    }


	    public List<Usuario> listarTodosUsuarios() {
	        logger.info("Recebida requisição para listar todos os usuários.");
	        meterRegistry.counter("usuario.listar.todas.chamadas").increment();

	        long start = System.currentTimeMillis();
	        List<Usuario> usuarios = repositoriUsuario.findAll();
	        long end = System.currentTimeMillis();
	        meterRegistry.timer("usuario.listar.todas.tempo").record(end - start, TimeUnit.MILLISECONDS);

	        if (usuarios.isEmpty()) {
	            logger.warn("Lista de usuários retornou vazia.");
	            meterRegistry.counter("usuario.listar.todas.vazio").increment();
	        } else {
	            logger.info("Total de usuários encontrados: {}", usuarios.size());
	            meterRegistry.counter("usuario.listar.todas.sucesso").increment();
	            meterRegistry.gauge("usuario.listar.todas.quantidade", usuarios, List::size);
	        }

	        return usuarios;
	    }

	    public Optional<Usuario> criarUsuario(UsuarioDTO dto) {
	        logger.info("Recebida requisição para criar novo usuário: {}", dto);
	        meterRegistry.counter("usuario.criar.chamadas").increment();

	        long start = System.currentTimeMillis();

	        Optional<Usuario> existente = repositoriUsuario.findByNome(dto.getNome());
	        if (existente.isPresent()) {
	            logger.warn("Usuário com nome '{}' já existe. Abortando criação.", dto.getNome());
	            meterRegistry.counter("usuario.criar.duplicado").increment();
	            return Optional.empty();
	        }
	        
	        Usuario usuario = new Usuario();
	        usuario.setNome(dto.getNome());
	        usuario.setEmail(dto.getEmail());
	        usuario.setDataCadastro(LocalDate.now());
	        Usuario salvo = repositoriUsuario.save(usuario);
	        
	        
	        meterRegistry.counter("usuario.criar.sucesso").increment();
	        logger.info("Usuário criado com sucesso: {}", salvo);

	        try {
	            streamBridge.send("usuarioEventos-out-0", salvo);
	            logger.info("Evento de criação enviado com sucesso via StreamBridge.");
	        } catch (Exception e) {
	            logger.error("Erro ao enviar evento de criação via StreamBridge: {}", e.getMessage());
	            meterRegistry.counter("usuario.criar.evento.falha").increment();
	        }

	        long end = System.currentTimeMillis();
	        meterRegistry.timer("usuario.criar.tempo").record(end - start, TimeUnit.MILLISECONDS);

	        return Optional.of(salvo);
	    }



	    public Optional<Usuario> buscarUsuarioPorId(Long id) {
	        logger.info("Recebida requisição para buscar usuário com ID: {}", id);
	        meterRegistry.counter("usuario.buscar.id.chamadas").increment();

	        long start = System.currentTimeMillis();
	        Optional<Usuario> usuario = repositoriUsuario.findById(id);
	        long end = System.currentTimeMillis();
	        meterRegistry.timer("usuario.buscar.id.tempo").record(end - start, TimeUnit.MILLISECONDS);

	        if (usuario.isPresent()) {
	            logger.info("Usuário encontrado: {}", usuario.get());
	            meterRegistry.counter("usuario.buscar.id.sucesso").increment();
	        } else {
	            logger.warn("Usuário com ID {} não encontrado.", id);
	            meterRegistry.counter("usuario.buscar.id.naoencontrado").increment();
	        }

	        return usuario;
	    }
	    
	    public Optional<Usuario> buscarUsuarioPorNome(String nome) {
	        logger.info("Recebida requisição para buscar usuário com Nome: {}", nome);
	        meterRegistry.counter("usuario.buscar.nome.chamadas").increment();

	        long start = System.currentTimeMillis();
	        Optional<Usuario> usuario = repositoriUsuario.findByNome(nome);
	        long end = System.currentTimeMillis();
	        meterRegistry.timer("usuario.buscar.nome.tempo").record(end - start, TimeUnit.MILLISECONDS);

	        if (usuario.isPresent()) {
	            logger.info("Usuário encontrado: {}", usuario.get());
	            meterRegistry.counter("usuario.buscar.nome.sucesso").increment();
	        } else {
	            logger.warn("Usuário com nome {} não encontrado.", nome);
	            meterRegistry.counter("usuario.buscar.nome.naoencontrado").increment();
	        }

	        return usuario;
	    }

	    public boolean deletarUsuarioPorId(Long id) {
	        logger.info("Recebida requisição para deletar usuário com ID: {}", id);
	        meterRegistry.counter("usuario.deletar.id.chamadas").increment();

	        long start = System.currentTimeMillis();

	        Optional<Usuario> usuarioExistente = repositoriUsuario.findById(id);
	        if (usuarioExistente.isPresent()) {
	            repositoriUsuario.deleteById(id);
	            logger.info("Usuário com ID {} deletado com sucesso.", id);
	            meterRegistry.counter("usuario.deletar.id.sucesso").increment();
	            meterRegistry.timer("usuario.deletar.id.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
	            return true;
	        } else {
	            logger.warn("Usuário com ID {} não encontrado para exclusão.", id);
	            meterRegistry.counter("usuario.deletar.id.naoencontrado").increment();
	            meterRegistry.timer("usuario.deletar.id.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
	            return false;
	        }
	    }
	    
	    public boolean deletarUsuarioPorNome(String nome) {
	        logger.info("Recebida requisição para deletar usuário com nome: {}", nome);
	        meterRegistry.counter("usuario.deletar.nome.chamadas").increment();

	        long start = System.currentTimeMillis();

	        Optional<Usuario> usuarioExistente = repositoriUsuario.findByNome(nome);
	        if (usuarioExistente.isPresent()) {
	            repositoriUsuario.delete(usuarioExistente.get());
	            logger.info("Usuário com nome {} deletado com sucesso.", nome);
	            meterRegistry.counter("usuario.deletar.nome.sucesso").increment();
	        } else {
	            logger.warn("Usuário com nome {} não encontrado para exclusão.", nome);
	            meterRegistry.counter("usuario.deletar.nome.naoencontrado").increment();
	            meterRegistry.timer("usuario.deletar.nome.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
	            return false;
	        }

	        meterRegistry.timer("usuario.deletar.nome.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
	        return true;
	    }

	    }
