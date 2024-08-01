package teste.gestaovendas.dtos;

import java.math.BigDecimal;

public class ResumoPedidoDTO {
    private Long id;
    private String nome;
    private BigDecimal valorTotal;

    public ResumoPedidoDTO() {}

    public ResumoPedidoDTO(Long id, String nome, BigDecimal valorTotal) {
        this.id = id;
        this.nome = nome;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
