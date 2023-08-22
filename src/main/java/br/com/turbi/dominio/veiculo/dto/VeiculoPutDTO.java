package br.com.turbi.dominio.veiculo.dto;

import br.com.turbi.dominio.veiculo.entity.Categoria;
import br.com.turbi.dominio.veiculo.entity.Veiculo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Relation(collectionRelation = "veiculos")
public record VeiculoPutDTO(
        long id,
        String fabricante,
        ModeloPutDTO modelo,
        Categoria categoria,
        ArrayList<String> acessorios,
        @Min(value = 0, message = "O valor da diária deve ser maior que zero")
        BigDecimal valorDiaria,
        String descricao
) {

    public static Set<VeiculoPutDTO> of(Collection<Veiculo > v) {
        return v.stream().map(VeiculoPutDTO::of).collect(Collectors.toCollection(LinkedHashSet<VeiculoPutDTO>::new));
    }
    
    public static VeiculoPutDTO of(Long id) {
        return new VeiculoPutDTO(
                id,
                "",
                null,
                null,
                null,
                null,
                ""
        );
    }

    public static VeiculoPutDTO of(Veiculo v) {
        return new VeiculoPutDTO(v.getId(), v.getFabricante(), ModeloPutDTO.of(v.getModelo()), v.getCategoria(), v.getAcessorios(), v.getValorDiaria(), v.getDescricao());
    }

    public Veiculo toModel(){
        return new Veiculo(id, fabricante, modelo.toModel(), categoria, acessorios, valorDiaria, descricao);
    }
    
}
