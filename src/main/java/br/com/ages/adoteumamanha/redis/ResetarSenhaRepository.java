package br.com.ages.adoteumamanha.redis;

import br.com.ages.adoteumamanha.domain.entity.ResetarSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetarSenhaRepository extends JpaRepository<ResetarSenha, Long> {

    Optional<ResetarSenha> findByTokenAndEmail(final String token, final String email);
}
