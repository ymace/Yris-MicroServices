package com.testyris.testyris.model;

import javax.persistence.Column;

public class LocationInformationDTO {

    private Long locationId;
    private String commentary;

    public LocationInformationDTO(Long locationId, String commentary) {
        this.locationId = locationId;
        this.commentary = commentary;
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
