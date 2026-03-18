package org.sfl.spotifybackendnew;

import org.sfl.spotifybackendnew.MusicDTOs.Playlist;
import org.sfl.spotifybackendnew.MusicDTOs.Track;
import org.sfl.spotifybackendnew.Services.Spotify.SpotifyProxyService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyProxyController {

    private final SpotifyProxyService spotifyProxyService;

    public SpotifyProxyController(SpotifyProxyService spotifyProxyService) {
        this.spotifyProxyService = spotifyProxyService;
    }

    @GetMapping("/search")
    public List<Track> searchTracks(@RequestParam String query) {
        return spotifyProxyService.searchTracks(query);
    }

    @GetMapping("/user-playlists")
    public List<Playlist> getUserPlaylists(@RegisteredOAuth2AuthorizedClient("spotify") OAuth2AuthorizedClient authorizedClient) {
        return spotifyProxyService.getUserPlaylists(authorizedClient);
    }

    @GetMapping("/playlist")
    public List<Track> getPlaylistTracks(@RegisteredOAuth2AuthorizedClient("spotify") OAuth2AuthorizedClient authorizedClient, @RequestParam String playlistId) {
        try {
            return spotifyProxyService.getPlaylistTracks(authorizedClient, playlistId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch playlist tracks: " + e.getMessage(), e);
        }
    }
}