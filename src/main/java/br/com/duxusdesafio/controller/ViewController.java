package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.repository.IntegranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @Autowired
    private IntegranteRepository integranteRepository;

    @GetMapping("/integrantes")
    public String telaIntegrantes() {
        return "cadastro-integrante";
    }

    @GetMapping("/montar-time")
    public String telaTimes(Model model) {
        model.addAttribute("todosIntegrantes", integranteRepository.findAll());
        return "montagem-time";
    }
}