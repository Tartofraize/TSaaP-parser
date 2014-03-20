package gift;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import questions.interfaces.QuestionType;
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
    //private static Logger logger = Logger.getLogger(GiftReader.class);

    public void readFichier(String input) {
    	// lecture du fichier contenant le quizz	
		try {
			File fXmlFile = new File(input);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			// récupère le contenu
			String contentFich = doc.getDocumentElement().getTextContent();
			
			StringReader reader = new StringReader(contentFich);
	        parse(reader);	
		} catch (Exception e){
			System.out.println(e.toString());
		}  	
    }
    
    
    public void readFichier(URL input) {
    	// lecture du fichier contenant le quizz	
		try {
			File fXmlFile = new File(input.getFile());
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			// récupère le contenu
			String contentFich = doc.getDocumentElement().getTextContent();
			
			StringReader reader = new StringReader(contentFich);
	        parse(reader);	
		} catch (Exception e){
			System.out.println(e.toString());
		}  	
    }
    
    public void parse(Reader reader) throws IOException, GiftReaderException {
    	// lancement du quizz
    	quizContentHandler.onStartQuiz();
    	
        int currentChar;
        while ((currentChar = reader.read()) != -1) {
        	System.out.println("debDUneBoucle");
        	
        	// recupere la question
        	String questionADecouper = "";
        	char rightBracketCharacter = '{';
        	char leftBracketCharacter = '}';
        	// on cherche le premier caractère {
			while((currentChar = reader.read()) != rightBracketCharacter);
			// quand on l'a trouvé, on concatène la chaine résultat jusqu'à }
			while((currentChar = reader.read()) != leftBracketCharacter) {
	    		questionADecouper += (char) currentChar;
	    	}	
			
        	// début de la question
        	quizContentHandler.onStartQuestion();
        	// découpe la question
        	decoupeQuestion(questionADecouper);
        	// fin de la question
        	quizContentHandler.onEndQuestion();
        	
        	// supprime ligne \n
        	while((currentChar = reader.read()) != '\n');
        	
        	// recupere le bloc de réponse
        	String blockAnswer = "";
        	boolean ok = false;
        	char lastChar = ' ';
        	while((currentChar = reader.read()) != '\n' || lastChar != '\n')  {
        		if(currentChar == '\n') {
        			lastChar = '\n';
        		} else {
        			lastChar = ' ';
        		}
        		
        		blockAnswer += (char)currentChar;
	    	}
 
        	// début du block reponse
        	quizContentHandler.onStartAnswerBlock();
        	// découpe le clock
        	decoupeBlockAnswer(blockAnswer);
        	// fin de lu block de réponse
        	quizContentHandler.onEndAnswerBlock();
        	
        	// recupere les reponses
        	System.out.println("finDUneBoucle");
        }
        
        endQuiz();
        quizContentHandler.onEndQuiz();
    }
    
    /**
     * Récupère le contenu d'un fichier se situant entre deux caractères donnés
     * @param bf, le fichier à parser
     * @param start, le caractère de départ
     * @param finish, le caractère de fin
     * @return la string contenu entre les 2 caractères
     */
    public String betweenTwoChar(Reader bf, char start, char finish) {
    	String questionADecouper = "";
    	int charCurrent;
    	try {
    		// on cherche le premier caractère
			while(bf.read() != start);
			// quand on l'a trouvé, on concatène la chaine résultat
			while((charCurrent = bf.read()) != finish) {
	    		questionADecouper += (char) charCurrent;
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}   
		return questionADecouper;
    }
    
    /**
     * Découpe une chaine afin de récupérer le nom de la question ainsi que son type
     * @param questionADecouper, la chaine à analyser et à découper
     */
    public void decoupeQuestion(String questionADecouper) {  	
    	questionADecouper = questionADecouper.replaceAll("\n", "");
    	String[] decoupe = questionADecouper.split("\\|");
    	String nomDeLaQuestion = decoupe[0];    	
    	char typeDelaQuestion = decoupe[1].charAt(6);
    	quizContentHandler.onModifQuestion(nomDeLaQuestion, typeDelaQuestion);
    }
    
    public void decoupeBlockAnswer(String blockAnswer) {
    	String[] decoupe = blockAnswer.split("\n");
    	for (int i = 0; i < decoupe.length; i++) {
    		quizContentHandler.onStartAnswer(decoupe[i].charAt(0), decoupe[i].substring(2,decoupe[i].length()-1));
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
            answerCreditHasStarted = false ;
            answerCreditHasEnded = true;
            getQuizContentHandler().onEndAnswerCredit();
        } else {
            answerCreditHasStarted = true ;
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
