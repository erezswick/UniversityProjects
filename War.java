import java.util.Scanner;
public class War {
	
	static Scanner sc = new Scanner(System.in);
	
	static int[]deck =new int[54];
	static int[]playersCards =new int[54];			//Cards in hand
	static int[]computersCards =new int[54];
	static int[]computerTabledraws =new int[27];		// temporary placements for cards on the table
	static int[]playerTabledraws =new int[27];
	
	
// Main Function
	public static void main(String[] args) {
		game();				//
	}
	
	public static void game() {
		initiliazeGame();	//1
		turnIntroduction();			//2
	}
	
	
	//1
	public static void initiliazeGame() {
		createDeck();	//1.1
		distributeDeck();	//1.2
		System.out.println("Welcome to Fatma War Game!");
		sc.nextLine();
		
	}
		//1.1
	public static void createDeck() {
		int number = 2,count=0;
		for(int i=0;i<52;i++) {
			if(count==4) {
				number++;
				count=0;
			}
			deck[i]=number;
			count++;
		}
		deck[52]=15; 
		deck[53]=15;
	}
		//1.2
	public static void distributeDeck() {
		int cardSpot,count=1;
		while(countCards(deck)>0) {
			cardSpot = (int)(Math.random() *countCards(deck));
			if(count%2==1)
				insertCard(playersCards,deck[cardSpot]);		// Helper Function
			if(count%2==0)
				insertCard(computersCards,deck[cardSpot]);		// Helper Function
			removeCard(deck,cardSpot);							// Helper Function
			count++;
		}
	}
	

	//2
	public static void turnIntroduction() {		//Before every turn, displaying the players option to continue
		showScore(); //2.1
		if(!checkIsWinner())	//2.2
			playOption();//2.3
		else
			endGameDeclareWinner(); //2.4
	}
		//2.1
	public static void showScore() {
		System.out.println("AI     "+countCards(computersCards)+" cards");
		System.out.println("Player "+countCards(playersCards)+" cards");
	}
		//2.2
	public static boolean checkIsWinner() {
		if((countCards(computersCards)==0)||(countCards(playersCards)==0))
			return true;
		return false;
	}
		//2.3
	public static void playOption() {
		System.out.println("Play?");
		String playerInput =sc.nextLine();
		while((!playerInput.equalsIgnoreCase("no"))&&(!playerInput.equals(""))){
			System.out.println("The input is wrong. Please try again");
			playerInput =sc.nextLine();
		}
		if(playerInput.equals(""))
			gameFlow();		//3											// If player chooses to continue, carry on to stage 3
		else
			System.out.println("AI wins! Good bye");
	}	
		//2.4
	public static void endGameDeclareWinner() {
		if(countCards(computersCards)==0)
			System.out.println("Player wins!");
		else
			System.out.println("AI wins!");
		restartHandle();				//2.4.1
	}
			////2.4.1
	public static void restartHandle() {
		System.out.println("Would you like to start a new game?");
		String playerInput=sc.nextLine();
		while((!playerInput.equalsIgnoreCase("no"))&&(!playerInput.equals(""))){
			System.out.println("The input is wrong. Please try again");
			playerInput =sc.nextLine();
		}
		if(playerInput.equals("")) {
			clearHandsForRestart();				//2.4.1.1 If player chooses to restart Game - Clear the hands
			game();					//	 	then go back the beginning for new game
		}											
		else
			System.out.println("Good bye");
	}
				////2.4.1.1
	public static void clearHandsForRestart() {
		while(countCards(computersCards)!=0)
			removeCard(computersCards,0);
		while(countCards(playersCards)!=0)
			removeCard(playersCards,0);
	}
	
	//3	- battle functionality
	public static void gameFlow() {
		firstDraw();					//3.1
		displayBattle(computerTabledraws,playerTabledraws);		//3.2
		battleResultHandle();
	}
		//3.1
	public static void firstDraw() {
		drawCardComputer(computerTabledraws);		//3.1.1
		drawCardPlayer(playerTabledraws);			//3.1.2
		removeCard(computersCards,0);				//3.1.3(reused)
		removeCard(playersCards,0);					//3.1.4(reused)
	}
			//3.1.1
	public static void drawCardComputer(int[]arr) {
		arr[countCards(arr)]=computersCards[0];
	}
			//3.1.2	
	public static void drawCardPlayer(int[]arr) {
		arr[countCards(arr)]=playersCards[0];
	}	
		//3.2
	public static void displayBattle(int[]compArr,int[]playerArr) {
		String[]computerTabledrawsToPrint =new String[27];			// Array of cards on table - printable
		String[]playerTabledrawsToPrint =new String[27];
		loadPrintArrays(compArr,playerArr,computerTabledrawsToPrint,playerTabledrawsToPrint);	//3.2.1
		PrintBattleToUser(computerTabledrawsToPrint,playerTabledrawsToPrint);					//3.2.2	
	}	
			//3.2.1
	public static void loadPrintArrays(int[]compArr,int[]playerArr,String[]computerTabledrawsToPrint,String[]playerTabledrawsToPrint) {
		for(int i=0;i<27;i++) {
			computerTabledrawsToPrint[i]=numberToString(compArr[i]);
			playerTabledrawsToPrint[i]=numberToString(playerArr[i]);
		}
	}
			//3.2.2	
	public static void PrintBattleToUser(String[]computerTabledrawsToPrint,String[]playerTabledrawsToPrint) {
		int size = countCards(computerTabledrawsToPrint);
		if(size==1)
			oneCardPrint(computerTabledrawsToPrint,playerTabledrawsToPrint);		//3.3.2.1
		else
			warPrint(computerTabledrawsToPrint,playerTabledrawsToPrint,size);		//3.3.2.2
	}
				//3.2.2.1	
	public static void oneCardPrint(String[]computerTabledrawsToPrint,String[]playerTabledrawsToPrint) {
		System.out.println("AI:    " +computerTabledrawsToPrint[0]);
		System.out.println("Player: "+playerTabledrawsToPrint[0]);
	}
				//3.2.2.2
	public static void warPrint(String[]computerTabledrawsToPrint,String[]playerTabledrawsToPrint,int size) {
		System.out.print("AI:    ");
		for (int i=0;i<size-1;i=i+4)
			System.out.print(computerTabledrawsToPrint[i]+"###");
		System.out.println(computerTabledrawsToPrint[size-1]);
		System.out.print("Player: ");
		for (int i=0;i<size-1;i=i+4)
			System.out.print(playerTabledrawsToPrint[i]+"###");
		System.out.println(playerTabledrawsToPrint[size-1]);
	}
		//3.3
	public static void battleResultHandle() {
			char winner = checkWinner();				//3.3.1
			if(winner=='C') {
				takeCardsFromTable(computersCards);		//3.3.2	
				sc.nextLine();
				turnIntroduction();					// go to the next turn Introduction (Stage 2)
			}
			if(winner=='P') {
				takeCardsFromTable(playersCards);		//3.3.2
				sc.nextLine();
				turnIntroduction();					// go to the next turn Introduction (Stage 2)
			}	
			if(winner=='T') 
				warHandle();							//3.3.3
				
	}
			//3.3.1
	public static char checkWinner() {
		int computerCard = computerTabledraws[countCards(computerTabledraws)-1];
		int playerCard = playerTabledraws[countCards(playerTabledraws)-1];
		if(computerCard>playerCard)
			return 'C';
		if(computerCard<playerCard)
			return 'P';
		return 'T';
	}
			//3.3.2
	public static void takeCardsFromTable(int[]arr) {
		while((countCards(computerTabledraws)!=0)&&(countCards(playerTabledraws)!=0)) {
			arr[countCards(arr)]=computerTabledraws[0];
			arr[countCards(arr)]=playerTabledraws[0];
			removeCard(computerTabledraws,0);				
			removeCard(playerTabledraws,0);	
		}
	}
			//3.3.3
	public static void warHandle() {
		if(!checkIfWinnerBeforeWar()) {			//3.3.3.1 				If no winner before war
			System.out.println("War!");
			sc.nextLine();
			warDraw();								//3.3.3.2
			displayBattle(computerTabledraws,playerTabledraws);		// displayBattle with war draw(Stage 3.2)
			battleResultHandle();									// Handle new battle
		}
		else {														//  If winner before war
			System.out.println("Not enough cards");
			declareWinnerBeforeWar();					//3.3.3.3
			restartHandle();							// Option to restart another game
		}
	}
				//3.3.3.1
	public static boolean checkIfWinnerBeforeWar() {
		if(((countCards(computersCards)<4))||(countCards(playersCards)<4))
			return true;
		return false;
	}
				//3.3.3.2
	public static void warDraw(){
		for(int i=0;i<4;i++) {
			computerTabledraws[countCards(computerTabledraws)]=computersCards[0];
			removeCard(computersCards,0);
			playerTabledraws[countCards(playerTabledraws)]=playersCards[0];
			removeCard(playersCards,0);
		}
		
	}
				//3.3.3.3
	public static void declareWinnerBeforeWar() {
		if(countCards(computersCards)<4)
			System.out.println("Player wins!");
		if(countCards(playersCards)<4)
			System.out.println("AI wins!");
	}
	
	
// Helper Functions:
	
	
	// Function that returns the number of cards in a hand for an int Array
	public static int countCards(int [] arr) {		
		int i;
		for(i=0;i<arr.length;i++)
			if(arr[i]==0)
				break;
		return i;
	}

	// Function that returns the number of cards in a hand for String Array(String version)
		public static int countCards(String [] arr) {		
			int i;
			for(i=0;i<arr.length;i++)
				if(arr[i]=="")
					break;
			return i;
		}
	
		
	// Function that inserts a card(takes it's value) into the bottom of a deck
	public static void insertCard(int[] arr, int cardValue) {
		arr[countCards(arr)]=cardValue;
	}
	
	//Function that removes a card from a deck from a certain index,and pushes up the rest of the deck 
	public static void removeCard(int[] arr,int index) {				
		while(arr[index]!=0) {
			arr[index]=arr[index+1];
			index++;
			if(index==arr.length-1) {
				arr[index]=0;
				break;
			}		
		}
	}	
		
		
		
	public static String numberToString(int num) {
		if(num==2)
			return "2";
		if(num==3)
			return "3";
		if(num==4)
			return "4";
		if(num==5)
			return "5";
		if(num==6)
			return "6";
		if(num==7)
			return "7";
		if(num==8)
			return "8";
		if(num==9)
			return "9";
		if(num==10)
			return "10";
		if (num==11)
			return "J";
		if (num==12)
			return "Q";
		if (num==13)
			return "K";
		if (num==14)
			return "A";
		if (num==15)
			return "JO";
		return "";
		//Integer wrapperInteger = new Integer(num);
		//return wrapperInteger.toString();
	}
}
