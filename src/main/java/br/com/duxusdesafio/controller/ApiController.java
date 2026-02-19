package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.repository.IntegranteRepository;
import br.com.duxusdesafio.repository.TimeRepository;
import br.com.duxusdesafio.repository.ComposicaoTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private IntegranteRepository integranteRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private ComposicaoTimeRepository composicaoTimeRepository;

    // Endpoint para cadastrar Integrantes
    @PostMapping("/integrante")
    public ResponseEntity<Integrante> cadastrarIntegrante(@RequestBody Integrante integrante) {
        Integrante salvo = integranteRepository.save(integrante);
        return ResponseEntity.ok(salvo);
    }

    // Endpoint para cadastrar Times
    @PostMapping("/time")
    public ResponseEntity<Time> cadastrarTime(@RequestBody Time time) {
  
        Time timeSalvo = timeRepository.save(time);
        
        if (time.getComposicaoTime() != null) {
            for (ComposicaoTime comp : time.getComposicaoTime()) {
                comp.setTime(timeSalvo);
                composicaoTimeRepository.save(comp);
            }
        }
        
        return ResponseEntity.ok(timeSalvo);
    }
}