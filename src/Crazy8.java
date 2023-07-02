package src;
import java.util.ArrayList;

public class Crazy8 {
    private ArrayList<Player> players;
    private Pile pile;
    private int currentPlayer, 
                direction = 1;
    private boolean gameEnd = false;

    public Crazy8(int numPlayers) {
        players = new ArrayList<>();
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

        //pile.playCard(pile.drawCard());

        currentPlayer = 0;
    }

    public void startGame() {
        for( ; !gameEnd ; currentPlayer += direction) {
            //Make sure currentPlayer is within bounds
            if (currentPlayer >= players.size()) {
                currentPlayer = 0;
            }

            if (currentPlayer < 0) {
                currentPlayer = players.size() - 1;
            }

            System.out.println("Player " + currentPlayer + "'s turn.");
            players.get(currentPlayer).showCards();
            
            //get top card
            Card topCard = pile.topCard();

            //find playable cards
            ArrayList<Integer> playableCards = new ArrayList<>();
            playableCards = players.get(currentPlayer).findSimilarCards(topCard);

            if (players.get(currentPlayer).getHandSize() == 0) {
                gameEnd = true;
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            }

            //if no playable cards, draw card
            if (playableCards.size() == 0) {
                players.get(currentPlayer).drawCard(pile.drawCard());
                System.out.println("Player " + currentPlayer + " drew a card.");
            }
            else {
                //generate random number between 0 and size of playableCards
                int random = (int)(Math.random() * playableCards.size());
                Card cardToPlay = players.get(currentPlayer).playCard(playableCards.get(random));

                if(cardToPlay.isSpecialCard()) {
                    switch(cardToPlay.getRank()) {
                        case EIGHT:
                            Card.Suit suit = players.get(currentPlayer).pickSuit();
                            System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and changed suit to " + suit.toString());
                            pile.playCard(cardToPlay);
                            changeSuit(suit);
                            break;
                        case TWO:
                            System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and made player " +((currentPlayer+1) % players.size())+ " pick up 2 cards.");
                            pickUp2();
                            pile.playCard(cardToPlay);
                            break;
                        case JACK:
                            System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and changed direction.");
                            changeDirection();
                            pile.playCard(cardToPlay);
                            break;
                        case SEVEN:
                            System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and skipped player " + ((currentPlayer+1) % players.size()) + ".");
                            skipNextPlayer();
                            pile.playCard(cardToPlay);
                            break;
                    }
                }
                else {
                    pile.playCard(cardToPlay);
                    System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + ".");
                }
                
            }


            
            System.out.println();
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
        currentPlayer++;
    }

    public void changeSuit(Card.Suit suit) {
        pile.changeSuit(suit);
    }

    public static void main(String[] args) {
        Crazy8 game = new Crazy8(2);
        game.startGame();
    }
}
