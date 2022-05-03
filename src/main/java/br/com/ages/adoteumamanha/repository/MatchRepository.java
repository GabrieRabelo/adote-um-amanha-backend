package br.com.ages.adoteumamanha.repository;

import br.com.ages.adoteumamanha.domain.entity.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    default Page<Match> findAllById(final Long id) {
        return null;
    }
}
