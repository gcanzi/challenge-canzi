package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.repository.IntegranteRepository;
import br.com.duxusdesafio.repository.TimeRepository;
import br.com.duxusdesafio.repository.ComposicaoTimeRepository;
import br.com.duxusdesafio.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private IntegranteRepository integranteRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private ComposicaoTimeRepository composicaoTimeRepository;
    
    @Autowired
    private ApiService apiService;

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
    
    /**
     * Busca a formação de um time específico para uma determinada data.
     * Retorna a data e a lista de nomes dos integrantes escalados.
     */
    @GetMapping("/time-da-data")
    public ResponseEntity<Map<String, Object>> timeDaData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        List<Time> todosOsTimes = timeRepository.findAll();
        Time time = apiService.timeDaData(data, todosOsTimes);
        
        if (time == null) return ResponseEntity.notFound().build();
        
        List<String> nomesIntegrantes = time.getComposicaoTime().stream()
                .map(comp -> comp.getIntegrante().getNome())
                .collect(Collectors.toList());
                
        Map<String, Object> response = new HashMap<>();
        response.put("data", time.getData());
        response.put("integrantes", nomesIntegrantes);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Identifica o integrante que participou de mais times dentro de um intervalo de datas.
     * Útil para métricas de engajamento ou frequência de jogadores.
     */
    @GetMapping("/integrante-mais-usado")
    public ResponseEntity<Integrante> integranteMaisUsado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeRepository.findAll();
        Integrante integrante = apiService.integranteMaisUsado(dataInicial, dataFinal, todosOsTimes);
        return ResponseEntity.ok(integrante);
    }

    /**
     * Retorna a lista de nomes dos integrantes que compõem a formação de time mais repetida
     * no período selecionado.
     */
    @GetMapping("/time-mais-comum")
    public ResponseEntity<List<String>> timeMaisComum(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeRepository.findAll();
        List<String> time = apiService.integrantesDoTimeMaisComum(dataInicial, dataFinal, todosOsTimes);
        return ResponseEntity.ok(time);
    }

    /**
     * Analisa e retorna qual função foi a mais utilizada nos times
     * dentro do período informado.
     */
    @GetMapping("/funcao-mais-comum")
    public ResponseEntity<Map<String, String>> funcaoMaisComum(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeRepository.findAll();
        String funcao = apiService.funcaoMaisComum(dataInicial, dataFinal, todosOsTimes);
        
        Map<String, String> response = new HashMap<>();
        response.put("Função", funcao);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Identifica a franquia com maior número de integrantes
     * nos times escalados no período.
     */
    @GetMapping("/franquia-mais-famosa")
    public ResponseEntity<Map<String, String>> franquiaMaisFamosa(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeRepository.findAll();
        String franquia = apiService.franquiaMaisFamosa(dataInicial, dataFinal, todosOsTimes);
        
        Map<String, String> response = new HashMap<>();
        response.put("Franquia", franquia);
        return ResponseEntity.ok(response);
    }

    /**
     * Gera um relatório quantitativo de quantos integrantes únicos cada franquia
     * possui dentro do intervalo de datas.
     */
    @GetMapping("/contagem-por-franquia")
    public ResponseEntity<Map<String, Long>> contagemPorFranquia(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeRepository.findAll();
        Map<String, Long> contagem = apiService.contagemPorFranquia(dataInicial, dataFinal, todosOsTimes);
        return ResponseEntity.ok(contagem);
    }

    /**
     * Gera um relatório quantitativo da distribuição de integrantes únicos por cada função 
     * no período selecionado.
     */
    @GetMapping("/contagem-por-funcao")
    public ResponseEntity<Map<String, Long>> contagemPorFuncao(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        
        List<Time> todosOsTimes = timeRepository.findAll();
        Map<String, Long> contagem = apiService.contagemPorFuncao(dataInicial, dataFinal, todosOsTimes);
        return ResponseEntity.ok(contagem);
    }
}