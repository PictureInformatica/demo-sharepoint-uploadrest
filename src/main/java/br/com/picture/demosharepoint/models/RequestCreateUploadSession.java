/*
 * Copyright (c) 2023 Picture Soluções em TI - All Rights Reserved
 */

package br.com.picture.demosharepoint.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestCreateUploadSession extends GraphBaseRequest {
    @JsonProperty("@microsoft.graph.conflictBehavior")
    private String conflictBehavior = "microsoft.graph.driveItemUploadableProperties";

    @JsonProperty("name")
    private String name;

    public String getConflictBehavior() {
        return conflictBehavior;
    }

    public void setConflictBehavior(String conflictBehavior) {
        this.conflictBehavior = conflictBehavior;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
