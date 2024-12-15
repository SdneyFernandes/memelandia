package br.com.memelandia.repositori;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.memelandia.entities.Categoria;

/**
 * @author fsdney
 */

public interface RepositoriCategoria extends JpaRepository<Categoria, Long> {

}
