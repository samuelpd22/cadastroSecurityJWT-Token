package com.cadastroAdv.securityAll.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode( of = "id")
@Entity( name = "users_lgn")
@Table ( name = "users_lgn")
public class User implements UserDetails { //Obrigatorio implementar essa interface!!

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE)
    private Long id;

    private String login;

    private String password;

    private UserRole role;


    //Metodos declarados pela interface!!



    //Roles que o usuario tem!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //Sempre utilizar esse modelo
        if(this.role == UserRole.ADMIN){ //SE ADMIN, Terá todas ROLES
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                           new SimpleGrantedAuthority("ROLE_USER"));
        } else {    // SE USER, Terá apenas ROLE USER
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
    public User(String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;

    }

    @Override
    public String getUsername() { //Retorna o nome, ou login etc
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
            return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
