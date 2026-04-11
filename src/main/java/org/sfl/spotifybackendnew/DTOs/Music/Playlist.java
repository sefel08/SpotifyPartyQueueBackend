package org.sfl.spotifybackendnew.DTOs.Music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    private String id;
    private String name;
    private String imageUrl;
    private int totalTracks;
}