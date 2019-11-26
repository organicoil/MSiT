package ua.nure.msit.dvortsov.examples.content.mso.elements;

import jade.content.onto.annotations.AggregateSlot;
import jade.util.leap.List;

public class Single extends CD {
    private static final long serialVersionUID = 1L;

    @AggregateSlot(cardMin = 2, cardMax = 2)
    public List getTracks() {
        return tracks;
    }

    public void setTracks(List l) {
        tracks = l;
    }
}
