# Ledger2 — Contexto do projeto

## O que é

App pessoal de controle de gastos e contas recorrentes. Cadastra contas com data
de vencimento e dispara lembretes automáticos (e-mail, e futuramente WhatsApp) no
dia/horário configurado. Sucessor do projeto de estudo original — ver histórico em
`docs/` se precisar de contexto de decisões antigas.

## Stack

- Back-end: Java + Spring Boot
- Banco: PostgreSQL
- Containerização: Docker + Docker Compose (aplicação e banco sobem via `docker compose up`)
- Hospedagem alvo: Oracle Cloud Always Free Tier (VM sempre ativa — importante pro
  scheduler de lembretes não depender do app "acordar" na hora certa)
- Front-end: React + Tailwind CSS + shadcn/ui (componentes) + lucide-react (ícones) +
  Recharts (gráficos). Diferente do projeto anterior, aqui o front recebe atenção
  de verdade — não é só uma tela básica de visualização.

## Filosofia deste projeto (diferente do anterior)

Não seguimos uma trilha fechada de módulos. O projeto cresce organicamente:
primeiro o essencial funcionando (MVP), depois funcionalidades conforme a
necessidade real aparece. O README tem um backlog de ideias sem ordem fixa —
não é um roteiro obrigatório, é só uma lista de possibilidades.

## Como trabalhar comigo neste projeto

- Antes de implementar algo novo ou usar um conceito que eu ainda não usei
  (ex: agendamento, envio de e-mail, deploy em VM), **explique o conceito e as
  alternativas primeiro**. Só implemente depois que eu confirmar que entendi.
- Nos módulos de back-end e SQL, revisar o "porquê" de cada decisão é mais
  importante que entregar código rápido. Não pule essa parte mesmo se eu não
  pedir explicitamente.
- Seja específico e direto nas implementações — evite gerar código especulativo
  ou features não pedidas, para não desperdiçar contexto.
- Ao final de cada funcionalidade concluída, atualize a seção "Progresso" abaixo.

## Progresso

**Em andamento:** ainda não iniciado — definindo estrutura inicial do projeto

### MVP
- [ ] Cadastro de conta (nome, valor, dia de vencimento)
- [ ] Job agendado que verifica contas vencendo e dispara lembrete
- [ ] Envio de lembrete por e-mail
- [ ] Marcar conta como paga

## Decisões tomadas

(atualizar conforme o projeto avança — schema, nomes de tabelas, escolhas de bibliotecas, etc.)
