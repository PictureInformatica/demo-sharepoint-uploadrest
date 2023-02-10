/*
 * Copyright (c) 2023 Picture Soluções em TI - All Rights Reserved
 */

package br.com.picture.demosharepoint.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseCreateUploadSession {
    @JsonProperty("uploadUrl")
    private String uploadUrl;

    @JsonProperty("expirationDateTime")
    private ZonedDateTime expirationDateTime;

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    @Override
    public String toString() {
        return "ResponseCreateUploadSession{" +
                "uploadUrl='" + uploadUrl + '\'' +
                ", expirationDateTime=" + expirationDateTime +
                '}';
    }
}
