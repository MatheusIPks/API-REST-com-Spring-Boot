#Nome: Matheus Iury  | RA: 172312657
# Movimentação Financeira com API REST e Spring Boot

## Como usar o sistema
1. Faça o download do repositório e extraia

**No Eclipse:**

1. File > Import > Maven > Existing Maven Projects e selecione a pasta do projeto.
2. Botão direito no projeto, Maven > Update Project.
3. Botão direito em FinancasApplication.java, Run As > Java Application.
4. Quando o console mostrar Started FinancasApplication, a API está no ar em:

http://localhost:8080/movimentacoes

# Testes das funcionalidades no Postman

**1 - Teste de cadastro de movimentação**
![cadastro](docs/primeiro_cadastro.jpg)

**2 - Teste consulta de movimentação**
![consulta](docs/consulta.jpg)

**3 - Teste alteração de movimentação pelo Id**
![alterar](docs/alterar_dados_id_1.jpg)

**4 - Teste de exclusão de movimentação** 
![excluir](docs/deletar_id_1.jpg)

# Testes das regras de aplicação

**1 - Valor maior que zero**
![valor](docs/teste_valor_0.jpg)

**2 - Possuir descrição**
![descricao](docs/descricao.jpg)

**3 - Possuir data**
![data](docs/teste_sem_data.jpg)

**4/5 - Possuir tipo/RECEITA ou DESPESA**
![tipo](docs/teste_sem_tipo.jpg)

**6 - Calculo do saldo mensal**
1. Cadastro da receita mensal
![receita](docs/cadastro_receita.jpg)
2. Calculo
![calculo](docs/calculo_saldo_mensal.jpg)

# FIM 😎


