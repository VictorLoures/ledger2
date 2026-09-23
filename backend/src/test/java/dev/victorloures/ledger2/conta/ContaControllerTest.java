package dev.victorloures.ledger2.conta;

import dev.victorloures.ledger2.conta.dto.ContaRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ContaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarListarBuscarAtualizarExcluirConta() throws Exception {
        ContaRequest request = new ContaRequest(
                "Aluguel", new BigDecimal("1500.00"), 5, "Moradia", true, null, null
        );

        String responseJson = mockMvc.perform(post("/api/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Aluguel"))
                .andExpect(jsonPath("$.paga").value(false))
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(responseJson).get("id").asText();

        mockMvc.perform(get("/api/contas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id));

        mockMvc.perform(get("/api/contas/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Aluguel"));

        ContaRequest atualizacao = new ContaRequest(
                "Aluguel apto novo", new BigDecimal("1600.00"), 10, "Moradia", true, null, "Reajuste"
        );
        mockMvc.perform(put("/api/contas/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atualizacao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Aluguel apto novo"))
                .andExpect(jsonPath("$.diaVencimento").value(10));

        mockMvc.perform(delete("/api/contas/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/contas/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRejeitarContaNaoRecorrenteSemDataVencimento() throws Exception {
        ContaRequest request = new ContaRequest(
                "Conserto do carro", new BigDecimal("300.00"), 15, null, false, null, null
        );

        mockMvc.perform(post("/api/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
