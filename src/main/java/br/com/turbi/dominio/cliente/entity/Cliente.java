package br.com.turbi.dominio.cliente.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"nome", "dataNascimento", "cpf", "cnh", "email", "senha", "sexo"})

@Entity
@Table(name = "TB_TURBI_CLIENTE", uniqueConstraints = {
		@UniqueConstraint(name = "UK_CLIENTE", columnNames = {"NR_CPF", "NR_CNH", "DS_EMAIL"})
})
public class Cliente {

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TURBI_CLIENTE")
	@SequenceGenerator(name = "SQ_TURBI_CLIENTE", sequenceName = "SQ_TURBI_CLIENTE", allocationSize = 1)
	@Column(name = "ID_CLIENTE")
	private long id;
	@Getter @Setter
    @Column(name = "NM_CLIENTE")
    private String nome;
	@Getter @Setter
    @Column(name = "DT_NASCIMENTO")
	private LocalDate dataNascimento;
	@Getter @Setter
    @Column(name = "NR_CPF")
	private String cpf;
	@Getter @Setter
    @Column(name = "NR_CNH")
	private String cnh;
	@Getter @Setter
    @Column(name = "DS_EMAIL")
	private String email;
	@Getter @Setter
    @Column(name = "DS_SENHA")
	private String senha;
	@Getter @Setter
    @Column(name = "DS_SEXO")
	private Sexo sexo;
}
