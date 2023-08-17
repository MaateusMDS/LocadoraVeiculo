package br.com.turbi.model.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"dataPedido", "dataEntrega", "dataDevolucao", "valorTotal"})
public class Aluguel {
    private Integer idAluguel;
    @Getter @Setter
    private Calendar dataPedido;
    @Getter @Setter
    private Date dataEntrega;
    @Getter @Setter
    private Date dataDevolucao;
    @Getter @Setter
    private BigDecimal valorTotal;

}
