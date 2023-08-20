package br.com.turbi.dominio.aluguel.repository;

import br.com.turbi.dominio.aluguel.entity.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
}
