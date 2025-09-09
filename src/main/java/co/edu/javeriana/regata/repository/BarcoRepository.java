package co.edu.javeriana.regata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.regata.domain.Barco;

@Repository
public interface BarcoRepository extends JpaRepository<Barco, Long> {
}
