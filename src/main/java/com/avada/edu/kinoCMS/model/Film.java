package com.avada.edu.kinoCMS.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "film")
public class Film implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "actual")
    private Boolean actual;
    @Column(name = "description")
    private String description;
    @Column(name = "main_picture")
    private String mainPicture;
    @Column(name = "trailer_url")
    private String trailerURL;

    @OneToOne
    @JoinColumn(name = "seo_id")
    private Seo seo;

    @ManyToMany
    @JoinTable(
            name = "film_filmtype",
            joinColumns = { @JoinColumn(name = "film_id") },
            inverseJoinColumns = { @JoinColumn(name = "filmtype_id") }
    )
    private List<FilmType> filmTypeList;


}
