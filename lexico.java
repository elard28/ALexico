import java.io.*;

class lexico
{
	public int charClass;
	public char[] lexeme =new char[100];
	public char nextChar;
	public int lexLen;
	public int token;
	public int nextToken;

	/* Character classes */
	private static final int LETTER =0;
	private static final int DIGIT = 1;
	private static final int UNKNOWN = 99;
	private static final int NOT_VALID = 100;
	/* Token codes */
	private static final int INT_LIT = 10;
	private static final int FLOAT = 12;
	private static final int IDENT = 11;
	private static final int ASSIGN_OP = 20;
	private static final int ADD_OP = 21;
	private static final int SUB_OP = 22;
	private static final int MULT_OP = 23;
	private static final int DIV_OP = 24;
	private static final int LEFT_PAREN = 25;
	private static final int RIGHT_PAREN = 26;
	private static final int POINT = 27;
	private static final int FLOAT = 28;



		/*****************************************************/
	/* lookup - a function to lookup operators and parentheses
	and return the token */
	public int lookup(char ch) {
	switch (ch) {
		case '(':
			addChar();
			nextToken = LEFT_PAREN;
			break;
		case ')':
			addChar();
			nextToken = RIGHT_PAREN;
			break;
		case '+':
			addChar();
			nextToken = ADD_OP;
			break;
		case '-':
			addChar();
			nextToken = SUB_OP;
			break;
		case '*':
			addChar();
			nextToken = MULT_OP;
			break;
		case '/':
			addChar();
			nextToken = DIV_OP;
			break;
		case '.':
			addChar();
			nextToken = POINT;
			break;
		default:
			addChar();
			nextToken = 100;
			break;
		}
		return nextToken;
	}
	/*****************************************************/
	/* addChar - a function to add nextChar to lexeme */
	public void addChar() {
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;
			}
		else
			//printf("Error - lexeme is too long \n");
			System.out.println("Error - lexeme is too long");
		}

	/*****************************************************/
	/* getChar - a function to get the next character of
	input and determine its character class */
	public void getChar() {
		if((nextChar = get_c()) != '?') 
		{
			if(is_alpha(nextChar))
				charClass = LETTER;
			else if(is_digit(nextChar))
				charClass = DIGIT;
			else charClass = UNKNOWN;
		}
		else
			charClass = 100;
	}

	/*****************************************************/
	/* getNonBlank - a function to call getChar until it
	returns a non-whitespace character */
	public void getNonBlank() 
	{
		while (is_space(nextChar))
			getChar();
	}

	public int lex() 
	{
		lexLen = 0;
		getNonBlank();
		switch (charClass) {
		/* Parse identifiers */
			case LETTER:
				addChar();
				getChar();
				while (charClass == LETTER || charClass == DIGIT) {
					addChar();
					getChar();
				}
			nextToken = IDENT;
			break;
	/* Parse integer literals */
			case DIGIT:
				addChar();
				getChar();
				while (charClass == DIGIT) 
				{
					addChar();
					getChar();
				}

				if(nextChar=='.')
				{
					getChar();
					while (charClass == DIGIT) 
					{
						addChar();
						getChar();
					}
					if(charClass == LETTER)
					{
						addChar();
						getChar();
						while (charClass == LETTER || charClass == DIGIT) 
						{
							addChar();
							getChar();
						}
						nextToken = UNKNOWN;
					}
					else nextToken = FLOAT;
				}

				else if(charClass == LETTER)
				{
					addChar();
					getChar();
					while (charClass == LETTER || charClass == DIGIT) {
						addChar();
						getChar();
					}
					nextToken = UNKNOWN;
				}
				else nextToken = INT_LIT;
				break;
			
	/* Parentheses and operators */
			case UNKNOWN:
				lookup(nextChar);
				getChar();
				break;
	/* 100 */
			case 100:
				nextToken = 100;
				lexeme[0] = 'E';
				lexeme[1] = 'O';
				lexeme[2] = 'F';
				lexeme[3] = 0;
				break;
		} /* End of switch */
		///////printf("Next token is: %d, Next lexeme is %s\n",nextToken, lexeme);
		System.out.println("Next token is: "+nextToken+", Next lexeme is"+lexeme);
		return nextToken;
	} /* End of function lex */
	


	public int fil=0;
	public int col=0;
	Vector<String> chain=new Vector<String>(1,1);

	public char get_c()
	{
		char c='?';
		if(fil<chain.length())
		{
			String ch=chain.elementAt(fil);
			if(col<ch.length())
			{
				c=ch.charAt(col);
				fil++;
				col++;
			}
		}
		return c;
	}

	public bool is_digit(char c)
	{
		if(c=='0' || c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' || c=='9')
			return true;
		return false;
	}

	public bool is_alpha(char c)
	{
		if(c=='a' || c=='b' || c=='c' || c=='d' || c=='e' || c=='f' || c=='g' || c=='h' || c=='i' || c=='j' ||
			c=='k' || c=='l' || c=='m' || c=='n' || c=='o' || c=='p' || c=='q' || c=='r' || c=='s' || c=='t' ||
			c=='u' || c=='v' || c=='w' || c=='x' || c=='y' || c=='z' || 
			c=='A' || c=='B' || c=='C' || c=='D' || c=='E' || c=='F' || c=='G' || c=='H' || c=='I' || c=='J' || 
			c=='K' || c=='L' || c=='M' || c=='N' || c=='O' || c=='P' || c=='Q' || c=='R' || c=='S' || c=='T' || 
			c=='U' || c=='V' || c=='W' || c=='X' || c=='Y' || c=='Z')
			return true;
		return false;
	}

	public bool is_space(char c)
	{
		if(c==' ')
			return true;
		return false;
	}

	public static void main(String[] args) throws FileNotFoundException
	{
		fil=0;
		col=0;

		FileReader fr = new FileReader("front.in");
    	BufferedReader bf = new BufferedReader(fr);

    	
    	String cadena;
    	while((cadena = bf.readLine())!=null)
    		chain.addElement(cadena);

    	getChar();
		do
		{
			lex();
		}while(nextToken != 100);
	}
}
/*
main() {
	if((in_fp = fopen("front.in", "r")) == NULL)
		printf("ERROR - cannot open front.in \n");
	else
	{
		getChar();
		do
		{
			lex();
		}while(nextToken != 100);
	}
}
*/