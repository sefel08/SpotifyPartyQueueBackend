package org.sfl.spotifybackendnew.SpotifyDTOs.ResponseDTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.sfl.spotifybackendnew.SpotifyDTOs.SubDTOs.SpotifyTrack;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifySearchResponse {
    @JsonIgnore
    private List<SpotifyTrack> tracks;
//    @JsonProperty("albums")
//    @JsonProperty("artists")
//    @JsonProperty("playlists")
}