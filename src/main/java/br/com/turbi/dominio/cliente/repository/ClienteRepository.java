package br.com.turbi.dominio.cliente.repository;

import br.com.turbi.dominio.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
