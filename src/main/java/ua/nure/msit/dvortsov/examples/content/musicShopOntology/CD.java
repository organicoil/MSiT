package ua.nure.msit.dvortsov.examples.content.musicShopOntology;

import jade.util.leap.Iterator;
import jade.util.leap.List;
import ua.nure.msit.dvortsov.examples.content.ecommerceOntology.Item;

public class CD extends Item {
    private String title = null;
    private List tracks = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        title = t;
    }

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


