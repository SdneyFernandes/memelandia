package br.com.memelandia.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fsdney
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeDTO {


	 @NotBlank(message = "Nome do meme é obrigatório")
	    private String name;

	    @NotBlank(message = "Descrição do meme é obrigatória")
	    private String description;

	    @NotBlank(message = "URL do meme é obrigatória")
	    private String url;

	    @NotBlank(message = "Nome da categoria é obrigatório")
	    private String categoriaName;

	    @NotBlank(message = "Nome do usuário é obrigatório")
	    private String usuarioName;

	}

