package fr.cyu.airportmadness.entity.person;

import fr.cyu.airportmadness.security.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "person", orphanRemoval = true)
    private Set<User> users = new LinkedHashSet<>();

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;


    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "nationality", nullable = false)
    private String nationality;


    public Person() {
    }

    public Person(String firstName, String lastName, LocalDate birthdate, Gender gender, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.nationality = nationality;
    }
    public Set<User> getUsers() {
        return this.users;
    }

    public Person setUsers(Set<User> users) {
        this.users = users;
        return this;

    }

    public Long getId() {
        return id;
    }

    public Person setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Person setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Person setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    public Person setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }
}