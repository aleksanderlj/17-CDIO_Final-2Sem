import DAL.DAO.IDAO;
import java.sql.SQLException;
import DAL.DAO.UserDAO;
import DAL.DTO.User;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserDAOTest {
    private UserDAO userDAO = new UserDAO();
    private User user = new User(7545, "SørenBobo", "SB", "061199-4269", true);
    private User recivedUser = new User();

    // Husk at delete bruger før hver test i workbench.
    @Test
    public void createTest() throws IDAO.DALException, SQLException {
        userDAO.create(user);
        recivedUser = userDAO.get(7545);
        assertEquals(user.getId(), recivedUser.getId());
        assertEquals(user.getNavn(), recivedUser.getNavn());
        assertEquals(user.getIni(), recivedUser.getIni());
        assertEquals(user.getCpr(), recivedUser.getCpr());
        assertEquals(user.isAktiv(), recivedUser.isAktiv());
    }

    @Test
    public void updateTest() throws SQLException, IDAO.DALException {
        userDAO.create(user);
        user.setNavn("SørenBob");
        user.setCpr("071199-4397");
        user.setAktiv(false);
        user.setIni("SBO");
        userDAO.update(user);
        recivedUser = userDAO.get(7545);
        assertEquals(user.getId(), recivedUser.getId());
        assertEquals(user.getNavn(), recivedUser.getNavn());
        assertEquals(user.getIni(), recivedUser.getIni());
        assertEquals(user.getCpr(), recivedUser.getCpr());
        assertEquals(user.isAktiv(), recivedUser.isAktiv());
    }

    @Test
    public void getListTest() throws SQLException, IDAO.DALException {
        User[] userList = userDAO.getList();
        assertEquals(userList[0].getNavn(), "Simon");
        assertEquals(userList[1].getNavn(), "Silas");
        assertEquals(userList[2].getNavn(), "Peter");
    }

    @Test
    public void deleteTest() throws SQLException, IDAO.DALException {
        user.setAktiv(true);
        userDAO.create(user);
        userDAO.delete(7545);
        assertEquals(!user.isAktiv(), userDAO.get(7545).isAktiv());
        userDAO.delete(7545);
        assertEquals(user.isAktiv(), userDAO.get(7545).isAktiv());
    }
}