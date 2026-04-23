package org.sfl.spotifybackendnew.Services.Spotify;

import org.sfl.spotifybackendnew.Exceptions.SpotifyClientException;
import org.sfl.spotifybackendnew.Exceptions.SpotifyServiceException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

@Service
public class SpotifyPlayerService {

    private final SpotifyClient spotifyClient;

    public SpotifyPlayerService(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public void setupPlayer(OAuth2AuthorizedClient authorizedClient, String deviceId) {
        try {
            spotifyClient.initializePlayer(authorizedClient.getAccessToken().getTokenValue(), deviceId);
        } catch (SpotifyClientException e) {
            System.err.println("Error setting up player: " + e.getMessage());
            throw new SpotifyServiceException("Failed to set up player: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    public void playTrack(OAuth2AuthorizedClient authorizedClient, String uri, String deviceId) {
        spotifyClient.playTrack(authorizedClient.getAccessToken().getTokenValue(), uri, deviceId);
    }
}