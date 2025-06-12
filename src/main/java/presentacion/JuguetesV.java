package presentacion;

import dominio.Producto;
import persistencia.ProductoDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuguetesV extends JDialog{
    private JTextField txtTipo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JButton btnInsertar;
    private JButton btnBuscar;
    private JButton btnCerrar;
    private JPanel mainPanel;
    private JCheckBox chbOferta;

    private final ProductoDAO productoDAO;
    private final int CATEGORIA_ID = 3;

    public JuguetesV()  {
        productoDAO = new ProductoDAO();
        setTitle("Gestión de Confitería y Papelería");
        setContentPane(mainPanel);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Eventos
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarProducto();
            }
        });

        btnBuscar.addActionListener(e -> {
            StockV stockV = new StockV(CATEGORIA_ID);
            stockV.setVisible(true);
        });

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }



    private void insertarProducto() {
        byte isOferta;
        if(chbOferta.isSelected() == true) {
            isOferta = 1;
        }
        else {
            isOferta = 0;
        }

        try {
            Producto producto = new Producto(
                    CATEGORIA_ID,
                    txtTipo.getText(),
                    txtNombre.getText(),
                    Double.parseDouble(txtPrecio.getText()),
                    Integer.parseInt(txtStock.getText()),
                    isOferta
            );
            Producto creado = productoDAO.create(producto);
            if (creado != null) {
                JOptionPane.showMessageDialog(this, "Producto creado con ID: " + creado.getId());
                txtNombre.setText("");
                txtTipo.setText("");
                txtPrecio.setText("0");
                txtStock.setText("0");
                chbOferta.setSelected(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al insertar: " + ex.getMessage());
        }
    }
}
