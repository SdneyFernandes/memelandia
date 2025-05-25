package br.com.memelandia.dto;

import jakarta.validation.constraints.Email;
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
public class UsuarioDTO {

 @NotBlank(message = "Nome é obrigatório")
 private String nome;

 @Email(message = "E-mail inválido")
 @NotBlank(message = "E-mail é obrigatório")
 private String email;
}
