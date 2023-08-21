package br.com.turbi.dominio.reserva.controller;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.aluguel.repository.AluguelRepository;
import br.com.turbi.dominio.cliente.repository.ClienteRepository;
import br.com.turbi.dominio.reserva.dto.ReservaDTO;
import br.com.turbi.dominio.reserva.entity.Reserva;
import br.com.turbi.dominio.reserva.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    ClienteRepository repoCliente;

    @Autowired
    AluguelRepository repoAluguel;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> findAll() {
        Collection<Reserva> reservas = repository.findAll();
        return ResponseEntity.ok(toCollectionHATEAOS(reservas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReservaDTO>> findById(@PathVariable Long id) {
        var reserva = repository.findById(id);
        if (reserva.isPresent()) {
            EntityModel<ReservaDTO> entityModel = toHATEOAS(reserva.get());
            return ResponseEntity.ok(entityModel);
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<EntityModel<ReservaDTO>> save(@RequestBody ReservaDTO dto, UriComponentsBuilder ucBuilder) {

        var cliente = repoCliente.findById(dto.cliente().getId()).orElse(null);
        var aluguel = repoAluguel.findById(dto.aluguel().getId()).orElse(null);

        if (cliente == null || aluguel == null) {
            return ResponseEntity.badRequest().build();
        }

        var reserva = repository.save(new Reserva(dto.id(), aluguel, cliente));

        var uri = ucBuilder.path("/{id}").buildAndExpand(reserva).toUri();
        return ResponseEntity.created(uri).body(toHATEOAS(reserva));

    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> update(@PathVariable Long id, @RequestBody ReservaDTO r) {
        Optional<Reserva> reserva = repository.findById(id);

        if (reserva.isPresent()) {
            Reserva reservaAtualizada = reserva.get();

            if (Objects.nonNull(r.aluguel())) {
                reservaAtualizada.setAluguel(r.aluguel());
            }
            if (Objects.nonNull(r.cliente())) {
                reservaAtualizada.setCliente(r.cliente());
            }
            return ResponseEntity.ok(ReservaDTO.of(reservaAtualizada));
        }

        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        var reserva = repository.findById(id);
        if (reserva.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CollectionModel<EntityModel<ReservaDTO>> toCollectionHATEAOS(Collection<Reserva> reservas) {
        var dtos = CollectionModel.of(reservas.stream().map(this::toHATEOAS).collect(Collectors.toSet()));
        dtos.add(linkTo(methodOn(br.com.turbi.dominio.reserva.controller.ReservaController.class).findAll()).withSelfRel());
        return dtos;
    }

    private EntityModel<ReservaDTO> toHATEOAS(Reserva reserva) {
        EntityModel<ReservaDTO> model;

        model = EntityModel.of(ReservaDTO.of(reserva))
                .add(linkTo(
                        methodOn(br.com.turbi.dominio.reserva.controller.ReservaController.class)
                                .findById(reserva.getId()))
                        .withSelfRel()
                        .withTitle(reserva.getAluguel().toString())
                );

        model.add(linkTo(methodOn(br.com.turbi.dominio.reserva.controller.ReservaController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
        return model;
    }

}
