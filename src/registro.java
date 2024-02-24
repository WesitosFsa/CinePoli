import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class registro extends JFrame{
    private JPanel panel1;
    private JTextField correo;
    private JTextField usuario;
    private JTextField contra;
    private JTextField rep_contra;
    private JButton registrarseButton;
    private JButton inicioButton;
    private JPanel bienvenida;

    public registro(){
        setContentPane(panel1);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login login = new login();
                login.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                frame.dispose();
            }
        });
    }
}
