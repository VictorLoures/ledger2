package dev.victorloures.ledger2.pagamento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {

    List<Pagamento> findByConta_IdOrderByMesReferenciaDesc(UUID contaId);

    boolean existsByConta_IdAndMesReferencia(UUID contaId, LocalDate mesReferencia);

    boolean existsByConta_Id(UUID contaId);

    Optional<Pagamento> findByIdAndConta_Id(UUID id, UUID contaId);
}
