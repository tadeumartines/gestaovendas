package teste.gestaovendas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teste.gestaovendas.dtos.PedidoDTO;
import teste.gestaovendas.dtos.ResumoPedidoDTO;
import teste.gestaovendas.model.Cliente;
import teste.gestaovendas.model.ItemPedido;
import teste.gestaovendas.model.Pedido;
import teste.gestaovendas.repository.PedidoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    public Pedido salvar(Pedido pedido) {
        validarPedido(pedido);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    private void validarPedido(Pedido pedido) {
        Cliente cliente = clienteService.buscarPorId(pedido.getCliente().getId()).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        BigDecimal totalPedido = pedido.getItens().stream()
                .map(item -> item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal limiteDisponivel = calcularLimiteDisponivel(cliente);

        if (totalPedido.compareTo(limiteDisponivel) > 0) {
            throw new RuntimeException("Limite de crédito excedido. Limite disponível: " + limiteDisponivel);
        }

        verificarProdutosRepetidos(pedido.getItens());
    }

    private BigDecimal calcularLimiteDisponivel(Cliente cliente) {
        return cliente.getLimiteCompra();
    }

    private void verificarProdutosRepetidos(List<ItemPedido> itens) {
        long distinctCount = itens.stream().map(ItemPedido::getProduto).distinct().count();
        if (distinctCount != itens.size()) {
            throw new RuntimeException("Produtos repetidos na venda não são permitidos");
        }
    }

    public void deletarPorId(Long id) {
    }

    public List<PedidoDTO> buscarPedidos(Long clienteId, Long produtoId, LocalDate dataInicio, LocalDate dataFim, Boolean ativo, Boolean excluido) {
        return pedidoRepository.findByFiltros(clienteId, produtoId, dataInicio, dataFim, ativo, excluido);
    }

    public List<ResumoPedidoDTO> buscarPedidosAgrupados(Boolean porCliente, Boolean porProduto, LocalDate dataInicio, LocalDate dataFim) {
        if (porCliente != null && porCliente) {
            return pedidoRepository.findAgrupadosPorCliente(dataInicio, dataFim);
        } else if (porProduto != null && porProduto) {
            return pedidoRepository.findAgrupadosPorProduto(dataInicio, dataFim);
        } else {
            throw new IllegalArgumentException("Deve especificar agrupamento por cliente ou por produto.");
        }
    }
}
