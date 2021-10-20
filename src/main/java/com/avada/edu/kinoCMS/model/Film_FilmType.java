package com.avada.edu.kinoCMS.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "film_filmtype")
public class Film_FilmType implements Serializable {
    @Id
    @ManyToOne
    private Film film;

    @Id
    @ManyToOne
    private FilmType filmType;

    public Film_FilmType() {
    }

    public Film_FilmType(Film film, FilmType filmType) {
        this.film = film;
        this.filmType = filmType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film_FilmType that = (Film_FilmType) o;
        return Objects.equals(film, that.film) && Objects.equals(filmType, that.filmType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, filmType);
    }
}
