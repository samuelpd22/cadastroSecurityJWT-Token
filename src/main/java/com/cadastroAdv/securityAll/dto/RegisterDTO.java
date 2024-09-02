package com.cadastroAdv.securityAll.dto;

import com.cadastroAdv.securityAll.entity.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
