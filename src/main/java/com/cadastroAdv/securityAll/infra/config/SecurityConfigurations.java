package com.cadastroAdv.securityAll.infra.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Indica que é uma CLASSE DE CONFIGURAÇÃO
@EnableWebSecurity // Habilita a configuração do WEB SECURITY NESTA CLASSE
public class SecurityConfigurations {



    @Autowired
    private SecurityFilter securityFilter;
    //Modelo de codigo!!
    //Onde vai filtrar as REQUISIÇÕES

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //POLITICA DE SEGURANÇA
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()//Permitindo qualquer REQUISIÇÃO
                        .requestMatchers(HttpMethod.POST,"/auth/registrar").permitAll()
                        //.requestMatchers(HttpMethod.GET,"/cadastro").permitAll()
                        //.requestMatchers(HttpMethod.POST,"cadastrar").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //Chama ANTES DE PASSAR NO CODIGO ACIMA,o metodo SecurityFilter

                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
