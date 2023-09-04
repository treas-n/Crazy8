package src;
import src.Pile;
import src.Player;
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

            //Display the table and number of cards each player has

            /*for (Player player : players) {
                for(int i = 0; i < player.getHandSize(); i++) {
                    System.out.print("#");
                }
            } */

            System.out.println("Player " + currentPlayer + "'s turn.");
            players.get(currentPlayer).showCards();
            
            //get top card
            Card topCard = pile.topCard();

            //find playable cards
            ArrayList<Integer> playableCards = new ArrayList<>();
            playableCards = players.get(currentPlayer).findSimilarCards(topCard);

            if (players.get(currentPlayer).getHandSize() == 1  && playableCards.size() == 1) {
                gameEnd = true;
                Card lastCard = players.get(currentPlayer).playCard(playableCards.get(0));
                pile.playCard(lastCard);
                System.out.println("Player " + currentPlayer + " played " +lastCard.toString()+ " and won!");
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
                            changeSuit(suit);
                            System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and changed suit to " + suit.toString());
                            pile.playCard(cardToPlay);
                            break;
                        case TWO:
                            pickUp2();
                            System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and made player " +((currentPlayer+direction) % players.size())+ " pick up 2 cards.");
                            pile.playCard(cardToPlay);
                            break;
                        case JACK:
                            changeDirection();
                            System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and changed direction.");
                            pile.playCard(cardToPlay);
                            break;
                        case SEVEN:
                            skipNextPlayer();
                            System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and skipped player " + ((currentPlayer+direction) % players.size()) + ".");
                            pile.playCard(cardToPlay);
                            break;
                        default:
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
        if(players.get(currentPlayer).equals(players.get(players.size()-1)) && direction == 1) {
            players.get(0).drawCard(pile.drawCard());
            players.get(0).drawCard(pile.drawCard());
            skipNextPlayer();
            return;
        }
        else if (players.get(currentPlayer).equals(players.get(0)) && direction == -1){
            players.get(players.size()-1).drawCard(pile.drawCard());
            players.get(players.size()-1).drawCard(pile.drawCard());
            skipNextPlayer();
            return;
        }

        players.get(currentPlayer+direction).drawCard(pile.drawCard());
        players.get(currentPlayer+direction).drawCard(pile.drawCard());
        skipNextPlayer();
    }

    public void skipNextPlayer() {
        currentPlayer += direction;
    }

    public void changeSuit(Card.Suit suit) {
        pile.changeSuit(suit);
    }

    public static void main(String[] args) {
        Crazy8 game = new Crazy8(4);
        Card card = new Card(Card.Suit.CLUBS, Card.Rank.EIGHT);
        System.out.println(card.printCard());

        
        game.startGame();
    }
}
