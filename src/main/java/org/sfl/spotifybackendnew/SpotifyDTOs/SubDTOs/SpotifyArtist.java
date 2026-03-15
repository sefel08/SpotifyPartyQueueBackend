package org.sfl.spotifybackendnew.SpotifyDTOs.SubDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyArtist {
    private String id;
    private String name;

    @JsonProperty("href")
    private String spotifyUrl;
}
