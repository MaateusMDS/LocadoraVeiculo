package br.com.turbi.dominio.aluguel.controller;

import br.com.turbi.dominio.aluguel.dto.AluguelDTO;
import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.aluguel.repository.AluguelRepository;
import br.com.turbi.dominio.cliente.repository.ClienteRepository;
import br.com.turbi.dominio.veiculo.repository.VeiculoRepository;
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
@RequestMapping("/aluguel")
public class AluguelController {

    @Autowired
    private AluguelRepository repo;

    @Autowired
    private ClienteRepository repoCliente;

    @Autowired
    private VeiculoRepository repoVeiculo;

    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<AluguelDTO>>> findAll() {
        Collection<Aluguel> alugueis = repo.findAll();
        return ResponseEntity.ok(toCollectionHATEOAS(alugueis));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AluguelDTO>> findById(@PathVariable Long id) {
        var aluguel = repo.findById(id);
        if (aluguel.isPresent()) {
            EntityModel<AluguelDTO> entityModel = toHATEOAS(aluguel.get());
            return ResponseEntity.ok(entityModel);
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<EntityModel<AluguelDTO>> save(@RequestBody AluguelDTO dto, UriComponentsBuilder ucBuilder) {

        var cliente = repoCliente.findById(dto.cliente().getId()).orElse(null);
        var veiculo = repoVeiculo.findById(dto.veiculo().getId()).orElse(null);

        if (cliente == null || veiculo == null) {
            return ResponseEntity.notFound().build();
        }

        var alugel = new Aluguel(dto.id(), cliente, veiculo, dto.dataInicio(), dto.dataTermino(), dto.valorTotal());
        var aluguel = repo.save(alugel);

        var uri = ucBuilder.path("/{id}").buildAndExpand(aluguel.getId()).toUri();
        return ResponseEntity.created(uri).body(toHATEOAS(aluguel));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<AluguelDTO> update(@PathVariable Long id, @RequestBody AluguelDTO c){
        Optional<Aluguel> aluguel = repo.findById(id);

        if (aluguel.isPresent()) {
            Aluguel aluguelAtualizado = aluguel.get();

            if (Objects.nonNull(c.cliente())) {
                aluguelAtualizado.setCliente(c.cliente());
            }

            if (Objects.nonNull(c.veiculo())) {
                aluguelAtualizado.setVeiculo(c.veiculo());
            }

            if (Objects.nonNull(c.dataInicio())) {
                aluguelAtualizado.setDataInicio(c.dataInicio());
            }

            if (Objects.nonNull(c.dataTermino())) {
                aluguelAtualizado.setDataTermino(c.dataTermino());
            }
            return ResponseEntity.ok(AluguelDTO.of(aluguelAtualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        var aluguel = repo.findById(id);
        if (aluguel.isPresent()) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CollectionModel<EntityModel<AluguelDTO>> toCollectionHATEOAS(Collection<Aluguel> alugueis) {
        var dtos = CollectionModel.of(alugueis.stream().map(this::toHATEOAS).collect(Collectors.toSet()));
        dtos.add(linkTo(methodOn(AluguelController.class).findAll()).withSelfRel());
        return dtos;
    }

    private EntityModel<AluguelDTO> toHATEOAS(Aluguel aluguel) {
        EntityModel<AluguelDTO> model;

        model = EntityModel.of(AluguelDTO.of(aluguel))
                .add(linkTo(
                        methodOn(AluguelController.class)
                                .findById(aluguel.getId()))
                        .withSelfRel()
                        .withTitle(aluguel.getCliente().getNome())
                );

        model.add(linkTo(methodOn(AluguelController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
        return model;
    }

}
