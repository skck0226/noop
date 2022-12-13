package com.ajou.noop.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name="user_name")
    private String username;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Builder
    public User(String email, String password, String username, String description, UserStatus status){
        this.email = email;
        this.password = password;
        this.username = username;
        this.description = description;
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
