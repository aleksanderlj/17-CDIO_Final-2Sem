import java.sql.SQLException;
import DAL.DAO.IDAO;
import DAL.DAO.ReceptDAO;
import DAL.DTO.Recept;
import DAL.DTO.ReceptKomp;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ReceptDAOTest {
    private ReceptDAO receptDAO = new ReceptDAO();
    private ReceptKomp receptKomp1 = new ReceptKomp(1, 4.5, 5.3);
    private ReceptKomp receptKomp2 = new ReceptKomp(2, 5.3, 4.5);
    private ReceptKomp[] receptKompArray = new ReceptKomp[]{receptKomp1, receptKomp2};
    private Recept recept = new Recept(-1, "Penicillin", receptKompArray);
    private Recept recivedRecept = new Recept();

    @Test
    public void createTest() throws SQLException, IDAO.DALException {
        receptDAO.delete(-1);
        receptDAO.create(recept);
        recivedRecept = receptDAO.get(-1);
        assertEquals(recept.getId(), recivedRecept.getId());
        assertEquals(recept.getNavn(), recivedRecept.getNavn());
        for (int i = 0 ; i < recept.getIndholdsListe().length ; i++) {
            assertEquals(recept.getIndholdsListe()[i].getRaavareId(), recivedRecept.getIndholdsListe()[i].getRaavareId());
            assertEquals(recept.getIndholdsListe()[i].getNonNetto(), recivedRecept.getIndholdsListe()[i].getNonNetto(), 1e-15);
            assertEquals(recept.getIndholdsListe()[i].getTolerance(), recivedRecept.getIndholdsListe()[i].getTolerance(), 1e-15);
        }
        receptDAO.delete(-1);
    }

    @Test
    public void updateTest() throws SQLException, IDAO.DALException {
        receptDAO.delete(-1);
        receptDAO.create(recept);
        receptKomp1.setTolerance(7.5);
        recept.getIndholdsListe()[0] = receptKomp1;
        recept.setNavn("Antibiotika");
        receptDAO.update(recept);
        recivedRecept = receptDAO.get(-1);
        assertEquals(recept.getNavn(), recivedRecept.getNavn());
        for (int i = 0 ; i < recept.getIndholdsListe().length ; i++) {
            assertEquals(recept.getIndholdsListe()[i].getRaavareId(), recivedRecept.getIndholdsListe()[i].getRaavareId());
            assertEquals(recept.getIndholdsListe()[i].getNonNetto(), recivedRecept.getIndholdsListe()[i].getNonNetto(), 1e-15);
            assertEquals(recept.getIndholdsListe()[i].getTolerance(), recivedRecept.getIndholdsListe()[i].getTolerance(), 1e-15);
        }
        receptDAO.delete(-1);
    }

    @Test
    public void getListTest() throws SQLException, IDAO.DALException {
        Recept[] receptArray = receptDAO.getList();
        assertEquals(receptArray[0].getId(), 1);
        assertEquals(receptArray[1].getId(), 2);
        assertEquals("Saltvand", receptArray[0].getNavn());
        assertEquals("Saftevand", receptArray[1].getNavn());
    }
}
