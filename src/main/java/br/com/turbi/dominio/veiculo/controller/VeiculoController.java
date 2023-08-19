package br.com.turbi.dominio.veiculo.controller;

import br.com.turbi.dominio.veiculo.dto.VeiculoDTO;
import br.com.turbi.dominio.veiculo.entity.Veiculo;
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
    public ResponseEntity<EntityModel<VeiculoDTO>> save(@RequestBody VeiculoDTO dto, UriComponentsBuilder ucBuilder) {
        var veiculo = repo.save(dto.toModel());
        var uri = ucBuilder.path("/{id}").buildAndExpand(veiculo.getId()).toUri();
        return ResponseEntity.created(uri).body(toHATEOAS(veiculo));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO> update(@PathVariable Long id, @RequestBody VeiculoDTO v){
        Optional<Veiculo> veiculo = repo.findById(id);

        if (veiculo.isPresent()) {
            Veiculo veiculoAtualizado = veiculo.get();


            if (Objects.nonNull(v.fabricante())) {
                veiculoAtualizado.setFabricante(v.fabricante());
            }

            if (Objects.nonNull(v.modelo())) {
                veiculoAtualizado.setModelo(v.modelo().toModel());
            }

            if (Objects.nonNull(v.categoria())) {
                veiculoAtualizado.setCategoria(v.categoria());
            }

            if (Objects.nonNull(v.acessorios())) {
                veiculoAtualizado.setAcessorios(v.acessorios());
            }

            if (Objects.nonNull(v.valorDiaria())) {
                veiculoAtualizado.setValorDiaria(v.valorDiaria());
            }

            if (Objects.nonNull(v.descricao())) {
                veiculoAtualizado.setDescricao(v.descricao());
            }

            if (Objects.nonNull(v.modelo().cor())) {
                veiculoAtualizado.getModelo().setCor(v.modelo().cor());
            }

            if (Objects.nonNull(v.modelo().chassi())) {
                veiculoAtualizado.getModelo().setChassi(v.modelo().chassi());
            }

            if (Objects.nonNull(v.modelo().placa())) {
                veiculoAtualizado.getModelo().setPlaca(v.modelo().placa());
            }
            return ResponseEntity.ok(VeiculoDTO.of(veiculoAtualizado));
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
