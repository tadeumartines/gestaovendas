package teste.gestaovendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teste.gestaovendas.dtos.PedidoDTO;
import teste.gestaovendas.dtos.ResumoPedidoDTO;
import teste.gestaovendas.model.Pedido;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Consultar pedidos com filtros
    @Query("SELECT new teste.gestaovendas.dtos.PedidoDTO(p) " +
            "FROM Pedido p " +
            "WHERE (:clienteId IS NULL OR p.cliente.id = :clienteId) " +
            "AND (:produtoId IS NULL OR :produtoId IN (SELECT ip.produto.id FROM ItemPedido ip WHERE ip.pedido.id = p.id)) " +
            "AND (:dataInicio IS NULL OR p.dataPedido >= :dataInicio) " +
            "AND (:dataFim IS NULL OR p.dataPedido <= :dataFim) " +
            "AND (:ativo IS NULL OR p.ativo = :ativo) " +
            "AND (:excluido IS NULL OR p.excluido = :excluido)")
    List<PedidoDTO> findByFiltros(@Param("clienteId") Long clienteId,
                                  @Param("produtoId") Long produtoId,
                                  @Param("dataInicio") LocalDate dataInicio,
                                  @Param("dataFim") LocalDate dataFim,
                                  @Param("ativo") Boolean ativo,
                                  @Param("excluido") Boolean excluido);

    // Agrupar pedidos por cliente
    @Query("SELECT new teste.gestaovendas.dtos.ResumoPedidoDTO(p.cliente.id, p.cliente.nome, SUM(ip.precoUnitario * ip.quantidade)) " +
            "FROM Pedido p " +
            "JOIN p.itens ip " +
            "WHERE (:dataInicio IS NULL OR p.dataPedido >= :dataInicio) " +
            "AND (:dataFim IS NULL OR p.dataPedido <= :dataFim) " +
            "GROUP BY p.cliente.id, p.cliente.nome")
    List<ResumoPedidoDTO> findAgrupadosPorCliente(@Param("dataInicio") LocalDate dataInicio,
                                                  @Param("dataFim") LocalDate dataFim);

    // Agrupar pedidos por produto
    @Query("SELECT new teste.gestaovendas.dtos.ResumoPedidoDTO(ip.produto.id, ip.produto.descricao, SUM(ip.precoUnitario * ip.quantidade)) " +
            "FROM Pedido p " +
            "JOIN p.itens ip " +
            "WHERE (:dataInicio IS NULL OR p.dataPedido >= :dataInicio) " +
            "AND (:dataFim IS NULL OR p.dataPedido <= :dataFim) " +
            "GROUP BY ip.produto.id, ip.produto.descricao")
    List<ResumoPedidoDTO> findAgrupadosPorProduto(@Param("dataInicio") LocalDate dataInicio,
                                                  @Param("dataFim") LocalDate dataFim);
}
