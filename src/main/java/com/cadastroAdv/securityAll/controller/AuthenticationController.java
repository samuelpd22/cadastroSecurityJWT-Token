package com.cadastroAdv.securityAll.controller;

import com.cadastroAdv.securityAll.dto.AuthenticationDTO;
import com.cadastroAdv.securityAll.dto.LoginResponseDTO;
import com.cadastroAdv.securityAll.dto.RegisterDTO;
import com.cadastroAdv.securityAll.entity.User;
import com.cadastroAdv.securityAll.repository.UserRepository;
import com.cadastroAdv.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(),data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);


        var token = tokenService.gerarToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null){
            return ResponseEntity.badRequest().build();
        } else {
            String esconderSenha = new BCryptPasswordEncoder().encode(data.password());
            User newUser = new User(data.login(), esconderSenha,data.role());

            this.repository.save(newUser);

            return ResponseEntity.ok().build();
        }
    }

}
