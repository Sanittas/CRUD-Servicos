package br.com.sanittas.app.service;

import br.com.sanittas.app.model.AgendamentoServico;
import br.com.sanittas.app.model.ServicoEmpresa;
import br.com.sanittas.app.model.Usuario;
import br.com.sanittas.app.repository.AgendamentoRepository;
import br.com.sanittas.app.repository.ServicoEmpresaRepository;
import br.com.sanittas.app.repository.UsuarioRepository;
import br.com.sanittas.app.service.agendamento.dto.AgendamentoCriacaoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendamentoServicoServices {
    @Autowired
    private AgendamentoRepository repository;
    @Autowired
    private ServicoEmpresaRepository servicoEmpresaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<AgendamentoServico> listar() {
        return repository.findAll();
    }

    public void cadastrar(AgendamentoCriacaoDto dados) {
        try {
            ServicoEmpresa servico = servicoEmpresaRepository.findById(dados.getIdServicoEmpresa()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            Usuario usuario = usuarioRepository.findById(dados.getIdUsuario()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            AgendamentoServico novoAgendamento = new AgendamentoServico();
            novoAgendamento.setDataHoraAgendamento(dados.getDataAgendamento());
            novoAgendamento.setServicoEmpresa(servico);
            novoAgendamento.setUsuario(usuario);
            repository.save(novoAgendamento);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public int countDistinctFkUsuarioByFkServicoEmpresa(int empresaId) {
        try {
            return repository.countDistinctFkUsuarioByFkServicoEmpresa(empresaId);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public Map<String, Double> getCSAT(int empresaId) {
        try {
            return repository.getCSAT(empresaId);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public Optional<AgendamentoServico> findServicoMaisPopularByEmpresaId(Integer empresaId) {
        return repository.findServicoMaisPopularByEmpresaId(empresaId);
    }

    public Optional<Double> calculateTotalRevenueByEmpresa(int empresaId) {
        return repository.calculateTotalRevenueByEmpresa(empresaId);
    }

    public List<Map<String, Object>> countAtendimentosDia(Integer empresaId) {
        try {
            List<Object[]> result = repository.countAtendimentosDia(empresaId);
            return result.stream()
                    .map(row -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("servico", row[0]);
                        map.put("quantidade_atendimentos", row[1]);
                        return map;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

//    public BigDecimal getReceitaDoDia(int empresaId) {
//        try {
//            return repository.getReceitaDoDia(empresaId);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getLocalizedMessage());
//        }
//    }

//    public List<Object[]> getReceitaServicoUltimos6Meses(int empresaId) {
//        try {
//            return repository.getReceitaServicoUltimos6Meses(empresaId);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getLocalizedMessage());
//        }
//    }

}
