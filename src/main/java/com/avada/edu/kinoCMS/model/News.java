package com.avada.edu.kinoCMS.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "publication_date")
    private Timestamp publication_date;

    @Column(name = "description")
    private String description;

    @Column(name= "main_picture")
    private String main_picture;

    @Column(name= "trailer_url")
    private String trailer_url;

    @Column(name= "isactive")
    private Boolean is_active;

    @Column(name="created_at")
    private Timestamp created_at;

    @OneToOne
    @JoinColumn(name = "seo_id")
    private Seo seo;

    public News(Long id, String title, Timestamp publication_date, String description, String main_picture, String trailer_url, Boolean is_active, Timestamp created_at, Seo seo) {
        this.id = id;
        this.title = title;
        this.publication_date = publication_date;
        this.description = description;
        this.main_picture = main_picture;
        this.trailer_url = trailer_url;
        this.is_active = is_active;
        this.created_at = created_at;
        this.seo = seo;
    }
}
