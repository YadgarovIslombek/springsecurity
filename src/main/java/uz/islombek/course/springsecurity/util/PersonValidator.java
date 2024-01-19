package uz.islombek.course.springsecurity.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import uz.islombek.course.springsecurity.model.Person;
import uz.islombek.course.springsecurity.service.PersonDetailService;

@Component
public class PersonValidator implements Validator {
    private final PersonDetailService personDetailService;

    @Autowired
    public PersonValidator(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            personDetailService.loadUserByUsername(person.getUsername());
        }catch (UsernameNotFoundException e){
            return; //
        }
        errors.rejectValue("username","","Bunday user bor");
    }
}
