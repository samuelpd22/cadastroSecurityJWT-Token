package com.cadastroAdv.securityAll.infra.config;

import com.cadastroAdv.securityAll.repository.UserRepository;
import com.cadastroAdv.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    UserRepository repository;
    @Autowired
    TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.pegaToken(request);
        if (token != null){
            var login = tokenService.validaToken(token);
            UserDetails user = repository.findByLogin(login);

            //VERIFICAÇÕES DE USUARIO
            var authenticaiton = new UsernamePasswordAuthenticationToken(user ,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticaiton);

        }
        filterChain.doFilter(request,response);

    }



    private String pegaToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ","");


    }
}
