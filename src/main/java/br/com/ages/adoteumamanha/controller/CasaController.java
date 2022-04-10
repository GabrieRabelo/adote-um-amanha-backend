package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.controller.api.CasaControllerApi;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.service.CasaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CasaController implements CasaControllerApi {

    private final CasaService casaService;

    @GetMapping(value = "/public/casas/{id}")
    public ResponseEntity<CasaDescricaoResponse> buscaCasaPorId(@PathVariable final Long id) {

        return ResponseEntity.ok(casaService.buscaCasaDescricao(id));
    }

}


