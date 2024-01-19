package uz.islombek.course.springsecurity.contorller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.islombek.course.springsecurity.model.Person;
import uz.islombek.course.springsecurity.service.PersonRegisterService;
import uz.islombek.course.springsecurity.util.PersonValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final PersonRegisterService personRegisterService;

    @Autowired
    public AuthController(PersonValidator personValidator, PersonRegisterService personRegisterService) {
        this.personValidator = personValidator;
        this.personRegisterService = personRegisterService;
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
    public String registerPerson(@ModelAttribute("person")@Valid Person person, BindingResult bindingResult) {
       personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "/auth/registration";

        personRegisterService.savePerson(person);
        return "redirect:/auth/login";
    }
}
