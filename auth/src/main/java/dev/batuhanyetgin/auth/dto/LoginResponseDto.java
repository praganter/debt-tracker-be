package dev.batuhanyetgin.auth.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginResponseDto {
    private String name;
    private String email;
    private Long id;
    private String token;
}
