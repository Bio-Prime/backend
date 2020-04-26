package si.fri;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import si.fri.core.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrimerTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse("31/12/2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Primer primer = new Primer("testname", "testsequence", Orientation.REVERSE, new Freezer("freezer2"),
                new Drawer("drawer3"), new Box("box5"), new PositionInReference("5'-promotor"), 65.2, 22.1, new PurificationMethod("Cartridge"),
                42.3, 30, AmountAvailablePackSize.PLATE, 30,
                42.2, 34.3, new Organism("Homo sapiens"), "gen123", "ncbigenid123", new HumanGenomBuild("NCBI Build 36.1"),
                new Formulation("Resuspended in TRIS"), new TypeOfPrimer("M13/pUC primer"), "sondaseq123", "assayid123", Size.M, new PrimerApplication("Sanger Sequencing"),
                "application comment 123",  new FiveModification("Aldehyde Modifier"), new ThreeModification("Biotin TEG"), 40,
                ConcentrationOrderedUnit.NANOMOL, true, "designer123", "publication123",
                "database123", new Project("project3"), "miha", new Supplier("Omega"), new Manufacturer("BioSearch"),
                "komentar", "dokument link", "analiza 123", OrderStatus.RECEIVED, null);

        // since date is always set to date of insertion, it needs to be manually set for testing
        primer.setDate(date);

        primer.generateName();

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("src/test/resources/fixtures/primer.json"), Primer.class));

        assertThat(MAPPER.writeValueAsString(primer)).isEqualTo(expected);
    }
}