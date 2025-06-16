package persistencia;

import dominio.Factura;
import dominio.FacturaDetalle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FacturaDetalleDAOTest {
    private FacturaDetalleDAO facturaDetalleDAO;
    private FacturaDAO facturaDAO;

    @BeforeEach
    void setUp() {
        facturaDetalleDAO = new FacturaDetalleDAO();
        facturaDAO = new FacturaDAO();
    }

    // --- Pruebas para create(FacturaDetalle facturaDetalle) ---

    @Test
    @DisplayName("Debe crear un detalle de factura exitosamente")
    void create_Success() throws SQLException {
        // Arrange
        Factura facturaToCreate = new Factura("usuarioGet", 500.0, new Timestamp(System.currentTimeMillis()));
        Factura created = facturaDAO.create(facturaToCreate);

        FacturaDetalle newDetalle = new FacturaDetalle(created.getCodFac(), "clienteA", "ProductoX", 2, 50.0);

        // Act
        boolean result = facturaDetalleDAO.create(newDetalle);

        // Assert
        assertTrue(result, "La creación del detalle debe ser exitosa");

        // Verificar que el detalle realmente existe en la DB
        ArrayList<FacturaDetalle> retrievedDetails = facturaDetalleDAO.getDetalle(newDetalle.getCodFac());
        assertNotNull(retrievedDetails);
        assertEquals(1, retrievedDetails.size(), "Debe haber 1 detalle creado para CodFac " + newDetalle.getCodFac());

        FacturaDetalle createdAndRetrieved = retrievedDetails.get(0);
        assertTrue(createdAndRetrieved.getId() > 0, "El ID del detalle debe ser generado por la base de datos");
        assertEquals(newDetalle.getCodFac(), createdAndRetrieved.getCodFac());
        assertEquals(newDetalle.getNombreUsu(), createdAndRetrieved.getNombreUsu());
        assertEquals(newDetalle.getNombreProdu(), createdAndRetrieved.getNombreProdu());
        assertEquals(newDetalle.getCantidad(), createdAndRetrieved.getCantidad());
        assertEquals(newDetalle.getPago(), createdAndRetrieved.getPago(), 0.001);

        facturaDetalleDAO.delete(newDetalle);
        facturaDAO.delete(created);
    }

    @Test
    @DisplayName("Debe lanzar SQLException si se intenta crear un detalle sin crear una factura (violación de CONSTRAINT)")
    void create_NullNombreUsu_ThrowsException() {
        // Arrange
        FacturaDetalle invalidDetalle = new FacturaDetalle(102, null, "ProductoY", 1, 10.0);

        // Act & Assert
        SQLException thrown = assertThrows(SQLException.class, () -> facturaDetalleDAO.create(invalidDetalle));
        assertTrue(thrown.getMessage().contains("Error al crear el detalle"),
                "El mensaje de error debe indicar un problema al crear el detalle");
    }

    @Test
    @DisplayName("Debe lanzar SQLException si se intenta crear un detalle con NombreProdu nulo (violación de NOT NULL)")
    void create_NullNombreProdu_ThrowsException() throws SQLException {
        // Arrange
        Factura facturaToCreate = new Factura("usuarioGet", 500.0, new Timestamp(System.currentTimeMillis()));
        Factura created = facturaDAO.create(facturaToCreate);

        FacturaDetalle invalidDetalle = new FacturaDetalle(created.getCodFac(), "clienteC", null, 5, 25.0);

        // Act & Assert
        SQLException thrown = assertThrows(SQLException.class, () -> facturaDetalleDAO.create(invalidDetalle));
        assertTrue(thrown.getMessage().contains("Error al crear el detalle"),
                "El mensaje de error debe indicar un problema al crear el detalle");

        facturaDAO.delete(created);
    }

    // --- Pruebas para delete(FacturaDetalle detalle) ---

    @Test
    @DisplayName("Debe eliminar un detalle de factura exitosamente por CodFac")
    void delete_Success() throws SQLException {
        // Arrange
        Factura facturaD = new Factura("clienteD", 30.0, new Timestamp(System.currentTimeMillis()));
        Factura createdD = facturaDAO.create(facturaD);
        Factura facturaE = new Factura("clienteE", 5.0, new Timestamp(System.currentTimeMillis()));
        Factura createdE = facturaDAO.create(facturaE);

        FacturaDetalle detalleToDelete1 = new FacturaDetalle(createdD.getCodFac(), "clienteD", "Item1", 1, 10.0);
        FacturaDetalle detalleToDelete2 = new FacturaDetalle(createdD.getCodFac(), "clienteD", "Item2", 2, 20.0); // Mismo CodFac
        FacturaDetalle otherDetail = new FacturaDetalle(createdE.getCodFac(), "clienteE", "Item3", 1, 5.0); // Otro CodFac

        facturaDetalleDAO.create(detalleToDelete1);
        facturaDetalleDAO.create(detalleToDelete2);
        facturaDetalleDAO.create(otherDetail);

        // Act
        // El método delete elimina TODOS los detalles asociados a un CodFac
        boolean result = facturaDetalleDAO.delete(detalleToDelete1); // Usamos cualquiera de los detalles con CodFac 201

        // Assert
        assertTrue(result, "La eliminación de los detalles debe ser exitosa");

        // Verificar que los detalles con CodFac D ya no existen
        ArrayList<FacturaDetalle> retrievedDeleted = facturaDetalleDAO.getDetalle(createdD.getCodFac());
        assertTrue(retrievedDeleted.isEmpty(), "Los detalles con CodFac D deben haber sido eliminados");

        // Verificar que los detalles con otro CodFac E aún existen
        ArrayList<FacturaDetalle> retrievedOther = facturaDetalleDAO.getDetalle(createdE.getCodFac());
        assertEquals(1, retrievedOther.size(), "El detalle con CodFac E no debe haber sido afectado");
        assertEquals(otherDetail.getNombreProdu(), retrievedOther.get(0).getNombreProdu());


        facturaDetalleDAO.delete(detalleToDelete1);
        facturaDetalleDAO.delete(detalleToDelete2);
        facturaDetalleDAO.delete(otherDetail);

        facturaDAO.delete(createdE);
        facturaDAO.delete(createdD);
    }

    @Test
    @DisplayName("Debe retornar false si se intenta eliminar detalles de un CodFac inexistente")
    void delete_NonExistentCodFac_ReturnsFalse() throws SQLException {
        // Arrange
        FacturaDetalle nonExistentDetail = new FacturaDetalle(999, "any", "any", 0, 0.0);

        // Act
        boolean result = facturaDetalleDAO.delete(nonExistentDetail);

        // Assert
        assertFalse(result, "La eliminación de un CodFac inexistente debe retornar false");
    }

    // --- Pruebas para getDetalle(int id) ---


    @Test
    @DisplayName("Debe retornar una lista vacía si no se encuentran detalles para el CodFac")
    void getDetalle_NoDetailsFound_ReturnsEmptyList() throws SQLException {
        // Arrange
        Factura facturaToCreate = new Factura("usuarioGet", 5.0, new Timestamp(System.currentTimeMillis()));
        Factura created = facturaDAO.create(facturaToCreate);

        facturaDetalleDAO.create(new FacturaDetalle(created.getCodFac(), "usuarioGet", "ProdD", 1, 5.0));
        int nonExistentCodFac = 999;

        // Act
        ArrayList<FacturaDetalle> results = facturaDetalleDAO.getDetalle(nonExistentCodFac);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty(), "La lista debe estar vacía si no hay detalles para el CodFac");
        ArrayList<FacturaDetalle> detalles = facturaDetalleDAO.getDetalle(created.getCodFac());

        for(FacturaDetalle detalle : detalles){
            facturaDetalleDAO.delete(detalle);
        }
        facturaDAO.delete(created);
    }

}