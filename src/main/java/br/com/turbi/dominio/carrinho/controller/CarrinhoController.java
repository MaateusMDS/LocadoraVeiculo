package br.com.turbi.dominio.carrinho.controller;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import br.com.turbi.dominio.aluguel.repository.AluguelRepository;
import br.com.turbi.dominio.carrinho.dto.CarrinhoDTO;
import br.com.turbi.dominio.carrinho.entity.Carrinho;
import br.com.turbi.dominio.carrinho.repository.CarrinhoRepository;
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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/cliente/{cliente}/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository repo;

    @Autowired
    private ClienteRepository repoCliente;

    @Autowired
    private AluguelRepository repoAluguel;

    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<CarrinhoDTO>>> findAll() {
        Collection<Carrinho> carrinhos = repo.findAll();
        return ResponseEntity.ok(toCollectionHATEOAS(carrinhos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CarrinhoDTO>> findById(@PathVariable Long id) {
        var carrinho = repo.findById(id);
        if (carrinho.isPresent()) {
            EntityModel<CarrinhoDTO> entityModel = toHATEOAS(carrinho.get());
            return ResponseEntity.ok(entityModel);
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<EntityModel<CarrinhoDTO>> save(@PathVariable("cliente") long idCliente, @RequestBody CarrinhoDTO dto, UriComponentsBuilder ucBuilder) {

        Optional<Cliente> optionalCliente = repoCliente.findById(idCliente);
        if (optionalCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cliente cliente = optionalCliente.get();
        Carrinho carrinho = repo.findByCliente(cliente).orElse(new Carrinho());

        Set<Aluguel> alugueis = dto.aluguel().stream()
                .map(aluguel -> repoAluguel.findById(aluguel.getId()).orElse(null))
                .filter(Objects::nonNull) // Filtra objetos não nulos
                .collect(Collectors.toSet());

        // Se o carrinho não existir, configure os atributos
        if (carrinho.getId() == 0) {
            carrinho.setCliente(cliente);
            carrinho.setAluguel(alugueis);
            carrinho.setCustoTotal(carrinho.calcularValorCarrinho());
        } else { // Se o carrinho já existir, atualize os atributos
            carrinho.getAluguel().addAll(alugueis);
            carrinho.calcularValorCarrinho();
        }

        Carrinho savedCarrinho = repo.save(carrinho);

        var uri = ucBuilder.path("/{id}").buildAndExpand(savedCarrinho.getId()).toUri();
        return ResponseEntity.created(uri).body(toHATEOAS(savedCarrinho));
    }


    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<CarrinhoDTO> update(@PathVariable Long id, @RequestBody CarrinhoDTO c) {
        Optional<Carrinho> carrinho = repo.findById(id);

        if (carrinho.isPresent()) {
            Carrinho carrinhoAtualizado = carrinho.get();

            if (Objects.nonNull(c.cliente())) {
                carrinhoAtualizado.setCliente(c.cliente());
            }

            if (Objects.nonNull(c.aluguel())) {
                carrinhoAtualizado.setAluguel(c.aluguel());
            }

            return ResponseEntity.ok(CarrinhoDTO.of(carrinhoAtualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        var carrinho = repo.findById(id);
        if (carrinho.isPresent()) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private CollectionModel<EntityModel<CarrinhoDTO>> toCollectionHATEOAS(Collection<Carrinho> carrinhos) {
        var dtos = CollectionModel.of(carrinhos.stream().map(this::toHATEOAS).collect(Collectors.toSet()));
        dtos.add(linkTo(methodOn(CarrinhoController.class).findAll()).withSelfRel());
        return dtos;
    }

    private EntityModel<CarrinhoDTO> toHATEOAS(Carrinho carrinho) {
        EntityModel<CarrinhoDTO> model;

        model = EntityModel.of(CarrinhoDTO.of(carrinho))
                .add(linkTo(
                        methodOn(CarrinhoController.class)
                                .findById(carrinho.getId()))
                        .withSelfRel()
                        .withTitle(carrinho.getCliente().getNome())
                );

        model.add(linkTo(methodOn(CarrinhoController.class).findAll()).withRel(IanaLinkRelations.COLLECTION));
        return model;
    }

}
