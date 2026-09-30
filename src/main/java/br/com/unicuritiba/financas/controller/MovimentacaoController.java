package br.com.unicuritiba.financas.controller;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.unicuritiba.financas.model.Movimentacao;
import br.com.unicuritiba.financas.model.SaldoMensal;
import br.com.unicuritiba.financas.model.TipoMovimentacao;
import br.com.unicuritiba.financas.repository.MovimentacaoRepository;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

	private final MovimentacaoRepository repositorio;

	MovimentacaoController(MovimentacaoRepository repositorio) {
		this.repositorio = repositorio;
	}


// Puxa dados	
@GetMapping
public List<Movimentacao> listar() {
	return repositorio.findAll();
	}
	

// Cadastrar
@PostMapping
public ResponseEntity<?> cadastrar(@RequestBody Movimentacao movimentacao) {
	String erro = validar(movimentacao);
	if (erro != null) {
		return ResponseEntity.badRequest().body(Map.of("erro", erro));
	}
	return ResponseEntity.status(HttpStatus.CREATED).body(repositorio.save(movimentacao));
}
	

// Busca ID
@GetMapping("/{id}")
public ResponseEntity<?> buscarPorId(@PathVariable long id) {
	return repositorio.findById(id)
			.<ResponseEntity<?>>map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}
	

// Alterar
@PutMapping("/{id}")
public ResponseEntity<?> alterar(@PathVariable long id,
		@RequestBody Movimentacao movimentacao){
	if (!repositorio.existsById(id)) {
		return ResponseEntity.notFound().build();
	}
	movimentacao.setId(id);
	return ResponseEntity.ok(repositorio.save(movimentacao));
	}

// Deletar
@DeleteMapping("/{id}")
public ResponseEntity<Void> excluir(@PathVariable long id){
	if (!repositorio.existsById(id)) {
		return ResponseEntity.notFound().build();
	}
	repositorio.deleteById(id);
	return ResponseEntity.noContent().build();
	}

private String validar(Movimentacao m) {
	if (m.getDescricao() == null || m.getDescricao().isBlank())
		return "Tem que escrever a descrição!";
	if (m.getValor() == null || m.getValor().compareTo(BigDecimal.ZERO) <= 0)
		return "Tem que ser maior que ZERO o valor!";
	if (m.getData() == null)
		return "Tem que ter DATA!";
	if (m.getTipo() == null)
		return "É RECEITA ou é DESPESA?";
	return null;
	}

@GetMapping("/saldo")
public ResponseEntity<?> saldoMensal(@RequestParam int ano, @RequestParam int mes) {

	if (mes < 1 || mes > 12) {
		return ResponseEntity.badRequest().body(Map.of("erro", "O mês deve estar entre 1 e 12."));
	}

	YearMonth periodo = YearMonth.of(ano, mes);
	List<Movimentacao> doMes = repositorio.findByDataBetween(
			periodo.atDay(1), periodo.atEndOfMonth());

	BigDecimal totalReceitas = BigDecimal.ZERO;
	BigDecimal totalDespesas = BigDecimal.ZERO;
	for (Movimentacao m : doMes) {
		if (m.getTipo() == TipoMovimentacao.RECEITA) {
			totalReceitas = totalReceitas.add(m.getValor());
		} else {
			totalDespesas = totalDespesas.add(m.getValor());
		}
	}

	BigDecimal saldo = totalReceitas.subtract(totalDespesas);
	return ResponseEntity.ok(new SaldoMensal(ano, mes, totalReceitas, totalDespesas, saldo));
}

}
