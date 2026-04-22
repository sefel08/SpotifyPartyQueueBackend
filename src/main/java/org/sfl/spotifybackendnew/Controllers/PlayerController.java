package org.sfl.spotifybackendnew.Controllers;

import org.sfl.spotifybackendnew.DTOs.Music.Track;
import org.sfl.spotifybackendnew.DTOs.User.UserData;
import org.sfl.spotifybackendnew.Services.Party.PartyService;
import org.sfl.spotifybackendnew.Services.Security.SpotifyAuthorizedClientService;
import org.sfl.spotifybackendnew.Services.Spotify.SpotifyPlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private final PartyService partyService;
    private final SpotifyPlayerService spotifyPlayerService;
    private final SpotifyAuthorizedClientService spotifyAuthorizedClientService;

    public PlayerController(PartyService partyService, SpotifyPlayerService spotifyPlayerService, SpotifyAuthorizedClientService spotifyAuthorizedClientService) {
        this.partyService = partyService;
        this.spotifyPlayerService = spotifyPlayerService;
        this.spotifyAuthorizedClientService = spotifyAuthorizedClientService;
    }


    public record SetupRequest(String deviceId, String playlistUri) {}
    public record PlayNextTrackRequest(String deviceId, String newTrackId) {}

    @PostMapping("/setup")
    public void setupPlayer(@AuthenticationPrincipal UserData user, Authentication authentication, @RequestBody SetupRequest setupRequest) {
        if (!user.isHasHostPermissions() || !user.isPremium()) return;
        OAuth2AuthorizedClient authorizedClient = spotifyAuthorizedClientService.getAuthorizedClient(user, authentication);
        spotifyPlayerService.setupPlayer(authorizedClient, setupRequest.deviceId, setupRequest.playlistUri);
    }

    @PostMapping("/playNext")
    public void playNextTrack(@AuthenticationPrincipal UserData user, Authentication authentication, @RequestBody PlayNextTrackRequest playNextTrackRequest) {
        if (!user.isHasHostPermissions() || !user.isPremium()) return;

        Track trackToPlay = partyService.pollTrackFromPartyQueue(user.getPartyId(), user);

        if (trackToPlay == null) return;

        OAuth2AuthorizedClient authorizedClient = spotifyAuthorizedClientService.getAuthorizedClient(user, authentication);
        spotifyPlayerService.playTrack(authorizedClient, trackToPlay.getUri(), playNextTrackRequest.deviceId);
    }
}