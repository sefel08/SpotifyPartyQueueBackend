package org.sfl.spotifybackendnew.Services.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.sfl.spotifybackendnew.DTOs.User.UserData;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    public void initializeSessionForGuest(HttpServletRequest request, HttpServletResponse response, String displayName) {
        UserData userData = new UserData(
                UUID.randomUUID(),
                displayName,
                null,
                false,
                null,
                null
        );

        log.info("Initialized session for guest user {} with id: {}", displayName, userData.getUserId());

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userData,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_GUEST"))
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
        repo.saveContext(context, request, response);
    }
    public void initializeSessionAfterSpotifyLogin(Authentication authentication, HttpServletRequest request, HttpServletResponse response, UUID oldUserId, String oldPartyId) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        // get profile picture from current session
        String imageUrl = "None";
        List<Map<String, Object>> images = oauthUser.getAttribute("images");
        if (images != null && !images.isEmpty()) {
            imageUrl = (String) images.getFirst().get("url");
        }

        // create new user session object
        UserData userData = new UserData(
                (oldUserId == null) ? UUID.randomUUID() : oldUserId,
                oauthUser.getAttribute("display_name"),
                oldPartyId,
                true,
                oauthUser.getName(),
                imageUrl
        );

        log.info("Initialized session for Spotify user: {}, spotifyId: {} with id: {}", userData.getDisplayName(), userData.getSpotifyId(), userData.getUserId());

        // register new session object
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                userData,
                authentication.getCredentials(),
                List.of(new SimpleGrantedAuthority("ROLE_SPOTIFY_USER"))
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(newAuth);
        SecurityContextHolder.setContext(context);

        HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
        repo.saveContext(context, request, response);
    }
}
