package ua.nure.msit.dvortsov.examples.content.musicShopOntology;

import ua.nure.msit.dvortsov.examples.content.ecommerceOntology.ECommerceVocabulary;

/**
 * Vocabulary containing constants used by the MusicShopOntology.
 *
 * @author Giovanni Caire - TILAB
 */
public interface MusicShopVocabulary extends ECommerceVocabulary {
    String CD = "CD";
    String CD_TITLE = "title";
    String CD_TRACKS = "tracks";

    String TRACK = "TRACK";
    String TRACK_NAME = "name";
    String TRACK_DURATION = "duration";
    String TRACK_PCM = "pcm";

    String SINGLE = "SINGLE";
}
