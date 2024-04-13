package dev.batuhanyetgin.auth.service.abstr;


import dev.batuhanyetgin.auth.dto.*;
import dev.batuhanyetgin.auth.exception.AlreadyRegisteredException;
import dev.batuhanyetgin.auth.exception.AuthException;
import dev.batuhanyetgin.auth.exception.RefreshTokenNotValidException;

public interface AuthService {

    LoginResponseDto login(LoginDto loginDto) throws AuthException;

    RegisterResponseDto register(RegisterDto registerDto) throws AlreadyRegisteredException;

    boolean isTokenExpired(String token);

    String getEmailByToken(String token);

    RefreshTokenDto refreshTokens(String oldToken) throws RefreshTokenNotValidException;

    UserDto changePassword(PasswordDto password, String id);
}