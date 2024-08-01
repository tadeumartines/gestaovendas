package teste.gestaovendas.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    public MainView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Tela Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JButton cadastrarProdutoButton = new JButton("Cadastrar Produto");
        JButton cadastrarClienteButton = new JButton("Cadastrar Cliente");
        JButton cadastrarPedidoButton = new JButton("Cadastrar Pedido");
        JButton consultarProdutoButton = new JButton("Consultar Produto");
        JButton consultarClienteButton = new JButton("Consultar Cliente");
        JButton consultarPedidoButton = new JButton("Consultar Pedido");

        panel.add(cadastrarProdutoButton);
        panel.add(cadastrarClienteButton);
        panel.add(cadastrarPedidoButton);
        panel.add(consultarProdutoButton);
        panel.add(consultarClienteButton);
        panel.add(consultarPedidoButton);

        add(panel);

        cadastrarProdutoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastroProduto();
            }
        });

        cadastrarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastroCliente();
            }
        });

        cadastrarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastroPedido();
            }
        });

        consultarProdutoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                abrirTelaConsultaProduto();
            }
        });

        consultarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                abrirTelaConsultaCliente();
            }
        });

        consultarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaConsultaPedido();
            }
        });
    }

    private void abrirTelaCadastroProduto() {
        JFrame cadastroProdutoFrame = new ProdutoView();
        cadastroProdutoFrame.setVisible(true);
    }

    private void abrirTelaCadastroCliente() {
        JFrame cadastroClienteFrame = new ClienteView();
        cadastroClienteFrame.setVisible(true);
    }

    private void abrirTelaCadastroPedido() {
        JFrame cadastroPedidoFrame = new PedidoView();
        cadastroPedidoFrame.setVisible(true);
    }

//    private void abrirTelaConsultaProduto() {
//        JFrame consultaProdutoFrame = new ConsultaProdutoView();
//        consultaProdutoFrame.setVisible(true);
//    }

//    private void abrirTelaConsultaCliente() {
//        JFrame consultaClienteFrame = new ConsultaClienteView();
//        consultaClienteFrame.setVisible(true);
//    }

    private void abrirTelaConsultaPedido() {
        JFrame consultaPedidoFrame = new ConsultaPedidosView();
        consultaPedidoFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
