package br.com.turbi.model.entities;

import lombok.*;

import java.util.Date;
import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"nome", "cpf", "cnh", "email", "senha", "dataNascimento"})
public class Cliente {
    private Integer id;
    @Getter @Setter
    private String nome;
    @Getter @Setter
    private String cpf;
    @Getter @Setter
    private String cnh;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String senha;
    @Getter @Setter
    private Date dataNascimento;

}
