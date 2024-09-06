package felipe.proj.ecom.infraestrutura.cache;

import felipe.proj.ecom.dominio.entidades.Produto;
import felipe.proj.ecom.dominio.entidades.Venda;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {
    @Cacheable(value = "produtos")
    public List<Produto> getAllProdutos(List<Produto> produtos) {
        return produtos;
    }

    @CacheEvict(value = "produtos", allEntries = true)
    public void evictAllProdutosCache() {
        // Usado para invalidar o cache de produtos
    }

    @Cacheable(value = "vendas")
    public List<Venda> getAllVendas(List<Venda> vendas) {
        return vendas;
    }

    @CacheEvict(value = "vendas", allEntries = true)
    public void evictAllVendasCache() {
        // Usado para invalidar o cache de vendas
    }
}
