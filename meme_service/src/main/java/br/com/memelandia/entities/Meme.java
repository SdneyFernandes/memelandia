package br.com.memelandia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok. *;

import java.time.LocalDate;



/**
 * 
 * Representa um meme do sistema.
 * Armazena informações de descrição e data de cadastro.
 * 
 * @author fsdney
 */


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meme")
public class Meme {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_meme")
    @SequenceGenerator(name = "sequence_meme", sequenceName = "meme_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Nome do meme é obrigatório")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Descrição do meme é obrigatória")
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank(message = "URL do meme é obrigatória")
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @NotBlank(message = "Nome da categoria é obrigatório")
    @Column(name = "categoria_name", nullable = false)
    private String categoriaName;

    @NotBlank(message = "Nome do usuário é obrigatório")
    @Column(name = "usuario_name", nullable = false)
    private String usuarioName;

}