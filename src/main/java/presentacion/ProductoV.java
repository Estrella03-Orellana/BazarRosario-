package presentacion;

import dominio.Producto;
import persistencia.ProductoDAO;

import javax.swing.*;
import java.sql.SQLException;

public class ProductoV extends JDialog {
    private JPanel somePanel;
    private JTextField txtNombre;
    private JTextField txtTipo;
    private JTextField txtPrecio;
    private JSpinner spnCantidad;
    private JButton btnCancelar;
    private JButton btnModificar;
    private JPanel mainPanel;
    private JCheckBox ofertaCheckBox;
    private Producto producto;
    private ProductoDAO productoDAO;

    public ProductoV(Producto producto){
        this.producto = producto;
        productoDAO = new ProductoDAO();
        setSize(500, 300);
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Modificar Producto");
        setLocationRelativeTo(null);
        txtNombre.setText(producto.getNombreProdu());
        txtTipo.setText(producto.getTipoProdu());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        spnCantidad.setValue(producto.getStock());
        if(producto.getIsOferta() == 0){
            ofertaCheckBox.setSelected(false);
        }
        else {
            ofertaCheckBox.setSelected(true);
        }

        btnCancelar.addActionListener(s -> {
            this.dispose();
        });

        btnModificar.addActionListener(s -> {
            try {
                int result = JOptionPane.showConfirmDialog(null,
                        "¿Desea modificar este producto?",
                        "Confirmar modificación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (result){
                    case JOptionPane.YES_OPTION:
                        modificar();
                        this.dispose();
                    case JOptionPane.NO_OPTION:
                        break;
                }


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                        "Error: " + e.getMessage(),
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

    }

    private void modificar() throws SQLException {
        try{
            producto.setNombreProdu(txtNombre.getText());
            producto.setTipoProdu(txtTipo.getText());
            producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            producto.setStock(Integer.parseInt(String.valueOf(spnCantidad.getValue())));
            if(ofertaCheckBox.isSelected() == true){
                producto.setIsOferta((byte) 1);
            }
            else {
                producto.setIsOferta((byte) 0);
            }

            productoDAO.update(producto);
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage(),
                    "Error", JOptionPane.WARNING_MESSAGE);
        }

    }
}
