package felipe.proj.ecom.dominio.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venda_id", nullable = false)
    @JsonIgnore
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;



    @Column(nullable = false)
    private float precoTotal;

    public ItemVenda() {}

    public ItemVenda(Long id, Venda venda, Produto produto, Integer quantidade,  float precoTotal) {
        this.id = id;
        this.venda = venda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public float getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(float precoTotal) {
        this.precoTotal = precoTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVenda itemVenda = (ItemVenda) o;
        return Float.compare(precoTotal, itemVenda.precoTotal) == 0 && Objects.equals(id, itemVenda.id) && Objects.equals(venda, itemVenda.venda) && Objects.equals(produto, itemVenda.produto) && Objects.equals(quantidade, itemVenda.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, venda, produto, quantidade, precoTotal);
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "id=" + id +
                ", venda=" + venda +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", precoTotal=" + precoTotal +
                '}';
    }
}
