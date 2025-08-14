package co.edu.javeriana.ejemplojpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.ejemplojpa.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
