package br.com.memelandia.entities;

import java.time.LocalDate;

import jakarta.persistence. *;
import jakarta.validation.constraints. *;
import lombok. *;

/**
 * 
 * Representa uma categoria do sistema.
 * Armazena informações de descrição e data de cadastro.
 * 
 * @author fsdney
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categoria")
public class Categoria {

	
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_categoria")
    @SequenceGenerator(name = "sequence_categoria", sequenceName = "categoria_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;
}
