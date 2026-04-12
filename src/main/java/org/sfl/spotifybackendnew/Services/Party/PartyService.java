package org.sfl.spotifybackendnew.Services.Party;

import lombok.extern.slf4j.Slf4j;
import org.sfl.spotifybackendnew.DTOs.User.UserData;
import org.sfl.spotifybackendnew.Exceptions.PartyNotFoundException;
import org.sfl.spotifybackendnew.DTOs.Party.PartySession;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class PartyService {
    // (spotifyId - party) map
    private final Map<String, PartySession> partySessionMap = new ConcurrentHashMap<>();

    public void createParty(String spotifyUserId) {
        PartySession userPartySession = partySessionMap.get(spotifyUserId);
        if(userPartySession != null) return;

        log.info("Creating party for user with spotify id {}", spotifyUserId);

        //create new party for user
        PartySession party = new PartySession(spotifyUserId);
        partySessionMap.put(spotifyUserId, party);
    }
    public void joinParty(String partyId, UserData user) {
        PartySession party = Optional.ofNullable(partySessionMap.get(partyId))
                .orElseThrow(() -> new PartyNotFoundException(partyId));
        party.addUser(user.getUserId());
        user.setPartyId(partyId);
    }
    public void removeUserFromParty(String partyId, UUID userId) {
        PartySession party = Optional.ofNullable(partySessionMap.get(partyId))
                .orElseThrow(() -> new PartyNotFoundException(partyId));
        party.removeUser(userId);
    }
}