package src;
import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int handSize;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public Card playCard(int n) {
        return hand.remove(n);
    }

    public void drawCard(Card card) {
        hand.add(card);
        handSize++;
    }

    public int[] findSimilarCards(Card card) {

    }
    
    public String showCards() {

    }

    public int getHandSize() {
        return handSize;
    }
}
