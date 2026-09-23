package dev.victorloures.ledger2.pagamento;

import dev.victorloures.ledger2.conta.Conta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @Column(name = "mes_referencia", nullable = false)
    private LocalDate mesReferencia;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;

    @Column(name = "valor_pago", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorPago;

    private String observacoes;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private Instant criadoEm;

    protected Pagamento() {
    }

    public Pagamento(Conta conta, LocalDate mesReferencia, LocalDate dataPagamento,
                      BigDecimal valorPago, String observacoes) {
        this.conta = conta;
        this.mesReferencia = mesReferencia;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
        this.observacoes = observacoes;
    }

    @PrePersist
    void prePersist() {
        criadoEm = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public Conta getConta() {
        return conta;
    }

    public LocalDate getMesReferencia() {
        return mesReferencia;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }
}
