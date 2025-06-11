package presentacion;

import dominio.Factura;
import dominio.FacturaDetalle;
import dominio.Producto;
import persistencia.FacturaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class FacturasC extends JDialog {
    private JTable tblFacturas;
    private JButton btnVer;
    private JButton btnVolver;
    private JPanel mainPanel;
    private MainForm mainForm;
    private FacturaDAO facturaDAO;

    public FacturasC(MainForm mainForm){
        this.mainForm = mainForm;
        facturaDAO = new FacturaDAO();
        setSize(500, 300);
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Compras anteriores");
        setLocationRelativeTo(mainForm);
        createTable();

        btnVolver.addActionListener(s ->
                this.dispose()
        );

        btnVer.addActionListener(s -> {
            int facturaId = selectFromTableRow();
            if (facturaId > 0) {
                FacturaDetalleC facturaDetalleC = new FacturaDetalleC(this.mainForm, facturaId);
                facturaDetalleC.setVisible(true);
                createTable();
            }
        });
    }

    private void createTable() {
        try {
            ArrayList<Factura> facturas = facturaDAO.getFacturas(mainForm.getUserAutenticate().getName());
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addColumn("Id");
            model.addColumn("Cliente");
            model.addColumn("Total");
            model.addColumn("Fecha");

            this.tblFacturas.setModel(model);
            Object row[] = null;

            for (int i = 0; i < facturas.size(); i++) {
                Factura factura = facturas.get(i);
                model.addRow(row);
                model.setValueAt(factura.getCodFac(), i, 0);
                model.setValueAt(factura.getNombreUsu(), i, 1);
                model.setValueAt(factura.getPago(), i, 2);
                model.setValueAt(factura.getFecha(), i, 3);
            }

            hideCol(0);

            if(facturas.size() > 0) {
                btnVer.setEnabled(true);
            }
            else {
                btnVer.setEnabled(false);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void hideCol(int pColumna) {
        this.tblFacturas.getColumnModel().getColumn(pColumna).setMaxWidth(0);
        this.tblFacturas.getColumnModel().getColumn(pColumna).setMinWidth(0);
        this.tblFacturas.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        this.tblFacturas.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }

    private int selectFromTableRow() {
        try {
            int filaSelect = this.tblFacturas.getSelectedRow();
            int id = 0;

            if (filaSelect != -1) {
                id = (int) this.tblFacturas.getValueAt(filaSelect, 0);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Seleccionar una fila de la tabla.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return -1;
            }

            if (id < 0) {
                JOptionPane.showMessageDialog(null,
                        "No se encontró ningúna factura.",
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
