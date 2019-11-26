package ua.nure.msit.dvortsov.examples.content.mso.elements;

import jade.content.Concept;
import jade.content.onto.annotations.Slot;

public class Track implements Concept {
    private static final long serialVersionUID = 1L;

    private String name = null;
    private Integer duration = null;
    private byte[] pcm = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Slot(mandatory = false)
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Slot(mandatory = false)
    public byte[] getPcm() {
        return pcm;
    }

    public void setPcm(byte[] pcm) {
        this.pcm = pcm;
    }

    public String toString() {
        return name + (duration != null ? ("[" + duration.intValue() + " sec]") : "");
    }
}
