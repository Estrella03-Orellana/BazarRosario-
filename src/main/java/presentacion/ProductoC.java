package presentacion;

import dominio.Producto;

import javax.swing.*;

public class ProductoC extends JDialog {
    private JPanel mainPanel;
    private JTextField txtNombre;
    private JTextField txtCategoria;
    private JTextField txtPrecio;
    private JSpinner spnCantidad;
    private JTextField txtTotal;
    private JButton btnCancelar;
    private JButton btnAgregar;
    private MainForm mainForm;
    private Producto producto;

    public ProductoC(MainForm mainForm, Producto producto){
        this.mainForm = mainForm;
        this.producto = producto;
        setSize(500, 300);
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Vista del producto");
        setLocationRelativeTo(mainForm);
        txtNombre.setText(producto.getNombreProdu());
        txtCategoria.setText(String.valueOf(producto.getCategoriaId()));
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        btnCancelar.addActionListener(e -> this.dispose());
    }
}
