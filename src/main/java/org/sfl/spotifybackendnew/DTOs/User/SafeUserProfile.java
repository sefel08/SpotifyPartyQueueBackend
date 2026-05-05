package org.sfl.spotifybackendnew.DTOs.User;

public record SafeUserProfile(String displayName, boolean spotifyAuthorized, String profileImageUrl, String smallProfileImageUrl) {}