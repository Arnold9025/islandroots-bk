package com.islandroots.bk.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "L'email est requis")
    @Email(message = "Format d'email invalide")
    private String email;

    String password;
}
