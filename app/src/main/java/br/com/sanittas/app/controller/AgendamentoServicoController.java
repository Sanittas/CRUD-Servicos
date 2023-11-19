package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.AgendamentoServico;
import br.com.sanittas.app.service.AgendamentoServicoServices;
import br.com.sanittas.app.service.agendamento.dto.AgendamentoCriacaoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoServicoController {
    @Autowired
    private AgendamentoServicoServices services;

    @GetMapping("/")
    public ResponseEntity<List<AgendamentoServico>>listar() {
        try {
            List<AgendamentoServico> response = services.listar();
            if (!response.isEmpty()) {
                return ResponseEntity.status(200).body(response);
            }
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid AgendamentoCriacaoDto dados) {
        try {
            services.cadastrar(dados);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
