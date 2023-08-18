package br.com.turbi.dominio.veiculo.repository;

import br.com.turbi.dominio.veiculo.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}
