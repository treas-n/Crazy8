package src;
public class Card {
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN,
        KING
    }

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String toString() {
        return rank + " of " + suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isSpecialCard() {
        if (rank == Rank.EIGHT || rank == Rank.TWO || rank == Rank.JACK || rank == Rank.SEVEN) {
            return true;
        }
        else return false;
    }

    public String printCard() {
        String card = "+---------+\n|	  |\n|	  |\n";

        card += "|    " + rank + "    |\n";
        
        switch(suit) {
            case CLUBS:
                card += "|  CLUBS  |\n";
                break;

            case DIAMONDS:
                card += "| DIAMONDS |\n";
                break;

            case HEARTS:
                card += "| HEARTS  |\n";
                break;

            case SPADES:
                card += "| SPADES  |\n";
                break;
        }

        card = "|	  |\n|	  |\n+---------+\n";

        return card;
    }
}