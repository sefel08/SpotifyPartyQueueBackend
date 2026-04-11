package org.sfl.spotifybackendnew.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sfl.spotifybackendnew.Services.User.UserService;
import org.sfl.spotifybackendnew.DTOs.User.UserData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login-as-guest")
    public ResponseEntity<?> loginAsGuest(
        @RequestBody String displayName, @AuthenticationPrincipal UserData user,
        HttpServletRequest request, HttpServletResponse response
    ) {

        // if user is already authenticated, do nothing
        if (user != null) {
            return ResponseEntity.ok().build();
        }

        if (displayName.length() > 20) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Display name must be less than 20 characters");
        }

        userService.initializeSessionForGuest(request, response, displayName);

        return ResponseEntity.ok().build();
    }
}
