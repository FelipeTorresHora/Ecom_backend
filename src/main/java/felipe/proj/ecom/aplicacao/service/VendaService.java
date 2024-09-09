package felipe.proj.ecom.aplicacao.service;

import felipe.proj.ecom.dominio.entidades.ItemVenda;
import felipe.proj.ecom.dominio.entidades.Produto;
import felipe.proj.ecom.dominio.entidades.Venda;
import felipe.proj.ecom.infraestrutura.excecao.ResourceNotFoundException;
import felipe.proj.ecom.infraestrutura.repositorio.ItemVendaRepository;
import felipe.proj.ecom.infraestrutura.repositorio.ProdutoRepository;
import felipe.proj.ecom.infraestrutura.repositorio.VendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @Transactional
    public Venda addVenda(Venda venda) {

        validarVenda(venda);
        processarItensVenda(venda);
        venda.setDatavenda(LocalDateTime.now());
        calcularValorTotal(venda);
        return vendaRepository.save(venda);
    }


    private void processarItensVenda(Venda venda) {
        for (ItemVenda item : venda.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + item.getProduto().getId()));

            validarProdutoAtivo(produto);
            atualizarEstoque(produto, item.getQuantidade());
            item.setProduto(produto);
            item.setVenda(venda);
        }
    }


    private void validarProdutoAtivo(Produto produto) {
        if (!produto.isAtivo()) {
            throw new IllegalArgumentException(
                    String.format("O produto '%s' está inativo e não pode ser vendido.", produto.getNome())
            );
        }
    }


    private void atualizarEstoque(Produto produto, int quantidade) {
        if (produto.getEstoque() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        produto.setEstoque(produto.getEstoque() - quantidade);
        produtoRepository.save(produto);
    }


    private void calcularValorTotal(Venda venda) {
        float valorTotal = 0.0f;

        for (ItemVenda item : venda.getItens()) {
            float precoTotal = item.getProduto().getPreco() * item.getQuantidade();
            item.setPrecoTotal(precoTotal);
            valorTotal += precoTotal;
        }
        venda.setValorTotal(valorTotal);
    }


    private void validarVenda(Venda venda) {
        if (venda.getItens() == null || venda.getItens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um produto.");
        }
    }

    public List<Venda> getAllVendas() {
        return vendaRepository.findAll();
    }
}
