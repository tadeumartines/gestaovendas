package teste.gestaovendas.dtos;

import teste.gestaovendas.model.Cliente;
import teste.gestaovendas.model.ItemPedido;
import teste.gestaovendas.model.Pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDTO {
    private Long id;
    private ClienteDTO cliente;
    private List<ItemPedidoDTO> itens;
    private BigDecimal valorTotal;
    private LocalDate dataPedido;

    public PedidoDTO() {}

    public PedidoDTO(Long id, Cliente cliente, List<ItemPedido> itens, BigDecimal valorTotal, LocalDate dataPedido) {
        this.id = id;
        this.cliente = new ClienteDTO(cliente);
        this.itens = itens.stream().map(ItemPedidoDTO::new).collect(Collectors.toList());
        this.valorTotal = valorTotal;
        this.dataPedido = dataPedido;
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cliente = new ClienteDTO(pedido.getCliente());
        this.itens = pedido.getItens().stream().map(ItemPedidoDTO::new).collect(Collectors.toList());
        this.valorTotal = pedido.getItens().stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.dataPedido = pedido.getDataPedido();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }
}
