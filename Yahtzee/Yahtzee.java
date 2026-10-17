//Sanvitti Shah
//@since October 6th, 2026
//@author Sanvitti Shah
//To run: java -cp Yahtzee.jar Yahtzee


public class Yahtzee {
	//keeps track of which player is taking their turn
	private boolean player1Turn;

	//the two players
	private YahtzeePlayer player1, player2;

	//the dice used during the game
	private DiceGroup dice; 

	public Yahtzee() {
		player1 = new YahtzeePlayer();
		player2 = new YahtzeePlayer();
		dice = new DiceGroup();
		player1Turn = true; //player 1 goes first
	}

	public void printHeader() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.                                                  |");
		System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen again.                                       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n\n");
	}

	public static void main(String [] args) {
		Yahtzee game = new Yahtzee();
		game.run();
	}

	//ask both players to enter their names
	public void getPlayers() {
		player1.setName(Prompt.getString("Player 1, please enter your name"));
		player2.setName(Prompt.getString("Player 2, please enter your name"));
	}

	//runs the game
	public void run() {
		printHeader();
		getPlayers();
		chooseFirst();
		printScoreCard();

		for(int round = 1; round <= 13; round++){
			System.out.println("\nRound " + round + " of 13 rounds.");
			oneRound(); //call oneRound() each round
		}

		//display final scorecard
		System.out.println("\nFinal Scorecard:");
		printScoreCard();

		//get each player's total score
		int score1 = player1.getScoreCard().getTotalScore();
		int score2 = player2.getScoreCard().getTotalScore();

		// Display the final scores
        System.out.println(player1.getName() + "'s final score: " + score1);
        System.out.println(player2.getName() + "'s final score: " + score2);

		// Determine the winner and display the result
		if (score1 > score2) {
            System.out.println(player1.getName() + " wins!");
        } 
		else if (score2 > score1) {
            System.out.println(player2.getName() + " wins!");
        } 
		else {
            System.out.println("It's a tie!");
        }
	}

	//determines which player goes first
	public void chooseFirst() {
		int total1 = 0;
		int total2 = 0;
		boolean tied = true; 

		System.out.println("Let's see who will go first.");


		while(tied){
			//player 1 rolls
			Prompt.getString("\n" + player1.getName() + ", press enter to roll the dice");
			dice.rollDice();
			dice.printDice();
			total1 = dice.getTotal();

			//player 2 rolls
			Prompt.getString("\n" + player2.getName() + ", press enter to roll the dice");
			dice.rollDice();
			dice.printDice();
			total2 = dice.getTotal();

			//Display both player's totals
			System.out.println(player1.getName() + ", you rolled a sum of " + total1 + ", and " 
				+ player2.getName() + ", you rolled a sum of " + total2 + ".");

			if(total1 == total2){
				System.out.println("You both tied! Roll again.");
			}
			else 
				tied = false; //if totals not equal (one is greater than the other), then not tied so stop loop
		}

		if(total1 > total2) {
			player1Turn = true;
			System.out.println(player1.getName() + ", since your sum was higher, you'll roll first.");
		}
		else {
			player1Turn = false; 
			System.out.println(player2.getName() + ", since your sum was higher, you'll roll first.");
		}
	}

	public void printScoreCard() {
		player1.getScoreCard().printCardHeader();
		player1.getScoreCard().printPlayerScore(player1);
		player2.getScoreCard().printPlayerScore(player2);
	}

	//runs one round of the game (each player takes ONE turn)
	public void oneRound() {
		if(player1Turn) {
			//player 1 goes first
			oneTurn(player1);

			//player 2 goes second
			oneTurn(player2);
		}
		else {
			//player 2 goes first
			oneTurn(player2);

			//player 1 goes second
			oneTurn(player1);
		}
	}

	public void oneTurn(YahtzeePlayer player) {
		//ask the player to roll the dice
		Prompt.getString("\n" + player.getName()
        	+ ", it's your turn to play. Please hit enter to roll the dice");
		//roll all 5 dice
		dice.rollDice();
		//display the dice
		dice.printDice();

		int roll = 2; //keeps track of which roll the player is on  (2 or 3) (allow for 2 more rolls)
		boolean keepRolling = true; //keeps track of whether the player wants to/can roll again
		
		while(roll <= 3 && keepRolling) {
			//gets user input for which die they want to hold
			String hold = Prompt.getString("Which di(c)e would you like to keep? Enter the values you'd like to 'hold' without spaces. " 
					+"For example, if you'd like to 'hold' die 1, 2, and 5, enter 125 (enter -1 if you'd like to end the turn)");
			
			//stop rolling if the user enters -1
			if(hold.equals("-1")) {
				keepRolling = false;
			}
			else {
				//reroll dice that player did not hold
				dice.rollDice(hold);

				//display dice after rerolling
				dice.printDice();

				//move to next roll
				roll++;	
			}			
		}

		//display current scorecard
		printScoreCard();

		//ask player to choose a scoring category
		int choice = Prompt.getInt(player.getName() + ", now you need to make a choice. Pick a valid integer from the list above", 1, 13);

		//keep asking until valid choice is picked
		while(player.getScoreCard().getScore(choice) != -1) { //while the score for the chosen category is not -1 (meaning it has already been used), keep asking
			choice = Prompt.getInt(player.getName() + ", now you need to make a choice. Pick a valid integer from the list above", 1, 13);
		}

		//record score for chosen category
		player.getScoreCard().changeScore(choice, dice);

		//display updated scorecard
		printScoreCard();
	}

}