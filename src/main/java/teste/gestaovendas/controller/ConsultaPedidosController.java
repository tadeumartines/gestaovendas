package teste.gestaovendas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.gestaovendas.dtos.PedidoDTO;
import teste.gestaovendas.dtos.ResumoPedidoDTO;
import teste.gestaovendas.service.PedidoService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class ConsultaPedidosController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> consultarPedidos(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long produtoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Boolean excluido) {

        List<PedidoDTO> pedidos = pedidoService.buscarPedidos(clienteId, produtoId, dataInicio, dataFim, ativo, excluido);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/agrupados")
    public ResponseEntity<List<ResumoPedidoDTO>> consultarPedidosAgrupados(
            @RequestParam(required = false) Boolean porCliente,
            @RequestParam(required = false) Boolean porProduto,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<ResumoPedidoDTO> resultados = pedidoService.buscarPedidosAgrupados(porCliente, porProduto, dataInicio, dataFim);
        return ResponseEntity.ok(resultados);
    }
}
