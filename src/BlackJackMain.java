import java.io.IOException;
import java.util.Scanner;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Scanner;



public class BlackJackMain
{
	static Machine m = new Machine();
	static int win;
	static int lose;
	static int draw;
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
		Scanner scanner = new Scanner(System.in);
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

		S.o("Please select a learning algorithm:\n1. Q-Learning\n2. SARSA");
		int choice = scanner.nextInt();
		if(choice == 1) m.setQLearning(true);
		
		while (m.willPlay().equalsIgnoreCase("Y"))
		{
			blackJack = new BlackJack(m);
			blackJack.start();
			play_blackjack();
			//S.o("\n\nDo u want to play BlackJack.\nEnter:\nYes: Y\nNo: N");
			
//			win+=blackJack.getWin();
//			lose+=blackJack.getLose();
//			draw+=blackJack.getDraw();
		}
		Machine n = new Machine(m.getQs());
		n.setQLearning(m.isQLearning());
		while (n.willPlay().equalsIgnoreCase("Y"))
		{
			blackJack = new BlackJack(n);
			blackJack.start();
			play_blackjack();
			//S.o("\n\nDo u want to play BlackJack.\nEnter:\nYes: Y\nNo: N");
			
			win+=blackJack.getWin();
			lose+=blackJack.getLose();
			draw+=blackJack.getDraw();
		}
		
		S.o("\nThanks for playing! \n");
		
		S.o("Win: " + win);
		S.o("Lose: " + lose);
		S.o("Draw: " + draw);
		
		S.o("\nOld Q values:");
		m.printQs();
		S.o("\nNew Q values:");
		n.printQs();
		try {
			m.writeReward();
		} catch (IOException e) {
			S.o("Your data could not be recorded.");
		}
	}
}
