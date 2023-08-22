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
    public ResponseEntity<EntityModel<CarrinhoDTO>> find(@PathVariable("cliente") Long id) {
        var carrinho = repo.findByClienteId(id);
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
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (carrinho.getId() == 0) {
            carrinho.setCliente(cliente);
            carrinho.setAluguel(alugueis);
            carrinho.setCustoTotal(carrinho.calcularValorCarrinho());
        } else {
            carrinho.getAluguel().addAll(alugueis);
            carrinho.calcularValorCarrinho();
        }

        Carrinho savedCarrinho = repo.save(carrinho);

        var uri = ucBuilder.path("/{id}").buildAndExpand(savedCarrinho.getId()).toUri();
        return ResponseEntity.created(uri).body(toHATEOAS(savedCarrinho));
    }


    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAluguel (@PathVariable("cliente") Long idCliente, @PathVariable("id") Long idAluguel) {
        var carrinho = repo.findByClienteId(idCliente);

        if (carrinho.isPresent()) {
            var aluguel = repoAluguel.findById(idAluguel);
            if (aluguel.isPresent()) {
                carrinho.get().getAluguel().remove(aluguel.get());
                repo.save(carrinho.get());
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.notFound().build();
    }


    @Transactional
    @DeleteMapping()
    public ResponseEntity<Object> delete(@PathVariable("cliente") Long id) {
        var carrinho = repo.findByClienteId(id);

        if (carrinho.isPresent()) {
            repo.deleteByClienteId(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    private EntityModel<CarrinhoDTO> toHATEOAS(Carrinho carrinho) {
        EntityModel<CarrinhoDTO> model;

        model = EntityModel.of(CarrinhoDTO.of(carrinho))
                .add(linkTo(
                        methodOn(CarrinhoController.class)
                                .find(carrinho.getId()))
                        .withSelfRel()
                        .withTitle(carrinho.getCliente().getNome())
                );

        model.add(linkTo(methodOn(CarrinhoController.class)).withRel(IanaLinkRelations.COLLECTION));
        return model;
    }

}
