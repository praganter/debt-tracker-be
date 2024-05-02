package dev.batuhanyetgin.auth.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenDto {
    private String name;
    private String email;
    private Long id;
    private String token;
    private String refreshToken;
}