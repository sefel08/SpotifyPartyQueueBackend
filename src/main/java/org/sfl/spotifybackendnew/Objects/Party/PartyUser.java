package org.sfl.spotifybackendnew.Objects.Party;

import lombok.Data;
import org.sfl.spotifybackendnew.DTOs.Music.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class PartyUser {
    private final UUID id;
    private final List<Track> queue = new CopyOnWriteArrayList<>();
}