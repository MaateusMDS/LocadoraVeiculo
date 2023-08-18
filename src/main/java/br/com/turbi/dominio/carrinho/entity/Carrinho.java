package br.com.turbi.dominio.carrinho.entity;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.cliente.entity.Cliente;
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
		aluguel.forEach(aluguel -> {
			custoTotal.add(aluguel.calcularValorAluguel());
		});
		return custoTotal;
	};
}
