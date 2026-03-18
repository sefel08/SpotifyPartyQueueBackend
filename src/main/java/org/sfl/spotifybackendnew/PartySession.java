package org.sfl.spotifybackendnew;

import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PartySession {
    @Getter
    private final String spotifyOwnerId;
    private final Map<UUID, PartyUser> users = new ConcurrentHashMap<>();

    public PartySession(String spotifyOwnerId) {
        this.spotifyOwnerId = spotifyOwnerId;
    }

    public UUID addUser() {
        UUID userId = UUID.randomUUID();

        PartyUser user = new PartyUser();
        user.setId(userId);

        users.put(userId, user);
        return userId;
    }
}
