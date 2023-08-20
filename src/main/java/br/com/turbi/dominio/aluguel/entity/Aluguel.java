package br.com.turbi.dominio.aluguel.entity;

import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.veiculo.entity.Veiculo;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"cliente", "veiculo", "dataInicio", "dataTermino", "valorTotal"})

@Entity
@Table(name = "TB_TURBI_ALUGUEL")
public class Aluguel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TURBI_ALUGUEL")
	@SequenceGenerator(name = "SQ_TURBI_ALUGUEL", sequenceName = "SQ_TURBI_ALUGUEL", allocationSize = 1)
	@Column(name = "ID_ALUGUEL")
	@Getter
	private long id;
	@Getter @Setter
	@ManyToOne(
			fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinColumn(
			name = "ID_CLIENTE",
			referencedColumnName = "ID_CLIENTE",
			foreignKey = @ForeignKey(name = "FK_CLIENTE_ALUGUEL")
	)
	private Cliente cliente;
	@Getter @Setter
	@ManyToOne(
			fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinColumn(
			name = "ID_VEICULO",
			referencedColumnName = "ID_VEICULO",
			foreignKey = @ForeignKey(name = "FK_VEICULO_ALUGUEL")
	)
	private Veiculo veiculo;
	@Getter @Setter
	@Column(name = "DT_INICIO")
	private LocalDate dataInicio;
	@Getter @Setter
	@Column(name = "DT_TERMINO")
	private LocalDate dataTermino;
	@Getter @Setter
	@Column(name = "VL_TOTAL")
	private BigDecimal valorTotal;

	public BigDecimal calcularValorAluguel() {

		return veiculo.getValorDiaria();
	}

}
