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

        JMenu menuPerfil = new JMenu("Perfil");
        menuBar.add(menuPerfil);

        JMenuItem itemChangePassword = new JMenuItem("Cambiar contraseña");
        menuPerfil.add(itemChangePassword);
        itemChangePassword.addActionListener(e -> {
            CambiarPassword changePassword = new CambiarPassword(this);
            changePassword.setVisible(true);
        });

        JMenuItem itemChangeUser = new JMenuItem("Cambiar de usuario");
        menuPerfil.add(itemChangeUser);
        itemChangeUser.addActionListener(e -> {
            Login loginForm = new Login(this);
            loginForm.setVisible(true);
        });

        JMenuItem itemSalir = new JMenuItem("Salir");
        menuPerfil.add(itemSalir);
        itemSalir.addActionListener(e -> System.exit(0));

        // Menú "Matenimiento"
        JMenu menuMantenimiento = new JMenu("Mantenimientos");
        menuBar.add(menuMantenimiento);
        JMenu menuMas = new JMenu("Mas...");
        menuBar.add(menuMas);


        if(userAutenticate.getIsAdmin() == 0){
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
            JMenuItem itemCui = new JMenuItem("Cuidado Personal");
            menuMantenimiento.add(itemCui);
            itemCui.addActionListener(e -> {
                CuidadoPersonalC formCui =new CuidadoPersonalC(this);
                formCui.setVisible(true);
                if(formCui.getListaFacturas().size() > 0){
                    listaFacturas = formCui.getListaFacturas();
                }
            });
            JMenuItem itemDepo = new JMenuItem("Deportes");
            menuMantenimiento.add(itemDepo);
            itemDepo.addActionListener(e -> {
                DeportesC formDepo =new DeportesC(this);
                formDepo.setVisible(true);
                if(formDepo.getListaFacturas().size() > 0){
                    listaFacturas = formDepo.getListaFacturas();
                }
            });
            JMenuItem itemElec = new JMenuItem("Electrodomesticos");
            menuMantenimiento.add(itemElec);
            itemElec.addActionListener(e -> {
                ElectrodomesticosC formElec =new ElectrodomesticosC(this);
                formElec.setVisible(true);
                if(formElec.getListaFacturas().size() > 0){
                    listaFacturas = formElec.getListaFacturas();
                }
            });
            JMenuItem itemHogar = new JMenuItem("Hogar y Cocina");
            menuMantenimiento.add(itemHogar);
            itemHogar.addActionListener(e -> {
                HogaryCocinaC formHogar =new HogaryCocinaC(this);
                formHogar.setVisible(true);
                if(formHogar.getListaFacturas().size() > 0){
                    listaFacturas = formHogar.getListaFacturas();
                }
            });
            JMenuItem itemJug = new JMenuItem("Juguetes");
            menuMantenimiento.add(itemJug);
            itemJug.addActionListener(e -> {
                JuguetesC formJug =new JuguetesC(this);
                formJug.setVisible(true);
                if(formJug.getListaFacturas().size() > 0){
                    listaFacturas = formJug.getListaFacturas();
                }
            });
            JMenuItem itemLimp = new JMenuItem("Limpieza");
            menuMantenimiento.add(itemLimp);
            itemLimp.addActionListener(e -> {
                LimpiezaC formLimp =new LimpiezaC(this);
                formLimp.setVisible(true);
                if(formLimp.getListaFacturas().size() > 0){
                    listaFacturas = formLimp.getListaFacturas();
                }
            });
            JMenuItem itemOfer = new JMenuItem("Ofertas");
            menuMantenimiento.add(itemOfer);
            itemOfer.addActionListener(e -> {
                OfertasC formOfer =new OfertasC(this);
                formOfer.setVisible(true);
                if(formOfer.getListaFacturas().size() > 0){
                    listaFacturas = formOfer.getListaFacturas();
                }
            });
            JMenuItem itemRopa = new JMenuItem("Ropa");
            menuMantenimiento.add(itemRopa);
            itemRopa.addActionListener(e -> {
                RopaC formRopa =new RopaC(this);
                formRopa.setVisible(true);
                if(formRopa.getListaFacturas().size() > 0){
                    listaFacturas = formRopa.getListaFacturas();
                }
            });
            JMenuItem itemTec = new JMenuItem("Tecnología");
            menuMantenimiento.add(itemTec);
            itemTec.addActionListener(e -> {
                TecnologiaC formTec =new TecnologiaC(this);
                formTec.setVisible(true);
                if(formTec.getListaFacturas().size() > 0){
                    listaFacturas = formTec.getListaFacturas();
                }
            });
        }
        else {
            JMenuItem itemStock = new JMenuItem("Stock");
            menuMas.add(itemStock);
            itemStock.addActionListener(e -> {
                StockV formStock =new StockV();
                formStock.setVisible(true);
            });
            JMenuItem itemConfi = new JMenuItem("Confitería y Papelería");
            menuMantenimiento.add(itemConfi);
            itemConfi.addActionListener(e -> {
                ConfiteriayPapeleriaV formConfi =new ConfiteriayPapeleriaV();
                formConfi.setVisible(true);
            });
            JMenuItem itemCui = new JMenuItem("Cuidado Personal");
            menuMantenimiento.add(itemCui);
            itemCui.addActionListener(e -> {
                CuidadoPersonalV formCui = new CuidadoPersonalV();
                formCui.setVisible (true);
            });

            JMenuItem itemDepo = new JMenuItem("Deportes");
            menuMantenimiento.add(itemDepo);
            itemDepo.addActionListener(e -> {
                DeportesV formDepo =new DeportesV();
                formDepo.setVisible(true);
            });

            JMenuItem itemElec = new JMenuItem("Electrodomesticos");
            menuMantenimiento.add(itemElec);
            itemElec.addActionListener(e -> {
                ElectrodomesticosV formElec =new ElectrodomesticosV();
                formElec.setVisible(true);
            });
            JMenuItem itemHogar = new JMenuItem("Hogar y Cocina");
            menuMantenimiento.add(itemHogar);
            itemHogar.addActionListener(e -> {
                HogaryCocinaV formHogar =new HogaryCocinaV();
                formHogar.setVisible(true);
            });
            JMenuItem itemJug = new JMenuItem("Juguetes");
            menuMantenimiento.add(itemJug);
            itemJug.addActionListener(e -> {
                JuguetesV formJug =new JuguetesV();
                formJug.setVisible(true);
            });
            JMenuItem itemLimp = new JMenuItem("Limpieza");
            menuMantenimiento.add(itemLimp);
            itemLimp.addActionListener(e -> {
                LimpiezaV formLimp =new LimpiezaV();
                formLimp.setVisible(true);
            });
            JMenuItem itemRopa = new JMenuItem("Ropa");
            menuMantenimiento.add(itemRopa);
            itemRopa.addActionListener(e -> {
                RopaV formRopa =new RopaV();
                formRopa.setVisible(true);
            });
            JMenuItem itemTec = new JMenuItem("Tecnología");
            menuMantenimiento.add(itemTec);
            itemTec.addActionListener(e -> {
                TecnologiaV formTec =new TecnologiaV();
                formTec.setVisible(true);
            });
        }

        this.setVisible(true);

    }
}
