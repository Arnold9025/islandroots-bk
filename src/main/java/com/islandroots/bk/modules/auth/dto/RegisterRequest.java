package com.islandroots.bk.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    @NotBlank(message = "L'email est requis")
    @Email(message = "Format d'email invalide")
    private String email;
    
    @NotBlank(message = "Le mot de passe est requis")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
}
