package br.com.sanittas.app.service;

import br.com.sanittas.app.api.configuration.security.jwt.GerenciadorTokenJwt;
import br.com.sanittas.app.model.CategoriaServico;
import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Servico;
import br.com.sanittas.app.repository.CategoriaServicoRepository;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.ServicoRepository;
import br.com.sanittas.app.service.autenticacao.dto.EmpresaLoginDto;
import br.com.sanittas.app.service.autenticacao.dto.EmpresaTokenDto;
import br.com.sanittas.app.service.categoria.dto.CategoriaServicoCriacaoDto;
import br.com.sanittas.app.service.empresa.dto.EmpresaMapper;
import br.com.sanittas.app.service.servico.dto.ServicoCriacaoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class ServicosServices {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private CategoriaServicoRepository categoriaServicoRepository;

    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    public void cadastrar(ServicoCriacaoDto dados) {
        try {
            CategoriaServico categoria = categoriaServicoRepository.findById(dados.getFkCategoriaServico()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Categoria não encontrada"));
            Servico novoServico = new Servico();
            novoServico.setDescricao(dados.getDescricao());
            novoServico.setCategoriaServico(categoria);
            servicoRepository.save(novoServico);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


}
