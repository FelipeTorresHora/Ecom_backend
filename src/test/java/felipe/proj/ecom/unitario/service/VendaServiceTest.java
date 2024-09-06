package felipe.proj.ecom.unitario.service;

import felipe.proj.ecom.dominio.entidades.ItemVenda;
import felipe.proj.ecom.dominio.entidades.Produto;
import felipe.proj.ecom.dominio.entidades.Venda;
import felipe.proj.ecom.infraestrutura.repositorio.ProdutoRepository;
import felipe.proj.ecom.infraestrutura.repositorio.VendaRepository;
import felipe.proj.ecom.aplicacao.service.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VendaServiceTest {
    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private VendaService vendaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddVenda_Success() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(100.0f);  // Preço como float
        produto1.setEstoque(10);

        ItemVenda itemVenda1 = new ItemVenda();
        itemVenda1.setProduto(produto1);
        itemVenda1.setQuantidade(2);

        Venda venda = new Venda();
        List<ItemVenda> itens = new ArrayList<>();
        itens.add(itemVenda1);
        venda.setItens(itens);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto1));
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        Venda result = vendaService.addVenda(venda);

        // Comparação usando float
        assertEquals(200.0f, result.getValorTotal(), 0.0001f);  // Valor total esperado como float
        assertEquals(8, produto1.getEstoque());
    }

    @Test
    void testAddVenda_InsufficientStock() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(100);
        produto1.setEstoque(1);  // Estoque insuficiente

        ItemVenda itemVenda1 = new ItemVenda();
        itemVenda1.setProduto(produto1);
        itemVenda1.setQuantidade(2);

        Venda venda = new Venda();
        List<ItemVenda> itens = new ArrayList<>();
        itens.add(itemVenda1);
        venda.setItens(itens);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto1));

        assertThrows(IllegalArgumentException.class, () -> vendaService.addVenda(venda));
    }
}
