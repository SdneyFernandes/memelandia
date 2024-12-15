package br.com.memelandia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.memelandia.entities.Categoria;
import br.com.memelandia.repositori.RepositoriCategoria;

/**
 * @author fsdney
 */

@Service
public class ServiceCategoria {
	
	private Logger logger = LoggerFactory.getLogger(ServiceCategoria.class);
	
	@Autowired
	private RepositoriCategoria repositoriCategoria;
	
	public List<Categoria> listarTodasCategorias() {
		logger.info("Listando todas as categorias.");
		List<Categoria> categorias = repositoriCategoria.findAll();
		logger.info("Total de usuarios encontrados: {}", categorias.size());
		return categorias;
	}
	
	public Categoria criarCategoria(Categoria categoria) {
		logger.info("Criando nova categoria: {}", categoria);
		categoria.setDataCadastro(new java.sql.Date(System.currentTimeMillis()));
		Categoria salva = repositoriCategoria.save(categoria);
		logger.info("Categoria criada com sucesso: {}", salva);
		return salva;
	}
	
	public Optional<Categoria> buscarCategoriaPorID(Long id) {
		logger.info("Buscando categoria cm ID: {}", id);
		Optional<Categoria> categoria = repositoriCategoria.findById(id);
		if (categoria.isPresent()) {
			logger.info("Categoria encontrada: {}", categoria.get());
		} else {
			logger.warn("Categoria com ID {} não encontrada.", id);
		}
		return categoria;
	}
	
	public void deletarCategoria(Long id) {
		logger.info("Deletando categoria com ID: {}", id);
		repositoriCategoria.deleteById(id);
		logger.info("Categoria com ID {} deletada com sucesso.", id);
	}

}
