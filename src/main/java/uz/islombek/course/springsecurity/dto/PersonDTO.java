package uz.islombek.course.springsecurity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PersonDTO {
    @NotEmpty(message = "Ism bo'sh bo'lmasligi kerak")
    @Size(min = 2,max = 100,message = "Ism 2 harfdan katta bo'lishi zarur!")
    private String username;
    @Min(value = 1900,message = "Yosh 1900 dan baland bo'lishi zarur")
    private int yearOfBirth;

    private String parol;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getParol() {
        return parol;
    }

    public void setParol(String parol) {
        this.parol = parol;
    }
}
