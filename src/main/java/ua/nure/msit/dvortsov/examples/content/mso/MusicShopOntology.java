package ua.nure.msit.dvortsov.examples.content.mso;

import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyUtils;
import ua.nure.msit.dvortsov.examples.content.eco.ECommerceOntology;
import ua.nure.msit.dvortsov.examples.content.mso.elements.CD;
import ua.nure.msit.dvortsov.examples.content.mso.elements.Single;
import ua.nure.msit.dvortsov.examples.content.mso.elements.Track;

/**
 * Ontology containing music related concepts.
 *
 * @author Giovanni Caire - TILAB
 */
public class MusicShopOntology extends BeanOntology {
    private static final long serialVersionUID = 1L;

    // NAME
    public static final String ONTOLOGY_NAME = "Music-shop-ontology";

    // The singleton instance of this ontology
    private static Ontology INSTANCE;

    public synchronized final static Ontology getInstance() throws BeanOntologyException {
        if (INSTANCE == null) {
            INSTANCE = new MusicShopOntology();
        }
        return INSTANCE;
    }

    /**
     * Constructor
     *
     * @throws BeanOntologyException
     */
    private MusicShopOntology() throws BeanOntologyException {
        super(ONTOLOGY_NAME, ECommerceOntology.getInstance());

        add(Track.class);
        add(CD.class);
        add(Single.class);
    }

    public static void main(String[] args) throws Exception {
        Ontology onto = getInstance();
        OntologyUtils.exploreOntology(onto);
        onto = ECommerceOntology.getInstance();
        OntologyUtils.exploreOntology(onto);
    }
}
