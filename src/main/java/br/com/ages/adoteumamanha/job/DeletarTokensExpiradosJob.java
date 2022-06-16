package br.com.ages.adoteumamanha.job;

import br.com.ages.adoteumamanha.domain.entity.ResetarSenha;
import br.com.ages.adoteumamanha.repository.ResetarSenhaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeletarTokensExpiradosJob {

    private final ResetarSenhaRepository repository;

    @Value("${resetar-password-token.ttl}")
    private Long resetarSenhaTokenTtl;

    @Scheduled(cron = "${cron.deletar-token-expirado}")
    public void executar() {

        final List<ResetarSenha> lista = repository.findAll();

        final List<ResetarSenha> expirados = lista.stream()
                .filter(resetarSenha -> isFalse(resetarSenha.getUsado()))
                .filter(resetarSenha -> Duration.between(resetarSenha.getData(), LocalDateTime.now()).toMinutes() > resetarSenhaTokenTtl)
                .collect(Collectors.toList());

        log.info(" Tokens expirados a serem deletados {}", expirados);
        repository.deleteAll(expirados);
    }
}

