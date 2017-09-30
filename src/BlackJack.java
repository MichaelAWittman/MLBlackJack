//import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
//import java.util.Scanner;

class S
{
	public static void o(String s)
	{
		System.out.println(s);
	}
}

class Card
{
	private String suit, rank;

	public Card(String suit, String rank)
		{
			this.suit = suit;
			this.rank = rank;
			// System.out.println("Card: " + this.suit + " " + this.rank);
		}

	public String getSuit()
	{
		return this.suit;
	}

	public String getRank()
	{
		return this.rank;
	}
}

class Deck
{

	private String[] suits = { "SPADES", "DIAMONDS", "HEARTS", "CLUBS" };
	private int len;
	private Card card;
	private ArrayList<Card> deck;
	protected HashMap<String, String> values = new HashMap<String, String>();;
	private String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"J", "Q", "K", "A" };
	private String[] value = { "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"10", "10", "10", "1" };

	public Deck()
		{
			initializeDeck();
		}

	private void initializeDeck()
	{
		setValues();
		Card card;
		this.deck = new ArrayList<Card>();
		for (String suit : suits)
		{
			for (String rank : ranks)
			{
				card = new Card(suit, rank);
				this.deck.add(card);
			}
		}
		shuffle(this.deck);
		//printDeck();
	}

	private void printDeck()
	{
		for (Card card : deck)
		{
			System.out.println("Card: " + card.getSuit() + " " + card.getRank()
					+ " : " + values.get(card.getRank()));
		}
	}

	private void setValues()
	{
		for (int i = 0; i < value.length; i++)
		{
			values.put(ranks[i], value[i]);
		}
	}

	private void shuffle(ArrayList<Card> array)
	{
		int index;
		Card temp;
		Random random = new Random();
		for (int i = array.size() - 1; i > 0; i--)
		{
			index = random.nextInt(i + 1);
			temp = array.get(index);
			array.set(index, array.get(i));
			array.set(i, temp);
		}
	}

	public Card dealCard()
	{
		len = this.deck.size() - 1;
		card = this.deck.get(len);
		this.deck.remove(len);
		return card;
	}
}

class Hand extends Deck
{
	private ArrayList<Card> cards;
	private int score = 0;

	public Hand()
		{
			cards = new ArrayList<Card>();
		}
	
	public int getFlippedCard()
	{
		int value = getValue(cards.get(1));
		
		return value;
	}

	private void addCard(Card card)
	{
		this.cards.add(card);
	}

	private int getValue(Card card)
	{
		return Integer.parseInt(values.get(card.getRank()));
	}

	public int total_sum()
	{
		// add logic for aces
		score = 0;
		for (Card card : this.cards)
		{
			score += getValue(card);
		}
		if (count_aces() == 0)
		{
			return score;
		} 
		
		else
		{
			if (score + 10 > 21)
				return score;
			else
			{
				return score + 10;
			}
		}
	}

	private int count_aces()
	{
		int aces = 0;
		for (Card card : this.cards)
		{
			if (card.getRank().equalsIgnoreCase("A"))
				++aces;
		}
		return aces;
	}

	public void hit()
	{
		addCard(dealCard());
	}

	public void printhands()
	{
		for (Card card : this.cards)
		{
			System.out.println(card.getSuit() + " : " + card.getRank() + " = "
					+ values.get(card.getRank()));
		}
		S.o("Total Sum: " + total_sum());
	}

	public boolean busted()
	{

		if (total_sum() > 21)
		{
			//S.o("Busted!!!");
			return true;
		}
		return false;
	}
}

class Player
{
	private String name;
	public Hand hand;
	public Player(String name, Hand h)
		{
			this.name = name;
			this.hand = h;
		}
	public Hand return_hand()
	{
		return this.hand;
	}
	
	public String getName()
	{
		return this.name;
	}
}
public class BlackJack
{
	private Hand player_hand, dealer_hand;
	public static Boolean playing, shown, busted;
	Machine m;
	int win=0, lose=0, draw=0;

	public BlackJack(Machine m){
		this.m = m;
	}
	
	public void hit()
	{
		if (!player_hand.busted())
		{
			player_hand.hit();
			//S.o("Player Hit");
			//player_hand.printhands();
//			if(12 <= player_hand.total_sum() && player_hand.total_sum() <= 21)
//			{
//				nextStateReward = 1;
//			}
			
		}
		if (player_hand.busted())
		{
			//System.out.println("\nDealer hand:");
			//dealer_hand.printhands();
			//S.o("Dealer wins");
			playing = false;

			m.incReward(-1);
			lose++;
			m.saveReward();
		}
	}

	public void stand()
	{
		//S.o("Player STAND!");
		if (playing)
		{
			if (!player_hand.busted())
			{
				while (dealer_hand.total_sum() < 17)
				{
					dealer_hand.hit();
				}
				//System.out.println("\nDealer hand:");
				//dealer_hand.printhands();
				if (dealer_hand.busted())
				{
					//S.o("Dealer busted. You win");
					
					m.incReward(1);
					win++;
					m.saveReward();
				} 
				
				else
				{

					if (dealer_hand.total_sum() < player_hand.total_sum())
					{
						//S.o("\nYou Win");
						
						win++;
						m.incReward(1);
						m.saveReward();
					} 
					
					else if (dealer_hand.total_sum() == player_hand.total_sum())
					{
						//S.o("\nDraw (PUSH)!!!");
						
						draw++;
						m.incReward(0);
						m.saveReward();
					}
					
					else
					{
						m.incReward(-1);
						m.saveReward();
						lose++;
						//S.o("\nDealer Wins");
					}
				}
				playing = false;
			}
		}
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public void start()
	{

		Deck deck = new Deck();
		playing = true;
		player_hand = new Hand();
		dealer_hand = new Hand();
		player_hand.hit();
		player_hand.hit();
		//System.out.println("Player hand:");
		//player_hand.printhands();
		dealer_hand.hit();
		dealer_hand.hit();
		//System.out.println("Dealer hand:");
		//dealer_hand.printhands();

	}
	
	public void init()
	{
		Deck deck = new Deck();
		playing = true;
	}

	public void play()
	{
		int choice = 1;		
		
		//Scanner scanner = new Scanner(System.in);
		while (playing)
		{
			//get current state
			m.setCurrentState(m.getState(dealer_hand.getFlippedCard(), player_hand.total_sum()));
			
			//choose an action
			choice = m.getChoice();
			
			m.setqIndex(m.getStateActionPairIndex(m.getCurrentState(), choice));
			
			//perform that action
			//also sets the reward for state
			switch (choice)
			{
			case 1:
				hit();
				break;

			case 2:
				stand();
				m.setStand(true);
				break;
			}
			
			//get the next state
			m.setNextState(m.getState(dealer_hand.getFlippedCard(), player_hand.total_sum()));
			
			m.qAlgo();
			
		}
	}
}
