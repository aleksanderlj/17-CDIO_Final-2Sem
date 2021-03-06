import DAL.DAO.*;
import DAL.DTO.ProduktBatch;
import DAL.DTO.ProduktBatchKomp;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProduktBatchKompTest {
    IKompDAO<ProduktBatchKomp> produktBatchKompDAO = new ProduktBatchKompDAO();
    IDAO<ProduktBatch> produktBatchDAO = new ProduktBatchDAO();
    private ProduktBatch produktBatch = new ProduktBatch
            (1, 0, "061199", null);

    @Test
    public void produktBatchKompTest() throws SQLException, IKompDAO.DALException, IDAO.DALException {
        produktBatch.setId(produktBatchDAO.create(produktBatch));
        ProduktBatchKomp produktBatchKomp = new ProduktBatchKomp(produktBatch.getId(),1,800.0,80.0, 1);
        produktBatchKompDAO.create(produktBatchKomp);
        ProduktBatchKomp received = produktBatchKompDAO.get(produktBatch.getId(),1);
        //Tester om dataen stemmer overens
        assertEquals(produktBatchKomp.getProduktBatchID(),received.getProduktBatchID());
        assertEquals(produktBatchKomp.getRaavareBatchID(),received.getRaavareBatchID());
        assertEquals(produktBatchKomp.getBrugerID(),received.getBrugerID());
        assertEquals(produktBatchKomp.getTara(),received.getTara(),1e-15);
        assertEquals(produktBatchKomp.getNetto(),received.getNetto(),1e-15);

        //prøver at lave ændringer i produktbatchet
        produktBatchKomp.setTara(20.0);
        produktBatchKomp.setNetto(2.0);
        produktBatchKompDAO.update(produktBatchKomp);
        //Henter ned igen
        ProduktBatchKomp received2 = produktBatchKompDAO.get(produktBatch.getId(),1);
        assertEquals(received2.getTara(),20.0,1e-15);
        assertEquals(received2.getNetto(),2.0,1e-15);
        //sletter oprettet data
        produktBatchKompDAO.delete(produktBatch.getId(),1);
        //tester om det er blevet slettet
        ProduktBatchKomp[] alleProduktKomp = produktBatchKompDAO.getList();
        assertEquals(1, alleProduktKomp[0].getProduktBatchID());
        assertEquals(1, alleProduktKomp[0].getRaavareBatchID());
        assertEquals(1, alleProduktKomp[1].getProduktBatchID());
        assertEquals(2, alleProduktKomp[1].getRaavareBatchID());
    }
}
