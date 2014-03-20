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
			
			StringReader readerVerif = new StringReader(contentFich);
			// on vérifie que le quizz est composé d'au moins une question
	        if (containsLeastOneQuestion(readerVerif)) {
	        	parse(new StringReader(contentFich));
	        } else {
	        	throw new GiftReaderException("No questions in the quizz.");
	        }		
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
			
			StringReader readerVerif = new StringReader(contentFich);
			// on vérifie que le quizz est composé d'au moins une question
	        if (containsLeastOneQuestion(readerVerif)) {
	        	parse(new StringReader(contentFich));
	        } else {
	        	throw new GiftReaderException("No questions in the quizz.");
	        }
		} catch (Exception e){
			System.out.println(e.toString());
		}  	
    }
    
    public void parse(Reader reader) throws IOException, GiftReaderException {
    	// lancement du quizz
    	quizContentHandler.onStartQuiz();
    	
        int currentChar;
        char leftBracketCharacter = '{';
    	char rightBracketCharacter = '}';
    	String questionADecouper = "";
    	String blockAnswer = "";
    	
        while ((currentChar= reader.read()) != -1) {
        	System.out.println("debDUneBoucle");

        	// recupere la question
        	questionADecouper = betweenTwoChar(reader, currentChar, leftBracketCharacter, rightBracketCharacter);
        	// début de la question
        	quizContentHandler.onStartQuestion();
        	// découpe la question
        	decoupeQuestion(questionADecouper);
        	
        	// supprime ligne \n
        	currentChar = reader.read();
        	
        	// recupere le bloc de réponse
        	blockAnswer = recupBlockAnswer(reader);
        	// début du block reponse
        	quizContentHandler.onStartAnswerBlock();
        	// découpe le block
        	decoupeBlockAnswer(blockAnswer);       	
        	// fin du block de réponse
        	quizContentHandler.onEndAnswerBlock();
        	
        	// fin de la question
        	quizContentHandler.onEndQuestion();
        	
        	System.out.println("finDUneBoucle");
        }
        
        //endQuiz();
        quizContentHandler.onEndQuiz();
    }
    
    /**
     * Récupère le contenu d'un fichier se situant entre deux caractères donnés
     * @param bf, le fichier à parser
     * @param start, le caractère de départ
     * @param finish, le caractère de fin
     * @return la string contenu entre les 2 caractères
     */
    public String betweenTwoChar(Reader reader, int currentChar, char start, char finish) throws GiftReaderQuestionWithInvalidFormatException, IOException {
    	String questionADecouper = "";
    	
    	if (currentChar != '{') {
			// on cherche le premier caractère
			while(((currentChar = reader.read()) != -1) && (currentChar != start));
			if (currentChar == -1) {
				throw new GiftReaderQuestionWithInvalidFormatException();
			}
    	}
		// quand on l'a trouvé, on concatène la chaine résultat
		while(((currentChar = reader.read()) != -1) && (currentChar != finish)) {
    		questionADecouper += (char) currentChar;
    		
    	} 
		if (currentChar == -1) {			
			throw new GiftReaderQuestionWithInvalidFormatException();
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
    
    public String recupBlockAnswer(Reader reader) throws GiftReaderQuestionWithInvalidFormatException, IOException {
    	boolean ok = false;
    	char lastChar = ' ';
    	String blockAnswer = "";
    	int currentChar;
    	while(((currentChar = reader.read()) != -1) && (currentChar != '\n' || lastChar != '\n'))  {
    		if(currentChar == '\n') {
    			lastChar = '\n';
    		} else {
    			lastChar = ' ';
    		}
    		
    		blockAnswer += (char)currentChar;
    	}
    	//if (currentChar == -1) {
			//throw new GiftReaderQuestionWithInvalidFormatException();
		//}
    	return blockAnswer;
    }
    
    public void decoupeBlockAnswer(String blockAnswer) {
    	String[] decoupe = blockAnswer.split("\n");
    	for (int i = 0; i < decoupe.length; i++) {
    		quizContentHandler.onStartAnswer(decoupe[i].charAt(0), decoupe[i].substring(2,decoupe[i].length()-1));
    		quizContentHandler.onEndAnswer();
    	}
    }
    
    public boolean containsLeastOneQuestion(Reader reader) throws IOException {
    	boolean trouve = false;
    	int currentChar;
    	while((currentChar = reader.read()) != -1)  {
    		if (currentChar == '{') {
    			trouve = true;
    		}
    	}
    	return trouve;
    }

    public QuizContentHandler getQuizContentHandler() {
        return quizContentHandler;
    }

    public void setQuizContentHandler(QuizContentHandler quizContentHandler) {
        this.quizContentHandler = quizContentHandler;
    }

}
