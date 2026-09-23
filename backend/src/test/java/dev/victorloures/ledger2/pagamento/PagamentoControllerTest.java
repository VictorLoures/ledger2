package dev.victorloures.ledger2.pagamento;

import dev.victorloures.ledger2.conta.Conta;
import dev.victorloures.ledger2.conta.ContaRepository;
import dev.victorloures.ledger2.pagamento.dto.PagamentoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContaRepository contaRepository;

    private UUID contaId;

    @BeforeEach
    void criarConta() {
        Conta conta = new Conta("Internet", new BigDecimal("120.00"), 20, "Assinatura", true, null, null);
        contaRepository.save(conta);
        contaId = conta.getId();
    }

    @Test
    void deveRegistrarPagamentoEMarcarContaComoPagaNoMes() throws Exception {
        LocalDate mesAtual = YearMonth.now().atDay(1);
        PagamentoRequest request = new PagamentoRequest(mesAtual, LocalDate.now(), new BigDecimal("120.00"), null);

        mockMvc.perform(post("/api/contas/" + contaId + "/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/contas/" + contaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paga").value(true));

        mockMvc.perform(get("/api/contas/" + contaId + "/pagamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].valorPago").value(120.00));
    }

    @Test
    void deveRejeitarPagamentoDuplicadoParaMesmoMes() throws Exception {
        LocalDate mesAtual = YearMonth.now().atDay(1);
        PagamentoRequest request = new PagamentoRequest(mesAtual, LocalDate.now(), new BigDecimal("120.00"), null);

        mockMvc.perform(post("/api/contas/" + contaId + "/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/contas/" + contaId + "/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void deveRetornar404AoRegistrarPagamentoParaContaInexistente() throws Exception {
        LocalDate mesAtual = YearMonth.now().atDay(1);
        PagamentoRequest request = new PagamentoRequest(mesAtual, LocalDate.now(), new BigDecimal("50.00"), null);

        mockMvc.perform(post("/api/contas/" + UUID.randomUUID() + "/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
