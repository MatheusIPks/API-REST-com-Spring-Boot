package br.com.unicuritiba.financas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unicuritiba.financas.model.Movimentacao;

public interface MovimentacaoRepository
	extends JpaRepository<Movimentacao, Long> {

		List<Movimentacao> findByDataBetween(LocalDate inicio, LocalDate fim);
}
