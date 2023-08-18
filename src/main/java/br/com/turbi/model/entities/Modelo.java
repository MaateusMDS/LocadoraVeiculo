package br.com.turbi.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
public class Modelo {
	@Getter @Setter
	private String placa;
	@Getter @Setter
	private String chassi;
	@Getter @Setter
	private String cor;

}
