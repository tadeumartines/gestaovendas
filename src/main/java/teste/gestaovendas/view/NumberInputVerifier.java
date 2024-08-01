package teste.gestaovendas.view;

import javax.swing.*;

public class NumberInputVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        JTextField textField = (JTextField) input;
        String text = textField.getText();
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(textField,
                    "O valor deve ser um número válido.",
                    "Erro de Entrada",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
