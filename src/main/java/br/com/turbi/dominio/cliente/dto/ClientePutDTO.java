package br.com.turbi.dominio.cliente.dto;

import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.cliente.entity.Sexo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Relation(collectionRelation = "clientes")
public record ClientePutDTO(
        long id,
        String nome,
        @Past(message = "A data de nascimento deve ser anterior a data atual")
        LocalDate dataNascimento,
        String cpf,
        String cnh,
        @Email(message = "O campo email está inválido")
        String email,
        String senha,
        Sexo sexo
) {

    public static Set<ClientePutDTO> of(Collection<Cliente> c) {
        return c.stream().map(ClientePutDTO::of).collect(Collectors.toCollection(LinkedHashSet<ClientePutDTO>::new));
    }

    public static ClientePutDTO of(Long id) {
        return new ClientePutDTO(
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

    public static ClientePutDTO of(Cliente c) {
        return new ClientePutDTO(c.getId(), c.getNome(), c.getDataNascimento(), c.getCpf(), c.getCnh(), c.getEmail(), c.getSenha(), c.getSexo());
    }

    public Cliente toModel(){
        return new Cliente(id, nome, dataNascimento, cpf, cnh, email, senha, sexo);
    }
}