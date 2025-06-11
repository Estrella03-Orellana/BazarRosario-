package presentacion;

import dominio.User;

import javax.swing.*;

public class MainForm extends JFrame {
    private JPanel mainPanel;

    private User userAutenticate;

    public User getUserAutenticate() {
        return userAutenticate;
    }

    public void setUserAutenticate(User userAutenticate) {
        this.userAutenticate = userAutenticate;
    }

    public MainForm(){
        setTitle("Bazar Rosario"); // Establece el título de la ventana principal (JFrame).
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configura la operación por defecto al cerrar la ventana para que la aplicación se termine.
        setLocationRelativeTo(null); // Centra la ventana principal en la pantalla.
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Inicializa la ventana principal en estado maximizado, ocupando toda la pantalla.

    }

    public void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

       /* JMenu menuPerfil = new JMenu("Perfil");
        menuBar.add(menuPerfil);

        JMenuItem itemChangePassword = new JMenuItem("Cambiar contraseña");
        menuPerfil.add(itemChangePassword);
        itemChangePassword.addActionListener(e -> {
            ChangePasswordForm changePassword = new ChangePasswordForm(this);
            changePassword.setVisible(true);
        });

        JMenuItem itemChangeUser = new JMenuItem("Cambiar de usuario");
        menuPerfil.add(itemChangeUser);
        itemChangeUser.addActionListener(e -> {
            LoginForm loginForm = new LoginForm(this);
            loginForm.setVisible(true);
        });

        JMenuItem itemSalir = new JMenuItem("Salir");
        menuPerfil.add(itemSalir);
        itemSalir.addActionListener(e -> System.exit(0));*/

        // Menú "Matenimiento"
        JMenu menuMantenimiento = new JMenu("Mantenimientos");
        menuBar.add(menuMantenimiento);

        if(userAutenticate.getIsAdmin() == 1){
            JMenuItem itemConfi = new JMenuItem("Confitería y Papelería");
            menuMantenimiento.add(itemConfi);
            itemConfi.addActionListener(e -> {
                ConfiteriayPapeleriaC formConfi =new ConfiteriayPapeleriaC(this);
                formConfi.setVisible(true);
            });
        }

        this.setVisible(true);

    }
}
