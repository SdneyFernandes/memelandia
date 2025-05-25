package br.com.memelandia.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.memelandia.dto.CategoriaDTO;
import br.com.memelandia.entities.Categoria;
import br.com.memelandia.repositori.RepositoriCategoria;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cloud.stream.function.StreamBridge;


/**
 * @author fsdney
 */

@Service
public class ServiceCategoria {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCategoria.class);

    private final StreamBridge streamBridge;
    private final RepositoriCategoria repositoriCategoria;
    private final MeterRegistry meterRegistry;

    public ServiceCategoria(StreamBridge streamBridge, RepositoriCategoria repositoriCategoria, MeterRegistry meterRegistry) {
        this.streamBridge = streamBridge;
        this.repositoriCategoria = repositoriCategoria;
        this.meterRegistry = meterRegistry;
    }
    
    

    public List<Categoria> listarTodasCategorias() {
        logger.info("Recebida requisição para listar todas as categorias.");
        meterRegistry.counter("categoria.listar.todas.chamadas").increment();

        long start = System.currentTimeMillis();
        List<Categoria> categorias = repositoriCategoria.findAll();
        long end = System.currentTimeMillis();
        meterRegistry.timer("categoria.listar.todas.tempo").record(end - start, TimeUnit.MILLISECONDS);

        if (categorias.isEmpty()) {
            logger.warn("Lista de categorias retornou vazia.");
            meterRegistry.counter("categoria.listar.todas.vazio").increment();
        } else {
            logger.info("Total de categorias encontradas: {}", categorias.size());
            meterRegistry.counter("categoria.listar.todas.sucesso").increment();
            meterRegistry.gauge("categoria.listar.todas.quantidade", categorias, List::size);
        }

        return categorias;
    }

    public Optional<Categoria> criarCategoria(CategoriaDTO dto) {
        logger.info("Recebida requisição para criar nova categoria: {}", dto);
        meterRegistry.counter("categoria.criar.chamadas").increment();

        long start = System.currentTimeMillis();

        Optional<Categoria> existente = repositoriCategoria.findByName(dto.getName());
        if (existente.isPresent()) {
            logger.warn("Categoria com nome '{}' já existe.", dto.getName());
            meterRegistry.counter("categoria.criar.existente").increment();
            return Optional.empty();
        }

        Categoria categoria = new Categoria();
        categoria.setName(dto.getName());
        categoria.setDescription(dto.getDescription());
        categoria.setDataCadastro(LocalDate.now());
        Categoria salva = repositoriCategoria.save(categoria);
        meterRegistry.counter("categoria.criar.sucesso").increment();
        logger.info("Categoria criada com sucesso: {}", salva);

        try {
            streamBridge.send("categoriaEventos-out-0", salva);
            logger.info("Evento de criação de categoria enviado com sucesso via StreamBridge.");
        } catch (Exception e) {
            logger.error("Erro ao enviar evento de criação via StreamBridge: {}", e.getMessage());
            meterRegistry.counter("categoria.criar.evento.falha").increment();
        }

        long end = System.currentTimeMillis();
        meterRegistry.timer("categoria.criar.tempo").record(end - start, TimeUnit.MILLISECONDS);

        return Optional.of(salva);
    }    
    
    

    public Optional<Categoria> buscarCategoriaPorID(Long id) {
        logger.info("Recebida requisição para buscar categoria com ID: {}", id);
        meterRegistry.counter("categoria.buscar.id.chamadas").increment();

        long start = System.currentTimeMillis();
        Optional<Categoria> categoria = repositoriCategoria.findById(id);
        long end = System.currentTimeMillis();
        meterRegistry.timer("categoria.buscar.id.tempo").record(end - start, TimeUnit.MILLISECONDS);

        if (categoria.isPresent()) {
            logger.info("Categoria encontrada: {}", categoria.get());
            meterRegistry.counter("categoria.buscar.id.sucesso").increment();
        } else {
            logger.warn("Categoria com ID {} não encontrada.", id);
            meterRegistry.counter("categoria.buscar.id.naoencontrada").increment();
        }

        return categoria;
    }
    
    
    public Optional<Categoria> buscarCategoriaPorNome(String name) {
        logger.info("Recebida requisição para buscar categoria com Nome: {}", name);
        meterRegistry.counter("categoria.buscar.nome.chamadas").increment();

        long start = System.currentTimeMillis();
        Optional<Categoria> categoria = repositoriCategoria.findByName(name);
        long end = System.currentTimeMillis();
        meterRegistry.timer("categoria.buscar.nome.tempo").record(end - start, TimeUnit.MILLISECONDS);

        if (categoria.isPresent()) {
            logger.info("Categoria encontrada: {}", categoria.get());
            meterRegistry.counter("categoria.buscar.nome.sucesso").increment();
        } else {
            logger.warn("Categoria com nome {} não encontrada.", name);
            meterRegistry.counter("categoria.buscar.nome.naoencontrada").increment();
        }

        return categoria;
    }

    public boolean deletarCategoriaPorId(Long id) {
        logger.info("Recebida requisição para deletar categoria com ID: {}", id);
        meterRegistry.counter("categoria.deletar.Id.chamadas").increment();

        long start = System.currentTimeMillis();

        Optional<Categoria> categoriaExistente = repositoriCategoria.findById(id);
        if (categoriaExistente.isPresent()) {
            repositoriCategoria.deleteById(id);
            logger.info("Categoria com ID {} deletada com sucesso.", id);
            meterRegistry.counter("categoria.deletar.id.sucesso").increment();
            meterRegistry.timer("categoria.deletar.id.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
            return true;
        } else {
            logger.warn("Categoria com ID {} não encontrada para exclusão.", id);
            meterRegistry.counter("categoria.deletar.id.naoencontrada").increment();
            meterRegistry.timer("categoria.deletar.id.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
            return false;
        }
    }
    
    public boolean deletarCategoriaPorNome(String name) {
        logger.info("Recebida requisição para deletar categoria com nome: {}", name);
        meterRegistry.counter("categoria.deletar.nome.chamadas").increment();

        long start = System.currentTimeMillis();

        Optional<Categoria> categoriaExistente = repositoriCategoria.findByName(name);
        if (categoriaExistente.isPresent()) {
        	 repositoriCategoria.delete(categoriaExistente.get());
            logger.info("Categoria com nome {} deletada com sucesso.", name);
            meterRegistry.counter("categoria.deletar.nome.sucesso").increment();
            meterRegistry.timer("categoria.deletar.nome.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
            return true;
        } else {
            logger.warn("Categoria com nome {} não encontrada para exclusão.", name);
            meterRegistry.counter("categoria.deletar.nome.naoencontrada").increment();
            meterRegistry.timer("categoria.deletar.nome.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
            return false;
        }
    }
}
