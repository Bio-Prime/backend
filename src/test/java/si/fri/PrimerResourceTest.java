package si.fri;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import si.fri.core.Primer;
import si.fri.core.User;
import si.fri.core.primer_enums.*;
import si.fri.core.primer_foreign_tables.*;
import si.fri.db.PrimerDAO;
import si.fri.db.PrimerForeignTablesDAO;
import si.fri.resources.PrimerResource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(DropwizardExtensionsSupport.class)
public class PrimerResourceTest {

    private static final PrimerDAO pDao = mock(PrimerDAO.class);
    private static final PrimerForeignTablesDAO pftDao = mock(PrimerForeignTablesDAO.class);

    public static final ResourceExtension resources = ResourceExtension.builder()
            .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
            .addResource(new PrimerResource(pDao, pftDao))
            .build();

    private Primer primer;

    @BeforeEach
    public void setup() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse("31/12/2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        primer = new Primer("testname", "testsequence", Orientation.REVERSE, new Freezer("freezer2"),
                new Drawer("drawer3"), new Box("box5"), new PositionInReference("5'-promotor"), 65.2, 22.1, new PurificationMethod("Cartridge"),
                42.3, 30, AmountAvailablePackType.PLATE, 30,
                "42.2", 34.3, new Organism("Homo sapiens"), new Gen("gen123"), new NcbiGenId("ncbigenid123"), new HumanGenomBuild("NCBI Build 36.1"),
                new Formulation("Resuspended in TRIS"), new TypeOfPrimer("M13/pUC primer"), "sondaseq123", "assayid123", Size.M, new PrimerApplication("Sanger Sequencing"),
                "application comment 123", new FiveModification("Aldehyde Modifier"), new ThreeModification("Biotin TEG"), 40.0,
                ConcentrationOrderedUnit.NANOM, true, new DesignerName("designer123"), new DesignerPublication("publication123"),
                new DesignerDatabase("database123"), new Project("project3"), new Supplier("Omega"), new Manufacturer("BioSearch"),
                "komentar", "dokument link", "analiza 123", OrderStatus.RECEIVED,
                new ThreeQuencher("TAMRA"), new FiveDye("NED"), date, null);
        primer.generateName();
        when(pDao.findById(1L)).thenReturn(Optional.ofNullable(primer));
    }

    @AfterEach
    public void tearDown() {
        // we have to reset the mock after each test because of the @ClassRule
        reset(pDao);
        reset(pftDao);
    }

    @Test
    public void testGetPrimer() throws JsonProcessingException {
        String response = resources.target("/primers/get/1").request().get(String.class);
        ObjectMapper mapper = new ObjectMapper();
        String primerString = mapper.writeValueAsString(primer);
        assertEquals(mapper.readTree(response), mapper.readTree(primerString));
        verify(pDao).findById(1L);
    }
}
