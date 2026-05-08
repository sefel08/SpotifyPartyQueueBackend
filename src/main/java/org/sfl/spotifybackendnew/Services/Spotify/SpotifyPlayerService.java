package org.sfl.spotifybackendnew.Services.Spotify;

import lombok.extern.slf4j.Slf4j;
import org.sfl.spotifybackendnew.Exceptions.SpotifyClientException;
import org.sfl.spotifybackendnew.Exceptions.SpotifyServiceException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

@Slf4j
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
    public boolean playTrack(OAuth2AuthorizedClient authorizedClient, String uri, String deviceId) {
        try {
            spotifyClient.playTrack(authorizedClient.getAccessToken().getTokenValue(), uri, deviceId);
            return true;
        } catch (SpotifyClientException e) {
            log.error("Spotify API error when playing next track [Device: {}]: {}", deviceId, e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Critical error in Spotify integration: {}", e.getMessage());
            return false;
        }
    }
}