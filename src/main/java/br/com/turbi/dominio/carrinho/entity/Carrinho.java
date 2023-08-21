package br.com.turbi.dominio.carrinho.entity;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.cliente.entity.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"cliente", "aluguel", "custoTotal"})

@Entity
@Table(name = "TB_TURBI_CARRINHO", uniqueConstraints = {
		@UniqueConstraint(name = "UK_CARRINHO_CLIENTE", columnNames = {"ID_CLIENTE", "ID_CARRINHO"})
})

public class Carrinho {

	public Carrinho() {
		this.custoTotal = BigDecimal.ZERO;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TURBI_CARRINHO")
	@SequenceGenerator(name = "SQ_TURBI_CARRINHO", sequenceName = "SQ_TURBI_CARRINHO", allocationSize = 1)
	@Column(name = "ID_CARRINHO")
	@Getter
	private long id;
	@Getter
	@Setter
	@ManyToOne(
			fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH}
	)
	@JoinColumn(
			name = "ID_CLIENTE",
			referencedColumnName = "ID_CLIENTE",
			foreignKey = @ForeignKey(name = "FK_CARRINHO_CLIENTE")
	)
	private Cliente cliente;
	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.MERGE})
	@JoinTable(
			name = "TB_CARRINHO_ALUGUEL",
			joinColumns = {
					@JoinColumn(
							name = "ID_CARRINHO",
							referencedColumnName = "ID_CARRINHO",
							foreignKey = @ForeignKey(name = "FK_CARRINHO")
					)
			},
			inverseJoinColumns = {
					@JoinColumn(
							name = "ID_ALUGUEL",
							referencedColumnName = "ID_ALUGUEL",
							foreignKey = @ForeignKey(name = "FK_ALUGUEL")
					)
			}
	)
	private Set<Aluguel> aluguel;
	@Getter
	@Setter
	@Column(name = "VL_CUSTO_TOTAL")
	private BigDecimal custoTotal;

	public BigDecimal calcularValorCarrinho() {
		custoTotal = BigDecimal.ZERO;
		for (Aluguel a : aluguel) {
			custoTotal = custoTotal.add(a.calcularValorAluguel());
		}
		return custoTotal;
	}
}