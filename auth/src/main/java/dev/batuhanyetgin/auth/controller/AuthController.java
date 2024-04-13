package dev.batuhanyetgin.auth.controller;


import dev.batuhanyetgin.auth.dto.*;
import dev.batuhanyetgin.auth.exception.AlreadyRegisteredException;
import dev.batuhanyetgin.auth.exception.AuthException;
import dev.batuhanyetgin.auth.exception.RefreshTokenNotValidException;
import dev.batuhanyetgin.auth.service.abstr.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) throws AuthException {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterDto registerDto) throws AlreadyRegisteredException {
        return ResponseEntity.ok(authService.register(registerDto));
    }

    @PatchMapping("/changePassword/{id}")
    public ResponseEntity<UserDto> changePassword(@PathVariable String id, @RequestBody PasswordDto passwordDto) {
        return ResponseEntity.ok(authService.changePassword(passwordDto, id));
    }
//
//    @GetMapping("/getEmail/{token}")
//    public ResponseEntity<String> getEmailFromToken(@PathVariable String token) {
//        return ResponseEntity.ok(authService.getEmailByToken(token));
//    }

    @GetMapping("/refreshToken")
    public ResponseEntity<RefreshTokenDto> refreshToken(@RequestParam String refreshToken) throws RefreshTokenNotValidException {
        return ResponseEntity.ok(authService.refreshTokens(refreshToken));
    }
}