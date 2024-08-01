package teste.gestaovendas.view;

import javax.swing.*;
import java.awt.*;

public class ConsultaPedidosView extends JFrame {
    public ConsultaPedidosView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Consulta de Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel clienteLabel = new JLabel("Cliente:");
        JComboBox<String> clienteComboBox = new JComboBox<>();
        JLabel produtoLabel = new JLabel("Produto:");
        JComboBox<String> produtoComboBox = new JComboBox<>();
        JButton consultarButton = new JButton("Consultar");

        panel.add(clienteLabel);
        panel.add(clienteComboBox);
        panel.add(produtoLabel);
        panel.add(produtoComboBox);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(consultarButton);

        add(panel);

        consultarButton.addActionListener(e -> consultarPedidos());
    }

    private void consultarPedidos() {
        //TODO:
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConsultaPedidosView().setVisible(true));
    }
}

