package presentacion;

import dominio.FacturaDetalle;
import dominio.Producto;
import persistencia.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockV extends JDialog{
    private JButton btnRegresar;
    private JScrollPane scrollProductos;
    private JTextField txtBuscar;
    private JTable tblProdu;
    private JButton btnEliminar;
    private JButton btnModificar;
    private JPanel mainPanel;
    private ProductoDAO productoDAO;
    private Producto producto;
    private int idCate;

    public StockV(int id){
        idCate = id;
        productoDAO = new ProductoDAO();
        setSize(500, 300);
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Stock de productos");
        setLocationRelativeTo(null);
        getFromCategory();

        txtBuscar.addKeyListener(new KeyAdapter() {
            // Sobrescribe el método keyReleased, que se llama cuando se suelta una tecla.
            @Override
            public void keyReleased(KeyEvent e) {
                // Verifica si el campo de texto txtNombre no está vacío.
                if (!txtBuscar.getText().trim().isEmpty()) {
                    searchFromCategory(txtBuscar.getText());
                } else {
                    getFromCategory();
                }
            }
        });

        btnEliminar.addActionListener(s -> {
            Producto product = getProductFromTableRow();
            if (product != null) {
                int result = JOptionPane.showConfirmDialog(null,
                        "¿Desea eliminar este producto?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                switch (result){
                    case JOptionPane.YES_OPTION:
                        try {
                            productoDAO.delete(product);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null,
                                    e.getMessage(),
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        getFromCategory();
                        JOptionPane.showMessageDialog(this, "Cambios realizados", "Información", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case JOptionPane.NO_OPTION:
                        break;
                }

            }
        });

        btnModificar.addActionListener(s -> {
            Producto producto = getProductFromTableRow();
            if (producto != null) {
                ProductoV productoV = new ProductoV(producto);
                productoV.setVisible(true);
                getFromCategory();
            }
        });

        btnRegresar.addActionListener(s -> {
            this.dispose();
        });
    }

    public StockV(){
        productoDAO = new ProductoDAO();
        setSize(500, 300);
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Stock de productos");
        setLocationRelativeTo(null);
        getAll();

        txtBuscar.addKeyListener(new KeyAdapter() {
            // Sobrescribe el método keyReleased, que se llama cuando se suelta una tecla.
            @Override
            public void keyReleased(KeyEvent e) {
                // Verifica si el campo de texto txtNombre no está vacío.
                if (!txtBuscar.getText().trim().isEmpty()) {
                    searchAll(txtBuscar.getText());
                } else {
                    getAll();
                }
            }
        });

        btnEliminar.addActionListener(s -> {
            Producto product = getProductFromTableRow();
            if (product != null) {
                int result = JOptionPane.showConfirmDialog(null,
                        "¿Desea eliminar este producto?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                switch (result){
                    case JOptionPane.YES_OPTION:
                        try {
                            productoDAO.delete(product);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null,
                                    e.getMessage(),
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        getAll();
                        JOptionPane.showMessageDialog(this, "Cambios realizados", "Información", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case JOptionPane.NO_OPTION:
                        break;
                }

            }
        });

        btnModificar.addActionListener(s -> {
            Producto producto = getProductFromTableRow();
            if (producto != null) {
                ProductoV productoV = new ProductoV(producto);
                productoV.setVisible(true);
                getAll();
            }
        });

        btnRegresar.addActionListener(s -> {
            this.dispose();
        });
    }

    private void searchFromCategory(String query) {
        try {
            ArrayList<Producto> productos = productoDAO.search(query, idCate);
            createTable(productos);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void searchAll(String query) {
        try {
            ArrayList<Producto> productos = productoDAO.searchName(query);
            createTable(productos);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void getFromCategory() {
        try {
            ArrayList<Producto> productos = productoDAO.getAll(idCate);
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addColumn("Id");
            model.addColumn("Categoria");
            model.addColumn("Tipo");
            model.addColumn("Nombre");
            model.addColumn("Precio");
            model.addColumn("Stock");

            this.tblProdu.setModel(model);
            Object row[] = null;

            for (int i = 0; i < productos.size(); i++) {
                Producto producto = productos.get(i);
                model.addRow(row);
                model.setValueAt(producto.getId(), i, 0);
                model.setValueAt(producto.getCategoriaId(), i, 1);
                model.setValueAt(producto.getTipoProdu(), i, 2);
                model.setValueAt(producto.getNombreProdu(), i, 3);
                model.setValueAt(producto.getPrecio(), i, 4);
                model.setValueAt(producto.getStock(), i, 5);
            }

            hideCol(0);



        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void getAll() {
        try {
            ArrayList<Producto> productos = productoDAO.getAllNoException();
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addColumn("Id");
            model.addColumn("Categoria");
            model.addColumn("Tipo");
            model.addColumn("Nombre");
            model.addColumn("Precio");
            model.addColumn("Stock");

            this.tblProdu.setModel(model);
            Object row[] = null;

            for (int i = 0; i < productos.size(); i++) {
                Producto producto = productos.get(i);
                model.addRow(row);
                model.setValueAt(producto.getId(), i, 0);
                model.setValueAt(producto.getCategoriaId(), i, 1);
                model.setValueAt(producto.getTipoProdu(), i, 2);
                model.setValueAt(producto.getNombreProdu(), i, 3);
                model.setValueAt(producto.getPrecio(), i, 4);
                model.setValueAt(producto.getStock(), i, 5);
            }

            hideCol(0);



        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    public void createTable(ArrayList<Producto> productos) {

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Id");
        model.addColumn("Categoria");
        model.addColumn("Tipo");
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Stock");

        this.tblProdu.setModel(model);

        Object row[] = null;

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            model.addRow(row);
            model.setValueAt(producto.getId(), i, 0);
            model.setValueAt(producto.getCategoriaId(), i, 1);
            model.setValueAt(producto.getTipoProdu(), i, 2);
            model.setValueAt(producto.getNombreProdu(), i, 3);
            model.setValueAt(producto.getPrecio(), i, 4);
            model.setValueAt(producto.getStock(), i, 5);
        }

        hideCol(0);
    }

    private void hideCol(int pColumna) {
        this.tblProdu.getColumnModel().getColumn(pColumna).setMaxWidth(0);
        this.tblProdu.getColumnModel().getColumn(pColumna).setMinWidth(0);
        this.tblProdu.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        this.tblProdu.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }

    private Producto getProductFromTableRow() {
        Producto producto = null;
        try {
            int filaSelect = this.tblProdu.getSelectedRow();
            int id = 0;

            if (filaSelect != -1) {
                id = (int) this.tblProdu.getValueAt(filaSelect, 0);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Seleccionar una fila de la tabla.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            producto = productoDAO.getById(id);

            if (producto.getId() == 0) {
                JOptionPane.showMessageDialog(null,
                        "No se encontró ningún producto.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            return producto;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
