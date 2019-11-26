package ua.nure.msit.dvortsov.examples.content.ecommerceOntology;

/**
 * Vocabulary containing constants used by the ECommerceOntology.
 *
 * @author Giovanni Caire - TILAB
 */
public interface ECommerceVocabulary {
    String ITEM = "ITEM";
    String ITEM_SERIALID = "serialID";

    String PRICE = "PRICE";
    String PRICE_VALUE = "value";
    String PRICE_CURRENCY = "currency";

    String CREDIT_CARD = "CREDITCARD";
    String CREDIT_CARD_TYPE = "type";
    String CREDIT_CARD_NUMBER = "number";
    String CREDIT_CARD_EXPIRATION_DATE = "expirationdate";

    String OWNS = "OWNS";
    String OWNS_OWNER = "Owner";
    String OWNS_ITEM = "item";

    String SELL = "SELL";
    String SELL_BUYER = "buyer";
    String SELL_ITEM = "item";
    String SELL_CREDIT_CARD = "creditcard";

    String COSTS = "COSTS";
    String COSTS_ITEM = "item";
    String COSTS_PRICE = "price";
}  
