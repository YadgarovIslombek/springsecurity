package uz.islombek.course.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.islombek.course.springsecurity.model.Person;
import uz.islombek.course.springsecurity.repository.PersonRepository;
import uz.islombek.course.springsecurity.security.PersonDetail;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonDetailService implements UserDetailsService {
    @Autowired
    private PersonRepository personRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> byUsername = personRepository.findByUsername(username);
        if (byUsername.isEmpty()){
            throw new UsernameNotFoundException("User not Found");
        }
        System.out.println("UserDetails: " + byUsername.get());

        return new PersonDetail(byUsername.get());
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
    public Person findOne(int id){
        return personRepository.findById(id).orElse(null);
    }
}
