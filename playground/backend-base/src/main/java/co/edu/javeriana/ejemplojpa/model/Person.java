package co.edu.javeriana.ejemplojpa.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cedula;
    private String firstName;
    private String lastName;

    // @ManyToOne
    // private Company employer;
    @ManyToMany
    private List<Company> employers = new ArrayList<>();

    public List<Company> getEmployers() {
        return employers;
    }

    // public Company getEmployer() {
    // return employer;
    // }

    // public void setEmployer(Company employer) {
    // this.employer = employer;
    // }

    public Person() {
    }

    public Person(String cedula, String firstName, String lastName) {
        this.cedula = cedula;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
