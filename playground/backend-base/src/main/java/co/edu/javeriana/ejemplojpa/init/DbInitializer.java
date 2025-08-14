package co.edu.javeriana.ejemplojpa.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.edu.javeriana.ejemplojpa.model.Company;
import co.edu.javeriana.ejemplojpa.model.Person;
import co.edu.javeriana.ejemplojpa.repository.CompanyRepository;
import co.edu.javeriana.ejemplojpa.repository.PersonRepository;

@Component
public class DbInitializer implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        Person person = personRepository.save(
                new Person("123",
                        "Alice",
                        "Alisson"));
        Company company = companyRepository.save(
            new Company("company1", "123445")
        );

        company.getEmployees().add(person);
        // person.setEmployer(company);
        person.getEmployers().add(company);
        personRepository.save(person);

        
    
    }

}
