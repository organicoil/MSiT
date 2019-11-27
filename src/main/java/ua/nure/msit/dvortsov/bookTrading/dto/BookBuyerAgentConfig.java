package ua.nure.msit.dvortsov.bookTrading.dto;

public class BookBuyerAgentConfig {

    private String targetBookTitle;

    public BookBuyerAgentConfig(String targetBookTitle) {
        this.targetBookTitle = targetBookTitle;
    }

    public String getTargetBookTitle() {
        return targetBookTitle;
    }

    public void setTargetBookTitle(String targetBookTitle) {
        this.targetBookTitle = targetBookTitle;
    }

}
