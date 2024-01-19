package uz.islombek.course.springsecurity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Ism bosh bo'lmasligi kerak")
    @Size(min = 2,max = 20,message = "ism kamida 2 harfdan iborat bo'lishi zarur!")
    @Column(name = "username")
    private String username;
    @Column(name = "year_of_birth")
    private int yearOfBirth;
    @Column(name = "parol")
    private String parol;

    @Column(name = "role")
    private String role;

    public Person() {
    }

    public Person(int id, String username, int yearOfBirth, String parol) {
        this.id = id;
        this.username = username;
        this.yearOfBirth = yearOfBirth;
        this.parol = parol;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Person(String username, int yearOfBirth, String parol) {
        this.username = username;
        this.yearOfBirth = yearOfBirth;
        this.parol = parol;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", parol='" + parol + '\'' +
                '}';
    }

    public Person(String username, String parol) {
        this.username = username;
        this.parol = parol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
