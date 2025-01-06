package br.com.memelandia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.memelandia.entities.Categoria;
import br.com.memelandia.repositori.RepositoriCategoria;
import io.micrometer.core.instrument.MeterRegistry;


/**
 * @author fsdney
 */

@Service
public class ServiceCategoria {
	
	private Logger logger = LoggerFactory.getLogger(ServiceCategoria.class);
	
	@Autowired
	private RepositoriCategoria repositoriCategoria;
	
	@Autowired
    private MeterRegistry meterRegistry; // Registro de métricas
	
	public List<Categoria> listarTodasCategorias() {
		logger.info("Recebida requisição para listar todas as categorias.");
		
		// Contador de chamadas ao método
        meterRegistry.counter("categoria.listar.todas.chamadas").increment();

        // Medição de tempo de execução
        long startTime = System.currentTimeMillis();

        List<Categoria> categorias = repositoriCategoria.findAll();
        
        long endTime = System.currentTimeMillis();
        meterRegistry.timer("categoria.listar.todas.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);

        if (categorias.isEmpty()) {
            logger.warn("A lista está vazia.");
            meterRegistry.counter("categoria.listar.todas.vazio").increment(); // Métrica para listas vazias
        } else {
            logger.info("Total de categorias encontradas: {}", categorias.size());
            meterRegistry.counter("categoria.listar.todas.sucesso").increment(); // Métrica para sucesso
            meterRegistry.gauge("categoria.listar.todas.tamanho", categorias, List::size); // Tamanho da lista retornada
        }

        return categorias;
    }

	public Categoria criarCategoria(Categoria categoria) {
		logger.info("Recebida requisição para criar nova categoria: {}", categoria);
		
		// Contador de chamadas ao método
		meterRegistry.counter("categoria.criar.chamadas").increment();

		// Medição de tempo de execução
        long startTime = System.currentTimeMillis();
		
		Optional<Categoria> categoriaExistente = repositoriCategoria.findByName(categoria.getName());
		categoria.setDataCadastro(new java.sql.Date(System.currentTimeMillis()));
		
		if (categoriaExistente.isPresent()) {
			logger.warn("A categoria com o nome '{}' já existe.", categoria.getName());
			//return ResponseEntity.status(HttpStatus.CONFLICT).build();
			meterRegistry.counter("categoria.criar.existente").increment(); // Métrica para categoria já existente
		} 
		
		Categoria salva = repositoriCategoria.save(categoria);
		logger.info("Categoria criada com sucesso: {}", salva);
		meterRegistry.counter("categoria.criar.sucesso").increment(); // Métrica para sucesso
		
		long endTime = System.currentTimeMillis();
		meterRegistry.timer("categoria.criar.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
		
		return salva;
	}
	
	public Optional<Categoria> buscarCategoriaPorID(Long id) {
        logger.info("Recebida requisição para buscar categoria com ID: {}", id);
        
     // Contador de chamadas ao método
     		meterRegistry.counter("categoria.buscarPorID.chamadas").increment();

     		// Medição de tempo de execução
             long startTime = System.currentTimeMillis();
     	 
     	Optional<Categoria> categoria = repositoriCategoria.findById(id);
     	
     	long endTime = System.currentTimeMillis();
		meterRegistry.timer("categoria.buscarPorID.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
     	 
     	 
     	if (categoria.isPresent()) {
            logger.info("Categoria encontrada: {}", categoria.get());
            meterRegistry.counter("categoria.buscarPorID.sucesso").increment(); // Métrica para sucesso
            
        } else {
            logger.warn("Categoria com ID {} não encontrada.", id);
            meterRegistry.counter("categoria.buscarPorID.naoEncontrada").increment(); // Métrica para falha
            
            
        }
        return categoria;
    }

	public void deletarCategoria(Long id) {
		logger.info("Recebida requisição para deletar categoria com ID: {}", id);
		
		// Contador de chamadas ao método
		meterRegistry.counter("categoria.deletar.chamadas").increment();

		// Medição de tempo de execução
        long startTime = System.currentTimeMillis();
		
		Optional<Categoria> categoriaExistente = repositoriCategoria.findById(id);
		if (categoriaExistente.isPresent()) {
            repositoriCategoria.deleteById(id);
            logger.info("Categoria com ID {} deletada com sucesso.", id);
            meterRegistry.counter("categoria.deletar.sucesso").increment(); // Métrica para sucesso
        } else {
            logger.warn("Categoria com ID {} não encontrada.", id);
            meterRegistry.counter("categoria.deletar.naoEncontrada").increment(); // Métrica para falha
        }
        
        long endTime = System.currentTimeMillis();
		meterRegistry.timer("categoria.deletar.tempo").record(endTime - startTime, java.util.concurrent.TimeUnit.MILLISECONDS);
	}
	
}
