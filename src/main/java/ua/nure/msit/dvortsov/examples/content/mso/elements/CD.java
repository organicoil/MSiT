package ua.nure.msit.dvortsov.examples.content.mso.elements;

import jade.content.onto.annotations.AggregateSlot;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import ua.nure.msit.dvortsov.examples.content.eco.elements.Item;

public class CD extends Item {
    private static final long serialVersionUID = 1L;

    private String title = null;
    protected List tracks = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        title = t;
    }

    @AggregateSlot(cardMin = 1)
    public List getTracks() {
        return tracks;
    }

    public void setTracks(List l) {
        tracks = l;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(title);
        if (tracks != null) {
            Iterator it = tracks.iterator();
            int i = 0;
            while (it.hasNext()) {
                sb.append(" ");
                Track t = (Track) it.next();
                sb.append("track-" + i + ": " + t.toString());
                i++;
            }
        }
        return sb.toString();
    }
}
