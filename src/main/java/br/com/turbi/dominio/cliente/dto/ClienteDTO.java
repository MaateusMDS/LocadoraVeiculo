package br.com.turbi.dominio.cliente.dto;

import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.cliente.entity.Sexo;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Relation(collectionRelation = "clientes")
public record ClienteDTO(
        long id,
        String nome,
        LocalDate dataNascimento,
        String cpf,
        String cnh,
        String email,
        String senha,
        Sexo sexo
) {

    public static Set<ClienteDTO> of(Collection<Cliente> c) {
        return c.stream().map(ClienteDTO::of).collect(Collectors.toCollection(LinkedHashSet<ClienteDTO>::new));
    }

    public static ClienteDTO of(Long id) {
        return new ClienteDTO(
                id,
                "",
                null,
                "",
                "",
                "",
                "",
                null
        );
    }

    public static ClienteDTO of(Cliente c) {
        return new ClienteDTO(c.getId(), c.getNome(), c.getDataNascimento(), c.getCpf(), c.getCnh(), c.getEmail(), c.getSenha(), c.getSexo());
    }

    public Cliente toModel(){
        return new Cliente(id, nome, dataNascimento, cpf, cnh, email, senha, sexo);
    }
}