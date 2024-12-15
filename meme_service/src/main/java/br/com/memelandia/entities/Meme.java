package br.com.memelandia.entities;

import jakarta.persistence.*;
import java.sql.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import br.com.memelandia.entities.Categoria; 
import br.com.memelandia.entities.Usuario;  

@Entity
@Data
public class Meme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_meme")
	@SequenceGenerator(name = "sequence_meme", sequenceName = "meme_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotBlank(message = "Nome do meme é obrigatorio")
	@Column( name = "name", nullable = false)
	private String name;
	
	@NotBlank(message = "Descrição do meme é obrigatia")
	@Column(name ="description", nullable = false)
	private String description;
	
	@NotBlank(message = "URL do meme é obrigatoria")
	@Column(name ="URL", nullable = false)
	private String url;
	
	@Column(name = "data_Cadastro", nullable = false)
	private Date dataCadastro;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull(message = "Categoria é obrigatória")
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "Usuário é obrigatório")
    private Usuario usuario;
	

}
