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

import java.util.List;

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
        try{
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
}
