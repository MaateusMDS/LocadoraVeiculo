package br.com.turbi.dominio.veiculo.dto;

import br.com.turbi.dominio.veiculo.entity.Modelo;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ModeloPutDTO(
        String placa,
        String chassi,
        String cor
){

    public static Set<ModeloPutDTO> of(Collection<Modelo> m) {
        return m.stream().map(ModeloPutDTO::of).collect(Collectors.toCollection(LinkedHashSet<ModeloPutDTO>::new));
    }

    public static ModeloPutDTO of(String placa, String chassi, String cor){
        return new ModeloPutDTO(placa, chassi, cor);
    }

    public static ModeloPutDTO of(Modelo m) {
        return new ModeloPutDTO(m.getPlaca(), m.getChassi(), m.getCor());
    }

    public Modelo toModel(){
        return new Modelo(placa, chassi, cor);
    }

}
