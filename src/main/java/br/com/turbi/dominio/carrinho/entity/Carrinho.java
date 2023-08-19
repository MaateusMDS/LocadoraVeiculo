package br.com.turbi.dominio.carrinho.entity;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.cliente.entity.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"cliente", "aluguel", "custoTotal"})

@Entity
@Table(name = "TB_TURBI_CARRINHO")
public class Carrinho {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TURBI_CARRINHO")
	@SequenceGenerator(name = "SQ_TURBI_CARRINHO", sequenceName = "SQ_TURBI_CARRINHO", allocationSize = 1)
	@Column(name = "ID_CARRINHO")
	private long id;
	@Getter @Setter
	@ManyToOne(
			fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinColumn(
			name = "ID_CLIENTE",
			referencedColumnName = "ID_CLIENTE",
			foreignKey = @ForeignKey(name = "FK_CARRINHO_CLIENTE")
	)
	private Cliente cliente;
	@Getter @Setter
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
	@Getter @Setter
	@Column(name = "CT_TOTAL")
	private BigDecimal custoTotal;

	public BigDecimal calcularValorCarrinho() {
		aluguel.forEach(aluguel -> {
			custoTotal.add(aluguel.calcularValorAluguel());
		});
		return custoTotal;
	};
}
