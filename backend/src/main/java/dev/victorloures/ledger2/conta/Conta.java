package dev.victorloures.ledger2.conta;

import dev.victorloures.ledger2.pagamento.Pagamento;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(name = "dia_vencimento", nullable = false)
    private Integer diaVencimento;

    private String categoria;

    @Column(nullable = false)
    private boolean recorrente;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    private String observacoes;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private Instant criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos = new ArrayList<>();

    protected Conta() {
    }

    public Conta(String nome, BigDecimal valor, Integer diaVencimento, String categoria,
                 boolean recorrente, LocalDate dataVencimento, String observacoes) {
        this.nome = nome;
        this.valor = valor;
        this.diaVencimento = diaVencimento;
        this.categoria = categoria;
        this.recorrente = recorrente;
        this.dataVencimento = dataVencimento;
        this.observacoes = observacoes;
    }

    @PrePersist
    void prePersist() {
        Instant agora = Instant.now();
        criadoEm = agora;
        atualizadoEm = agora;
    }

    @PreUpdate
    void preUpdate() {
        atualizadoEm = Instant.now();
    }

    public void atualizar(String nome, BigDecimal valor, Integer diaVencimento, String categoria,
                           boolean recorrente, LocalDate dataVencimento, String observacoes) {
        this.nome = nome;
        this.valor = valor;
        this.diaVencimento = diaVencimento;
        this.categoria = categoria;
        this.recorrente = recorrente;
        this.dataVencimento = dataVencimento;
        this.observacoes = observacoes;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isRecorrente() {
        return recorrente;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }
}
