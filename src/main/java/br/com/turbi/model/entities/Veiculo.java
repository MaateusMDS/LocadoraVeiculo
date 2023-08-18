package br.com.turbi.model.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"fabricante", "modelo", "categoria", "acessorios", "valorDiaria", "descricao"})
public class Veiculo {
	private Integer idVeiculo;
	@Getter @Setter
	private String fabricante;
	@Getter @Setter
	private Modelo modelo;
	@Getter @Setter
	private Categoria categoria;
	@Getter @Setter
	private ArrayList<String> acessorios;
	@Getter @Setter
	private BigDecimal valorDiaria;
	@Getter @Setter
	private String descricao;

}
