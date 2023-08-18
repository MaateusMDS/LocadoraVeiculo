package br.com.turbi.dominio.aluguel.entity;

import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.veiculo.entity.Veiculo;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"cliente", "veiculo", "dataInicio", "dataTermino", "valorTotal"})
public class Aluguel {
	private long id;
	@Getter @Setter
	private Cliente cliente;
	@Getter @Setter
	private Veiculo veiculo;
	@Getter @Setter
	private LocalDate dataInicio;
	@Getter @Setter
	private LocalDate dataTermino;
	@Getter @Setter
	private BigDecimal valorTotal;

	public BigDecimal calcularValorAluguel() {

		return veiculo.getValorDiaria();
	};

}
