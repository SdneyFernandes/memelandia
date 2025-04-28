package br.com.memelandia.repositori;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.memelandia.entities.Usuario;

/**
 * @author fsdney
 */

public interface RepositoriUsuario extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByNome(String nome);

}
