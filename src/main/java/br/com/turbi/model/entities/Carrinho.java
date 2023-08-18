package br.com.turbi.model.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
public class Carrinho {
	@Getter @Setter
	private Cliente cliente;
	@Getter @Setter
	private Set<Aluguel> aluguel;
	@Getter @Setter
	private BigDecimal custoTotal;

	public BigDecimal calcularValorCarrinho() {
		return null;
	}

}
