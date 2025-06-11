package presentacion;

import dominio.FacturaDetalle;
import dominio.User;

import javax.swing.*;
import java.util.ArrayList;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private ArrayList<FacturaDetalle> listaFacturas;

    private User userAutenticate;

    public ArrayList<FacturaDetalle> getListaFacturas() { return listaFacturas;}

    public void setListaFacturas(ArrayList<FacturaDetalle> list) { this.listaFacturas = list;}

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
        listaFacturas = new ArrayList<FacturaDetalle>();
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
        JMenu menuMas = new JMenu("Mas...");
        menuBar.add(menuMas);


        if(userAutenticate.getIsAdmin() == 1){
            JMenuItem itemCarrito = new JMenuItem("Carrito de compras");
            menuMas.add(itemCarrito);
            itemCarrito.addActionListener(e -> {
                ListaCompraC formCarrito = new ListaCompraC(this);
                formCarrito.setVisible(true);
                listaFacturas = formCarrito.getListaFacturas();
            });

            JMenuItem itemFacturas = new JMenuItem("Facturas anteriores");
            menuMas.add(itemFacturas);
            itemFacturas.addActionListener(e -> {
                FacturasC formFacturas = new FacturasC(this);
                formFacturas.setVisible(true);
            });

            JMenuItem itemConfi = new JMenuItem("Confitería y Papelería");
            menuMantenimiento.add(itemConfi);
            itemConfi.addActionListener(e -> {
                ConfiteriayPapeleriaC formConfi =new ConfiteriayPapeleriaC(this);
                formConfi.setVisible(true);
                if(formConfi.getListaFacturas().size() > 0){
                    listaFacturas = formConfi.getListaFacturas();
                }
            });
        }

        this.setVisible(true);

    }
}
