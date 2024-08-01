package teste.gestaovendas.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import teste.gestaovendas.client.HttpUtil;
import teste.gestaovendas.model.Cliente;
import teste.gestaovendas.model.ItemPedido;
import teste.gestaovendas.model.Pedido;
import teste.gestaovendas.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoView extends JFrame {
    private JComboBox<Cliente> clienteComboBox;
    private JComboBox<Produto> produtoComboBox;
    private JTextField quantidadeField;
    private JButton adicionarButton;
    private JButton salvarButton;
    private JTable itensTable;
    private DefaultTableModel tableModel;
    private JButton excluirButton;
    private JButton editarQuantidadeButton;
    private ObjectMapper objectMapper;

    public PedidoView() {
        initComponents();
        configurarJackson();
        carregarClientes();
        carregarProdutos();
    }

    private void initComponents() {
        setTitle("Cadastro de Pedido");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel clienteLabel = new JLabel("Cliente:");
        clienteComboBox = new JComboBox<>();

        JLabel produtoLabel = new JLabel("Produto:");
        produtoComboBox = new JComboBox<>();

        JLabel quantidadeLabel = new JLabel("Quantidade:");
        quantidadeField = new JTextField();

        adicionarButton = new JButton("Adicionar");
        salvarButton = new JButton("Salvar");

        panel.add(clienteLabel);
        panel.add(clienteComboBox);
        panel.add(produtoLabel);
        panel.add(produtoComboBox);
        panel.add(quantidadeLabel);
        panel.add(quantidadeField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(adicionarButton);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(salvarButton);

        excluirButton = new JButton("Excluir Produto");
        editarQuantidadeButton = new JButton("Editar Quantidade");
        panel.add(excluirButton);
        panel.add(editarQuantidadeButton);

        tableModel = new DefaultTableModel(new String[]{"Produto", "Quantidade", "Preço Unitário", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All columns are not editable
            }
        };
        itensTable = new JTable(tableModel);
        itensTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(itensTable);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarItem();
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarPedido();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirItem();
            }
        });

        editarQuantidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarQuantidadeItem();
            }
        });
    }

    private void configurarJackson() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Para suportar LocalDate
    }

    private void carregarClientes() {
        try {
            String response = HttpUtil.get("http://localhost:8080/clientes");
            List<Cliente> clientes = objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, Cliente.class));

            clienteComboBox.setModel(new DefaultComboBoxModel<>(clientes.toArray(new Cliente[0])));
            clienteComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Cliente cliente = (Cliente) value;
                    setText(cliente.getId() + " - " + cliente.getNome());
                    return this;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarProdutos() {
        try {
            String response = HttpUtil.get("http://localhost:8080/produtos");
            List<Produto> produtos = objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, Produto.class));

            produtoComboBox.setModel(new DefaultComboBoxModel<>(produtos.toArray(new Produto[0])));
            produtoComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Produto produto = (Produto) value;
                    setText(produto.getId() + " - " + produto.getDescricao());
                    return this;
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarItem() {
        Produto produto = (Produto) produtoComboBox.getSelectedItem();
        String quantidadeStr = quantidadeField.getText();

        if (produto == null || quantidadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser um número positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser um número inteiro positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BigDecimal precoUnitario = produto.getPreco();
        BigDecimal valorTotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(produto.getDescricao())) {
                int quantidadeExistente = (Integer) tableModel.getValueAt(i, 1);
                int novaQuantidade = quantidadeExistente + quantidade;
                BigDecimal novoValorTotal = precoUnitario.multiply(BigDecimal.valueOf(novaQuantidade));
                tableModel.setValueAt(novaQuantidade, i, 1);
                tableModel.setValueAt(novoValorTotal, i, 3);
                quantidadeField.setText("");
                return;
            }
        }

        tableModel.addRow(new Object[]{produto.getDescricao(), quantidade, precoUnitario, valorTotal});
        quantidadeField.setText("");
    }

    private void salvarPedido() {
        Cliente cliente = (Cliente) clienteComboBox.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate total value of the order
        BigDecimal valorTotalPedido = BigDecimal.ZERO;
        List<ItemPedido> itens = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String descricao = (String) tableModel.getValueAt(i, 0);
            int quantidade = (Integer) tableModel.getValueAt(i, 1);
            BigDecimal precoUnitario = (BigDecimal) tableModel.getValueAt(i, 2);
            BigDecimal valorTotal = (BigDecimal) tableModel.getValueAt(i, 3);

            valorTotalPedido = valorTotalPedido.add(valorTotal);

            Produto produto = null;
            for (int j = 0; j < produtoComboBox.getItemCount(); j++) {
                Produto p = produtoComboBox.getItemAt(j);
                if (p.getDescricao().equals(descricao)) {
                    produto = p;
                    break;
                }
            }

            if (produto == null) {
                JOptionPane.showMessageDialog(this, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(quantidade);
            itemPedido.setPrecoUnitario(precoUnitario);

            itens.add(itemPedido);
        }

        // Validate customer's credit limit
        if (!validarLimiteCredito(cliente, valorTotalPedido)) {
            return;
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setItens(itens);
        pedido.setDataPedido(LocalDate.now());

        try {
            String pedidoJson = objectMapper.writeValueAsString(pedido);
            HttpUtil.post("http://localhost:8080/pedidos", pedidoJson);
            JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar pedido: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarLimiteCredito(Cliente cliente, BigDecimal valorTotalPedido) {
        BigDecimal limiteCredito = cliente.getLimiteCompra();
        BigDecimal saldoDevedor = BigDecimal.ZERO;

        try {
            String response = HttpUtil.get("http://localhost:8080/saldoDevedor?clienteId=" + cliente.getId());
            saldoDevedor = objectMapper.readValue(response, BigDecimal.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao verificar saldo devedor: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        BigDecimal totalDisponivel = limiteCredito.subtract(saldoDevedor);
        if (valorTotalPedido.compareTo(totalDisponivel) > 0) {
            JOptionPane.showMessageDialog(this, "O valor do pedido excede o limite de crédito disponível. Limite disponível: " + totalDisponivel, "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void excluirItem() {
        int selectedRow = itensTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarQuantidadeItem() {
        int selectedRow = itensTable.getSelectedRow();
        if (selectedRow != -1) {
            String novaQuantidadeStr = JOptionPane.showInputDialog(this, "Informe a nova quantidade:");
            if (novaQuantidadeStr != null && !novaQuantidadeStr.isEmpty()) {
                int novaQuantidade;
                try {
                    novaQuantidade = Integer.parseInt(novaQuantidadeStr);
                    if (novaQuantidade <= 0) {
                        JOptionPane.showMessageDialog(this, "Quantidade deve ser um número positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Quantidade deve ser um número inteiro positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                BigDecimal precoUnitario = (BigDecimal) tableModel.getValueAt(selectedRow, 2);
                BigDecimal novoValorTotal = precoUnitario.multiply(BigDecimal.valueOf(novaQuantidade));
                tableModel.setValueAt(novaQuantidade, selectedRow, 1);
                tableModel.setValueAt(novoValorTotal, selectedRow, 3);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item para editar a quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PedidoView().setVisible(true));
    }
}
