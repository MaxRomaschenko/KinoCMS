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
@Table(name="stock")
public class Stock {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "publication_date")
    private Timestamp publication_date;

    @Column(name = "description")
    private String description;

    @Column(name = "main_picture")
    private String main_picture;

    @Column(name = "trailer_url")
    private String trailer_url;

    @Column(name = "is_active")
    private Boolean is_active;

    @OneToOne
    @JoinColumn(name = "seo_id")
    private Seo seo;
}
