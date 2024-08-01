package teste.gestaovendas.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import teste.gestaovendas.client.HttpUtil;
import teste.gestaovendas.model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ClienteView extends JFrame {
    private JTextField codigoField;
    private JTextField nomeField;
    private JTextField limiteCompraField;
    private JTextField diaFechamentoField;
    private JButton salvarButton;
    private ObjectMapper objectMapper;

    public ClienteView() {
        initComponents();
        configurarJackson();
    }

    private void initComponents() {
        setTitle("Cadastro de Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel codigoLabel = new JLabel("Código:");
        codigoField = new JTextField();

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();

        JLabel limiteCompraLabel = new JLabel("Limite de Compra:");
        limiteCompraField = new JTextField();

        JLabel diaFechamentoLabel = new JLabel("Dia de Fechamento:");
        diaFechamentoField = new JTextField();

        salvarButton = new JButton("Salvar");

        panel.add(codigoLabel);
        panel.add(codigoField);
        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(limiteCompraLabel);
        panel.add(limiteCompraField);
        panel.add(diaFechamentoLabel);
        panel.add(diaFechamentoField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(salvarButton);

        add(panel, BorderLayout.CENTER);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarCliente();
            }
        });
    }

    private void configurarJackson() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Para suportar LocalDate, se necessário
    }

    private void salvarCliente() {
        String codigo = codigoField.getText();
        String nome = nomeField.getText();
        String limiteCompra = limiteCompraField.getText();
        String diaFechamento = diaFechamentoField.getText();

        Cliente cliente = new Cliente();
        cliente.setCodigo(codigo);
        cliente.setNome(nome);
        cliente.setLimiteCompra(new BigDecimal(limiteCompra));
        cliente.setDiaFechamentoFatura(Integer.parseInt(diaFechamento));

        try {
            String clienteJson = objectMapper.writeValueAsString(cliente);
            HttpUtil.post("http://localhost:8080/clientes", clienteJson);
            JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClienteView().setVisible(true);
            }
        });
    }
}
