package br.com.memelandia.entities;

import java.time.LocalDate;

import jakarta.persistence. *;
import jakarta.validation.constraints. *;
import lombok. *;

/**
 * 
 * Representa um usuário do sistema.
 * Armazena informações de identificação e data de cadastro.
 * 
 * @author fsdney
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_usuario")
	@SequenceGenerator(name = "sequence_usuario", sequenceName = "usuario_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@NotNull(message = "Data de Cadastro é obrigatoria")
	@Column(name = "data_cadastro", nullable = false)
	private LocalDate dataCadastro;

}
