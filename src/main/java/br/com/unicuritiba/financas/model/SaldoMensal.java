package br.com.unicuritiba.financas.model;

import java.math.BigDecimal;

public record SaldoMensal(int ano, int mes,
		BigDecimal totalReceitas, BigDecimal totalDespesas, BigDecimal saldo) { 
	
}
