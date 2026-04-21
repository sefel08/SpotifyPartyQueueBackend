package org.sfl.spotifybackendnew.Services.Spotify;

import org.sfl.spotifybackendnew.DTOs.Music.Track;
import org.sfl.spotifybackendnew.Exceptions.SpotifyClientException;
import org.sfl.spotifybackendnew.SpotifyDTOs.ResponseDTOs.SpotifyGetUserPlaylistsResponse;
import org.sfl.spotifybackendnew.SpotifyDTOs.ResponseDTOs.SpotifySearchResponse;
import org.sfl.spotifybackendnew.SpotifyDTOs.SpotifyWrappersDTOs.SpotifyTrackWrapper;
import org.sfl.spotifybackendnew.SpotifyDTOs.SubDTOs.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class SpotifyClient {

    private final JsonMapper jsonMapper;
    private final RestTemplate restTemplate;
    private final SpotifyTokenService spotifyTokenService;
    private final String BASE_URL = "https://api.spotify.com/v1";

    public SpotifyClient(JsonMapper jsonMapper, RestTemplate restTemplate, SpotifyTokenService spotifyTokenService) {
        this.jsonMapper = jsonMapper;
        this.restTemplate = restTemplate;
        this.spotifyTokenService = spotifyTokenService;
    }

    // proxy service
    public SpotifyGetUserPlaylistsResponse getUserPlaylists(String accessToken) {
        try {
            String url = BASE_URL + "/me/playlists";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    SpotifyGetUserPlaylistsResponse.class
            ).getBody();

        } catch (Exception e) {
            throw new SpotifyClientException("Failed to fetch user playlists: " + e.getMessage(), e);
        }
    }
    public SpotifyPlaylist getPlaylistData(String accessToken, String playlistId) {
        try {
            String url = BASE_URL + "/playlists/" + playlistId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            JsonNode json = Objects.requireNonNull(restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    JsonNode.class
            ).getBody());

            SpotifyPlaylist playlist = jsonMapper.treeToValue(json, SpotifyPlaylist.class);

            List<SpotifyTrackWrapper> wrappers = jsonMapper.readerForListOf(SpotifyTrackWrapper.class)
                    .readValue(json.get("items").get("items"));

            playlist.setTracks(wrappers.stream().map(SpotifyTrackWrapper::getTrack).toList());

            return playlist;

        } catch (Exception e) {
            throw new SpotifyClientException("Failed to fetch playlist data: " + e.getMessage(), e);
        }
    }
    public SpotifySearchResponse search(String query) {
        try {
            String url = UriComponentsBuilder.fromUriString(BASE_URL + "/search")
                    .queryParam("q", query)
                    .queryParam("limit", 10)
                    .queryParam("type", "track")
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(spotifyTokenService.getApplicationToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            JsonNode json = Objects.requireNonNull(restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    JsonNode.class
            ).getBody());

            SpotifySearchResponse response = new SpotifySearchResponse();

            List<SpotifyTrack> tracks = jsonMapper.readerForListOf(SpotifyTrack.class)
                    .readValue(json.get("tracks").get("items"));
            //todo albums and others containers

            response.setTracks(tracks);

            return response;

        } catch (Exception e) {
            throw new SpotifyClientException("Failed to perform search: " + e.getMessage(), e);
        }
    }
    public SpotifyTrack getTrack(String trackId) {
        try {
            String url = BASE_URL + "/tracks/" + trackId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(spotifyTokenService.getApplicationToken());
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    SpotifyTrack.class
            ).getBody();

        } catch (Exception e) {
            throw new SpotifyClientException("Failed to fetch track: " + e.getMessage(), e);
        }
    }

    // player service
    public void addTrackToQueue(String accessToken, String trackUri, String deviceId) {
        try {
            String url = BASE_URL + "/me/player/play?device_id=" + deviceId;

            Map<String, String> body = new HashMap<>();
            body.put("context_uri", "");

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    Void.class
            );
        } catch (Exception e) {
            throw new SpotifyClientException("Failed to add track to queue: " + e.getMessage(), e);
        }
        try {
            String url = BASE_URL + "/me/player/queue?uri=" + trackUri + "&device_id=" + deviceId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Void.class
            );
        } catch (Exception e) {
            throw new SpotifyClientException("Failed to add track to queue: " + e.getMessage(), e);
        }
    }
}