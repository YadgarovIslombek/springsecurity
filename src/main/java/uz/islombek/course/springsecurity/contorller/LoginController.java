package uz.islombek.course.springsecurity.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uz.islombek.course.springsecurity.security.PersonDetail;
import uz.islombek.course.springsecurity.service.AdminService;

@Controller
public class LoginController {
    private final AdminService adminService;

    @Autowired
    public LoginController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }

    @GetMapping("/admin")
    public String adminPage(){
        adminService.doAdmin();
        return "admin";
    }

    @GetMapping("/showUserInfo")
    @ResponseBody
    public String showUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetail personDetail = (PersonDetail) authentication.getPrincipal();
        System.out.println(personDetail.getUsername());
        return personDetail.getUsername();
    }
}
