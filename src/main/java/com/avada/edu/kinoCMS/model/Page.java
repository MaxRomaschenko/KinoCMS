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
@Table(name="page")
public class Page {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "banner_url")
    private String banner_url;

    @Column(name = "is_active")
    private String is_active;

    @Column(name = "created_at")
    private Timestamp created_at;

    @OneToOne
    @JoinColumn(name = "seo_id")
    private Seo seo;

}
