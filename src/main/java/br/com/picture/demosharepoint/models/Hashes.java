/*
 * Copyright (c) 2023 Picture Soluções em TI - All Rights Reserved
 */

package br.com.picture.demosharepoint.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hashes {
    @JsonProperty("crc32Hash")
    private String crc32Hash;
    @JsonProperty("quickXorHash")
    private String quickXorHash;
    @JsonProperty("sha1Hash")
    private String sha1Hash;
    @JsonProperty("sha256Hash")
    private String sha256Hash;

    public String getCrc32Hash() {
        return crc32Hash;
    }

    public void setCrc32Hash(String crc32Hash) {
        this.crc32Hash = crc32Hash;
    }

    public String getQuickXorHash() {
        return quickXorHash;
    }

    public void setQuickXorHash(String quickXorHash) {
        this.quickXorHash = quickXorHash;
    }

    public String getSha1Hash() {
        return sha1Hash;
    }

    public void setSha1Hash(String sha1Hash) {
        this.sha1Hash = sha1Hash;
    }

    public String getSha256Hash() {
        return sha256Hash;
    }

    public void setSha256Hash(String sha256Hash) {
        this.sha256Hash = sha256Hash;
    }

    @Override
    public String toString() {
        return "Hashes{" +
                "crc32Hash='" + crc32Hash + '\'' +
                ", quickXorHash='" + quickXorHash + '\'' +
                ", sha1Hash='" + sha1Hash + '\'' +
                ", sha256Hash='" + sha256Hash + '\'' +
                '}';
    }
}
