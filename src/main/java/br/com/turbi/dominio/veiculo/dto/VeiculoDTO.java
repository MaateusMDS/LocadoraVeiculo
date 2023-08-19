package br.com.turbi.dominio.veiculo.dto;

import br.com.turbi.dominio.veiculo.entity.Veiculo;
import br.com.turbi.dominio.veiculo.entity.Categoria;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Relation(collectionRelation = "veiculos")
public record VeiculoDTO(
        long id,
        String fabricante,
        ModeloDTO modelo,
        Categoria categoria,
        ArrayList<String> acessorios,
        BigDecimal valorDiaria,
        String descricao
) {

    public static Set<VeiculoDTO> of(Collection<Veiculo > v) {
        return v.stream().map(VeiculoDTO::of).collect(Collectors.toCollection(LinkedHashSet<VeiculoDTO>::new));
    }
    
    public static VeiculoDTO of(Long id) {
        return new VeiculoDTO(
                id,
                "",
                null,
                null,
                null,
                null,
                ""
        );
    }

    public static VeiculoDTO of(Veiculo v) {
        return new VeiculoDTO(v.getId(), v.getFabricante(), ModeloDTO.of(v.getModelo()), v.getCategoria(), v.getAcessorios(), v.getValorDiaria(), v.getDescricao());
    }

    public Veiculo toModel(){
        return new Veiculo(id, fabricante, modelo.toModel(), categoria, acessorios, valorDiaria, descricao);
    }
    
}
