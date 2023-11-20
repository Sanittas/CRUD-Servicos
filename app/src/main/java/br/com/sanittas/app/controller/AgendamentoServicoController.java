package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.AgendamentoServico;
import br.com.sanittas.app.repository.AgendamentoRepository;
import br.com.sanittas.app.service.AgendamentoServicoServices;
import br.com.sanittas.app.service.agendamento.dto.AgendamentoCriacaoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoServicoController {
    @Autowired
    private AgendamentoServicoServices services;
    private AgendamentoRepository agendamentoRepository;

    @GetMapping("/")
    public ResponseEntity<List<AgendamentoServico>> listar() {
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

    @GetMapping("/numUsuarios/{empresaId}")
    public ResponseEntity<Integer> getNumUsuariosByEmpresa(@PathVariable int empresaId) {
        try {
            int numUsuarios = agendamentoRepository.countDistinctFkUsuarioByFkServicoEmpresa(empresaId);
            return ResponseEntity.status(200).body(numUsuarios);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @GetMapping("/csatStats/{empresaId}")
    public ResponseEntity<Map<String, Double>> getCSATStats(@PathVariable int empresaId) {
        try {
            Map<String, Double> csat = services.getCSAT(empresaId);
            return ResponseEntity.status(200).body(csat);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @GetMapping("/servicoMaisPopular/{empresaId}")
    public ResponseEntity<AgendamentoServico> getServicoMaisPopular(@PathVariable Integer empresaId) {
        try {
            Optional<AgendamentoServico> servicoMaisPopular = services.findServicoMaisPopularByEmpresaId(empresaId);
            return servicoMaisPopular.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @GetMapping("/receita-total/{empresaId}")
    public ResponseEntity<Double> calculateTotalRevenueByEmpresa(@PathVariable int empresaId) {
        Optional<Double> receitaTotalOptional = services.calculateTotalRevenueByEmpresa(empresaId);

        return receitaTotalOptional.map(receitaTotal ->
                        ResponseEntity.status(200).body(receitaTotal))
                .orElse(ResponseEntity.status(204).build());
    }

    @GetMapping("/atendimentosDia/{empresaId}")
    public ResponseEntity<List<Map<String, Object>>> countAtendimentosDia(@PathVariable Integer empresaId) {
        try {
            List<Map<String, Object>> atendimentosDia = services.countAtendimentosDia(empresaId);
            return ResponseEntity.status(200).body(atendimentosDia);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


//    @GetMapping("/receitaDoDia/{empresaId}")
//    public ResponseEntity<BigDecimal> getReceitaDoDia(@PathVariable int empresaId) {
//        try {
//            BigDecimal receitaDoDia = services.getReceitaDoDia(empresaId);
//            return ResponseEntity.ok(receitaDoDia);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getLocalizedMessage());
//        }
//    }


//    @GetMapping("/receitaUltimos6Meses/{empresaId}")
//    public ResponseEntity<List<Object[]>> getReceitaServicoUltimos6Meses(@PathVariable int empresaId) {
//        try {
//            List<Object[]> receitaServico = services.getReceitaServicoUltimos6Meses(empresaId);
//            return ResponseEntity.ok(receitaServico);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getLocalizedMessage());
//        }
//    }

}
