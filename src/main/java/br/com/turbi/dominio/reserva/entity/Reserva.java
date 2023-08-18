package br.com.turbi.dominio.reserva.entity;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.cliente.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
	@Getter @Setter
	private Set<Aluguel> aluguel;
	@Getter @Setter
	private Cliente cliente;

}
