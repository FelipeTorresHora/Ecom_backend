package felipe.proj.ecom.unitario.service;

import felipe.proj.ecom.dominio.entidades.Produto;
import felipe.proj.ecom.infraestrutura.excecao.ResourceNotFoundException;
import felipe.proj.ecom.infraestrutura.repositorio.ProdutoRepository;
import felipe.proj.ecom.aplicacao.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduto_Success() {
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setPreco(100);
        produto.setEstoque(10);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto result = produtoService.addProduto(produto);

        assertEquals("Produto Teste", result.getNome());
        assertEquals(100, result.getPreco());
        assertEquals(10, result.getEstoque());
    }

    @Test
    void testAddProduto_InvalidPrice() {
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setPreco(-100);  // Preço inválido
        produto.setEstoque(10);

        assertThrows(IllegalArgumentException.class, () -> produtoService.addProduto(produto));
    }

    @Test
    void testGetProdutoById_Success() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100);
        produto.setEstoque(10);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Produto result = produtoService.getProdutoById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetProdutoById_NotFound() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> produtoService.getProdutoById(1L));
    }

    @Test
    void testDeleteProduto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100);
        produto.setEstoque(10);
        produto.setAtivo(true);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        produtoService.deleteProduto(1L);

        assertFalse(produto.isAtivo());
        verify(produtoRepository, times(1)).save(produto);
    }
}
