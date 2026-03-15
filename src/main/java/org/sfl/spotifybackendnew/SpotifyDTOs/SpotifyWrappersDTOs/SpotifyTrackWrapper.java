package org.sfl.spotifybackendnew.SpotifyDTOs.SpotifyWrappersDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.sfl.spotifybackendnew.SpotifyDTOs.SubDTOs.SpotifyTrack;

@Data
public class SpotifyTrackWrapper {
    @JsonProperty("item")
    private SpotifyTrack track;
}
