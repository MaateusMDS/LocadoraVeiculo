package br.com.turbi.dominio.veiculo.dto;

import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.veiculo.entity.Modelo;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ModeloDTO (
    String placa,
    String chassi,
    String cor
){

    public static Set<ModeloDTO> of(Collection<Modelo> m) {
        return m.stream().map(ModeloDTO::of).collect(Collectors.toCollection(LinkedHashSet<ModeloDTO>::new));
    }

    public static ModeloDTO of(String placa, String chassi, String cor){
        return new ModeloDTO(placa, chassi, cor);
    }

    public static ModeloDTO of(Modelo m) {
        return new ModeloDTO(m.getPlaca(), m.getChassi(), m.getCor());
    }

    public Modelo toModel(){
        return new Modelo(placa, chassi, cor);
    }

}
