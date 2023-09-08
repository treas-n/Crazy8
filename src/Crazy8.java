package src;
import java.util.ArrayList;

public class Crazy8 {
    private ArrayList<Player> players;
    private Pile pile;
    private int currentPlayer, 
                direction = 1, 
                turn = 1;
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

            //first line
            if(currentPlayer == 0) {
                System.out.println("\n\n---\nTurn " +turn+ ": Your turn.\n---");
            }
            else System.out.println("\n\n---\nTurn " +turn+ ": Player " + currentPlayer + "'s turn.\n---");

            //Display the table and number of cards each player has
            int[] spaceNeededForPlayers = getSpaceNeededForPlayers();
            
            //next line
            for(int i = 1; i < players.size(); i++)  { //start from p1 because we are p0
                System.out.print("P" + i);

                for(int j = 0; j < spaceNeededForPlayers[i] - 3; j++) {
                    System.out.print(" ");
                }

                System.out.print("|");
            }

            System.out.println();

            // next line
            for(int i = 1; i < players.size(); i++)  {
                for (int j = 0; j < players.get(i).getHandSize(); j++) {
                    System.out.print("#");
                } 
                
                if (players.get(i).getHandSize() == 1) 
                    System.out.print("  |");
                
                else System.out.print(" |");             
            }

            System.out.println();

            // next line
            for (int j = 0; j < getTotalSpaceNeededForPlayers(); j++) {
                System.out.print("-");
            }

            System.out.println("\n");

            // show face-up card
            System.out.print(pile.topCard().printCard());

            System.out.println();

            players.get(0).playersHand();

            /*for (Player player : players) {
                for(int i = 0; i < player.getHandSize(); i++) {
                    System.out.print("#");
                }
            } */

            
            //get top card
            Card topCard = pile.topCard();

            //find playable cards
            ArrayList<Integer> playableCards = new ArrayList<>();
            playableCards = players.get(currentPlayer).findSimilarCards(topCard);

            if (players.get(currentPlayer).getHandSize() == 1  && playableCards.size() == 1) {
                gameEnd = true;
                Card lastCard = players.get(currentPlayer).playCard(playableCards.get(0));
                pile.playCard(lastCard);
                
                if(currentPlayer == 0)
                    System.out.println("You played " +lastCard.toString()+ " and won!");
                else
                    System.out.println("Player " + currentPlayer + " played " +lastCard.toString()+ " and won!");
                
                break;
            }

            //if no playable cards, draw card
            if (playableCards.size() == 0) {
                players.get(currentPlayer).drawCard(pile.drawCard());
                if(currentPlayer == 0) 
                    System.out.println("You drew a card.");
                else
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

                            if(currentPlayer == 0) 
                                System.out.println("You played " + cardToPlay.toString() + " and changed suit to " + suit.toString());
                            else
                                System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and changed suit to " + suit.toString());
                            
                            pile.playCard(cardToPlay);
                            break;
                        case TWO:
                            
                            if(currentPlayer == 0) 
                                System.out.println("You played " + cardToPlay.toString() + " and made player " +((currentPlayer+direction) % players.size())+ " pick up 2 cards.");
                            else if((currentPlayer == players.size()-1 && direction == 1) || (currentPlayer == 1 && direction == -1))
                                System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and made you pick up 2 cards.");
                            else
                                System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and made player " +((currentPlayer+direction) % players.size())+ " pick up 2 cards.");
                            
                            pickUp2();
                            pile.playCard(cardToPlay);
                            break;
                        case JACK:
                            changeDirection();
                            
                            if(currentPlayer == 0) 
                                System.out.println("You played " + cardToPlay.toString() + " and changed direction.");
                            else
                                System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and changed direction.");
                            
                            pile.playCard(cardToPlay);
                            break;
                        case SEVEN:
                            if(currentPlayer == 0) 
                                System.out.println("You played " + cardToPlay.toString() + " and skipped player " + ((currentPlayer+direction) % players.size()) + ".");
                            else if ((currentPlayer == players.size()-1 && direction == 1) || (currentPlayer == 1 && direction == -1) )
                                System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and skipped player your turn.");
                            else
                                System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + " and skipped player " + ((currentPlayer+direction) % players.size()) + "'s turn'.");
                            
                            skipNextPlayer();
                            pile.playCard(cardToPlay);
                            break;
                        default:
                            break;
                    }
                }
                else {
                    pile.playCard(cardToPlay);
                    
                    if(currentPlayer == 0) 
                        System.out.println("You played " + cardToPlay.toString() + ".");
                    else
                        System.out.println("Player " + currentPlayer + " played " + cardToPlay.toString() + ".");
                }
                
            }


            turn++;
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

    /*
     * This function establishes how many characters each player needs 
     * for their cards to be displayed properly
     */
    private int[] getSpaceNeededForPlayers() {
        
        int[] return_ = new int[players.size()]; 

        for (int i = 0; i < return_.length; i++) {            
            if(players.get(i).getHandSize() > 2) {
                return_[i] = players.get(i).getHandSize() + 2;
            }
            else return_[i] = 4; // always need at least 4 i.e. "P1 |"
        }
        return return_;
    }

    private int getTotalSpaceNeededForPlayers() {
        int[] temp = getSpaceNeededForPlayers();
        int total = 0;

        for (int i = 1; i < players.size(); i++) {
            total += temp[i];    
        }

        return total;
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Crazy8 game = new Crazy8(4);
        //Card card = new Card(Card.Suit.CLUBS, Card.Rank.EIGHT);
        //System.out.println(card.printCard());

        
        game.startGame();
        long endTime = System.nanoTime();
        long executionTimeMS = (endTime - startTime) / 1000000;
        System.out.println("\nRuntime: " + executionTimeMS + " ms.\n");
    }
}
