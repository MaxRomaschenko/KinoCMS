package com.avada.edu.kinoCMS.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "banner")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo")
    private String photo;

    @NotEmpty(message = "Текст")
    @Column(name = "url")
    private String url;

    @Column(name = "true_false")
    private Boolean trueFalse;

    @NotEmpty(message = "Текст")
    @Column(name = "text_message")
    private String textMessage;

}
