package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FacturaDAO {
    private ConnectionManager conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public FacturaDAO(){
        conn = ConnectionManager.getInstance();
    }
}
