package com.avada.edu.kinoCMS.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "filmType")
public class FilmType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_type")
    private String nameType;

    @OneToMany(
            mappedBy = "filmType",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Film_FilmType> film_filmTypeList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public List<Film_FilmType> getFilm_filmTypeList() {
        return film_filmTypeList;
    }

    public void setFilm_filmTypeList(List<Film_FilmType> film_filmTypeList) {
        this.film_filmTypeList = film_filmTypeList;
    }
}
