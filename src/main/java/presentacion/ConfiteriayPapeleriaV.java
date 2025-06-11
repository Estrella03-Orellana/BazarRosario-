package presentacion;

import dominio.Producto;
import persistencia.ProductoDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ConfiteriayPapeleriaV extends JDialog {
    private JTextField txtCodigoConfiteria;
    private JComboBox cbTipoProduConfi;
    private JTextField txtNomProduConfi;
    private JTextField txtPrecioConfi;
    private JTextField txtStockConfi;
    private JButton btnInsertar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnCerrar;
    private JLabel lblNomProdu;
    private JLabel lblTipoProdu;
    private JPanel lblCodigoProdu;
    private JLabel lblPrecio;
    private JLabel lblStock;
    private JPanel panelPrincipal;

    private final ProductoDAO productoDAO;
    private final int CATEGORIA_ID = 6;

    public ConfiteriayPapeleriaV()  {
        productoDAO = new ProductoDAO();
        setTitle("Gestión de Confitería y Papelería");
        setContentPane(panelPrincipal);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Eventos
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarProducto();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public ConfiteriayPapeleriaV(MainForm mainForm) {
        this();
    }


    private void insertarProducto() {
        try {
            Producto producto = new Producto(
                    CATEGORIA_ID,
                    cbTipoProduConfi.getSelectedItem().toString(),
                    txtNomProduConfi.getText(),
                    Double.parseDouble(txtPrecioConfi.getText()),
                    Integer.parseInt(txtStockConfi.getText()),
                    (byte) 0 // No está en oferta
            );
            Producto creado = productoDAO.create(producto);
            if (creado != null) {
                JOptionPane.showMessageDialog(this, "Producto creado con ID: " + creado.getId());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al insertar: " + ex.getMessage());
        }
    }

    private void modificarProducto() {
        try {
            int id = Integer.parseInt(txtCodigoConfiteria.getText());
            Producto producto = new Producto(
                    CATEGORIA_ID,
                    cbTipoProduConfi.getSelectedItem().toString(),
                    txtNomProduConfi.getText(),
                    Double.parseDouble(txtPrecioConfi.getText()),
                    Integer.parseInt(txtStockConfi.getText()),
                    (byte) 0
            );
            producto.setId(id);
            boolean actualizado = productoDAO.update(producto);
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage());
        }
    }

    private void eliminarProducto() {
        try {
            int id = Integer.parseInt(txtCodigoConfiteria.getText());
            Producto producto = new Producto();
            producto.setId(id);
            boolean eliminado = productoDAO.delete(producto);
            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }

    private void buscarProducto() {
        try {
            int id = Integer.parseInt(txtCodigoConfiteria.getText());
            Producto producto = productoDAO.getById(id);
            if (producto != null && producto.getCategoriaId() == CATEGORIA_ID) {
                cbTipoProduConfi.setSelectedItem(producto.getTipoProdu());
                txtNomProduConfi.setText(producto.getNombreProdu());
                txtPrecioConfi.setText(String.valueOf(producto.getPrecio()));
                txtStockConfi.setText(String.valueOf(producto.getStock()));
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado en esta categoría.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar: " + ex.getMessage());
        }
    }
}

