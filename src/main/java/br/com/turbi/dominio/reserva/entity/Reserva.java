package br.com.turbi.dominio.reserva.entity;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.cliente.entity.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"aluguel", "cliente"})

@Entity
@Table(name = "TB_TURBI_RESERVA")
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TURBI_RESERVA")
	@SequenceGenerator(name = "SQ_TURBI_RESERVA", sequenceName = "SQ_TURBII_RESERVA", allocationSize = 1)
	@Column(name = "ID_RESERVA")
	@Getter @Setter
	private long id;
	@Getter @Setter
	@OneToMany(
			fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinColumn(
			name = "ID_RESERVA",
			referencedColumnName = "ID_RESERVA",
			foreignKey = @ForeignKey(name = "FK_ALUGUEL_RESERVA")
	)
	private Set<Aluguel> aluguel;

	@Getter @Setter
	@ManyToOne(
			fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinColumn(
			name = "ID_CLIENTE",
			referencedColumnName = "ID_CLIENTE",
			foreignKey = @ForeignKey(name = "FK_CLIENTE_RESERVA")
	)
	private Cliente cliente;

}
