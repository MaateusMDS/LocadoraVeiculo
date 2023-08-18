package br.com.turbi.dominio.veiculo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor

@Embeddable
public class Modelo {
	@Getter @Setter
	@Column(name = "DS_PLACA")
	private String placa;
	@Getter @Setter
	@Column(name = "DS_CHASSI")
	private String chassi;
	@Getter @Setter
	@Column(name = "DS_COR")
	private String cor;
}
