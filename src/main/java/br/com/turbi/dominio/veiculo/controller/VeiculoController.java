package br.com.turbi.dominio.veiculo.controller;

import br.com.turbi.dominio.cliente.dto.ClienteDTO;
import br.com.turbi.dominio.cliente.dto.ClientePutDTO;
import br.com.turbi.dominio.cliente.entity.Cliente;
import br.com.turbi.dominio.veiculo.dto.ModeloDTO;
import br.com.turbi.dominio.veiculo.dto.VeiculoDTO;
import br.com.turbi.dominio.veiculo.dto.VeiculoPutDTO;
import br.com.turbi.dominio.veiculo.entity.Veiculo;
import br.com.turbi.dominio.veiculo.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository repo;

    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<VeiculoDTO>>> findAll() {
        Collection<Veiculo> veiculos = repo.findAll();
        return ResponseEntity.ok(toCollectionHATEOAS(veiculos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<VeiculoDTO>> findById(@PathVariable Long id) {
        var veiculo = repo.findById(id);
        if (veiculo.isPresent()) {
            EntityModel<VeiculoDTO> entityModel = toHATEOAS(veiculo.get());
            return ResponseEntity.ok(entityModel);
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<EntityModel<VeiculoDTO>> save(@Valid @RequestBody VeiculoDTO dto, UriComponentsBuilder ucBuilder) {
        var veiculo = repo.save(dto.toModel());
        var uri = ucBuilder.path("/{id}").buildAndExpand(veiculo.getId()).toUri();
        return ResponseEntity.created(uri).body(toHATEOAS(veiculo));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO> update(@PathVariable Long id, @RequestBody @Valid VeiculoPutDTO c){
        Optional<Veiculo> veiculo = repo.findById(id);

        if (veiculo.isPresent()) {
            Veiculo clienteAtualizado = veiculo.get();

            if (Objects.nonNull(c.modelo().cor())) {
                clienteAtualizado.getModelo().setCor(c.modelo().cor());
            }

            if (Objects.nonNull(c.modelo().placa())) {
                clienteAtualizado.getModelo().setPlaca(c.modelo().placa());
            }

            if (Objects.nonNull(c.modelo().chassi())) {
                clienteAtualizado.getModelo().setChassi(c.modelo().chassi());
            }

            if (Objects.nonNull(c.fabricante())) {
                clienteAtualizado.setFabricante(c.fabricante());
            }

            if (Objects.nonNull(c.categoria())) {
                clienteAtualizado.setCategoria(c.categoria());
            }

            if (Objects.nonNull(c.acessorios())) {
                clienteAtualizado.setAcessorios(c.acessorios());
            }

            if (Objects.nonNull(c.descricao())) {
                clienteAtualizado.setDescricao(c.descricao());
            }

            if (Objects.nonNull(c.valorDiaria())) {
                clienteAtualizado.setValorDiaria(c.valorDiaria());
            }

            return ResponseEntity.ok(VeiculoDTO.of(clienteAtualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        var veiculo = repo.findById(id);
        if (veiculo.isPresent()) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CollectionModel<EntityModel<VeiculoDTO>> toCollectionHATEOAS(Collection<Veiculo> veiculos) {
        var dtos = CollectionModel.of(veiculos.stream().map(this::toHATEOAS).collect(Collectors.toSet()));
        dtos.add(linkTo(methodOn(br.com.turbi.dominio.veiculo.controller.VeiculoController.class).findAll()).withSelfRel());
        return dtos;
    }

    private EntityModel<VeiculoDTO> toHATEOAS(Veiculo veiculo) {
        EntityModel<VeiculoDTO> model;

        model = EntityModel.of(VeiculoDTO.of(veiculo))
                .add(linkTo(
                        methodOn(br.com.turbi.dominio.veiculo.controller.VeiculoController.class)
                                .findById(veiculo.getId()))
                        .withSelfRel()
                        .withTitle(veiculo.getModelo().getPlaca())
                );

        model.add(linkTo(methodOn(br.com.turbi.dominio.veiculo.controller.VeiculoController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
        return model;
    }

}
