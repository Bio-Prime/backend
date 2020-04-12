INSERT INTO users VALUES (1,'t','ADMIN','test');

INSERT INTO positionInReference (positionInReference) VALUES ('5''-promotor'), ('5''-untranslated region'), ('3''-untranslated region'), ('5''-exon'), ('3''-exon'), ('5''-intron'), ('3''-intron'), ('5''-enhancer'), ('5''-up'), ('3''-dw')
INSERT INTO purificationMethod (purificationMethod) VALUES ('Desalted'), ('Cartridge'), ('HPLC'), ('HPLC X'), ('PAGE')
INSERT INTO freezer (freezer) VALUES ('freezer1'), ('freezer2'), ('freezer3'), ('freezer4'), ('freezer5')
INSERT INTO drawer (drawer) VALUES ('drawer1'), ('drawer2'), ('drawer3'), ('drawer4'), ('drawer5')
INSERT INTO box (box) VALUES ('box1'), ('box2'), ('box3'), ('box4'), ('box5')
INSERT INTO organism (organism) VALUES ('Escherichia coli TG1'), ('Escherichia coli WK6'), ('Homo sapiens'), ('Mus musculus'), ('Rattus norvegicus domestica'), ('Other')
INSERT INTO humanGenomBuild (humanGenomBuild) VALUES ('NCBI Build 34'), ('NCBI Build 35'), ('NCBI Build 36.1'), ('GRCh37'), ('GRCh38')
INSERT INTO formulation (formulation) VALUES ('Lyophilized'), ('Water'), ('Resuspended in TRIS'), ('Resuspended in 1x TE'), ('Resuspended in 0,1 X TE'), ('Other')
INSERT INTO typeOfPrimer (typeOfPrimer) VALUES ('GAPDH primer'), ('M13 primer'), ('M13/pUC primer'), ('miRNA primer'), ('DNA/RNA probe'), ('TaqProbe'), ('Random primer'), ('Oligo dT'), ('RNA probes'), ('SP6 probes'), ('T3 primer'), ('T7 primer'), ('Gene Specific primer'), ('Diseas Specific primer'), ('Region Specific primer'), ('Gene Fusion primer'), ('Break Apart primer'), ('Chromosome Control primer')
INSERT INTO primerApplication (primerApplication) VALUES ('Genotyping'), ('Cloning'), ('NGS Sequencing'), ('Sanger Sequencing'), ('Bisulfite converted'), ('cDNA synthesis'), ('RT-qPCR-SYBR'), ('RT-qPCR-TaqMan'), ('PCR'), ('ddPCR'), ('Methylation-specific PCR'), ('In situ hybridisation'), ('FISH '), ('ChIP'), ('Flow Cytometry'), ('Other')
INSERT INTO fiveModification (fiveModification) VALUES ('FAM'), ('HEX'), ('ROX'), ('VIC'), ('T(JOE)'), ('T(ROX)'), ('Aldehyde Modifier'), ('Aleza Fluor 488'), ('Aleza Fluor 532'), ('Aleza Fluor 546'), ('Aleza Fluor 555'), ('Aleza Fluor 594'), ('Aleza Fluor 647'), ('Aleza Fluor 660'), ('Aleza Fluor 750'), ('Alkaline Phspates'), ('Amino C12'), ('Amino Linker C6'), ('BHQ-0'), ('BHQ-1'), ('BHQ-2'), ('BHQ-3'), ('Biotin'), ('Biotin TEG'), ('BODIPY 493/503'), ('BODIPY 530/550'), ('BODIPY 558/568'), ('BODIPY 564/570'), ('BODIPY 576/589'), ('BODIPY 581/591'), ('BODIPY 630/650-X'), ('BODIPY 650/655-X'), ('BODIPY FL'), ('BODIPY FL-X'), ('BODIPY R6G'), ('BODIPY TMR-x'), ('BODIPY TR-X'), ('CAL Fluor Gold 540'), ('CAL Fluor Orange 560'), ('CAL Fluor Red 590'), ('CAL Fluor Red 610'), ('CAL Fluor Red 635'), ('CIV-550'), ('DABCYL'), ('Fluorescein'), ('Horseradish Peroxidase'), ('Marina BlueTM'), ('Oregon Green 488'), ('Oregon Green 488-X'), ('Oregon Green 500'), ('Oregon Green 514'), ('Pacific BlureTM'), ('Phos'), ('Phosphate'), ('Quasar 570'), ('Quasar 670'), ('Quasar 705'), ('Rhodamine GreenTM'), ('Rhodamine Red-X'), ('Rhodol Green'), ('Spacer 18'), ('T(BHQ-1)'), ('T(BHQ-2)'), ('T(C6-Amino)'), ('T(Methylene Blue)'), ('TAMRA'), ('TET'), ('Texas Red-X'), ('Thiol'), ('None'), ('Other')
INSERT INTO threeModification (threeModification) VALUES ('Amino Linker C7'), ('BHQ-0'), ('BHQ-1'), ('BHQ-2'), ('BHQ-3'), ('Biosearch Blue'), ('Biotin'), ('Biotin TEG'), ('C3-Fluorescein'), ('CAL Fluor Orange 560'), ('CAL Fluor Red 610'), ('d-Uridine'), ('DABCYL'), ('mdC(ROX)'), ('mdC(TEG-Amino)'), ('Methylen Blue'), ('Phos'), ('Phosphate'), ('Pulsar 650'), ('Quasar 570'), ('Quasar 670'), ('Quasar 705'), ('Spacer 6'), ('Spacer C3'), ('T(C6-Amino)'), ('T(C6-Biotin)'), ('T(CAL Fluor Gold 540)'), ('T(CAL Fluor Orange 560)'), ('T(CAL Fluor Red 590)'), ('T(CAL Fluor Red 610)'), ('T(CAL Fluor Red 635)'), ('T(Methylene Blue)'), ('TAMRA'), ('Thiol C6 SS'), ('None'), ('Other')
INSERT INTO project (project) VALUES ('project1'), ('project2'), ('project3'), ('project4'), ('project5')
INSERT INTO supplier (supplier) VALUES ('Kemomed'), ('MikroPolo'), ('Omega'), ('Other')
INSERT INTO manufacturer (manufacturer) VALUES ('Biocompare'), ('BioSearch'), ('CustomArray'), ('Genewiz'), ('Integrated DNA Technologies'), ('Sigma-Aldrich'), ('ThermoFisher Scientific'), ('Other')

INSERT INTO primers (generatedName, name, sequence, orientation, length, freezer_id, drawer_id, box_id, positionInReference_id, Tm, OptimalTOfAnnealing, purificationMethod_id, amountAvailableMikroL, amountAvailablePacks, amountAvailablePackSize, date, lengthOfAmplicone, storingT, GCPercent, organism_id, gen, ncbiGenId, humanGenomBuild_id, formulation_id, typeOfPrimer_id, sondaSequence, assayId, size, primerApplication_id, applicationComment, fiveModification_id, threeModification_id, concentrationOrdered, concentrationOrderedUnit, checkSpecifityInBlast, designerName, designerPublication, designerDatabase, project_id, orderedBy, supplier_id, manufacturer_id, comment, document, analysis, orderStatus, user_id) VALUES ('generatedname', 'testname', 'testsequence', 1, 42, (SELECT id FROM freezer WHERE freezer = 'freezer2'), (SELECT id FROM drawer WHERE drawer = 'drawer5'), (SELECT id FROM box WHERE box = 'box1'), (SELECT id FROM positionInReference WHERE positionInReference = '5''-intron'), 24, 53, (SELECT id FROM purificationMethod WHERE purificationMethod = 'Cartridge'), 42.3, 28, 1, '12-31-2019', 4251, 324.2, 32.9, (SELECT id FROM organism WHERE organism = 'Mus musculus'), 'gen123', 'ncbiGenId42', (SELECT id FROM humanGenomBuild WHERE humanGenomBuild = 'GRCh37'), (SELECT id FROM formulation WHERE formulation = 'Resuspended in 0,1 X TE'), (SELECT id FROM typeOfPrimer WHERE typeOfPrimer = 'T7 primer'), 'SondaSequence123', 'assayId24', 3, (SELECT id FROM primerApplication WHERE primerApplication = 'Methylation-specific PCR'), 'application comment 123', (SELECT id FROM fiveModification WHERE fiveModification = 'Biotin TEG'), (SELECT id FROM threeModification WHERE threeModification = 'Quasar 705'), 76, 2, true, 'ime designerja', 'publikacija designerja', 'link do podatkovne baze designerja', (SELECT id FROM project WHERE project = 'project4'), 'Matija Potokar', (SELECT id FROM supplier WHERE supplier = 'Omega'), (SELECT id FROM manufacturer WHERE manufacturer = 'BioSearch'), 'moj komentar', 'link do dokumenta', 'analysis 123', 1, (SELECT id FROM users WHERE username = 'test'))