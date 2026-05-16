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

    public boolean playTrack(String userToken, String uri, String deviceId) {
        try {
            spotifyClient.playTrack(userToken, uri, deviceId);
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