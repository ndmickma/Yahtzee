//@author Sanvitti Shah
//@since October 12th, 2026


public class YahtzeeScoreCard {
	private int [] scores; //array of scores 

	public YahtzeeScoreCard() {
		scores = new int[14]; //creates array of 14 ints to represent scorecard (index 0 not used)

		//initialize all the scores to -1 to show they are not used yet
		for(int i = 0; i < scores.length; i++)
		{
			scores[i] = -1; 
		}
	}

	public int getScore(int choice) {
		return scores[choice]; //returns the score at the given index (the index represents the category)
	}

	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader() {
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
						"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	
	/**
	 *  Prints the player's score
	 */
	public void printPlayerScore(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++) {
			if (getScore(i) > -1)
				System.out.printf(" %2d |", getScore(i));
			else System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}


	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg) {
		//check if category number is valid
		if(choice < 1 || choice > 13){ //invalid choice
			return false;
		}

		//check if category already filled (-1 means not filled yet)
		if(scores[choice] != -1){
			return false; //category already taken
		}

		//call scoring method for the category picked
		if(choice >=1  && choice <= 6) //number score
			numberScore(choice, dg);
		else if(choice == 7) //three of a kind
			threeOfAKind(dg);
		else if(choice == 8) //four of a kind
			fourOfAKind(dg);
		else if(choice == 9) //full house
			fullHouse(dg);
		else if(choice == 10) //small straight
			smallStraight(dg);
		else if(choice == 11) //large straight
			largeStraight(dg);
		else if(choice == 12) //chance
			chance(dg);
		else if(choice == 13) //yahtzee
			yahtzeeScore(dg);
		return true; //score successfully changed
	}
	
	/**
	 *  Change the scorecard for a number score 1 to 6
	 *
	 *  @param choice The choice of the player 1 to 6
	 *  @param dg  The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg) {
		int total = 0;
		for(int i = 0; i < dg.getNumDice(); i++)
		{
			if(dg.getDie(i).getValue() == choice) //if the die value is the choice add it to total
				total += choice;
		}
		scores[choice] = total; //set score for the category to the total
	}
	
	/**
	 *	Updates the scorecard for Three Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeOfAKind(DiceGroup dg) {
		int [] counts = new int[7]; //counts how many of each number (1-6) appears, 0 is unused
		int total = 0; 
		boolean found = false; //true if three of a kind found

		for(int i = 0; i < dg.getNumDice(); i++) {//loop through the dice
			int value = dg.getDie(i).getValue(); //get the value of the die (get each time so it updates)
			counts[value]++; //add one to the count for that value
			total += value; //add the value to the total (we count it here because if it's three of a kind we will return this value)
		}

		//check if any value appears 3 or more times by looping through the counts array
		for(int i = 1; i <= 6; i++) {
			if(counts[i] >= 3) //if any value has 3 or more counts, there is three of a kind so found is true
				found = true; 
		}

		if(found) //if three of a kind, score is total of all dice
			scores[7] = total;
		else //no three of a kind found
			scores[7] = 0; //score is 0
	}
	
	public void fourOfAKind(DiceGroup dg) {
		int counts [] = new int[7]; //counts how many of each number (1-6) appears
		int total = 0;
		boolean found = false;

		for(int i = 0; i < dg.getNumDice(); i++) { //loop through the dice in the dice group
			int value = dg.getDie(i).getValue(); //get the value of the die 
			counts[value]++; //add one to the count for that value
			total += value; //add value to the total
		}

		//check if any value appears 4 or more times
		for(int i = 1; i <= 6; i++) {
			if(counts[i] >= 4)
				found = true;
		}

		if(found)
			scores[8] = total;
		else
			scores[8] = 0;
	}
	
	public void fullHouse(DiceGroup dg) {
		int counts [] = new int[7]; //counts how many of each number (1-6) appears
		boolean threeFound = false; //true if exactly three of the dice are the same
		boolean twoFound = false; // true if exactly two of the dice are the same

		//loop through dice group and count how many of each number appears
		for(int i = 0; i < dg.getNumDice(); i++) {
			int value = dg.getDie(i).getValue(); 
			counts[value]++;
		}

		//loop through counts array to see if there is a two and three of a kind pair
		for(int i = 1; i <= 6; i++) {
			if(counts[i] == 3)
				threeFound = true;
			else if(counts[i] == 2)
				twoFound = true;
		}

		if(threeFound && twoFound) //if both a three and two of a kind are found, score is always 25
			scores[9] = 25;
		else
			scores[9] = 0;

	}
	
	public void smallStraight(DiceGroup dg) {
		int counts [] = new int[7]; //counts how many of each number appears
		boolean found = false; //true if small straight found

		//lopp through dies and count how many of each number appears
		for(int i = 0; i < dg.getNumDice(); i++) {
			int value = dg.getDie(i).getValue();
			counts[value]++;
		}

		//loop through counts to see if there is a small straight
		for(int i = 1; i <= 3; i++) { //only need to check i = 1, 2, 3 because small sraight is 4 nums in a row
			if(counts[i] > 0 && counts[i + 1] > 0 && counts[i + 2] > 0 && counts[i + 3] > 0) //if the four numbers in a row all have at least one die
				found = true; //small straight found
		}

		if(found)
			scores[10] = 30; 
		else
			scores[10] = 0;

	}
	
	public void largeStraight(DiceGroup dg) {
		int counts [] = new int[7];
		boolean found = false;

		for(int i = 0; i < dg.getNumDice(); i++) {
			int value = dg.getDie(i).getValue();
			counts[value]++;
		}

		for(int i = 1; i <= 2; i++) {
			if(counts[i] > 0 && counts[i + 1] > 0 && counts[i + 2] > 0 && counts[i + 3] > 0 && counts[i + 4] > 0)
				found = true; 
		}

		if(found)
			scores[11] = 40;
		else
			scores[11] = 0;
	}
	
	public void chance(DiceGroup dg) {
		int total = 0;

		for(int i = 0; i < dg.getNumDice(); i++) {
			total += dg.getDie(i).getValue(); //add the value of each die to the total
		}
		scores[12] = total; //score is total of all dice
	}
	
	public void yahtzeeScore(DiceGroup dg) { //5 of a kind
		int counts [] = new int[7];
		boolean found = false;

		for(int i = 0; i < dg.getNumDice(); i++) {
			int value = dg.getDie(i).getValue();
			counts[value]++;
		}

		for(int i = 1; i <= 6; i++){
			if(counts[i] == 5) //if any value appears 5 times
				found = true;
		}

		if(found)
			scores[13] = 50;
		else
			scores[13] = 0;
	}

	public int getTotalScore() { //gets the total score of all the categories
		int total = 0; 
		for(int i = 1; i < scores.length; i++) { //loop through scores array and add up the categoreis (start from 1 bc index 0 not used)
			if(scores[i] != -1) //only add the score if it has been filled (not -1)
				total += scores[i];
		}

		return total;

	}
}