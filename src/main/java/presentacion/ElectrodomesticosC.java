package presentacion;

import dominio.FacturaDetalle;
import dominio.Producto;
import persistencia.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ElectrodomesticosC extends JDialog {
    private JTextField txtBuscar;
    private JScrollPane mainScrollPanel;
    private JTable tblElec;
    private JButton btnVer;
    private JPanel mainPanel;
    private JButton btnRegresar;
    private MainForm mainForm;
    private ProductoDAO productoDAO;
    private final int ID_CATEGORIA = 1;
    private ArrayList<FacturaDetalle> listaFacturas;

    public ArrayList<FacturaDetalle> getListaFacturas() {
        return this.listaFacturas;
    }

    public ElectrodomesticosC(MainForm mainForm){
        this.mainForm = mainForm;
        listaFacturas = mainForm.getListaFacturas();
        productoDAO = new ProductoDAO();
        setSize(500, 300);
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Deportes");
        setLocationRelativeTo(mainForm);
        getAll();
        txtBuscar.addKeyListener(new KeyAdapter() {
            // Sobrescribe el método keyReleased, que se llama cuando se suelta una tecla.
            @Override
            public void keyReleased(KeyEvent e) {
                // Verifica si el campo de texto txtNombre no está vacío.
                if (!txtBuscar.getText().trim().isEmpty()) {
                    search(txtBuscar.getText());
                } else {
                    getAll();
                }
            }
        });
        btnVer.addActionListener(s -> {
            Producto producto = getProductFromTableRow();
            if (producto != null) {
                ProductoC productoC = new ProductoC(this.mainForm, producto);
                productoC.setVisible(true);
                if(productoC.getFacturaDetalle().getCodFac() > 1
                        || productoC.getFacturaDetalle().getCantidad() > 0){
                    listaFacturas.add(productoC.getFacturaDetalle());
                }
                getAll();
            }
        });
        btnRegresar.addActionListener(s -> {
            this.dispose();
        });
    }

    private void search(String query) {
        try {
            ArrayList<Producto> productos = productoDAO.search(query, ID_CATEGORIA);
            createTable(productos);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void getAll() {
        try {
            ArrayList<Producto> productos = productoDAO.getAll(ID_CATEGORIA);
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

            this.tblElec.setModel(model);
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

        this.tblElec.setModel(model);

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
        this.tblElec.getColumnModel().getColumn(pColumna).setMaxWidth(0);
        this.tblElec.getColumnModel().getColumn(pColumna).setMinWidth(0);
        this.tblElec.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        this.tblElec.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }

    private Producto getProductFromTableRow() {
        Producto producto = null;
        try {
            int filaSelect = this.tblElec.getSelectedRow();
            int id = 0;

            if (filaSelect != -1) {
                id = (int) this.tblElec.getValueAt(filaSelect, 0);
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
