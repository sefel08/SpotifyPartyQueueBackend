package org.sfl.spotifybackendnew.Exceptions;

import java.util.UUID;

public class PartyNotFoundException extends RuntimeException {
    public PartyNotFoundException(String partyId) {
        super("Party with id (or deviceId): " + partyId + " does not exist.");
    }
}