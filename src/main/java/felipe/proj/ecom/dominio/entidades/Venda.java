package felipe.proj.ecom.dominio.entidades;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDateTime datavenda;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> itens;

    @Column(nullable = false)
    private float valorTotal;

    public Venda() {}

    public Venda(Long id, Usuario usuario, LocalDateTime datavenda, List<ItemVenda> itens, float valorTotal) {
        this.id = id;
        this.usuario = usuario;
        this.datavenda = datavenda;
        this.itens = itens;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDatavenda() {
        return datavenda;
    }

    public void setDatavenda(LocalDateTime datavenda) {
        this.datavenda = datavenda;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Float.compare(valorTotal, venda.valorTotal) == 0 && Objects.equals(id, venda.id) && Objects.equals(usuario, venda.usuario) && Objects.equals(datavenda, venda.datavenda) && Objects.equals(itens, venda.itens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, datavenda, itens, valorTotal);
    }

    @Override
    public String
    toString() {
        return "Venda{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", datavenda=" + datavenda +
                ", itens=" + itens +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
