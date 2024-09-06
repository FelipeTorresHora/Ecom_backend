package felipe.proj.ecom.aplicacao.service;

import felipe.proj.ecom.dominio.entidades.Produto;
import felipe.proj.ecom.infraestrutura.excecao.ResourceNotFoundException;
import felipe.proj.ecom.infraestrutura.repositorio.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    public Produto getProdutoById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
    }

    public Produto addProduto(Produto produto) {
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    public Produto updateProduto(Long id, Produto produto) {
        Produto produtoExistente = getProdutoById(id);
        validarProduto(produto);
        produtoExistente.setNome(produto.getNome());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setEstoque(produto.getEstoque());
        return produtoRepository.save(produtoExistente);
    }

    public void deleteProduto(Long id) {
        Produto produto = getProdutoById(id);
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

    private void validarProduto(Produto produto) {
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço deve ser positivo.");
        }
    }
}
