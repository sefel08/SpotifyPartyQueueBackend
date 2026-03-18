package org.sfl.spotifybackendnew.Exceptions;

import java.util.UUID;

public class PartyNotFoundException extends RuntimeException {
    public PartyNotFoundException(String spotifyOwnerId) {
        super("Spotify user " + spotifyOwnerId + " does not have active party session.");
    }
}