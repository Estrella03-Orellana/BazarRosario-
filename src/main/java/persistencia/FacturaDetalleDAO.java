package persistencia;

import dominio.FacturaDetalle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacturaDetalleDAO {
    private ConnectionManager conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public FacturaDetalleDAO(){
        conn = ConnectionManager.getInstance();
    }

    public boolean create(FacturaDetalle facturaDetalle) throws SQLException {
        boolean res = false;
        try{
            PreparedStatement ps = conn.connect().prepareStatement(
                    "INSERT INTO " +
                            "FACTURAS_DETALLE (CodFac, NombreUsu, NombreProdu, Cantidad, Pago)" +
                            "VALUES (?, ?, ?, ?, ?)"

            );
            ps.setInt(1, facturaDetalle.getCodFac());
            ps.setString(2, facturaDetalle.getNombreUsu());
            ps.setString(3, facturaDetalle.getNombreProdu());
            ps.setInt(4, facturaDetalle.getCantidad());
            ps.setDouble(5, facturaDetalle.getPago());

            int affectedRows = ps.executeUpdate();

            if (affectedRows != 0) {
                res = true;
            }
            ps.close();
        } catch (SQLException e){
            throw new SQLException("Error al crear el detalle: " + e.getMessage(), e);
        } finally {
            ps = null;
            conn.disconnect();
        }
        return res;
    }

    public boolean delete(FacturaDetalle detalle) throws SQLException {
        boolean res = false;
        try {
            ps = conn.connect().prepareStatement(
                    "DELETE FROM FACTURAS_DETALLE WHERE CodFac = ?"
            );
            ps.setInt(1, detalle.getCodFac());

            if(ps.executeUpdate() > 0){
                res = true;
            }
            ps.close();
        }catch (SQLException e){
            throw new SQLException("Error al eliminar la factura: " + e.getMessage(), e);
        } finally {
            ps = null;
            conn.disconnect();
        }
        return res;
    }

    public ArrayList<FacturaDetalle> getDetalle(int id) throws SQLException {
        ArrayList<FacturaDetalle> records = new ArrayList<>();
        try {
            ps = conn.connect().prepareStatement("SELECT Id, CodFac, NombreUsu, NombreProdu, Cantidad, Pago " +
                    "FROM FACTURAS_DETALLE " +
                    "WHERE CodFac = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            while (rs.next()){
                FacturaDetalle detalle = new FacturaDetalle();
                detalle.setId(rs.getInt(1));
                detalle.setCodFac(rs.getInt(2));
                detalle.setNombreUsu(rs.getString(3));
                detalle.setNombreProdu(rs.getString(4));
                detalle.setCantidad(rs.getInt(5));
                detalle.setPago(rs.getDouble(6));
                records.add(detalle);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){
            throw new SQLException("Error al buscar detalles: " + ex.getMessage(), ex);
        } finally {
            ps = null;
            rs = null;
            conn.disconnect();
        }
        return records;
    }

}
