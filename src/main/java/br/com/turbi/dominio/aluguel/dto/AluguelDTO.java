package br.com.turbi.dominio.aluguel.dto;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.cliente.dto.ClienteDTO;
import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.veiculo.dto.VeiculoDTO;
import br.com.turbi.dominio.veiculo.entity.Veiculo;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Relation(collectionRelation = "aluguel")
public record AluguelDTO(
        long id,
        Cliente cliente,
        Veiculo veiculo,
        LocalDate dataInicio,
        LocalDate dataTermino,
        BigDecimal valorTotal
) {

    public static Set<AluguelDTO> of(Collection<Aluguel> a) {
        return a.stream().map(AluguelDTO::of).collect(Collectors.toCollection(LinkedHashSet<AluguelDTO>::new));
    }

    public static AluguelDTO of(Long id, BigDecimal valorTotal) {
        return new AluguelDTO(
                id,
                null,
                null,
                null,
                null,
                valorTotal
        );
    }

    public static AluguelDTO of(Aluguel a) {
        return new AluguelDTO(a.getId(), a.getCliente(), a.getVeiculo(), a.getDataInicio(), a.getDataTermino(), a.calcularValorAluguel());
    }

    public Aluguel toModel() {
        return new Aluguel(id, cliente, veiculo, dataInicio, dataTermino, valorTotal);
    }
}