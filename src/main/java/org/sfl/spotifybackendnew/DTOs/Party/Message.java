package org.sfl.spotifybackendnew.DTOs.Party;

import org.sfl.spotifybackendnew.Enums.MessageType;

public record Message(MessageType type, Object payload) {}
