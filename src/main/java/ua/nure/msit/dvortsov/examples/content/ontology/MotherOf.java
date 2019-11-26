package ua.nure.msit.dvortsov.examples.content.ontology;

import jade.content.Predicate;
import jade.util.leap.List;

/**
 * @author Federico Bergenti - Universita` di Parma
 */

public class MotherOf implements Predicate {
    private List children = null;
    private Woman mother = null;

    public void setChildren(List children) {
        this.children = children;
    }

    public void setMother(Woman mother) {
        this.mother = mother;
    }

    public Woman getMother() {
        return mother;
    }

    public List getChildren() {
        return children;
    }
}
