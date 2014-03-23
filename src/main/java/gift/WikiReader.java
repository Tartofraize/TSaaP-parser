package gift;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import quizz.interfaces.QuizContentHandler;
import quizz.interfaces.QuizReader;

/**
 * @author franck Silvestre
 */



public class WikiReader implements QuizReader {

    private QuizContentHandler quizContentHandler;
    private StringBuffer accumulator;
    private int controlCharAccumulator = -1;
    private boolean escapeMode;

    private boolean questionHasStarted;
    private boolean questionHasEnded;
    private boolean answerFragmentHasStarted;
    private boolean answerFragmentHasEnded;
    private boolean answerHasStarted;
    private boolean answerFeedbackHasStarted;
    private boolean answerCreditHasStarted;
    private boolean answerCreditHasEnded;
    
    public static final int INDEX_TYPE = 6;
    //private static Logger logger = Logger.getLogger(GiftReader.class);

    /**
     * Read and parse the file
     * @param input File
     */
    public void readFileFromString(String input) {	
		try {
			File fXmlFile = new File(input);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			String fileContent = doc.getDocumentElement().getTextContent();
			System.out.println(fileContent);
			StringReader reader = new StringReader(fileContent);
	        parse(reader);	
		} catch (Exception e) {
			System.out.println(e.toString());
		}  	
    }
    
    /**
     * Read and parse the file
     * @param input URL
     */
    public void readFileURL(URL input) {
		try {
			File fXmlFile = new File(input.getFile());
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			String fileContent = doc.getDocumentElement().getTextContent();
			
			StringReader reader = new StringReader(fileContent);
	        parse(reader);	
		} catch (Exception e) {
			System.out.println(e.toString());
		}  	
    }
    
    /**
     * @param reader File to parse
     */
    public void parse(Reader reader) throws IOException, GiftReaderException {
        int currentChar;
        char leftBracketCharacter = '{';
    	char rightBracketCharacter = '}';
    	String questionToSplit = "";
    	String blockAnswer = "";

    	quizContentHandler.onStartQuiz();
    	    
        while ((currentChar = reader.read()) != -1) {
        	questionToSplit = getQuestionFromQuizz(reader, currentChar, leftBracketCharacter, rightBracketCharacter);
        	
        	quizContentHandler.onStartQuestion();
        	
        	splitQuestion(questionToSplit);
                	
        	// supprime ligne \n
        	currentChar = reader.read();
        	
        	blockAnswer = getBlockAnswer(reader);
        	
        	quizContentHandler.onStartAnswerBlock();
        	// découpe le block
        	splitBlockAnswer(blockAnswer);       	
        	// fin du block de réponse
        	quizContentHandler.onEndAnswerBlock();
        	
        	// fin de la question
        	quizContentHandler.onEndQuestion();
        	
        	System.out.println("finDUneBoucle");
        }
        
        //endQuiz();
        quizContentHandler.onEndQuiz();
        System.out.println("methode du graphe");
        quizContentHandler.getQuizz().getGraphe();

    }
    
    /**
     * Get the question of the quiz
     * @param reader 		File to parse
     * @param currentChar	Current character
     * @param start 		Start character
     * @param finish 		End character
     * @return the question of the quiz
     */
    public String getQuestionFromQuizz(Reader reader, int currentChar, char start, char finish) throws GiftReaderQuestionWithInvalidFormatException, IOException {
    	String questionToSplit = "";
    	
    	if (currentChar != '{') {
			while (((currentChar = reader.read()) != -1) && (currentChar != start));
			if (currentChar == -1) {
				throw new GiftReaderQuestionWithInvalidFormatException();
			}
    	}

		while (((currentChar = reader.read()) != -1) && (currentChar != finish)) {
    		questionToSplit += (char) currentChar;
    		
    	} 
		if (currentChar == -1) {			
			throw new GiftReaderQuestionWithInvalidFormatException();
		}
    	
    	
		return questionToSplit;
    }
    
    /**
     * Split a string to get the name and the type of the question
     * @param questionToSplit The string to split
     */
    public void splitQuestion(String questionToSplit) {  	
    	questionToSplit = questionToSplit.replaceAll("\n", "");
    	String[] result = questionToSplit.split("\\|");
    	String questionName = result[0];    	
    	char questionType = result[1].charAt(INDEX_TYPE);
    	quizContentHandler.onModifQuestion(questionName, questionType);
    }
    
    /**
     * Get the answer of the quiz
     * @param reader	File to parse
     * @return the answer of the quiz
     * @throws GiftReaderQuestionWithInvalidFormatException
     * @throws IOException
     */
    public String getBlockAnswer(Reader reader) throws GiftReaderQuestionWithInvalidFormatException, IOException {
    	char lastChar = ' ';
    	String blockAnswer = "";
    	int currentChar;
    	
    	while (((currentChar = reader.read()) != -1) && (currentChar != '\n' || lastChar != '\n'))  {
    		if (currentChar == '\n') {
    			lastChar = '\n';
    		} else {
    			lastChar = ' ';
    		}
    		
    		blockAnswer += (char) currentChar;
    	}
    	if (currentChar == -1) {
			throw new GiftReaderQuestionWithInvalidFormatException();
		}
    	return blockAnswer;
    }
    
    /**
     * Get all the answers of the quiz
     * @param blockAnswer
     */
    public void splitBlockAnswer(String blockAnswer) {
    	String[] result = blockAnswer.split("\n");
    	for (int i = 0; i < result.length; i++) {
    		quizContentHandler.onStartAnswer(result[i].charAt(0), result[i].substring(2,result[i].length()-1));
    		quizContentHandler.onEndAnswer();
    	}
    }

    private void checkQuestionHasStarted() {
        if (!questionHasStarted) {
            questionHasStarted = true;
            quizContentHandler.onStartQuestion();
        }
    }

    private void endQuiz() throws GiftReaderQuestionWithInvalidFormatException {
        if (!questionHasEnded && !answerFragmentHasEnded) {
            throw new GiftReaderQuestionWithInvalidFormatException();
        }
        if (!questionHasEnded) {
            flushAccumulator();
            questionHasEnded = true;
            quizContentHandler.onEndQuestion();
        }

    }

    private void processAntiSlashCharacter() throws GiftReaderNotEscapedCharacterException {
        if (escapeMode) {
            processAnyCharacter('\\');
            return;
        }
        escapeMode = true;
    }

    private void processLeftBracketCharacter() throws GiftReaderNotEscapedCharacterException {
        if (escapeMode) {
            processAnyCharacter('{');
            return;
        }
        if (answerFragmentHasStarted) {
            throw new GiftReaderNotEscapedCharacterException();
        }
        flushAccumulator();
        answerFragmentHasStarted = true;
        answerFragmentHasEnded = false;
        quizContentHandler.onStartAnswerBlock();

    }

    private void processRightBracketCharacter() throws GiftReaderNotEscapedCharacterException {
        if (escapeMode) {
            processAnyCharacter('}');
            return;
        }
        if (!answerFragmentHasStarted) {
            throw  new GiftReaderNotEscapedCharacterException();
        }
        flushAccumulator();
        answerFragmentHasEnded = true;
        answerFragmentHasStarted = false;
        if (answerHasStarted) {
            answerHasStarted = false;
            quizContentHandler.onEndAnswer();
        }
        quizContentHandler.onEndAnswerBlock();

    }

    private void processEqualCharacter() throws GiftReaderException {
        processAnswerPrefix('=');
    }

    private void processTildeCharacter() throws GiftReaderException {
        processAnswerPrefix('~');
    }

    private void processAnswerPrefix(char prefix) throws GiftReaderNotEscapedCharacterException {
        if (escapeMode) {
            processAnyCharacter(prefix);
            return;
        }
        if (!answerFragmentHasStarted) {
            throw new GiftReaderNotEscapedCharacterException();
        }
        flushAccumulator();
        if (answerFeedbackHasStarted) {
            answerFeedbackHasStarted = false;
            getQuizContentHandler().onEndAnswerFeedBack();
        }
        if (answerHasStarted) { // the '=' or '~' char marks the end of the current answer
            getQuizContentHandler().onEndAnswer();
        } else {
            answerHasStarted = true;
        }
        answerCreditHasStarted = false;
        answerCreditHasEnded = false;
        //getQuizContentHandler().onStartAnswer(String.valueOf(prefix)); // it marks the beginning of a new one too
    }

    private void processSharpCharacter() throws GiftReaderNotEscapedCharacterException {
        if (escapeMode) {
            processAnyCharacter('#');
            return;
        }
        if (!answerHasStarted || answerFeedbackHasStarted) {
            throw new GiftReaderNotEscapedCharacterException();
        }
        flushAccumulator();
        answerFeedbackHasStarted = true;
        getQuizContentHandler().onStartAnswerFeedBack(); // it marks the beginning of a new one too
    }

    private void processPercentCharacter() throws GiftReaderNotEscapedCharacterException {
        if (escapeMode) {
            processAnyCharacter('%');
            return;
        }
        if (!answerHasStarted || answerCreditHasEnded) {
            throw new GiftReaderNotEscapedCharacterException();
        }
        flushAccumulator();
        if (answerCreditHasStarted) {
            answerCreditHasStarted = false;
            answerCreditHasEnded = true;
            getQuizContentHandler().onEndAnswerCredit();
        } else {
            answerCreditHasStarted = true;
            getQuizContentHandler().onStartAnswerCredit();
        }

    }

    private void processAnyCharacter(int currentChar) throws GiftReaderNotEscapedCharacterException {
        if (accumulator == null) {
            accumulator = new StringBuffer();
        }
        accumulator.append((char) currentChar);
        if (controlCharAccumulator != -1) { // if a control caracter is present,
            if (controlCharAccumulator != '\\') {  // it must be a \
                throw new GiftReaderNotEscapedCharacterException();
            }
            controlCharAccumulator = -1;
        }
        escapeMode = false;
    }

    private void flushAccumulator() {
        if (accumulator != null) {
            quizContentHandler.onString(accumulator.toString());
            accumulator = null;
        }
    }

    public QuizContentHandler getQuizContentHandler() {
        return quizContentHandler;
    }

    public void setQuizContentHandler(QuizContentHandler quizContentHandler) {
        this.quizContentHandler = quizContentHandler;
    }


}
