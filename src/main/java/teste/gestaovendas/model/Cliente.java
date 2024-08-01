package teste.gestaovendas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal limiteCompra;

    @Column(nullable = false)
    private int diaFechamentoFatura;

    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
    private List<Pedido> pedidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getLimiteCompra() {
        return limiteCompra;
    }

    public void setLimiteCompra(BigDecimal limiteCompra) {
        this.limiteCompra = limiteCompra;
    }

    public int getDiaFechamentoFatura() {
        return diaFechamentoFatura;
    }

    public void setDiaFechamentoFatura(int diaFechamentoFatura) {
        this.diaFechamentoFatura = diaFechamentoFatura;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", limiteCompra=" + limiteCompra +
                ", diaFechamentoFatura=" + diaFechamentoFatura +
                '}';
    }
}
