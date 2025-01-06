package br.com.memelandia.repositori;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.memelandia.entities.Categoria;

/**
 * @author fsdney
 */

public interface RepositoriCategoria extends JpaRepository<Categoria, Long> {
	Optional<Categoria> findByName(String name);

}
