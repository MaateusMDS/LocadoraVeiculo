package br.com.turbi.dominio.veiculo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"fabricante", "modelo", "categoria", "acessorios", "valorDiaria", "descricao"})

@Entity
@Table(name = "TB_TURBI_VEICULO")
public class Veiculo {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.SEQUENCE, generator = "SQ_TURBI_VEICULO")
	@SequenceGenerator(name = "SQ_TURBI_VEICULO", sequenceName = "SQ_TURBI_VEICULO", allocationSize = 1)
	@Column(name = "ID_VEICULO")
	private long id;
	@Getter @Setter
	@Column(name = "DS_FABRICANTE")
	private String fabricante;
	@Getter @Setter
	@Column(name = "DS_MODELO")
	@Embedded
	private Modelo modelo;
	@Getter @Setter
	@Column(name = "DS_CATEGORIA")
	private Categoria categoria;
	@Getter @Setter
	@Column(name = "DS_ACESSORIOS")
	private ArrayList<String> acessorios;
	@Getter @Setter
	@Column(name = "VL_DIARIA")
	private BigDecimal valorDiaria;
	@Getter @Setter
	@Column(name = "DS_DESCRICAO")
	private String descricao;

}
