package com.testyris.testyris.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity()
@Table(name = "locationInformation",schema="public")
public class LocationInformationEntity {

    @Id
    @Column(name = "id")
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "locationId")
    private Long locationId;

    @Column(name = "commentary")
    private String commentary;


    public LocationInformationEntity() {
    }

    public LocationInformationEntity(Long id, Long locationId, String commentary) {
        this.id = id;
        this.locationId = locationId;
        this.commentary = commentary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}
