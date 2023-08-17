package br.com.turbi.model.entities;

import lombok.*;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"placa", "chassi", "cor", "valorDiaria"})
public class Carro {
    private Integer idCarro;
    @Getter @Setter
    private String placa;
    @Getter @Setter
    private String chassi;
    @Getter @Setter
    private String cor;
    @Getter @Setter
    private BigDecimal valorDiaria;
}
