package br.com.turbi.dominio.aluguel.dto;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.veiculo.entity.Veiculo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Relation(collectionRelation = "aluguel")
public record AluguelPutDTO(
        long id,
        Cliente cliente,
        Veiculo veiculo,
        LocalDate dataInicio,
        LocalDate dataTermino,
        @DecimalMin(value = "0.0", message = "O valor total deve ser maior que zero")
        BigDecimal valorTotal
) {

    public static Set<AluguelPutDTO> of(Collection<Aluguel> a) {
        return a.stream().map(AluguelPutDTO::of).collect(Collectors.toCollection(LinkedHashSet<AluguelPutDTO>::new));
    }

    public static AluguelPutDTO of(Long id, BigDecimal valorTotal) {
        return new AluguelPutDTO(
                id,
                null,
                null,
                null,
                null,
                valorTotal
        );
    }

    public static AluguelPutDTO of(Aluguel a) {
        return new AluguelPutDTO(a.getId(), a.getCliente(), a.getVeiculo(), a.getDataInicio(), a.getDataTermino(), a.calcularValorAluguel());
    }

    public Aluguel toModel() {
        return new Aluguel(id, cliente, veiculo, dataInicio, dataTermino, valorTotal);
    }
}