package org.sfl.spotifybackendnew.Controllers;

import org.sfl.spotifybackendnew.Services.Party.PartyService;
import org.sfl.spotifybackendnew.DTOs.User.UserData;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/party")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    // only for authenticated via spotify users, so UserData always contain spotifyId
    @PostMapping("/create")
    public void createParty(@AuthenticationPrincipal UserData user) {
        partyService.createParty(user.getSpotifyId());
    }
    @PostMapping("/join")
    public void joinParty(@AuthenticationPrincipal UserData user, @RequestParam String partyId) {
        partyService.joinParty(partyId, user);
    }
}