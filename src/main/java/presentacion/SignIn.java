package presentacion;

import persistencia.UserDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignIn extends JDialog{
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JButton btnCrearCuenta;
    private JPasswordField fieldContraseña;
    private JLabel lblBienvenida;
    private JTextPane registrateConBazarRosarioTextPane;
    private JPanel mainPanel;
    private JComboBox cbTipo;
    private JButton btnRegresar;
    private MainForm mainForm;
    private UserDAO userDAO;

    public SignIn(MainForm mainForm){
        this.mainForm = mainForm;
        userDAO = new UserDAO();
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Iniciar Sesión");
        setSize(900, 600);
        setLocationRelativeTo(mainForm);

        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverALogin();
            }
        });
    }

    public void volverALogin(){
        Login login = new Login(mainForm);
        this.dispose();
        login.setVisible(true);
    }
}
