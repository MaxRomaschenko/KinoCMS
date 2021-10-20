package com.avada.edu.kinoCMS.model;

import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Table(name = "film")
public class Film implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "main_picture")
    private String mainPicture;
    @Column(name = "trailer_url")
    private String trailerURL;

    @OneToOne
    @JoinColumn(name = "seo_id")
    private Seo seo;

    @OneToMany(
            mappedBy = "film",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Film_FilmType> film_filmTypeList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public Seo getSeo() {
        return seo;
    }

    public void setSeo(Seo seo) {
        this.seo = seo;
    }

    public List<Film_FilmType> getFilm_filmTypeList() {
        return film_filmTypeList;
    }

    public void setFilm_filmTypeList(List<Film_FilmType> film_filmTypeList) {
        this.film_filmTypeList = film_filmTypeList;
    }

    public void addFilmType(FilmType filmType) {
        Film_FilmType film_filmType = new Film_FilmType( this, filmType );
        film_filmTypeList.add( film_filmType );
        filmType.getFilm_filmTypeList().add(film_filmType);
    }

    public void removeFilmType(FilmType filmType){
        Film_FilmType film_filmType = new Film_FilmType( this, filmType );
        filmType.getFilm_filmTypeList().remove(film_filmType);
        film_filmTypeList.remove(film_filmType);
        film_filmType.setFilm(null);
        film_filmType.setFilmType(null);
    }
}
