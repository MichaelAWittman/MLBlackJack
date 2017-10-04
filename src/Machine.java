import java.io.FileWriter;
import java.io.IOException;

		
public class Machine {
	boolean first = true;
	StringBuilder rewardString = new StringBuilder();
	int reward = 0;
	int totEpisodes = 100000;
	int episodes = 0;
	double epsilon = .9; //.6 for sarsa
	double[] Qs = new double[20];
	int currentState = -1;
	int nextState = -1;
	double gamma = .9;
	double alpha = 0.0015; //.01 for sarsa
	int sarsaState = -1;
	boolean isStand = false;
	int qIndex = 0;
	int sarsaIndex = 0;
	int nextStateReward = 0;
	int sarsaStateReward = 0;
	int didWin = 0;
	boolean isQLearning = false;

	
	public boolean isQLearning() {
		return isQLearning;
	}

	public void setQLearning(boolean isQLearning) {
		this.isQLearning = isQLearning;
	}

	public Machine(){
		//Initialize each Vs to 0;
		for(double d : Qs)
		{
			d = 0;
		}
	}
	
	public Machine(double[] d){
		Qs = d.clone();
		epsilon = 0.1;
	}
	
	/*
	 * low state: 4 to 7
	 * 
	 * Medium state: 8 to 11
	 * 
	 * Player Bust state 12 to 16
	 * 
	 * high state: 17 to 21
	 * 
	 * Bust Card state: 12 to 16 (dealer only)
	 * 
	 * None Bust Card: 4 to 11 (dealer only)
	 * 
	 */
	
	public double[] getQs() {
		return Qs;
	}

	public void setQs(double[] qs) {
		Qs = qs;
	}

	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public int getState(int dealerHand, int playerHand)
	{
		int state = 0;
		
		boolean low = false;
		boolean medium = false;
		boolean playerBust = false;
		boolean high = false;
		
		boolean bustCard = false;
		boolean notBustCard = false;
		
		boolean busted = false;
		
			if(4 <= playerHand && playerHand <= 7)
			{
				low = true;
			}
			else if(8 <= playerHand && playerHand <= 11)
			{
				medium = true;
			}
			else if(12 <= playerHand && playerHand <= 16)
			{
				playerBust = true;
			}
			else if(17 <= playerHand && playerHand <= 21)
			{
				high = true;
			}
			else if(playerHand > 21)
			{
				busted = true;
			}
			
			if(2 <= dealerHand && dealerHand <= 6)
			{
				bustCard = true;
			}
			else if(7 <= dealerHand && dealerHand <= 11)
			{
				notBustCard = true;
			}
			
			
			if(low && bustCard)
			{
				state = 0;
			}
			else if(low && notBustCard)
			{
				state = 1;
			}
			else if(medium && bustCard)
			{
				state = 2;
			}
			else if(medium && notBustCard)
			{
				state = 3;
			}
			else if(playerBust && bustCard)
			{
				state = 4;
			}
			else if(playerBust && notBustCard)
			{
				state = 5;
			}
			else if(high && bustCard)
			{
				state = 6;
			}
			else if(high && notBustCard)
			{
				state = 7;
			}
			else if(busted && bustCard)
			{
				state = 8;
			}
			else if(busted && notBustCard)
			{
				state = 9;
			}
			else if(isStand && notBustCard)
			{
				state = 10;
			}
			else if(isStand && notBustCard)
			{
				state = 11;
			}
			
		
		return state;
	}
	
	
	double max = 0;
	public double getMaxQ(double[] Qs)
	{	
		return max;
	}
	
	public int getMaxQIndex(double[] Qs)
	{
		double max = Qs[0];
		int index = 0;
		int count = 0;

		for (double d : Qs) 
		{
		    if (d > max) 
		    {
		      index = count;
		    }
		    ++count;
		}
		
		return index;
	}
	
	public int getSarsaState() {
		return sarsaState;
	}

	public void setSarsaState(int sarsaState) {
		this.sarsaState = sarsaState;
	}

	public int getSarsaIndex() {
		return sarsaIndex;
	}

	public void setSarsaIndex(int sarsaIndex) {
		this.sarsaIndex = sarsaIndex;
	}

	public int getStateActionPairIndex(int state, int action)
	{
		int index = 0;
		
		if(state == 0 && action == 2)
		{
			index = 0;
		}
		else if(state == 0 && action == 1)
		{
			index = 1;
		}
		else if(state == 1 && action == 2)
		{
			index = 2;
		}
		else if(state == 1 && action == 1)
		{
			index = 3;
		}
		else if(state == 2 && action == 2)
		{
			index = 4;
		}
		else if(state == 2 && action == 1)
		{
			index = 5;
		}
		else if(state == 3 && action == 2)
		{
			index = 6;
		}
		else if(state == 3 && action == 1)
		{
			index = 7;
		}
		else if(state == 4 && action == 2)
		{
			index = 8;
		}
		else if(state == 4 && action == 1)
		{
			index = 9;
		}
		else if(state == 5 && action == 2)
		{
			index = 10;
		}
		else if(state == 5 && action == 1)
		{
			index = 11;
		}
		else if(state == 6 && action == 2)
		{
			index = 12;
		}
		else if(state == 6 && action == 1)
		{
			index = 13;
		}
		else if(state == 7 && action == 2)
		{
			index = 14;
		}
		else if(state == 7 && action == 1)
		{
			index = 15;
		}
		//Do not need to do state 8 to 11 since no action will be taken
		
		return index;
	}
		
	
	private int getBestAction(int currentState, double[] Qs)
	{
		int action = 2;
		double temp = 0;
		int index = 0;
	
		if(currentState == 0)
		{		
			
			if(Qs[0] > Qs[1])
			{
				action = 2;
				max = Qs[0];
			}
			else if(Qs[1] > Qs[0])
			{
				action = 1;
				max = Qs[1];
			}
			else
			{
				action = (Math.random() <= 0.5) ? 1 : 2;
				
				if(action == 1)
				{
					max = Qs[1];
				}
				else if(action == 2)
				{
					max = Qs[0];
				}
				
			}
		}
		else if(currentState == 1)
		{		
			
			if(Qs[2] > Qs[3])
			{
				action = 2;
				max = Qs[2];
			}
			else if(Qs[3] > Qs[2])
			{
				action = 1;
				max = Qs[3];
			}
			else
			{
				action = (Math.random() <= 0.5) ? 1 : 2;
				
				if(action == 1)
				{
					max = Qs[3];
				}
				else if(action == 2)
				{
					max = Qs[2];
				}
				
			}
		}
		else if(currentState == 2)
		{
			if(Qs[4] > Qs[5])
			{
				action = 2;
				max = Qs[4];
			}
			else if(Qs[5] > Qs[4])
			{
				action = 1;
				max = Qs[5];
			}
			else
			{
				action = (Math.random() <= 0.5) ? 1 : 2;
				
				if(action == 1)
				{
					max = Qs[5];
				}
				else if(action == 2)
				{
					max = Qs[4];
				}
			}
		}
		else if(currentState == 3)
		{
			if(Qs[6] > Qs[7])
			{
				action = 2;
				max = Qs[6];
			}
			else if(Qs[7] > Qs[6])
			{
				action = 1;
				max = Qs[7];
			}
			else
			{
				action = (Math.random() <= 0.5) ? 1 : 2;
				
				if(action == 1)
				{
					max = Qs[7];
				}
				else if(action == 2)
				{
					max = Qs[6];
				}
			}
		}
		else if(currentState == 4)
		{
			if(Qs[8] > Qs[9])
			{
				action = 2;
				max = Qs[8];
			}
			else if(Qs[9] > Qs[8])
			{
				action = 1;
				max = Qs[9];
			}
			else
			{
				action = (Math.random() <= 0.5) ? 1 : 2;
				
				if(action == 1)
				{
					max = Qs[9];
				}
				else if(action == 2)
				{
					max = Qs[8];
				}
				
			}
		}
		else if(currentState == 5)
		{
			if(Qs[10] > Qs[11])
			{
				action = 2;
				max = Qs[10];
			}
			else if(Qs[11] > Qs[10])
			{
				action = 1;
				max = Qs[11];
			}
			else
			{
				action = (Math.random() <= 0.5) ? 1 : 2;
				
				if(action == 1)
				{
					max = Qs[11];
				}
				else if(action == 2)
				{
					max = Qs[10];
				}
			}
		}
		else if(currentState == 6)
		{
			if(Qs[12] > Qs[13])
			{
				action = 2;
				max = Qs[12];
			}
			else if(Qs[13] > Qs[12])
			{
				action = 1;
				max = Qs[13];
			}
			else
			{
				action = (Math.random() <= 0.5) ? 1 : 2;
				
				if(action == 1)
				{
					max = Qs[13];
				}
				else if(action == 2)
				{
					max = Qs[12];
				}
			}
		}
		else if(currentState == 7)
		{
			if(Qs[14] > Qs[15])
			{
				action = 2;
				max = Qs[14];
			}
			else if(Qs[15] > Qs[14])
			{
				action = 1;
				max = Qs[15];
			}
			else
			{
				action = (Math.random() <= 0.5) ? 1 : 2;
				
				if(action == 1)
				{
					max = Qs[15];
				}
				else if(action == 2)
				{
					max = Qs[14];
				}
			}
		}
		
		return action;
			
		
	}
	
	public boolean isStand() {
		return isStand;
	}

	public void setStand(boolean isStand) {
		this.isStand = isStand;
	}

	public int getChoice(){
		
		 double d = Math.random();
		 int action = 0; 
		 
		 if(d <= epsilon)
		 {
			 action = (Math.random() <= 0.5) ? 1 : 2;
		 }
		 else
		 {
			 action = getBestAction(currentState, Qs);
			 //System.out.println("Doing best action");

		 }
			
			if(episodes % 375 == 0)
			{
				epsilon = (epsilon * .9); //.29 sarsa
			}
		
		return action;
	}
	
	public int getNextState() {
		return nextState;
	}

	public void setNextState(int nextState) {
		this.nextState = nextState;
	}

	public int getqIndex() {
		return qIndex;
	}

	public void setqIndex(int qIndex) {
		this.qIndex = qIndex;
	}
	
	public void qAlgo(){
		double maxQ = getMaxQ(Qs);
		
		//perform the algorithm 
		Qs[qIndex] = Qs[qIndex] + (alpha * (nextStateReward + (gamma * maxQ) - Qs[qIndex]));
		
		//set the next state as the current state
		currentState = nextState;
		nextStateReward = 0;
	}

//	def learn(self, state1, action1, reward, state2, action2):
//	    qnext = self.getQ(state2, action2)
//	    self.learnQ(state1, action1,
//	                reward, reward + self.gamma * qnext)
	public void sarsaAlgo(){
		Qs[sarsaIndex] = Qs[sarsaIndex] + (alpha * (nextStateReward + (gamma * Qs[qIndex]) - Qs[sarsaIndex]));
		sarsaState = currentState;
		currentState = nextState;
		nextStateReward = 0;
	}
	
	public void printQs(){
		int count = 0;
		for(double d : Qs){
			S.o("State: " + count + " Q(s,a) = " + d);
			++count;
		}
	}
	
	public String willPlay(){
		if(totEpisodes - episodes > 0){
			return "Y";
		}
		else
			return "N";
	}
	
	public void incReward(int i){
		reward = reward + (i);
		nextStateReward = i;
		if(i>0) didWin = 1;
		else if(i<0) didWin = -1;
		else didWin = 0;
	}
	
	public void incEpisodes(){
		episodes++;
	}
	
	public void saveReward(){
		
		if(first){
			rewardString.append("Reward,Won Hand,Trials\n");
			first = false;
		}
		rewardString.append(reward + "," + didWin + "," + episodes + "\n");
	}
	
	public void writeReward() throws IOException{
		FileWriter w = new FileWriter("data.csv");
		
		w.append(rewardString);
		w.flush();
		w.close();
	}
}
