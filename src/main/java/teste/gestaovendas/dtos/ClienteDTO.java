package teste.gestaovendas.dtos;

import teste.gestaovendas.model.Cliente;

import java.math.BigDecimal;

public class ClienteDTO {
    private Long id;
    private String nome;
    private String codigo;
    private BigDecimal limiteCompra;
    private Integer diaFechamentoFatura;

    public ClienteDTO() {}

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.codigo = cliente.getCodigo();
        this.limiteCompra = cliente.getLimiteCompra();
        this.diaFechamentoFatura = cliente.getDiaFechamentoFatura();
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getLimiteCompra() {
        return limiteCompra;
    }

    public void setLimiteCompra(BigDecimal limiteCompra) {
        this.limiteCompra = limiteCompra;
    }

    public Integer getDiaFechamentoFatura() {
        return diaFechamentoFatura;
    }

    public void setDiaFechamentoFatura(Integer diaFechamentoFatura) {
        this.diaFechamentoFatura = diaFechamentoFatura;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", limiteCompra=" + limiteCompra +
                ", diaFechamentoFatura=" + diaFechamentoFatura +
                '}';
    }
}
