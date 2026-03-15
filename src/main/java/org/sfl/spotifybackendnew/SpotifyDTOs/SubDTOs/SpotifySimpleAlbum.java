package org.sfl.spotifybackendnew.SpotifyDTOs.SubDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifySimpleAlbum {
    private List<SpotifyImage> images;
}
