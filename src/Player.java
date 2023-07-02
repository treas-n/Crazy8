package src;
import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public Card playCard(int n) {
        return hand.remove(n);
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public ArrayList<Integer> findSimilarCards(Card card) {
        ArrayList similarCards = new ArrayList<Integer>();
        for (Card c : hand) {
            if (c.getSuit() == card.getSuit() || c.getRank() == card.getRank() || c.getRank() == Card.Rank.EIGHT) {
                similarCards.add(hand.indexOf(c));
            }
        }
        return similarCards;
    }
    
    public void showCards() {
        System.out.println("----------------------------------");
        System.out.println("| Player " + name + "'s hand:");

        for (Card card : hand) {
            System.out.println("| " +card);
        }
        System.out.println("----------------------------------");
    }

    public Card.Suit pickSuit() {
        int[] suitCount = new int[4]; //CLUBS = 0, DIAMONDS = 1, HEARTS = 2, SPADES = 3

        for (int i = 0; i < suitCount.length; i++) {
            suitCount[i] = 0; //initialize all values to 0
        }

        for (Card card : hand) {
            switch(card.getSuit()) {
                case CLUBS:
                    suitCount[0]++;
                    break;
                case DIAMONDS:
                    suitCount[1]++;
                    break;
                case HEARTS:
                    suitCount[2]++;
                    break;
                case SPADES:
                    suitCount[3]++;
                    break;
            }
        }

        int largest = (int)(Math.random() * 4);
        for (int i = 0; i < suitCount.length; i++) {
            if(suitCount[i] > suitCount[largest]) {
                largest = i;
            }
        }

        return Card.Suit.values()[largest];
    }

    public int getHandSize() {
        return hand.size();
    }
}
