package si.fri.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import si.fri.core.Primer;
import si.fri.core.Roles;
import si.fri.core.User;
import si.fri.core.primer_enums.*;
import si.fri.core.primer_foreign_tables.*;
import si.fri.db.PrimerDAO;
import si.fri.db.PrimerForeignTablesDAO;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/primers")
@Produces(MediaType.APPLICATION_JSON)
public class PrimerResource {

    private final PrimerDAO pDao;
    private final PrimerForeignTablesDAO pftDao;

    public PrimerResource(PrimerDAO pDao, PrimerForeignTablesDAO pftDao) {
        this.pDao = pDao;
        this.pftDao = pftDao;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed({Roles.ADMIN, Roles.TECHNICIAN, Roles.RESEARCHER, Roles.STUDENT, Roles.GUEST})
    public List<Primer> getAll(){
        return pDao.findAll();
    }

    @GET
    @Path("/wanted")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed({Roles.ADMIN, Roles.TECHNICIAN, Roles.RESEARCHER, Roles.STUDENT, Roles.GUEST})
    public List<Primer> getWanted(){
        return pDao.findWanted();
    }

    @GET
    @Path("/ordered")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed({Roles.ADMIN, Roles.TECHNICIAN, Roles.RESEARCHER, Roles.STUDENT, Roles.GUEST})
    public List<Primer> getOrdered(){
        return pDao.findOrdered();
    }

    @GET
    @Path("/received")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed({Roles.ADMIN, Roles.TECHNICIAN, Roles.RESEARCHER, Roles.STUDENT, Roles.GUEST})
    public List<Primer> getReceived(){
        return pDao.findReceived();
    }


    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed({Roles.RESEARCHER, Roles.ADMIN, Roles.TECHNICIAN})
    public Primer addPrimer(@Auth User user, PrimerJSON p) {
        Primer primer = new Primer(p.name, p.sequence, Orientation.fromString(p.orientation), pftDao.findFreezer(p.freezer),
                pftDao.findDrawer(p.drawer), pftDao.findBox(p.box), pftDao.findPositionInReference(p.positionInReference), p.tm,
                p.optimalTOfAnnealing, pftDao.findPurificationMethod(p.purificationMethod), p.amountAvailable,
                p.amountAvailablePacks, AmountAvailablePackType.fromString(p.amountAvailablePackType), p.lengthOfAmplicone,
                p.storingT, p.gcpercent, pftDao.findOrganism(p.organism), pftDao.findGen(p.gen), pftDao.findNcbiGenId(p.ncbiGenId),
                pftDao.findHumanGenomBuild(p.humanGenomBuild), pftDao.findFormulation(p.formulation), pftDao.findTypeOfPrimer(p.typeOfPrimer),
                p.sondaSequence, p.assayId, Size.fromString(p.size), pftDao.findPrimerApplication(p.primerApplication),
                p.applicationComment, pftDao.findFiveModification(p.fiveModification), pftDao.findThreeModification(p.threeModification),
                p.concentrationOrdered, ConcentrationOrderedUnit.fromString(p.concentrationOrderedUnit), p.checkSpecifityInBlast,
                pftDao.findDesignerName(p.designerName), pftDao.findDesignerPublication(p.designerPublication),
                pftDao.findDesignerDatabase(p.designerDatabase), pftDao.findProject(p.project), pftDao.findSupplier(p.supplier),
                pftDao.findManufacturer(p.manufacturer), p.comment, p.document, p.analysis, OrderStatus.fromString(p.orderStatus),
                pftDao.findThreeQuencher(p.threeQuencher), pftDao.findFiveDye(p.fiveDye), p.date, user);
        primer = pDao.create(primer, user);
        return primer;
    }

    @POST
    @Path("/delete")
    @UnitOfWork
    @RolesAllowed({Roles.RESEARCHER, Roles.ADMIN})
    public Response deletePrimer(@Auth User user, List<Long> ids) {
        pDao.delete(ids, user);
        return Response.ok().build();
    }

    @POST
    @Path("/update")
    @UnitOfWork
    @RolesAllowed({Roles.RESEARCHER, Roles.ADMIN, Roles.TECHNICIAN})
    public Optional<Primer> updatePrimer(@Auth User user, @QueryParam("id") long id, PrimerJSON primerJson) {
        if (user.getRole().equalsIgnoreCase(Roles.TECHNICIAN)) {
            pDao.updateTechnician(id, primerJson, user);
        } else {
            pDao.update(id, primerJson, user);
        }
        return pDao.findById(id);
    }

    @POST
    @Path("/update/orderStatus/{orderStatus}")
    @UnitOfWork
    @RolesAllowed({Roles.RESEARCHER, Roles.ADMIN, Roles.TECHNICIAN})
    public Response updatePrimer(@Auth User user, List<Long> ids, @PathParam("orderStatus") String orderStatus) {
        pDao.update(ids, OrderStatus.fromString(orderStatus), user);
        return Response.ok().build();
    }

    @POST
    @Path("/updateAmountCommentAnalysisi")
    @UnitOfWork
    @RolesAllowed({Roles.RESEARCHER, Roles.TECHNICIAN, Roles.STUDENT, Roles.ADMIN})
    public Optional<Primer> updateAmountCommentAnalysisi(@Auth User user, @QueryParam("id") long id, PrimerJSON primerJson) {
        pDao.updateAmountCommentAnalysisi(id, primerJson.amountAvailable, primerJson.comment, primerJson.analysis, user);
        return pDao.findById(id);
    }

    @POST
    @Path("/link")
    @UnitOfWork
    @RolesAllowed({Roles.RESEARCHER, Roles.ADMIN})
    public Response link(@Auth User user, Set<Long> ids) {
        pDao.linkAllWithAll(ids, user);
        return Response.ok().build();
    }

    @POST
    @Path("/unlink")
    @UnitOfWork
    @RolesAllowed({Roles.RESEARCHER, Roles.ADMIN})
    public Response unlink(@Auth User user, Set<Long> ids) {
        pDao.unlink(ids, user);
        return Response.ok().build();
    }

    @GET
    @Path("/links")
    @UnitOfWork
    @RolesAllowed({Roles.RESEARCHER, Roles.ADMIN, Roles.TECHNICIAN,Roles.STUDENT,Roles.GUEST})
    public Optional<List<Primer>> getLinks(@QueryParam("id") Long id) {
        return pDao.getLinkedPrimers(id);
    }

    @POST
    @Path("/copy-as-wanted")
    @UnitOfWork
    @RolesAllowed({Roles.RESEARCHER, Roles.ADMIN})
    public Primer copyPrimerAsWanted(@Auth User user, long id) {
        Primer primer = pDao.copy(id, user);
        primer.setOrderStatus(OrderStatus.WANTED);
        return primer;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed({Roles.ADMIN, Roles.TECHNICIAN, Roles.RESEARCHER, Roles.STUDENT, Roles.GUEST})
    public Primer getPrimer(@PathParam("id") long id) {
        Optional<Primer> primer = pDao.findById(id);
        return primer.orElse(null);
    }

    @GET
    @Path("/get-foreign-table")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed({Roles.ADMIN, Roles.TECHNICIAN, Roles.RESEARCHER, Roles.GUEST})
    public List getForeignTable(@QueryParam("table") String table) {
        switch(table) {
            case "box":
                return pftDao.findAllBox();
            case "designerdatabase":
                return pftDao.findAllDesignerDatabase();
            case "designername":
                return pftDao.findAllDesignerName();
            case "designerpublication":
                return pftDao.findAllDesignerPublication();
            case "drawer":
                return pftDao.findAllDrawer();
            case "fivedye":
                return pftDao.findAllFiveDye();
            case "fivemodification":
                return pftDao.findAllFiveModification();
            case "formulation":
                return pftDao.findAllFormulation();
            case "freezer":
                return pftDao.findAllFreezer();
            case "gen":
                return pftDao.findAllGen();
            case "humangenombuild":
                return pftDao.findAllHumanGenomBuild();
            case "ncbigenid":
                return pftDao.findAllNcbiGenId();
            case "organism":
                return pftDao.findAllOrganism();
            case "positioninreference":
                return pftDao.findAllPositionInReference();
            case "primerapplication":
                return pftDao.findAllPrimerApplication();
            case "project":
                return pftDao.findAllProject();
            case "purificationmethod":
                return pftDao.findAllPurificationMethod();
            case "supplier":
                return pftDao.findAllSupplier();
            case "threemodification":
                return pftDao.findAllThreeModification();
            case "threequencher":
                return pftDao.findAllThreeQuencher();
            case "typeofprimer":
                return pftDao.findAllTypeOfPrimer();
        }
        return null;
    }

    @GET
    @Path("/get-all-foreign-tables")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed({Roles.ADMIN, Roles.TECHNICIAN, Roles.RESEARCHER, Roles.GUEST})
    public PrimerForeignTableJSON getAllForeignTables() {
        return new PrimerForeignTableJSON(pftDao);
    }

    @GET
    @Path("/primerjson-example")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed({Roles.ADMIN, Roles.TECHNICIAN, Roles.RESEARCHER, Roles.GUEST})
    public PrimerJSON getPrimerJSONExample() {
        return new PrimerJSON();
    }

    public static class PrimerJSON implements Serializable {
        @JsonProperty
        public String name;
        @JsonProperty
        public String sequence;
        @JsonProperty
        public String orientation;
        @JsonProperty
        public String freezer;
        @JsonProperty
        public String drawer;
        @JsonProperty
        public String box;
        @JsonProperty
        public String positionInReference;
        @JsonProperty
        public Double tm;
        @JsonProperty
        public Double optimalTOfAnnealing;
        @JsonProperty
        public String purificationMethod;
        @JsonProperty
        public Double amountAvailable;
        @JsonProperty
        public Integer amountAvailablePacks;
        @JsonProperty
        public String amountAvailablePackType;
        @JsonProperty
        public Date date;
        @JsonProperty
        public Integer lengthOfAmplicone;
        @JsonProperty
        public String storingT;
        @JsonProperty
        public Double gcpercent;
        @JsonProperty
        public String organism;
        @JsonProperty
        public String gen;
        @JsonProperty
        public String ncbiGenId;
        @JsonProperty
        public String humanGenomBuild;
        @JsonProperty
        public String formulation;
        @JsonProperty
        public String typeOfPrimer;
        @JsonProperty
        public String sondaSequence;
        @JsonProperty
        public String assayId;
        @JsonProperty
        public String size;
        @JsonProperty
        public String primerApplication;
        @JsonProperty
        public String applicationComment;
        @JsonProperty
        public String fiveModification;
        @JsonProperty
        public String threeModification;
        @JsonProperty
        public Double concentrationOrdered;
        @JsonProperty
        public String concentrationOrderedUnit;
        @JsonProperty
        public Boolean checkSpecifityInBlast;
        @JsonProperty
        public String designerName;
        @JsonProperty
        public String designerPublication;
        @JsonProperty
        public String designerDatabase;
        @JsonProperty
        public String project;
        @JsonProperty
        public String supplier;
        @JsonProperty
        public String manufacturer;
        @JsonProperty
        public String comment;
        @JsonProperty
        public String document;
        @JsonProperty
        public String analysis;
        @JsonProperty
        public String orderStatus;
        @JsonProperty
        public String threeQuencher;
        @JsonProperty
        public String fiveDye;
    }

    public static class PrimerForeignTableJSON {
        @JsonProperty
        public List<String> box;
        @JsonProperty
        public List<String> designerDatabase;
        @JsonProperty
        public List<String> designerName;
        @JsonProperty
        public List<String> designerPublication;
        @JsonProperty
        public List<String> drawer;
        @JsonProperty
        public List<String> fiveDye;
        @JsonProperty
        public List<String> fiveModification;
        @JsonProperty
        public List<String> formulation;
        @JsonProperty
        public List<String> freezer;
        @JsonProperty
        public List<String> gen;
        @JsonProperty
        public List<String> humanGenomBuild;
        @JsonProperty
        public List<String> manufacturer;
        @JsonProperty
        public List<String> ncbiGenId;
        @JsonProperty
        public List<String> organism;
        @JsonProperty
        public List<String> positionInReference;
        @JsonProperty
        public List<String> primerApplication;
        @JsonProperty
        public List<String> project;
        @JsonProperty
        public List<String> purificationMethod;
        @JsonProperty
        public List<String> supplier;
        @JsonProperty
        public List<String> threeModification;
        @JsonProperty
        public List<String> threeQuencher;
        @JsonProperty
        public List<String> typeOfPrimer;

        public PrimerForeignTableJSON(PrimerForeignTablesDAO dao) {
            box = dao.findAllBox().stream().map(Box::getBox).collect(Collectors.toList());
            designerDatabase = dao.findAllDesignerDatabase().stream().map(DesignerDatabase::getDesignerDatabase).collect(Collectors.toList());
            designerName = dao.findAllDesignerName().stream().map(DesignerName::getDesignerName).collect(Collectors.toList());
            designerPublication = dao.findAllDesignerPublication().stream().map(DesignerPublication::getDesignerPublication).collect(Collectors.toList());
            drawer = dao.findAllDrawer().stream().map(Drawer::getDrawer).collect(Collectors.toList());
            fiveDye = dao.findAllFiveDye().stream().map(FiveDye::getFiveDye).collect(Collectors.toList());
            fiveModification = dao.findAllFiveModification().stream().map(FiveModification::getFiveModification).collect(Collectors.toList());
            formulation = dao.findAllFormulation().stream().map(Formulation::getFormulation).collect(Collectors.toList());
            freezer = dao.findAllFreezer().stream().map(Freezer::getFreezer).collect(Collectors.toList());
            gen = dao.findAllGen().stream().map(Gen::getGen).collect(Collectors.toList());
            humanGenomBuild = dao.findAllHumanGenomBuild().stream().map(HumanGenomBuild::getHumanGenomBuild).collect(Collectors.toList());
            manufacturer = dao.findAllManufacturer().stream().map(Manufacturer::getManufacturer).collect(Collectors.toList());
            ncbiGenId = dao.findAllNcbiGenId().stream().map(NcbiGenId::getNcbiGenId).collect(Collectors.toList());
            organism = dao.findAllOrganism().stream().map(Organism::getOrganism).collect(Collectors.toList());
            positionInReference = dao.findAllPositionInReference().stream().map(PositionInReference::getPositionInReference).collect(Collectors.toList());
            primerApplication = dao.findAllPrimerApplication().stream().map(PrimerApplication::getPrimerApplication).collect(Collectors.toList());
            project = dao.findAllProject().stream().map(Project::getProject).collect(Collectors.toList());
            purificationMethod = dao.findAllPurificationMethod().stream().map(PurificationMethod::getPurificationMethod).collect(Collectors.toList());
            supplier = dao.findAllSupplier().stream().map(Supplier::getSupplier).collect(Collectors.toList());
            threeModification = dao.findAllThreeModification().stream().map(ThreeModification::getThreeModification).collect(Collectors.toList());
            threeQuencher = dao.findAllThreeQuencher().stream().map(ThreeQuencher::getThreeQuencher).collect(Collectors.toList());
            typeOfPrimer = dao.findAllTypeOfPrimer().stream().map(TypeOfPrimer::getTypeOfPrimer).collect(Collectors.toList());
        }
    }
}