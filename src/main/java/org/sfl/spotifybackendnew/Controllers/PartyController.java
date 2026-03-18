package org.sfl.spotifybackendnew.Controllers;

import org.sfl.spotifybackendnew.Services.Party.PartyService;
import org.springframework.security.core.Authentication;
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


    @PostMapping("/create")
    public void createParty(Authentication auth) {
        partyService.createParty(auth.getName()); //spotifyId
    }
    @PostMapping("/join")
    public String joinParty(@RequestParam String spotifyUserId) {
        return partyService.joinParty(spotifyUserId).toString();
    }
}