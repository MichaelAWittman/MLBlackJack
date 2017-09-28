import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Scanner;



public class BlackJackMain
{
	static Machine m = new Machine();
	static BlackJack blackJack;

	public static void play_blackjack()
	{
		while (BlackJack.playing)
		{
			blackJack.play();
		}
	}

	public static void main(String[] args)
	{
//		Scanner scanner = new Scanner(System.in);
		blackJack = new BlackJack(m);
		blackJack.init();
		
		// Multiplayer
//		ArrayList<Player> players = new ArrayList<Player>();
		
//		players.add(new Player("Devajit", new Hand()));
//		players.add(new Player("Dragfire", new Hand()));
		
//		players.get(0).hand.hit();
//		players.get(0).hand.hit();
//		players.get(1).hand.hit();
//		players.get(1).hand.hit();
//		S.o("\nPlayer1:\n");
//		players.get(0).hand.printhands();
//		S.o("\nPlayer2:\n");
//		players.get(1).hand.printhands();

		//S.o("Do u want to play BlackJack.\nEnter:\nYes: Y\nNo: N");
		String choice = m.willPlay();
		while (choice.equalsIgnoreCase("Y"))
		{
			blackJack = new BlackJack(m);
			blackJack.start();
			play_blackjack();
			//S.o("\n\nDo u want to play BlackJack.\nEnter:\nYes: Y\nNo: N");
			choice = m.willPlay();
		}
		S.o("\nThanks for playing! \n");
		try {
			m.writeReward();
		} catch (IOException e) {
			S.o("Your data could not be recorded.");
		}
	}
}
