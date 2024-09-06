package felipe.proj.ecom.integracao.controller;

import felipe.proj.ecom.dominio.entidades.Produto;
import felipe.proj.ecom.infraestrutura.repositorio.ProdutoRepository;
import felipe.proj.ecom.infraestrutura.repositorio.VendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @BeforeEach
    void setUp() {
        produtoRepository.deleteAll();
        vendaRepository.deleteAll();
    }

    @Test
    void testAddVenda() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto A");
        produto.setPreco(100);
        produto.setEstoque(50);
        produto = produtoRepository.save(produto);

        String vendaJson = "{\"itens\": [{\"produto\": {\"id\": " + produto.getId() + "}, \"quantidade\": 2}]}";

        mockMvc.perform(post("/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vendaJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.valorTotal").value(200.00));

        Produto produtoAtualizado = produtoRepository.findById(produto.getId()).get();
        assertEquals(48, produtoAtualizado.getEstoque());
    }

    @Test
    void testGetRelatorioPorSemanaAtual() throws Exception {
        mockMvc.perform(get("/vendas/relatorio/semana"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));  // Supondo que não há vendas na semana atual
    }
}
