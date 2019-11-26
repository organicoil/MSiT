package ua.nure.msit.dvortsov.examples.content.eco;

import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import ua.nure.msit.dvortsov.examples.content.eco.elements.Costs;

/**
 * Ontology containing concepts related to buying/selling items.
 *
 * @author Giovanni Caire - TILAB
 */
public class ECommerceOntology extends BeanOntology {
    private static final long serialVersionUID = 1L;

    // NAME
    public static final String ONTOLOGY_NAME = "E-Commerce-ontology";

    // The singleton instance of this ontology
    private static Ontology INSTANCE;

    public synchronized final static Ontology getInstance() throws BeanOntologyException {
        if (INSTANCE == null) {
            INSTANCE = new ECommerceOntology();
        }
        return INSTANCE;
    }

    /**
     * Constructor
     *
     * @throws BeanOntologyException
     */
    private ECommerceOntology() throws BeanOntologyException {
        super(ONTOLOGY_NAME);

        String pkgname = Costs.class.getName();
        pkgname = pkgname.substring(0, pkgname.lastIndexOf("."));
        add(pkgname);
    }
}
