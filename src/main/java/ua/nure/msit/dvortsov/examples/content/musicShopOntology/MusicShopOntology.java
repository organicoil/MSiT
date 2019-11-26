package ua.nure.msit.dvortsov.examples.content.musicShopOntology;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PrimitiveSchema;
import jade.content.schema.TermSchema;
import jade.content.schema.facets.CardinalityFacet;
import ua.nure.msit.dvortsov.examples.content.ecommerceOntology.ECommerceOntology;

/**
 * Ontology containing music related concepts.
 *
 * @author Giovanni Caire - TILAB
 */
public class MusicShopOntology extends Ontology implements MusicShopVocabulary {
    // NAME
    public static final String ONTOLOGY_NAME = "Music-shop-ontology";

    // The singleton instance of this ontology
    private static Ontology theInstance = new MusicShopOntology(ECommerceOntology.getInstance());

    public static Ontology getInstance() {
        return theInstance;
    }

    /**
     * Constructor
     */
    private MusicShopOntology(Ontology base) {
        super(ONTOLOGY_NAME, base);

        try {
            add(new ConceptSchema(CD), CD.class);
            add(new ConceptSchema(TRACK), Track.class);
            add(new ConceptSchema(SINGLE), Single.class);

            ConceptSchema cs = (ConceptSchema) getSchema(CD);
            cs.addSuperSchema((ConceptSchema) getSchema(ECommerceOntology.ITEM));
            cs.add(CD_TITLE, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            cs.add(CD_TRACKS, (ConceptSchema) getSchema(TRACK), 1, ObjectSchema.UNLIMITED);

            cs = (ConceptSchema) getSchema(TRACK);
            cs.add(TRACK_NAME, (TermSchema) getSchema(BasicOntology.STRING));
            cs.add(TRACK_DURATION, (TermSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
            cs.add(TRACK_PCM, (TermSchema) getSchema(BasicOntology.BYTE_SEQUENCE), ObjectSchema.OPTIONAL);

            cs = (ConceptSchema) getSchema(SINGLE);
            cs.addSuperSchema((ConceptSchema) getSchema(CD));
            // A SINGLE only includes two tracks
            cs.addFacet(CD_TRACKS, new CardinalityFacet(2, 2));
        } catch (OntologyException oe) {
            oe.printStackTrace();
        }
    }

}
