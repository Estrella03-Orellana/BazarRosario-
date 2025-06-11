package presentacion;

import dominio.Factura;
import dominio.FacturaDetalle;
import persistencia.FacturaDetalleDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class FacturaDetalleC extends JDialog {
    private JTable tblFacturasDetalle;
    private JButton btnVolver;
    private JPanel mainPanel;
    private MainForm mainForm;
    private FacturaDetalleDAO facturaDetalleDAO;
    private final int CODFACT;

    public FacturaDetalleC(MainForm mainForm, int id){
        this.mainForm = mainForm;
        this.CODFACT = id;
        facturaDetalleDAO = new FacturaDetalleDAO();
        setSize(500, 300);
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Detalle de compra");
        setLocationRelativeTo(mainForm);
        createTable();

        btnVolver.addActionListener(s ->
                this.dispose()
        );
    }

    private void createTable() {
        try {
            ArrayList<FacturaDetalle> detalles = facturaDetalleDAO.getDetalle(CODFACT);
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

            this.tblFacturasDetalle.setModel(model);
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

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void hideCol(int pColumna) {
        this.tblFacturasDetalle.getColumnModel().getColumn(pColumna).setMaxWidth(0);
        this.tblFacturasDetalle.getColumnModel().getColumn(pColumna).setMinWidth(0);
        this.tblFacturasDetalle.getTableHeader().getColumnModel().getColumn(pColumna).setMaxWidth(0);
        this.tblFacturasDetalle.getTableHeader().getColumnModel().getColumn(pColumna).setMinWidth(0);
    }
}
