package presentacion;

import dominio.User;
import persistencia.UserDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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

        cbTipo.addItem("CLIENTE");
        cbTipo.addItem("VENDEDOR");


        btnCrearCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String email = txtEmail.getText().trim();
                String password = new String(fieldContraseña.getPassword());
                String tipoSeleccionado = (String) cbTipo.getSelectedItem();

                // Validar campos vacíos
                if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || tipoSeleccionado == null) {
                    JOptionPane.showMessageDialog(SignIn.this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Asignar isAdmin según tipo
                byte isAdmin = 0;
                if (tipoSeleccionado.equals("CLIENTE")) {
                    isAdmin = 1;
                } else if (tipoSeleccionado.equals("VENDEDOR")) {
                    isAdmin = 2;
                }


                User nuevoUsuario = new User(nombre, password, email, isAdmin);
                User creado = null;
                try {
                    creado = userDAO.create(nuevoUsuario);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (creado != null) {
                    JOptionPane.showMessageDialog(SignIn.this, "¡Cuenta creada exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    volverALogin();
                } else {
                    JOptionPane.showMessageDialog(SignIn.this, "No se pudo crear la cuenta. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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
