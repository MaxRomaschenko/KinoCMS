package com.avada.edu.kinoCMS.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "secondname")
    private String secondName;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "birthday_date")
    private Timestamp birthdayDate;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "address")
    private String address;
    @Column(name = "password")
    private String password;
    @Column(name = "card_number")
    private String cardNumber; //TODO:: поменять тип данных для номера,языка,гендера
    @Column(name = "language")
    private String language;
    @Column(name = "gender")
    private String gender;

    public User(String name, String secondName) {
        this.name = name;
        this.secondName = secondName;
    }
}
