package uz.islombek.course.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.islombek.course.springsecurity.model.Person;

import java.util.Optional;

@Repository
public interface PersonRepository  extends JpaRepository<Person,Integer> {

    Optional<Person> findByUsername(String name);
}
