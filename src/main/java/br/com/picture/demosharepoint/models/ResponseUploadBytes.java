/*
 * Copyright (c) 2023 Picture Soluções em TI - All Rights Reserved
 */

package br.com.picture.demosharepoint.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseUploadBytes extends DriveItem {
    @JsonProperty("expirationDateTime")
    private String expirationDateTime;

    @JsonProperty("nextExpectedRanges")
    private List<String> nextExpectedRanges;

    public String getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(String expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }

    public List<String> getNextExpectedRanges() {
        return nextExpectedRanges;
    }

    public void setNextExpectedRanges(List<String> nextExpectedRanges) {
        this.nextExpectedRanges = nextExpectedRanges;
    }

    @Override
    public String toString() {
        return "ResponseUploadBytes{" +
                "expirationDateTime='" + expirationDateTime + '\'' +
                ", nextExpectedRanges=" + nextExpectedRanges +
                "} " + super.toString();
    }
}
