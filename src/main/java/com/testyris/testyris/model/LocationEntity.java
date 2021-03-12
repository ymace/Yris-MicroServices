package com.testyris.testyris.model;

import com.sun.istack.NotNull;

import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;


import javax.persistence.*;


@Entity()
@Table(name = "area",schema="public")
public class LocationEntity {

    @Id
    @Column(name = "gid")
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "geom", nullable = true)
    private Geometry geometry;

    @Column(name = "category")
    private Integer categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}