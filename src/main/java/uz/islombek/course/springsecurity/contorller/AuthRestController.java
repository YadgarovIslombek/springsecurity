package uz.islombek.course.springsecurity.contorller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import uz.islombek.course.springsecurity.dto.AuthenticationDTO;
import uz.islombek.course.springsecurity.dto.PersonDTO;
import uz.islombek.course.springsecurity.model.Person;
import uz.islombek.course.springsecurity.service.JWTUtil;
import uz.islombek.course.springsecurity.service.PersonRegisterService;
import uz.islombek.course.springsecurity.util.PersonValidator;

import java.util.Map;

@RestController
@RequestMapping("/auth/v1")
public class AuthRestController {
    private final PersonValidator personValidator;
    private final PersonRegisterService personRegisterService;
    private final JWTUtil util;

    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthRestController(PersonValidator personValidator, PersonRegisterService personRegisterService, JWTUtil util, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.personRegisterService = personRegisterService;
        this.util = util;
        this.modelMapper = modelMapper;

        this.authenticationManager = authenticationManager;
    }


//    @GetMapping("/login")
//    public String loginPage() {
//        return "auth/login";
//    }
//
//
//    @GetMapping("/registration")
//    public String register(@ModelAttribute("person") Person person) {
//        return "auth/registration";
//    }
    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> registerPerson(@RequestBody
                                                              @Valid PersonDTO personDTO, BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok(Map.of("Message", "Xatolik"));
        }
        personRegisterService.savePerson(person);
        String token = util.generateToken(personDTO.getUsername());
        return ResponseEntity.ok(Map.of("token", token)); //jackson convert qiladi
    }
    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody
                                            @Valid AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),authenticationDTO.getParol());
        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            return Map.of("Error","Userni parol yoki username xato");
        }
        String token = util.generateToken(authenticationDTO.getUsername());
        return Map.of("token",token);
    }
}
