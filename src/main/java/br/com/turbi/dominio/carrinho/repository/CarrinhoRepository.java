package br.com.turbi.dominio.carrinho.repository;

import br.com.turbi.dominio.carrinho.entity.Carrinho;
import br.com.turbi.dominio.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Optional<Carrinho> findByCliente(Cliente cliente);
}
