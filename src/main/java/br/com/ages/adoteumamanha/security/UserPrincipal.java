package br.com.ages.adoteumamanha.security;

import br.com.ages.adoteumamanha.domain.entity.UsuarioEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@Slf4j
@EqualsAndHashCode(of = "id")
public class UserPrincipal implements UserDetails {

    private Long id;

    private String nome;

    private String email;

    @JsonIgnore
    private String senha;

    private Boolean ativo;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(final Long id, final String nome, final String email, final String senha, final Boolean ativo,
                         final Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.authorities = authorities;
    }

    public static UserPrincipal create(final UsuarioEntity usuarioEntity) {
        final List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(usuarioEntity.getPerfil().getDescricao()));

        return new UserPrincipal(usuarioEntity.getId(), usuarioEntity.getNome(), usuarioEntity.getEmail(), usuarioEntity.getSenha(),
                usuarioEntity.getAtivo(), authorities);
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    public String getPassword() {
        return senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return ativo;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonExpired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired();
    }

}