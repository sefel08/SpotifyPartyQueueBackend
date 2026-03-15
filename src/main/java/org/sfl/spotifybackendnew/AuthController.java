package org.sfl.spotifybackendnew;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/status")
    public Map<String, Object> checkStatus(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return Map.of("isLoggedIn", false);
        }
        return Map.of(
                "isLoggedIn", true,
                "name", user.getAttribute("display_name"),
                "email", user.getAttribute("email")
        );
    }
}