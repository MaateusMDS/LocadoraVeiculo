package br.com.turbi.model.entities;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"nome", "dataNascimento", "cpf", "cnh", "email", "senha", "sexo"})
public class Cliente {
	private Integer id;
	@Getter @Setter
	private String nome;
	@Getter @Setter
	private LocalDate dataNascimento;
	@Getter @Setter
	private String cpf;
	@Getter @Setter
	private String cnh;
	@Getter @Setter
	private String email;
	@Getter @Setter
	private String senha;
	@Getter @Setter
	private Sexo sexo;

}
