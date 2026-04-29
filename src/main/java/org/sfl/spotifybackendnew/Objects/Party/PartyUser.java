package org.sfl.spotifybackendnew.Objects.Party;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.sfl.spotifybackendnew.DTOs.Music.Track;
import org.sfl.spotifybackendnew.DTOs.User.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PartyUser {
    private final UUID id;

    // display info
    private UserProfile profile;

    @Getter(AccessLevel.NONE)
    private final List<Track> queue = new ArrayList<>();

    public PartyUser(UUID userId, UserProfile profile) {
        id = userId;
        this.profile = profile;
    }

    public synchronized void addTrack(Track track) {
        queue.add(track);
    }
    public synchronized Track removeTrack(int index) {
        if (index >= 0 && index < queue.size()) {
            return queue.remove(index);
        }
        return null;
    }
    public synchronized List<Track> getQueue() {
        return new ArrayList<>(queue);
    }
}