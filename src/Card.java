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

        card += "|    ";

        switch(rank) {
            case ACE:
                card += "A";
                break;

            case TWO:
                card += "2";
                break;

            case THREE:
                card += "3";
                break;

            case FOUR:
                card += "4";
                break;

            case FIVE:
                card += "5";
                break;

            case SIX:
                card += "6";
                break;
            
            case SEVEN:
                card += "7";
                break;
            
            case EIGHT:
                card += "8";
                break;
            
            case NINE:
                card += "9";
                break;
            
            case TEN:
                card = card.substring(0, card.length()-1);
                card += "10";
                break;
            
            case JACK:
                card += "J";
                break;

            case QUEEN:
                card += "Q";
                break;
            
            case KING:
                card += "K";
                break;
        } 
        
        card += "    |\n";
        
        switch(suit) {
            case CLUBS:
                card += "|  CLUBS  |\n";
                break;

            case DIAMONDS:
                card += "| DIAMOND |\n";
                break;

            case HEARTS:
                card += "| HEARTS  |\n";
                break;

            case SPADES:
                card += "| SPADES  |\n";
                break;
        }

        card += "|	  |\n|	  |\n+---------+\n";

        return card;
    }
}