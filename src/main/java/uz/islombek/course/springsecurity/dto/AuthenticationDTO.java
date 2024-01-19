package uz.islombek.course.springsecurity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthenticationDTO {
    @NotEmpty(message = "Ism bo'sh bo'lmasligi kerak")
    @Size(min = 2,max = 100,message = "Ism 2 harfdan katta bo'lishi zarur!")
    private String username;

    private String parol;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParol() {
        return parol;
    }

    public void setParol(String parol) {
        this.parol = parol;
    }
}
