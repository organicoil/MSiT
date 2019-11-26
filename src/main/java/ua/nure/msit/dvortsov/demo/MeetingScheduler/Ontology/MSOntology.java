package ua.nure.msit.dvortsov.demo.MeetingScheduler.Ontology;

//import jade.onto.*;
//import jade.onto.basic.*;

import jade.content.onto.BCReflectiveIntrospector;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PrimitiveSchema;

public class MSOntology extends Ontology {
    /**
     * A symbolic constant, containing the name of this ontology.
     */
    public static final String NAME = "Meeting-Scheduling-Ontology";

    public static final String PERSON = "Person";
    public static final String APPOINTMENT = "Appointment";

    private static Ontology theInstance = new MSOntology();

    public static Ontology getInstance() {
        return theInstance;
    }

    private MSOntology() {

        // Adds the roles of the basic ontology (ACTION, AID,...)
        super(NAME, BasicOntology.getInstance(), new BCReflectiveIntrospector());

        try {
            //Concepts.
            add(new ConceptSchema(PERSON), Person.class);
            add(new ConceptSchema(APPOINTMENT), Appointment.class);

            ConceptSchema cs = (ConceptSchema) getSchema(PERSON);
            cs.add("name", (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
            cs.add("AID", (ConceptSchema) getSchema(BasicOntology.AID), ObjectSchema.OPTIONAL);
            cs.add("DFName", (ConceptSchema) getSchema(BasicOntology.AID), ObjectSchema.OPTIONAL);

            cs = (ConceptSchema) getSchema(APPOINTMENT);
            cs.add("inviter", (ConceptSchema) getSchema(BasicOntology.AID), ObjectSchema.MANDATORY);
            cs.add("description", (PrimitiveSchema) getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
            cs.add("startingon", (PrimitiveSchema) getSchema(BasicOntology.DATE), ObjectSchema.OPTIONAL);
            cs.add("endingwith", (PrimitiveSchema) getSchema(BasicOntology.DATE), ObjectSchema.OPTIONAL);
            cs.add("fixeddate", (PrimitiveSchema) getSchema(BasicOntology.DATE), ObjectSchema.OPTIONAL);
            cs.add("invitedpersons", (ConceptSchema) getSchema(PERSON), 0, ObjectSchema.UNLIMITED, BasicOntology.SET);
            cs.add("possibledates", (PrimitiveSchema) getSchema(BasicOntology.DATE), 0, ObjectSchema.UNLIMITED, BasicOntology.SET);

        } catch (OntologyException oe) {
            oe.printStackTrace();
        }
    }

}




