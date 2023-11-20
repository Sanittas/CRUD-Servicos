package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.ServicoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicoEmpresaRepository extends JpaRepository<ServicoEmpresa, Integer> {

}
