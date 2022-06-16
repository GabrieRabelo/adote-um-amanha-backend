package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByIdAndPerfil(Long id, Perfil perfil);

    List<Usuario> findByPerfilAndAtivo(Perfil perfil, boolean ativo);
}
