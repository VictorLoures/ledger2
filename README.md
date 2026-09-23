# Ledger2

App pessoal de controle de gastos e contas recorrentes, com lembretes automáticos
(e-mail/WhatsApp) e visualização de gastos por gráficos.

## Ideia central

Cadastrar contas com data de vencimento. No dia (ou horário) configurado, o sistema
dispara um lembrete — por e-mail ou WhatsApp — avisando que a conta vence.

## Stack

- Back-end: Java + Spring Boot
- Banco: PostgreSQL
- Containerização: Docker + Docker Compose
- Hospedagem: Oracle Cloud Always Free Tier
- Front-end: React + Tailwind CSS + shadcn/ui + lucide-react (ícones) + Recharts (gráficos)

## Front-end

Diferente do projeto anterior (front básico, sem foco), aqui a intenção é caprichar:
React + Tailwind + shadcn/ui (componentes acessíveis, copiados pro projeto — não uma
lib fechada) + lucide-react pros ícones + Recharts pros gráficos de gastos.

## Filosofia do projeto

Diferente do projeto de estudo anterior (trilha por módulos fechados), este cresce
organicamente: começa enxuto com o essencial funcionando, e ganha funcionalidades
conforme a necessidade real aparece — não por um plano fixo. Ideias futuras entram
no backlog abaixo conforme forem surgindo, sem compromisso de ordem.

## MVP (primeira versão funcional)

- [ ] Cadastro de conta (nome, valor, dia de vencimento)
- [ ] Job agendado que verifica contas vencendo e dispara lembrete
- [ ] Envio de lembrete por e-mail (canal mais simples pra começar)
- [ ] Marcar conta como paga

## Backlog (ideias futuras, sem ordem definida)

- Lembrete por WhatsApp (além de e-mail)
- Horário de lembrete configurável por conta
- Contas fixas x variáveis, categorização
- Gráficos de gastos por categoria/mês
- Histórico de pagamentos
- Dashboard com visão geral do mês

## Como rodar

_(preencher conforme o setup inicial for definido)_
