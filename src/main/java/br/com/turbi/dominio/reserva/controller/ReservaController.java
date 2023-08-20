package br.com.turbi.dominio.reserva.controller;

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

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaRepository repository;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> findAll(){
        Collection<Reserva> reservas = repository.findAll();
        return ResponseEntity.ok(toCollectionHATEAOS(reservas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReservaDTO>> findById(@PathVariable Long id){
        var reserva = repository.findById(id);
        if(reserva.isPresent()){
            EntityModel<ReservaDTO> entityModel = toHATEOAS(reserva.get());
            return ResponseEntity.ok(entityModel);
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<EntityModel<ReservaDTO>> save(@RequestBody ReservaDTO dto, UriComponentsBuilder rvBuilder){
        var reserva = repository.save(dto.toModel());
        var uri = rvBuilder.path("/{id}").buildAndExpand(reserva.getId()).toUri();
        return ResponseEntity.created(uri).body(toHATEOAS(reserva));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> update(@PathVariable Long id, @RequestBody ReservaDTO r){
        Optional<Reserva> reserva = repository.findById(id);

        if(reserva.isPresent()){
            Reserva reservaAtualizada = reserva.get();

            if(Objects.nonNull(r.aluguel())){
                reservaAtualizada.setAluguel(r.aluguel());
            }
            if(Objects.nonNull(r.cliente())){
                reservaAtualizada.setCliente(r.cliente().toModel());
            }
            return ResponseEntity.ok(ReservaDTO.of(reservaAtualizada));
        }

        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id){
        var reserva = repository.findById(id);
        if(reserva.isPresent()){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CollectionModel<EntityModel<ReservaDTO>> toCollectionHATEAOS(Collection<Reserva> reservas){
        var dtos = CollectionModel.of(reservas.stream().map(this::toHATEOAS).collect(Collectors.toSet()));
        dtos.add(linkTo(methodOn(br.com.turbi.dominio.reserva.controller.ReservaController.class).findAll()).withSelfRel());
        return dtos;
    }

    private EntityModel<ReservaDTO> toHATEOAS(Reserva reserva){
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
