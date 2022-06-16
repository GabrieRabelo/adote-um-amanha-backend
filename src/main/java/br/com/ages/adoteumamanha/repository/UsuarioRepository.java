package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByIdAndPerfil(Long id, Perfil perfil);

    List<Usuario> findByPerfilAndAtivo(Perfil perfil, boolean ativo);


    @Query("SELECT u FROM Usuario u " +
            "WHERE (coalesce(:nome) is null or (((lower(u.nome) like(concat('%', lower(:nome), '%')))))" +
            "AND u.ativo = true " +
            "AND u.perfil = 'DOADOR')"
    )
    Page<Usuario> findAllDoadorComFiltro(@Param("nome") String nome, final Pageable pageable);
}
