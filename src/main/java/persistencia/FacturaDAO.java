package persistencia;

import dominio.Factura;
import dominio.FacturaDetalle;
import dominio.Producto;

import java.sql.*;
import java.util.ArrayList;

public class FacturaDAO {
    private ConnectionManager conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public FacturaDAO(){
        conn = ConnectionManager.getInstance();
    }

    public Factura create(Factura factura) throws SQLException {
        Factura res = null;

        try{
            PreparedStatement ps = conn.connect().prepareStatement(
                    "INSERT INTO " +
                            "FACTURAS (NombreUsu, Pago, Fecha)" +
                            "VALUES (?, ?, ?)",
                    java.sql.Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, factura.getNombreUsu());
            ps.setDouble(2, factura.getPago());
            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
            ps.setTimestamp(3,sqlTimestamp);

            int affectedRows = ps.executeUpdate();

            if (affectedRows != 0) {
                ResultSet  generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGenerado= generatedKeys.getInt(1);
                    res = getById(idGenerado);
                } else {
                    throw new SQLException("Fallo al crear factura, no se obtuvo el ID.");
                }
            }
            ps.close();
        }catch (SQLException ex){
            throw new SQLException("Error al crear la factura: " + ex.getMessage(), ex);
        } finally {
            ps = null;
            conn.disconnect();
        }
        return res;
    }

    public Factura getById(int id) throws SQLException {
        Factura factura = new Factura();
        try{
            ps = conn.connect().prepareStatement("SELECT CodFac, NombreUsu, Pago, Fecha" +
                    "FROM FACTURAS " +
                    "WHERE CodFac = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                factura.setCodFac(rs.getInt(1));
                factura.setNombreUsu(rs.getString(2));
                factura.setPago(rs.getDouble(3));
                factura.setFecha(rs.getTimestamp(4));
            } else {
                factura = null;
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            throw new SQLException("Error al obtener una factura por id: " + e.getMessage(), e);
        }finally {
            ps = null;
            rs = null;
            conn.disconnect();
        }
        return factura;
    }

    public ArrayList<Factura> getFacturas(String nombre) throws SQLException {
        ArrayList<Factura> records = new ArrayList<>();
        try {
            ps = conn.connect().prepareStatement("SELECT CodFac, NombreUsu, Pago, Fecha" +
                    "FROM FACTURAS " +
                    "WHERE NombreUsu = ?");

            ps.setString(1, nombre);

            rs = ps.executeQuery();

            while (rs.next()){
                Factura factura = new Factura();
                factura.setCodFac(rs.getInt(1));
                factura.setNombreUsu(rs.getString(2));
                factura.setPago(rs.getDouble(3));
                factura.setFecha(rs.getTimestamp(4));
                records.add(factura);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){
            throw new SQLException("Error al buscar facturas: " + ex.getMessage(), ex);
        } finally {
            ps = null;
            rs = null;
            conn.disconnect();
        }
        return records;
    }
}
