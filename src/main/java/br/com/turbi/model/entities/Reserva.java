package br.com.turbi.model.entities;

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
