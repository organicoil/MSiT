package ua.nure.msit.dvortsov.examples.content.musicShopOntology;

import jade.content.Concept;

public class Track implements Concept {
    private String name = null;
    private Integer duration = null;
    private byte[] pcm = null;

    // NAME
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // DURATION
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    // PCM
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


