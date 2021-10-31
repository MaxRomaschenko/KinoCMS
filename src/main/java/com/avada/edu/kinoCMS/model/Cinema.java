package com.avada.edu.kinoCMS.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "cinema")
public class Cinema {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cinema_name")
    private String cinema_name;

    @Column(name = "description")
    private String description;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "logo_picture")
    private String logo_picture;

    @Column(name = "banner_picture")
    private String banner_picture;

    @OneToOne
    @JoinColumn(name = "seo_id")
    private Seo seo;
}
