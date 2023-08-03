## Game Description
This is a tic tac toe game!
In this game, the board is with dimensions of n x n.
The winner is the player that got a sequence of k squares (of X or O)- the sequence can be vertical, horizontal or diagonal.
The user has to provide the program with the arguments:
- number of rounds in the tournament.
- n : the size of the board. by deafult n=4
- k : winning streak. by deafult k=3 (and 2 ≤ k ≤ n)
- rendering way: console or none.
- two types of players, can be human/whatever/clever/genius.

The user chooses two kind of players, then the players will play a tournament, the program will show how many rounds each player won or if there was a tie.

# Types of Players
Each player has different strategy.
- human player: this is the user that plays through the console - System.in.
- whatever player: on each step, chooses random (available) square on the board.
- clever player: its strategy is to fill as many lines as it can (with the same mark). this player beats the whatever player on most of the games.
- genius player-  its strategy is to fill as many lines, columns and diagonals as it can (with the same mark). this player beats clever player on most of the games.


## Run example
without rendering the board- a game between whatever and clever:

![image](https://github.com/lioraVes/OOP/assets/135438143/7d2cb189-3c1f-4bdd-9388-bfd0f3a035ef)

with rendering the board- a game between human and genius:

![Untitled-1](https://github.com/lioraVes/OOP/assets/135438143/c580467a-602b-49a3-b255-04a137430453)



