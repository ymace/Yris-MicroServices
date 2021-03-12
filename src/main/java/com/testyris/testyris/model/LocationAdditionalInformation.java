package com.testyris.testyris.model;

import java.util.List;

public class LocationAdditionalInformation {
    private Long id;
    private String commentary;

    public LocationAdditionalInformation(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}
