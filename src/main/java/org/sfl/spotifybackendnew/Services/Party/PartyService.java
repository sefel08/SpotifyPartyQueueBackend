package org.sfl.spotifybackendnew.Services.Party;

import org.sfl.spotifybackendnew.Exceptions.PartyNotFoundException;
import org.sfl.spotifybackendnew.PartySession;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PartyService {
    //SpotifyUserIf - PartySession
    private final Map<String, PartySession> partySessionMap = new ConcurrentHashMap<>();

    public void createParty(String spotifyUserId) {
        PartySession userSession = partySessionMap.get(spotifyUserId);
        if(userSession != null) return;

        //create new session for user
        PartySession partySession = new PartySession(spotifyUserId);
        partySessionMap.put(spotifyUserId, partySession);
    }
    public UUID joinParty(String spotifyOwnerId) {
        return Optional.ofNullable(partySessionMap.get(spotifyOwnerId))
                .map(PartySession::addUser)
                .orElseThrow(() -> new PartyNotFoundException(spotifyOwnerId));
    }
}
