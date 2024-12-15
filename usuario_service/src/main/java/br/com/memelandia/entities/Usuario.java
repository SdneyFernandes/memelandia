package br.com.memelandia.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;

/**
 * @author fsdney
 */

@Entity
@Data
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_usuario")
	@SequenceGenerator(name = "sequence_usuario", sequenceName = "usuario_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	@Column(name = "name", nullable = false)
	private String name;
	
	@Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "data_cadastro", nullable = false)
	private Date dataCadastro;

}
