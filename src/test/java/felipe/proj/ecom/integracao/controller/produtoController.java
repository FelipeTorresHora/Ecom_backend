package felipe.proj.ecom.integracao.controller;

import felipe.proj.ecom.dominio.entidades.Produto;
import felipe.proj.ecom.infraestrutura.repositorio.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class produtoController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        produtoRepository.deleteAll();
    }

    @Test
    void testGetAllProdutos() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto A");
        produto.setPreco(100);
        produto.setEstoque(50);
        produtoRepository.save(produto);

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto A"));
    }

    @Test
    void testAddProduto() throws Exception {
        String produtoJson = "{\"nome\": \"Produto B\", \"preco\": 200.00, \"estoque\": 20}";

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Produto B"));
    }

    @Test
    void testUpdateProduto() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto A");
        produto.setPreco(100);
        produto.setEstoque(50);
        produto = produtoRepository.save(produto);

        String produtoAtualizadoJson = "{\"nome\": \"Produto A Atualizado\", \"preco\": 120.00, \"estoque\": 40}";

        mockMvc.perform(put("/produtos/" + produto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto A Atualizado"));
    }

    @Test
    void testDeleteProduto() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto A");
        produto.setPreco(100);
        produto.setEstoque(50);
        produto = produtoRepository.save(produto);

        mockMvc.perform(delete("/produtos/" + produto.getId()))
                .andExpect(status().isNoContent());

        Produto produtoInativo = produtoRepository.findById(produto.getId()).get();
        assertFalse(produtoInativo.isAtivo());
    }
}
