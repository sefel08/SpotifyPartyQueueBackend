package org.sfl.spotifybackendnew.Services.Spotify;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

@Service
public class SpotifyPlayerService {

    private final SpotifyClient spotifyClient;

    public SpotifyPlayerService(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public void addToQueue(OAuth2AuthorizedClient authorizedClient, String trackUri, String deviceId) {
        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        spotifyClient.addTrackToQueue(accessToken, trackUri, deviceId);
    }
}
