package org.sfl.spotifybackendnew.DTOs.Party;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.sfl.spotifybackendnew.DTOs.Music.Track;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class PartySession {
    @Getter
    private final String partyId;

    private final Map<UUID, PartyUser> userMap = new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<UUID> joinOrder = new ConcurrentLinkedQueue<>();

    public PartySession(String partyId) {
        this.partyId = partyId;
    }

    public void addUser(UUID userId) {
        if (userMap.containsKey(userId))
            return;

        PartyUser user = new PartyUser(userId);
        userMap.put(userId, user);

        log.info("Adding user {} to party with id {}", userId, partyId);
        log.info("Current users in party {}: {} ({})", partyId, userMap.keySet(), userMap.size());
    }

    public void addToUserQueue(UUID userId, Track track) {
        PartyUser user = getPartyUser(userId);
        if (user != null) {

        }
    }

    private PartyUser getPartyUser(UUID userId) {
        return userMap.get(userId);
    }
}
