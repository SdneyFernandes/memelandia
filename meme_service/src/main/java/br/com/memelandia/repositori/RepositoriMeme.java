package br.com.memelandia.repositori;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.memelandia.entities.Meme;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fsdney
 */

public interface RepositoriMeme extends JpaRepository<Meme, Long> {
	// Selecionar um meme aleatório
    @Query(value = "SELECT * FROM Meme ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Meme findRandomMeme();
}
