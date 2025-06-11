package presentacion;

import dominio.FacturaDetalle;
import dominio.Producto;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
    private FacturaDetalle facturaDetalle;

    public FacturaDetalle getFacturaDetalle(){
        return this.facturaDetalle;
    }


    public ProductoC(MainForm mainForm, Producto producto){
        this.mainForm = mainForm;
        this.producto = producto;
        facturaDetalle = new FacturaDetalle();
        setSize(500, 300);
        setContentPane(mainPanel);
        setModal(true);
        setTitle("Vista del producto");
        setLocationRelativeTo(mainForm);
        txtNombre.setText(producto.getNombreProdu());
        txtCategoria.setText(String.valueOf(producto.getCategoriaId()));
        txtPrecio.setText(String.valueOf(producto.getPrecio()));

        btnCancelar.addActionListener(e -> {
            facturaDetalle.setNombreUsu(null);
            facturaDetalle.setNombreProdu(null);
            facturaDetalle.setCodFac(0);
            facturaDetalle.setCantidad(0);
            facturaDetalle.setPago(0);
            this.dispose();
        });

        btnAgregar.addActionListener(e -> this.dispose());

        spnCantidad.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Object valor = spnCantidad.getValue();
                recalculate(valor);
            }
        });

    }

    public void recalculate(Object valor){
        int cantidad = Integer.parseInt(String.valueOf(valor));
        if(!(cantidad < 1)){
            btnAgregar.setEnabled(true);
        }
        else {
            btnAgregar.setEnabled(false);
        }
        double precio = producto.getPrecio();
        double total = cantidad * precio;
        facturaDetalle.setCantidad(cantidad);
        facturaDetalle.setPago(total);
        facturaDetalle.setNombreUsu(mainForm.getUserAutenticate().getName());
        facturaDetalle.setNombreProdu(producto.getNombreProdu());
        txtTotal.setText(String.valueOf(total));
    }


}
