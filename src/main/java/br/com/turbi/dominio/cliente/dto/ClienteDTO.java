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
public record ClienteDTO(
        long id,
        @NotBlank(message = "O campo nome é obrigatório")
        String nome,
        @NotNull(message = "O campo data de nascimento é obrigatório") @Past(message = "A data de nascimento deve ser anterior a data atual")
        LocalDate dataNascimento,
        @NotBlank(message = "O campo CPF é obrigatório")
        String cpf,
        @NotBlank(message = "O campo CNH é obrigatório")
        String cnh,
        @NotBlank(message = "O campo email é obrigatório") @Email(message = "O campo email é obrigatório")
        String email,
        @NotBlank(message = "O campo senha é obrigatório")
        String senha,
        @NotNull(message = "O campo sexo é obrigatório")
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