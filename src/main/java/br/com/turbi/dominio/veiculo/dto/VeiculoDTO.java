package br.com.turbi.dominio.veiculo.dto;

import br.com.turbi.dominio.veiculo.entity.Veiculo;
import br.com.turbi.dominio.veiculo.entity.Categoria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Relation(collectionRelation = "veiculos")
public record VeiculoDTO(
        long id,
        @NotBlank(message = "O campo fabricante é obrigatório")
        String fabricante,
        @NotNull(message = "O campo modelo é obrigatório")
        ModeloDTO modelo,
        @NotNull(message = "O campo categoria é obrigatório")
        Categoria categoria,
        @NotNull(message = "O campo acessorios é obrigatório")
        ArrayList<String> acessorios,
        @NotNull(message = "O campo valor da diária é obrigatório") @Min(value = 0, message = "O valor da diária deve ser maior que zero")
        BigDecimal valorDiaria,
        @NotNull(message = "O campo descrição é obrigatório")
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
