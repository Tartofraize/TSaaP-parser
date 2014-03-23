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

import quizz.interfaces.QuizContentHandler;
import quizz.interfaces.QuizReader;

/**
 * @author franck Silvestre
 */
public class WikiReader implements QuizReader {

    private QuizContentHandler quizContentHandler;

    /**
     * Recovers the quiz file from an absolute path
     * @param input, the file with quizz
     */
    public void readFileFromString(String input) {
		try {
			File fXmlFile = new File(input);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			if (!doc.getDocumentElement().getAttribute("display").equals("simple")) {
				throw new GiftReaderException("File don't respect the required form.");
			}
			
			// récupère le contenu
			String fileContent = doc.getDocumentElement().getTextContent();
			StringReader reader = new StringReader(fileContent);
			
			// on vérifie que le quizz est composé d'au moins une question
	        if (containsLeastOneQuestion(reader)) {
	        	parse(new StringReader(fileContent));
	        } else {
	        	throw new GiftReaderException("No questions in the quizz.");
	        }		
		} catch (Exception e){
			System.out.println(e.toString());
		}  	
    }
    
    
    public void readFileURL(URL input) {
    	// lecture du fichier contenant le quizz	
		try {
			File fXmlFile = new File(input.getFile());
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			if (!doc.getDocumentElement().getAttribute("display").equals("simple")) {
				throw new GiftReaderException("File don't respect the required form.");
			}
			
			// récupère le contenu
			String fileContent = doc.getDocumentElement().getTextContent();
			StringReader reader = new StringReader(fileContent);
			
			// on vérifie que le quizz est composé d'au moins une question
	        if (containsLeastOneQuestion(reader)) {
	        	parse(new StringReader(fileContent));
	        } else {
	        	throw new GiftReaderException("No questions in the quizz.");
	        }
		} catch (Exception e){
			System.out.println(e.toString());
		}  	
    }
    
    /**
     * Parse the file content
     * @param reader, the file content to parse
     * @throws GiftReaderException
     * @throws IOException
     */
    public void parse(Reader reader) throws IOException, GiftReaderException {
    	// lancement du quizz
    	quizContentHandler.onStartQuiz();
    	
        int currentChar;
        char leftBracketCharacter = '{';
    	char rightBracketCharacter = '}';
    	String questionToSplit = "";
    	String blockAnswer = "";
    	char questionType;
    	
        while ((currentChar= reader.read()) != -1) {
        	System.out.println("debDUneBoucle");

        	// recupere la question
        	questionToSplit = getQuestionFromQuizz(reader, currentChar, leftBracketCharacter, rightBracketCharacter);
        	// début de la question
        	quizContentHandler.onStartQuestion();
        	// découpe la question
        	questionType = splitQuestion(questionToSplit);
        	
        	// supprime ligne \n
        	currentChar = reader.read();
        	
        	// recupere le bloc de réponse
        	blockAnswer = getBlockAnswer(reader);
        	// vérifie si les réponses correspondent au type de la question
        	checkQuestion(blockAnswer, questionType);
        	// début du block reponse
        	quizContentHandler.onStartAnswerBlock();
        	// découpe le block
        	splitBlockAnswer(blockAnswer);       	
        	// fin du block de réponse
        	quizContentHandler.onEndAnswerBlock();
        	
        	// fin de la question
        	quizContentHandler.onEndQuestion();
        	
        	System.out.println("finDUneBoucle");
        }
        
        quizContentHandler.onEndQuiz();
        System.out.println("\n--------Methode du graphe----------\n");
        quizContentHandler.getQuizz().getGraphe();
    }
    
    /**
     * Get the question of the quiz
     * @param reader 		File to parse
     * @param currentChar	Current character
     * @param start 		Start character
     * @param finish 		End character
     * @return the question of the quiz
     * @throws GiftReaderQuestionWithInvalidFormatException
     * @throws IOException
     */
    public String getQuestionFromQuizz(Reader reader, int currentChar, char start, char finish) throws GiftReaderQuestionWithInvalidFormatException, IOException {
    	String questionToSplit = "";
    	
    	if (currentChar != '{') {
			// on cherche le premier caractère
			while(((currentChar = reader.read()) != -1) && (currentChar != start));
			if (currentChar == -1) {
				throw new GiftReaderQuestionWithInvalidFormatException();
			}
    	}
		// quand on l'a trouvé, on concatène la chaine résultat
		while(((currentChar = reader.read()) != -1) && (currentChar != finish)) {
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
     * @return the type of the question
     */
    public char splitQuestion(String questionToSplit) {  	
    	questionToSplit = questionToSplit.replaceAll("\n", " ");
    	String[] result = questionToSplit.split("\\|");
    	String questionName = result[0];    	
    	char questionType = result[1].charAt(6);
    	quizContentHandler.onModifQuestion(questionName, questionType);
    	return questionType;
    }
    
    /**
     * Checks whether the type of question is consistent with the responses
     * @param blockAnswer The set of reponses
     * @param questionType The type of the question
     */
    public void checkQuestion(String blockAnswer, char questionType) throws GiftReaderException {
    	int nbPlus = 0;
    	String[] result = blockAnswer.split("\n");
    	for (int i = 0; i < result.length; i++) {
    		if (result[i].charAt(0) == '+') {
    			nbPlus++;
    		}
    	}
    	if (questionType == '[' && nbPlus < 2) {
        	throw new GiftReaderException("The number of potential responses to a multiple choice quiz don't match.");
        } else if (questionType == '(' && nbPlus != 1) {
        	throw new GiftReaderException("The number of potential responses to a exclusif choice quiz don't match.");
        }
    }
    
    /**
     * Get the answer of the quiz
     * @param reader	File to parse
     * @return the answer of the quiz
     * @throws GiftReaderQuestionWithInvalidFormatException
     * @throws IOException
     */
    public String getBlockAnswer(Reader reader) throws GiftReaderQuestionWithInvalidFormatException, IOException {
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
    
    /**
     * Get all the answers of the quiz
     * @param blockAnswer
     */
    public void splitBlockAnswer(String blockAnswer) {
    	String[] result = blockAnswer.split("\n");
    	String comment = null;
    	for (int i = 0; i < result.length; i++) {
    		// c'est une réponse
    		if (result[i].charAt(0) == '+' || result[i].charAt(0) == '-') {
    			if (i > 0) { // pour parer la première question
    				quizContentHandler.onModifAnswer(comment);
    				quizContentHandler.onEndAnswer();
    			}
    			quizContentHandler.onStartAnswer(result[i].charAt(0), result[i].substring(2,result[i].length()-1));
    			comment = null;
    		} else if (result[i].charAt(0) == '|' && result[i].charAt(1) == '|') { // c'est un commentaire
    			comment = result[i].substring(3,result[i].length()-1);
    		} else { // suite d'un commentaire
    			comment += " " + result[i].substring(0,result[i].length()-1);
    		}
    	}
    	// ajoute la dernière question
    	quizContentHandler.onModifAnswer(comment);
    	quizContentHandler.onEndAnswer();
    }
    
    
    
    /**
     * Determines whether the quiz contains at least one question
     * @param reader, the quiz file
     * @return <p><li>true if quizz contains at least one question</li>
     * 			  <li>false otherwise</li></p>
     * @throws IOException
     */
    public boolean containsLeastOneQuestion(Reader reader) throws IOException {
    	boolean find = false;
    	int currentChar;
    	while((currentChar = reader.read()) != -1)  {
    		if (currentChar == '{') {
    			find = true;
    		}
    	}
    	return find;
    }

    public QuizContentHandler getQuizContentHandler() {
        return quizContentHandler;
    }

    public void setQuizContentHandler(QuizContentHandler quizContentHandler) {
        this.quizContentHandler = quizContentHandler;
    }    

}
