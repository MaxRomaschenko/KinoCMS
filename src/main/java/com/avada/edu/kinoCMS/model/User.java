package com.avada.edu.kinoCMS.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;


@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "reg_date")
    private LocalDate reg_date;
    @Column(name = "name")
    private String name;
    @Column(name = "secondname")
    private String secondName;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "birthday_date")
    private LocalDate birthdayDate;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "address")
    private String address;
    @Column(name = "town")
    private String town;
    @Column(name = "password")
    private String password;
    @Column(name = "test_password")
    private String test_password;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "language")
    private Boolean language;
    @Column(name = "gender")
    private Boolean gender;


}
