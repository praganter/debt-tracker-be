package dev.batuhanyetgin.auth.service.impl;


import dev.batuhanyetgin.auth.component.TokenManager;
import dev.batuhanyetgin.auth.dto.*;
import dev.batuhanyetgin.auth.entity.RefreshTokenEntity;
import dev.batuhanyetgin.auth.entity.UserEntity;
import dev.batuhanyetgin.auth.exception.AlreadyRegisteredException;
import dev.batuhanyetgin.auth.exception.AuthException;
import dev.batuhanyetgin.auth.exception.RefreshTokenNotValidException;
import dev.batuhanyetgin.auth.repository.RefreshTokenRepository;
import dev.batuhanyetgin.auth.repository.UserRepository;
import dev.batuhanyetgin.auth.service.abstr.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenManager tokenManager;


    @Override
    public RegisterResponseDto register(RegisterDto registerDto) throws AlreadyRegisteredException {
        String refreshToken = "";
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new AlreadyRegisteredException("Email already registered.");
        }
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        UserEntity newUser = userRepository.save(modelMapper.map(registerDto, UserEntity.class));
        if (newUser.getEmail().equals(registerDto.getEmail())
                && registerDto.getPassword().equals(newUser.getPassword())) {
            log.info("Registered user: {}", registerDto.getEmail());
            refreshToken = addRefreshTokenNewUserAndReturnRefreshToken(registerDto.getEmail());

        }
        return new RegisterResponseDto("Successfully registered with " + registerDto.getEmail(), refreshToken);
    }

    private String addRefreshTokenNewUserAndReturnRefreshToken(String eMail) {
        UserEntity newlyAddedUser = userRepository.getByEmail(eMail);
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(tokenManager.generateRefreshToken(eMail));
        refreshToken.setUserId(newlyAddedUser.getId());
        refreshTokenRepository.save(refreshToken);
        log.info("New refresh token created -> {}", refreshToken);
        return refreshToken.getToken();
    }

    @Override
    public boolean isTokenExpired(String token) {
        return (tokenManager.isExpired(token) || userRepository.existsByEmail(tokenManager.getSubject(token)));
    }

    @Override
    public String getEmailByToken(String token) {
        return tokenManager.getSubject(token);
    }

    @Override
    public RefreshTokenDto refreshTokens(String oldToken) throws RefreshTokenNotValidException {
        String userMail = tokenManager.getSubject(oldToken);
        UserEntity user = userRepository.getByEmail(userMail);
        RefreshTokenEntity refreshToken = refreshTokenRepository.getByUserId(user.getId());
        if (refreshToken.getToken().equals(oldToken)) {
            return new RefreshTokenDto(tokenManager.generateToken(getEmailByToken(oldToken)),
                    tokenManager.generateRefreshToken(getEmailByToken(oldToken)));
        } else {
            throw new RefreshTokenNotValidException("Refresh token not valid.");
        }
    }

    @Override
    public UserDto changePassword(PasswordDto password, String id) {
        UserEntity user = userRepository.getReferenceById(Long.parseLong(id));
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        userRepository.save(user);
        log.info("Changed password");
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public LoginResponseDto login(LoginDto loginDto) throws AuthException {

        if (!userRepository.existsByEmail(loginDto.getEmail())) {
            throw new AuthException("There is no user with email : " + loginDto.getEmail());
        }
        UserEntity user = userRepository.getByEmail(loginDto.getEmail());

        if (!passwordMatches(loginDto.getPassword(), user.getPassword())) {
            throw new AuthException("Check your email or password ");
        }
        String token = tokenManager.generateToken(loginDto.getEmail());
        log.info("Successfully login.{}", user.getEmail());
        return LoginResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .token(token)
                .build();

    }

    private Boolean passwordMatches(String rawPass, String encodedPass) {
        return passwordEncoder.matches(rawPass, encodedPass);
    }
}