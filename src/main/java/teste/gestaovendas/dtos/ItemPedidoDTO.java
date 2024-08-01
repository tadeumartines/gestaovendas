package teste.gestaovendas.dtos;

import teste.gestaovendas.model.ItemPedido;
import teste.gestaovendas.model.Produto;

import java.math.BigDecimal;

public class ItemPedidoDTO {
    private Long id;
    private ProdutoDTO produto;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    public ItemPedidoDTO() {}

    public ItemPedidoDTO(Long id, Produto produto, Integer quantidade, BigDecimal precoUnitario) {
        this.id = id;
        this.produto = new ProdutoDTO(produto);
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public ItemPedidoDTO(ItemPedido itemPedido) {
        this.id = itemPedido.getId();
        this.produto = new ProdutoDTO(itemPedido.getProduto());
        this.quantidade = itemPedido.getQuantidade();
        this.precoUnitario = itemPedido.getPrecoUnitario();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
