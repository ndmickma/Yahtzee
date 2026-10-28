-Computer/Java command line version of the two player dice game Yahtzee.

What is it:
-This project makes playing the dice game Yahtzee more accessible for those who don't have the game and 
makes it easier for people to play (because the computer calculates the points and arranges them in a neat scoreboard).

Key Features: 
-Automatic score calculations -> no more manual calculations or potential scoring errors; automatically updates the scoreboard
-Formatted Terminal Scoreboard -> displays organized, updated scorecards for both players at each turn
-Classic Gameplay Rules -> keeps the standard Yahtzee scoring rules
-Allows players to roll up to three times per turn -> they can choose exactly which di(c)e to hold

To play:
-Download the Yahtzee.jar file from the releases tab
-Run game using Java terminal command -> make sure you are in the folder that contains the jar file and type in:
-java -jar Yahtzee.jar

What I learned: 
-Main scoring logic: using an array to count how many times a certain dice value appears in each roll
-Managing multiple turn game
-Player switching -> using multiple instances of the YahtzeePlayer class

What I struggled with: 
-Detecting Consecutive Sequences: (small straight 4 in a row and large straight 5 in a row) required
counting consecutive counts while resetting the counter whenever a frequency of 0 occured.
-Coordinating the main game loop across multiple turns, each player has 3 rolls per turn and they can hold dice -> picking which player is "active"
