package src;
import java.util.Collections;
import java.util.ArrayList;

public class Crazy8 {
    private ArrayList<Player> players;
    private Pile pile;
    private int currentPlayer, 
                direction = 1;
    private boolean gameEnd = false;

    public Crazy8(int numPlayers) {
        for(Integer i = 0; i < numPlayers; i++) {
            players.add(new Player(i.toString()));
        }

        pile = new Pile();
        
        //deal cards
        for (Player player : players) {
            for (int i = 0; i < 4; i++) {
                player.drawCard(pile.drawCard());
            }
        }

        pile.playCard(pile.drawCard());

        currentPlayer = 0;
    }

    public void startGame() {
        for( ; !gameEnd ; currentPlayer += direction) {
            //Make sure currentPlayer is within bounds
            if (currentPlayer >= players.size()) {
                currentPlayer = 0;
            }
            
            //get top card
            Card topCard = pile.topCard();

            //find playable cards
            int[] playableCards = players.get(currentPlayer).findSimilarCards(topCard);

            //if no playable cards, draw card
            if (playableCards.length == 0) {
                players.get(currentPlayer).drawCard(pile.drawCard());
            }
            else {
                //generate random number between 0 and size of playableCards
                int random = (int)(Math.random() * playableCards.length);

                pile.playCard(players.get(currentPlayer).playCard(playableCards[random]));
            }








            if (players.get(currentPlayer).getHandSize() == 0) {
                gameEnd = true;
            }
        }
    }

    public void changeDirection() {
        direction *= -1;
    }

    public void pickUp2() {
        players.get(currentPlayer+1).drawCard(pile.drawCard());
        players.get(currentPlayer+1).drawCard(pile.drawCard());
        skipNextPlayer();
    }

    public void skipNextPlayer() {
        currentPlayer += 2;
    }

    public void changeSuit(Card.Suit suit) {
        pile.playCard(new Card(suit, Card.Rank.EIGHT));
    }


}
