package br.com.turbi.dominio.carrinho.dto;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.carrinho.entity.Carrinho;
import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.veiculo.entity.Veiculo;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Relation(collectionRelation = "carrinho")
public record CarrinhoDTO(
        long id,
        Cliente cliente,
        Set<Aluguel> aluguel,
        BigDecimal custoTotal
) {

    public static Set<CarrinhoDTO> of(Collection<Carrinho> c) {
        return c.stream().map(CarrinhoDTO::of).collect(Collectors.toCollection(LinkedHashSet<CarrinhoDTO>::new));
    }

    public static CarrinhoDTO of(Long id, BigDecimal custoTotal) {
        return new CarrinhoDTO(
                id,
                null,
                null,
                custoTotal
        );
    }

    public static CarrinhoDTO of(Carrinho a) {
        return new CarrinhoDTO(a.getId(), a.getCliente(), a.getAluguel(), a.calcularValorCarrinho());
    }

    public Carrinho toModel() {
        return new Carrinho(id, cliente, aluguel, custoTotal);
    }
}