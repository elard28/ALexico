import java.io.*;

class lexico
{
	int charClass;
	char lexeme [100];
	char nextChar;
	int lexLen;
	int token;
	int nextToken;

	/* Character classes */
	int LETTER =0;
	int DIGIT = 1;
	int UNKNOWN = 99;
	int NOT_VALID = 100;
	/* Token codes */
	int INT_LIT = 10;
	int FLOAT = 12;
	int IDENT = 11;
	int ASSIGN_OP = 20;
	int ADD_OP = 21;
	int SUB_OP = 22;
	int MULT_OP = 23;
	int DIV_OP = 24;
	int LEFT_PAREN = 25;
	int RIGHT_PAREN = 26;
	int FLOAT_POINT = 27;



		/*****************************************************/
	/* lookup - a function to lookup operators and parentheses
	and return the token */
	int lookup(char ch) {
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
			nextToken = EOF;
			break;
		}
		return nextToken;
	}
	/*****************************************************/
	/* addChar - a function to add nextChar to lexeme */
	void addChar() {
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;
			}
		else
			printf("Error - lexeme is too long \n");
		}

	/*****************************************************/
	/* getChar - a function to get the next character of
	input and determine its character class */
	void getChar() {
		if((nextChar = getc(in_fp)) != EOF) 
		{
			if(isalpha(nextChar))
				charClass = LETTER;
			else if(isdigit(nextChar))
				charClass = DIGIT;
			else charClass = UNKNOWN;
		}
		else
			charClass = EOF;
	}

	/*****************************************************/
	/* getNonBlank - a function to call getChar until it
	returns a non-whitespace character */
	void getNonBlank() {
		while (isspace(nextChar))
			getChar();
	}

	int lex() {
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
	/* EOF */
			case EOF:
				nextToken = EOF;
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
	


	public static void main(String[] args) throws FileNotFoundException
	{
		FileReader fr = new FileReader("front.in");
    	BufferedReader bf = new BufferedReader(fr);

    	Vector<String> chain=new Vector<String>(1,1);
    	String cadena;
    	while((cadena = b.readLine())!=null)
    		chain.addElement(cadena);
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
		}while(nextToken != EOF);
	}
}
*/