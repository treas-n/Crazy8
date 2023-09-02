package src;
import src.Card;
import java.util.Collections;
import java.util.ArrayList;

public class Pile {
    private ArrayList<Card> pickUpPile;
    private ArrayList<Card> discardPile;
    private Card.Rank topCardRank;
    private Card.Suit topCardSuit;

    public Pile() {
        pickUpPile = new ArrayList<>();
        discardPile = new ArrayList<>();

        for(int suit = 0; suit < 4; suit++) {
            for(int rank = 0; rank < 13; rank++) {
                pickUpPile.add(new Card(Card.Suit.values()[suit], Card.Rank.values()[rank]));
            }
        }

        Collections.shuffle(pickUpPile);
        discardPile.add(pop());
        topCardRank = discardPile.get(0).getRank();
        topCardSuit = discardPile.get(0).getSuit();
        System.out.println("Top card: " + discardPile.get(0));
    }

    private void reshuffle() {
        Card topCard = pop();
        pickUpPile.addAll(discardPile);
        Collections.shuffle(pickUpPile);
        discardPile.clear();
        discardPile.add(topCard);
        topCardRank = topCard.getRank();
        topCardSuit = topCard.getSuit();}

    public Card drawCard() {
        if(pickUpPile.size() <= 10) {
            reshuffle();
        }
        return pop();

    }

    public void playCard(Card card) {
        push(card);
        topCardRank = card.getRank();
        topCardSuit = card.getSuit();
    }

    public Card topCard() {
        return new Card(topCardSuit, topCardRank);
    }

    private void push(Card card) {
        discardPile.add(card);
        topCardRank = card.getRank();
        topCardSuit = card.getSuit();
    }

    private Card pop() {
        return pickUpPile.remove(pickUpPile.size() - 1);
    }

    public void changeSuit(Card.Suit suit) {
        topCardSuit = suit;
    }
}