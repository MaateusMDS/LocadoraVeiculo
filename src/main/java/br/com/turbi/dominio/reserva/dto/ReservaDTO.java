package br.com.turbi.dominio.reserva.dto;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.cliente.dto.ClienteDTO;
import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.reserva.entity.Reserva;
import org.springframework.hateoas.server.core.Relation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Relation(collectionRelation = "reservas")
public record ReservaDTO (
        long id,
        Set<Aluguel> aluguel,
        ClienteDTO cliente
)   {

    public static Set<ReservaDTO> of(Collection<Reserva> c){
        return c.stream().map(ReservaDTO::of).collect(Collectors.toCollection(LinkedHashSet< ReservaDTO>::new));
    }

    public static ReservaDTO of(Long id){
        return new ReservaDTO(
                id,
                null,
                null

        );
    }
    public static ReservaDTO of(Reserva c){
        return new ReservaDTO(c.getId(),c.getAluguel(), ClienteDTO.of(c.getCliente()));
    }


    public Reserva toModel(){return  new Reserva(id, aluguel, cliente.toModel());}
}
