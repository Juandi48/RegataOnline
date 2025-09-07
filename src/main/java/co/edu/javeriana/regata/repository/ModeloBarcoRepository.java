package co.edu.javeriana.regata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.regata.domain.ModeloBarco;

@Repository
public interface ModeloBarcoRepository extends JpaRepository<ModeloBarco, Long> {}
