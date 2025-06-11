package presentacion;

import dominio.User;
import persistencia.UserDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class Login extends JDialog{
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JPanel mainPanel;
    private JButton btnRegistrar;
    private JLabel lblBienvenida;
    private JLabel lblLogo;
    private JTextPane entraABazarRosarioTextPane;
    private MainForm mainForm;
    private UserDAO userDAO;



    public Login(MainForm mainForm){
        this.mainForm = mainForm;
        userDAO = new UserDAO();
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Iniciar Sesión");
        setSize(900, 600);
        setLocationRelativeTo(mainForm);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irASignIn();
            }
        });

        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    public void irASignIn(){
        SignIn signIn = new SignIn(mainForm);
        this.dispose();
        signIn.setVisible(true);
    }

    private void login() {
        try{
            User user = new User();
            user.setEmail(txtEmail.getText());
            user.setPasswordHash(new String(txtPassword.getPassword()));

            User userAut = userDAO.authenticate(user);

            if(userAut != null && userAut.getId() > 0 && userAut.getEmail().equals((user.getEmail()))){
                this.mainForm.setUserAutenticate(userAut);
                this.mainForm.createMenu();
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null,
                        "Email y password incorrecto",
                        "Login",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "Sistem",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}