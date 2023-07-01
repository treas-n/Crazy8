package src;
import java.util.Collections;
import java.util.ArrayList;

public class Pile {
    private ArrayList<Card> pickUpPile;
    private ArrayList<Card> discardPile;

    public Pile() {
        pickUpPile = new ArrayList<>();
        discardPile = new ArrayList<>();

        for(int suit = 0; suit < 4; suit++) {
            for(int rank = 0; rank < 13; rank++) {
                pickUpPile.add(new Card(Card.Suit.values()[suit], Card.Rank.values()[rank]));
            }
        }

        Collections.shuffle(pickUpPile);
    }

    public Card drawCard() {
        return pop();
    }

    public void playCard(Card card) {
        push(card);
    }

    public Card topCard() {
        Card return_ = pop();
        pickUpPile.add(0, return_);
        return return_;

    }

    private void push(Card card) {
        pickUpPile.add(card);
    }

    private Card pop() {
        return pickUpPile.remove(pickUpPile.size() - 1);
    }
}