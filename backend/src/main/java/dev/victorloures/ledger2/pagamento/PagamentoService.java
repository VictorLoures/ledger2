package dev.victorloures.ledger2.pagamento;

import dev.victorloures.ledger2.conta.Conta;
import dev.victorloures.ledger2.conta.ContaNotFoundException;
import dev.victorloures.ledger2.conta.ContaRepository;
import dev.victorloures.ledger2.pagamento.dto.PagamentoRequest;
import dev.victorloures.ledger2.pagamento.dto.PagamentoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ContaRepository contaRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, ContaRepository contaRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.contaRepository = contaRepository;
    }

    public PagamentoResponse registrar(UUID contaId, PagamentoRequest request) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new ContaNotFoundException(contaId));

        LocalDate mesReferencia = YearMonth.from(request.mesReferencia()).atDay(1);

        if (pagamentoRepository.existsByConta_IdAndMesReferencia(contaId, mesReferencia)) {
            throw new PagamentoDuplicadoException(mesReferencia);
        }

        Pagamento pagamento = new Pagamento(
                conta,
                mesReferencia,
                request.dataPagamento(),
                request.valorPago(),
                request.observacoes()
        );
        pagamentoRepository.save(pagamento);
        return toResponse(pagamento);
    }

    @Transactional(readOnly = true)
    public List<PagamentoResponse> listarPorConta(UUID contaId) {
        if (!contaRepository.existsById(contaId)) {
            throw new ContaNotFoundException(contaId);
        }
        return pagamentoRepository.findByConta_IdOrderByMesReferenciaDesc(contaId).stream()
                .map(this::toResponse)
                .toList();
    }

    public void excluir(UUID contaId, UUID pagamentoId) {
        Pagamento pagamento = pagamentoRepository.findByIdAndConta_Id(pagamentoId, contaId)
                .orElseThrow(() -> new PagamentoNotFoundException(pagamentoId));
        pagamentoRepository.delete(pagamento);
    }

    private PagamentoResponse toResponse(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getId(),
                pagamento.getMesReferencia(),
                pagamento.getDataPagamento(),
                pagamento.getValorPago(),
                pagamento.getObservacoes(),
                pagamento.getCriadoEm()
        );
    }
}
