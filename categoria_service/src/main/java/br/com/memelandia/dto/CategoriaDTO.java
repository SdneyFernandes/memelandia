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
public class CategoriaDTO {

 @NotBlank(message = "Nome é obrigatório")
 private String name;

 
 @NotBlank(message = "Descrição é obrigatória")
 private String description;
}
