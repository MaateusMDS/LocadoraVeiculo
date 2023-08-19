package br.com.turbi.dominio.cliente.controller;

import br.com.turbi.dominio.cliente.dto.ClienteDTO;
import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.cliente.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository repo;

    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> findAll() {
        Collection<Cliente> clientes = repo.findAll();
        return ResponseEntity.ok(toCollectionHATEOAS(clientes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDTO>> findById(@PathVariable Long id) {
        var cliente = repo.findById(id);
        if (cliente.isPresent()) {
            EntityModel<ClienteDTO> entityModel = toHATEOAS(cliente.get());
            return ResponseEntity.ok(entityModel);
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<EntityModel<ClienteDTO>> save(@RequestBody ClienteDTO dto, UriComponentsBuilder ucBuilder) {
        var cliente = repo.save(dto.toModel());
        var uri = ucBuilder.path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(toHATEOAS(cliente));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteDTO c){
        Optional<Cliente> cliente = repo.findById(id);

        if (cliente.isPresent()) {
            Cliente clienteAtualizado = cliente.get();
            if (Objects.nonNull(c.nome())) {
                clienteAtualizado.setNome(c.nome());
            }

            if (Objects.nonNull(c.dataNascimento())) {
                clienteAtualizado.setDataNascimento(c.dataNascimento());
            }

            if (Objects.nonNull(c.cpf())) {
                clienteAtualizado.setCpf(c.cpf());
            }

            if (Objects.nonNull(c.cnh())) {
                clienteAtualizado.setCnh(c.cnh());
            }

            if (Objects.nonNull(c.email())) {
                clienteAtualizado.setEmail(c.email());
            }

            if (Objects.nonNull(c.senha())) {
                clienteAtualizado.setSenha(c.senha());
            }

            if (Objects.nonNull(c.sexo())) {
                clienteAtualizado.setSexo(c.sexo());
            }

            return ResponseEntity.ok(ClienteDTO.of(clienteAtualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        var cliente = repo.findById(id);
        if (cliente.isPresent()) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CollectionModel<EntityModel<ClienteDTO>> toCollectionHATEOAS(Collection<Cliente> clientes) {
        var dtos = CollectionModel.of(clientes.stream().map(this::toHATEOAS).collect(Collectors.toSet()));
        dtos.add(linkTo(methodOn(ClienteController.class).findAll()).withSelfRel());
        return dtos;
    }

    private EntityModel<ClienteDTO> toHATEOAS(Cliente cliente) {
        EntityModel<ClienteDTO> model;

        model = EntityModel.of(ClienteDTO.of(cliente))
                .add(linkTo(
                        methodOn(ClienteController.class)
                                .findById(cliente.getId()))
                        .withSelfRel()
                        .withTitle(cliente.getNome())
                );

        model.add(linkTo(methodOn(ClienteController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
        return model;
    }

}
