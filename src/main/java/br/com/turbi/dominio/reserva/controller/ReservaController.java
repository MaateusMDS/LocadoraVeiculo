package br.com.turbi.dominio.reserva.controller;

import br.com.turbi.dominio.aluguel.controller.AluguelController;
import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.aluguel.repository.AluguelRepository;
import br.com.turbi.dominio.cliente.entity.Cliente;
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

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/cliente/{cliente}/reserva")
public class ReservaController {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private ClienteRepository repoCliente;

    @Autowired
    private AluguelRepository repoAluguel;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReservaDTO>>> findAll(@PathVariable("cliente") Long id) {
        Collection<Reserva> reservas = repository.findByClienteId(id);
        return ResponseEntity.ok(toCollectionHATEOAS(reservas));

    }

    @Transactional
    @PostMapping
    public ResponseEntity<EntityModel<ReservaDTO>> save(@PathVariable("cliente") long idCliente, @RequestBody ReservaDTO dto, UriComponentsBuilder ucBuilder) {
        Optional<Cliente> optionalCliente = repoCliente.findById(idCliente);
        if (optionalCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var cliente = optionalCliente.get();
        var aluguel = repoAluguel.findById(dto.aluguel().getId()).orElse(null);

        if (aluguel == null) {
            return ResponseEntity.notFound().build();
        }

        var reserva = repository.save(new Reserva(dto.id(), aluguel, cliente));

        var uri = ucBuilder.path("/{id}").buildAndExpand(reserva.getId()).toUri();
        return ResponseEntity.created(uri).body(toHATEOAS(reserva));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "cliente") Long idCliente, @PathVariable(name = "id") Long idReserva) {
        var reserva = repository.findByClienteId(idCliente);

        if (reserva != null) {
            repository.deleteById(idReserva);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CollectionModel<EntityModel<ReservaDTO>> toCollectionHATEOAS(Collection<Reserva> reservas) {
        var dtos = CollectionModel.of(reservas.stream().map(this::toHATEOAS).collect(Collectors.toSet()));
        dtos.add(linkTo(methodOn(AluguelController.class).findAll()).withSelfRel());
        return dtos;
    }


    private EntityModel<ReservaDTO> toHATEOAS(Reserva reserva) {
        EntityModel<ReservaDTO> model = EntityModel.of(ReservaDTO.of(reserva));
        model.add(linkTo(
                methodOn(ReservaController.class)
                        .findAll(reserva.getCliente().getId()))
                .withSelfRel()
                .withTitle(reserva.getAluguel().getCliente().getNome())
        );

        model.add(linkTo(methodOn(ReservaController.class).findAll(reserva.getCliente().getId())).withRel(IanaLinkRelations.COLLECTION));
        return model;
    }
}
