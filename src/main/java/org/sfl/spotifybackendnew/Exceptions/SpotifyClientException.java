package org.sfl.spotifybackendnew.Exceptions;

public class SpotifyClientException extends RuntimeException {
    public SpotifyClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
