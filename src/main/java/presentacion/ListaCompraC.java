package presentacion;

import dominio.Factura;
import dominio.FacturaDetalle;
import persistencia.FacturaDAO;
import persistencia.FacturaDetalleDAO;
import persistencia.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListaCompraC extends JDialog {
    private JPanel mainPanel;
    private JTable tblListaCompra;
    private JButton btnVolver;
    private JButton btnRealizar;
    private JButton btnEliminar;
    private MainForm mainForm;
    private ArrayList<FacturaDetalle> listaFacturas;
    private ProductoDAO productoDAO;
    private FacturaDAO facturaDAO;
    private FacturaDetalleDAO facturaDetalleDAO;

    public ArrayList<FacturaDetalle> getListaFacturas() {
        return this.listaFacturas;
    }

    private void setListaFacturas(ArrayList<FacturaDetalle> list){
        this.listaFacturas = list;
    }

    public ListaCompraC(MainForm mainForm){
        this.mainForm = mainForm;
        this.listaFacturas = mainForm.getListaFacturas();
        productoDAO = new ProductoDAO();
        facturaDAO = new FacturaDAO();
        facturaDetalleDAO = new FacturaDetalleDAO();
        setSize(500, 300);
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Confitería y Papelería");
        setLocationRelativeTo(mainForm);
        createTable();

        btnVolver.addActionListener(s ->
            this.dispose()
        );

        btnEliminar.addActionListener(s -> {
            int id = selectFromTableRow();
            if (id > -1 && id < getListaFacturas().size()) {
                int result = JOptionPane.showConfirmDialog(null,
                        "¿Desea eliminar este producto de su carrito?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                switch (result){
                    case JOptionPane.YES_OPTION:
                        ArrayList<FacturaDetalle> list = getListaFacturas();
                        list.remove(id);
                        setListaFacturas(list);
                        createTable();
                        JOptionPane.showMessageDialog(this, "Cambios realizados", "Información", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case JOptionPane.NO_OPTION:
                        break;
                }

            }
        });

        btnRealizar.addActionListener(s -> {
                int result = JOptionPane.showConfirmDialog(null,
                        "¿Desea realizar esta compra?",
                        "Confirmar compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                switch (result){
                    case JOptionPane.YES_OPTION:
                        try {
                            buy(getListaFacturas());
                            JOptionPane.showMessageDialog(this, "Cambios realizados", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null,
                                    "Error: " + e,
                                    "Error", JOptionPane.WARNING_MESSAGE);
                        }
                        setListaFacturas(new ArrayList<>());
                        createTable();
                        break;
                    case JOptionPane.NO_OPTION:
                        break;
                }

        });
    }

    private void buy(ArrayList<FacturaDetalle> detalles) throws SQLException {
        try{
            Factura factura = new Factura();
            factura.setNombreUsu(mainForm.getUserAutenticate().getName());
            factura.setPago(calcularPago());

            Factura result = facturaDAO.create(factura);
            if(result != null){
                for(FacturaDetalle detalle : detalles){
                    detalle.setCodFac(result.getCodFac());
                    facturaDetalleDAO.create(detalle);
                }
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,
                    "Error: " + e,
                    "Error", JOptionPane.WARNING_MESSAGE);
        }


    }

    private double calcularPago(){
        double total = 0;

        for(FacturaDetalle detalle : listaFacturas){
            total += detalle.getPago();
        }

        return total;
    }

    private void createTable() {
        try {
            ArrayList<FacturaDetalle> detalles = getListaFacturas();
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addColumn("Id");
            model.addColumn("Cliente");
            model.addColumn("Producto");
            model.addColumn("Cantidad");
            model.addColumn("Precio");

            this.tblListaCompra.setModel(model);
            Object row[] = null;

            for (int i = 0; i < detalles.size(); i++) {
                FacturaDetalle detalle = detalles.get(i);
                model.addRow(row);
                model.setValueAt(detalle.getId(), i, 0);
                model.setValueAt(detalle.getNombreUsu(), i, 1);
                model.setValueAt(detalle.getNombreProdu(), i, 2);
                model.setValueAt(detalle.getCantidad(), i, 3);
                model.setValueAt(detalle.getPago(), i, 4);
            }

            hideCol(0);

            if(getListaFacturas().size() > 0) {
                btnRealizar.setEnabled(true);
                btnEliminar.setEnabled(true);
            }
            else {
                btnRealizar.setEnabled(false);
                btnEliminar.setEnabled(false);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void hideCol(int pColumna) {
        this.tblListaCompra.getColumnModel().getColumn(pColumna).setMaxWidth(0);
        this.tblListaCompra.getColumnModel().getColumn(pColumna).setMinWidth(0);
        this.tblListaCompra.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        this.tblListaCompra.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }

    private int selectFromTableRow() {
        try {
            int filaSelect = this.tblListaCompra.getSelectedRow();
            int id = 0;

            if (filaSelect != -1) {
                id = (int) this.tblListaCompra.getValueAt(filaSelect, 0);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Seleccionar una fila de la tabla.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return -1;
            }


            if (id < 0) {
                JOptionPane.showMessageDialog(null,
                        "No se encontró ningún producto.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return -1;
            }

            return id;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
}
