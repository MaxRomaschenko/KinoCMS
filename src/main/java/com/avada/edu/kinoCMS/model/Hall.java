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
@Table(name = "hall")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hall_number")
    private Integer hall_number;

    @Column(name = "hall_description")
    private String hall_description;

    @Column(name = "hall_layout_picture")
    private String hall_layout_picture;

    @Column(name = "banner_picture")
    private String banner_picture;

    @Column(name = "created_at")
    private Timestamp created_at;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    @JoinColumn(name = "cinema_id",referencedColumnName = "id")
    private Cinema cinema;

    @OneToOne
    @JoinColumn(name = "seo_id")
    private Seo seo;
}
