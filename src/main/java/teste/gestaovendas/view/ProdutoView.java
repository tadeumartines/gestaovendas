package teste.gestaovendas.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import teste.gestaovendas.client.HttpUtil;
import teste.gestaovendas.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ProdutoView extends JFrame {
    private JTextField codigoField;
    private JTextField descricaoField;
    private JTextField precoField;
    private JButton salvarButton;
    private ObjectMapper objectMapper;

    public ProdutoView() {
        initComponents();
        configurarJackson();
    }

    private void initComponents() {
        setTitle("Cadastro de Produto");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel codigoLabel = new JLabel("Código:");
        codigoField = new JTextField();

        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoField = new JTextField();

        JLabel precoLabel = new JLabel("Preço:");
        precoField = new JTextField();
        precoField.setInputVerifier(new NumberInputVerifier());

        salvarButton = new JButton("Salvar");

        panel.add(codigoLabel);
        panel.add(codigoField);
        panel.add(descricaoLabel);
        panel.add(descricaoField);
        panel.add(precoLabel);
        panel.add(precoField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(salvarButton);

        add(panel, BorderLayout.CENTER);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarProduto();
            }
        });
    }

    private void configurarJackson() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Para suportar LocalDate, se necessário
    }

    private void salvarProduto() {
        String codigo = codigoField.getText();
        String descricao = descricaoField.getText();
        String preco = precoField.getText();

        Produto produto = new Produto();
        produto.setCodigo(codigo);
        produto.setDescricao(descricao);
        try {
            produto.setPreco(new BigDecimal(preco));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O preço deve ser um número válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String produtoJson = objectMapper.writeValueAsString(produto);
            HttpUtil.post("http://localhost:8080/produtos", produtoJson);
            JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProdutoView().setVisible(true);
            }
        });
    }
}
