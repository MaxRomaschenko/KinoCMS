package com.avada.edu.kinoCMS.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "picture_gallery")
public class PictureGallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "picture")
    private String picture;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    @JoinColumn(name = "cinema_id",referencedColumnName = "id")
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    @JoinColumn(name = "film_id",referencedColumnName = "id")
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    @JoinColumn(name = "hall_id",referencedColumnName = "id")
    private Hall hall;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    @JoinColumn(name = "news_id",referencedColumnName = "id")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    @JoinColumn(name = "stock_id",referencedColumnName = "id")
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    @JoinColumn(name = "page_id",referencedColumnName = "id")
    private Page page;

}
