package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.service.CasaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CasaController {

    private final CasaService casaService;

    @GetMapping(value = "/public/casas/{id}")
    public ResponseEntity<CasaDescricaoResponse> buscaCasaPorId(@PathVariable Long id) {

        log.info("Buscando um usuario por id. Id: {}", id);

        return ResponseEntity.of(casaService.buscaCasaDescricao(id));
    }

}
