/*
 * Copyright (c) 2023 Picture Soluções em TI - All Rights Reserved
 */

package br.com.picture.demosharepoint.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GraphBaseRequest {
    @JsonProperty("@odata.type")
    private String odataType;

    public String getOdataType() {
        return odataType;
    }

    public void setOdataType(String odataType) {
        this.odataType = odataType;
    }
}
