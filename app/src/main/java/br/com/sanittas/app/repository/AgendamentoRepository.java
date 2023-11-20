package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.AgendamentoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<AgendamentoServico, Integer> {

    // contagem usuarios
    @Query("SELECT COUNT(DISTINCT a.usuario.id) FROM AgendamentoServico a WHERE a.servicoEmpresa.id = :empresaId")
    int countDistinctFkUsuarioByFkServicoEmpresa(@Param("empresaId") int empresaId);

    // csat
    @Query("SELECT COUNT(*) AS total_avaliacoes, " +
            "SUM(CASE WHEN a.avaliacaoServico >= 4 THEN 1 ELSE 0 END) AS avaliacoes_positivas, " +
            "ROUND((SUM(CASE WHEN a.avaliacaoServico >= 4 THEN 1 ELSE 0 END) / COUNT(*)) * 100, 1) AS csat_percentual " +
            "FROM AgendamentoServico a WHERE a.servicoEmpresa.id = :empresaId")
    Map<String, Double> getCSAT(@Param("empresaId") int empresaId);

    // servico + popular
    @Query("SELECT a FROM AgendamentoServico a JOIN FETCH a.servicoEmpresa s WHERE a.servicoEmpresa.id = :empresaId " +
            "GROUP BY a.servicoEmpresa ORDER BY COUNT(a) DESC")
    Optional<AgendamentoServico> findServicoMaisPopularByEmpresaId(@Param("empresaId") Integer empresaId);

    // receita total
    @Query("SELECT SUM(a.servicoEmpresa.valorServico) FROM AgendamentoServico a WHERE a.servicoEmpresa.id = :empresaId")
    Optional<Double> calculateTotalRevenueByEmpresa(@Param("empresaId") int empresaId);

    // atendimento no dia
    @Query("SELECT s.descricao AS servico, COUNT(a) AS quantidade_atendimentos " +
            "FROM AgendamentoServico a " +
            "JOIN a.servicoEmpresa se " +
            "JOIN se.servico s " +
            "WHERE DATE(a.dataHoraAgendamento) = CURRENT_DATE " +
            "AND a.servicoEmpresa.empresa.id = :empresaId " +
            "GROUP BY s.descricao")
    List<Object[]> countAtendimentosDia(@Param("empresaId") Integer empresaId);

    //receita dia
//    @Query("SELECT COALESCE(SUM(se.valorServico), 0) FROM AgendamentoServico a " +
//            "JOIN a.servicoEmpresa se JOIN a.pagamento.id p " +
//            "WHERE DATE(p.dataHoraPagamento) = CURRENT_DATE AND a.servicoEmpresa.id = :empresaId")
//    BigDecimal getReceitaDoDia(@Param("empresaId") int empresaId);


    // receita serviço 6 meses
//    @Query("SELECT s.id, s.descricao, MONTH(a.dataHoraAgendamento) AS mes, YEAR(a.dataHoraAgendamento) AS ano, COALESCE(SUM(se.valorServico), 0) AS receita " +
//            "FROM Servico s " +
//            "JOIN ServicoEmpresa se ON s.id = se.servico.id " +
//            "JOIN AgendamentoServico a ON se.empresa.id = a.servicoEmpresa.id AND se.id = a.servicoEmpresa.id " +
//            "JOIN Pagamento p ON a.pagamento.id = p.id " +
//            "WHERE a.dataHoraAgendamento >= CURRENT_DATE - INTERVAL 6 MONTH AND se.empresa.id = :empresaId " +
//            "GROUP BY s.id, s.descricao, YEAR(a.dataHoraAgendamento), MONTH(a.dataHoraAgendamento) " +
//            "ORDER BY ano DESC, mes DESC, receita DESC")
//    List<Object[]> getReceitaServicoUltimos6Meses(@Param("empresaId") int empresaId);
}
