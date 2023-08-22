package br.com.turbi.dominio.reserva.repository;

import br.com.turbi.dominio.reserva.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Collection<Reserva> findByClienteId(Long id);
}
