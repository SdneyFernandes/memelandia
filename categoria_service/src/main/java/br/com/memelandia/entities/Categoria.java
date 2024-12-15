package br.com.memelandia.entities;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author fsdney
 */


@Data
@Entity
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_categoria")
	@SequenceGenerator(name = "sequence_categoria", sequenceName = "categoria_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@NotBlank(message = "Descrição é obrigatório")
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "data_cadastro", nullable = false)
	private Date dataCadastro;
	
	

}
