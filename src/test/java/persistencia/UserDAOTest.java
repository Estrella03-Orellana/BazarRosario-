package persistencia;

import dominio.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    void setUp(){
        userDAO = new UserDAO();
    }
    private User create(User user) throws SQLException{
        User res = userDAO.create(user);

        assertNotNull(res, "El usuario creado no debería ser nulo.");
        assertEquals(user.getName(), res.getName(), "El nombre del usuario creado debe ser igual al original.");
        assertEquals(user.getEmail(), res.getEmail(), "El email del usuario creado debe ser igual al original.");
        assertEquals(user.getIsAdmin(), res.getIsAdmin(), "El status del usuario creado debe ser igual al original.");

        return res;
    }

    private void update(User user) throws SQLException{
        user.setName(user.getName() + "_u");
        user.setEmail("u" + user.getEmail());
        user.setIsAdmin((byte)1);

        boolean res = userDAO.update(user);

        assertTrue(res, "La actualización del usuario debería ser exitosa.");

        getById(user);
    }

    private void getById(User user) throws SQLException {
        User res = userDAO.getById(user.getId());

        assertNotNull(res, "El usuario obtenido por ID no debería ser nulo.");
        assertEquals(user.getId(), res.getId(), "El ID del usuario obtenido debe ser igual al original.");
        assertEquals(user.getName(), res.getName(), "El nombre del usuario obtenido debe ser igual al esperado.");
        assertEquals(user.getEmail(), res.getEmail(), "El email del usuario obtenido debe ser igual al esperado.");
        assertEquals(user.getIsAdmin(), res.getIsAdmin(), "El status del usuario obtenido debe ser igual al esperado.");
    }

    private void search(User user) throws SQLException {
        ArrayList<User> users = userDAO.search(user.getName());
        boolean find = false;

        for (User userItem : users) {
            if (userItem.getName().contains(user.getName())) {
                find = true;
            }
            else{
                find = false;
                break;
            }
        }

        assertTrue(find, "el nombre buscado no fue encontrado : " + user.getName());
    }

    private void delete(User user) throws SQLException{
        boolean res = userDAO.delete(user);

        assertTrue(res, "La eliminación del usuario debería ser exitosa.");

        User res2 = userDAO.getById(user.getId());

        assertNull(res2, "El usuario debería haber sido eliminado y no encontrado por ID.");
    }

    private void autenticate(User user) throws SQLException {
        User res = userDAO.authenticate(user);

        assertNotNull(res, "La autenticación debería retornar un usuario no nulo si es exitosa.");
        assertEquals(res.getEmail(), user.getEmail(), "El email del usuario autenticado debe coincidir con el email proporcionado.");
        assertEquals(res.getIsAdmin(), 1, "El status del usuario autenticado debe ser 1 (Vendedor).");
    }

    private void autenticacionFails(User user) throws SQLException {
        User res = userDAO.authenticate(user);

        assertNull(res, "La autenticación debería fallar y retornar null para credenciales inválidas.");
    }

    private void updatePassword(User user) throws SQLException{
        boolean res = userDAO.updatePassword(user);
        assertTrue(res, "La actualización de la contraseña debería ser exitosa.");

        autenticate(user);
    }
    @Test
    void testUserDAO() throws SQLException {
        Random random = new Random();
        int num = random.nextInt(1000) + 1;
        String strEmail = "test" + num + "@example.com";
        User user = new User("Test User" + num, "password", strEmail, (byte) 0);

        User testUser = create(user);

        update(testUser);

        search(testUser);

        testUser.setPasswordHash(user.getPasswordHash());
        autenticate(testUser);

        testUser.setPasswordHash("12345");
        autenticacionFails(testUser);

        testUser.setPasswordHash("new_password");
        updatePassword(testUser);
        testUser.setPasswordHash("new_password");
        autenticate(testUser);


        delete(testUser);
    }

    void createUser() throws SQLException {
        User user = new User( "admin", "12345", "admin@gmail.com", (byte) 1);
        User res = userDAO.create(user);
        assertNotEquals(res,null);
    }
}