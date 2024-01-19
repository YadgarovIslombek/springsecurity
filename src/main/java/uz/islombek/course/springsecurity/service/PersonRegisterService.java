package uz.islombek.course.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.islombek.course.springsecurity.model.Person;
import uz.islombek.course.springsecurity.repository.PersonRepository;

@Service
public class PersonRegisterService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonRegisterService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;

        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void savePerson(Person person){
        person.setParol(passwordEncoder.encode(person.getParol()));//1234->34523764dfbjh@#@!%dsgs
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }
}
