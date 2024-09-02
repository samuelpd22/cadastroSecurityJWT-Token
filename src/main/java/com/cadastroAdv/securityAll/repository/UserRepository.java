package com.cadastroAdv.securityAll.repository;

import com.cadastroAdv.securityAll.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {


    UserDetails findByLogin(String login);

    //UserDetails pois será utilizado pelo SpringSecurity
}
