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

    /**
     * Fonction permettant à partir d'un chemin absolu de récupérer le fichier contenant le quizz
     * @param input, le fichier contenant le quizz
     */
    public void readFichier(String input) {
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
			String contentFich = doc.getDocumentElement().getTextContent();
			StringReader reader = new StringReader(contentFich);
			
			// on vérifie que le quizz est composé d'au moins une question
	        if (containsLeastOneQuestion(reader)) {
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

			if (!doc.getDocumentElement().getAttribute("display").equals("simple")) {
				throw new GiftReaderException("File don't respect the required form.");
			}
			
			// récupère le contenu
			String contentFich = doc.getDocumentElement().getTextContent();
			StringReader reader = new StringReader(contentFich);
			
			// on vérifie que le quizz est composé d'au moins une question
	        if (containsLeastOneQuestion(reader)) {
	        	parse(new StringReader(contentFich));
	        } else {
	        	throw new GiftReaderException("No questions in the quizz.");
	        }
		} catch (Exception e){
			System.out.println(e.toString());
		}  	
    }
    
    /**
     * Fonction permettant de parser le contenu du fichier 
     * @param reader, le contenu du fichier à parser
     * @throws GiftReaderException
     * @throws IOException
     */
    public void parse(Reader reader) throws IOException, GiftReaderException {
    	// lancement du quizz
    	quizContentHandler.onStartQuiz();
    	
        int currentChar;
        char leftBracketCharacter = '{';
    	char rightBracketCharacter = '}';
    	String questionADecouper = "";
    	String blockAnswer = "";
    	char typeDelaQuestion;
        while ((currentChar= reader.read()) != -1) {
        	System.out.println("debDUneBoucle");

        	// recupere la question
        	questionADecouper = betweenTwoChar(reader, currentChar, leftBracketCharacter, rightBracketCharacter);
        	// début de la question
        	quizContentHandler.onStartQuestion();
        	// découpe la question
        	typeDelaQuestion = decoupeQuestion(questionADecouper);
        	
        	// supprime ligne \n
        	currentChar = reader.read();
        	
        	// recupere le bloc de réponse
        	blockAnswer = recupBlockAnswer(reader);
        	// vérifie si les réponses correspondent au type de la question
        	verifQuestion(blockAnswer, typeDelaQuestion);
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
        
        quizContentHandler.onEndQuiz();
    }
    
    /**
     * Récupère le contenu d'un fichier se situant entre deux caractères donnés
     * @param reader, le fichier à parser
     * @param currentChar, le caractère courant
     * @param start, le caractère de départ
     * @param finish, le caractère de fin
     * @return la string contenu entre les 2 caractères
     * @throws GiftReaderQuestionWithInvalidFormatException
     * @throws IOException
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
    public char decoupeQuestion(String questionADecouper) {  	
    	questionADecouper = questionADecouper.replaceAll("\n", " ");
    	String[] decoupe = questionADecouper.split("\\|");
    	String nomDeLaQuestion = decoupe[0];    	
    	char typeDelaQuestion = decoupe[1].charAt(6);
    	quizContentHandler.onModifQuestion(nomDeLaQuestion, typeDelaQuestion);
    	return typeDelaQuestion;
    }
    
    public void verifQuestion(String blockAnswer, char typeDelaQuestion) throws GiftReaderException {
    	int nbPlus = 0;
    	String[] decoupe = blockAnswer.split("\n");
    	for (int i = 0; i < decoupe.length; i++) {
    		if (decoupe[i].charAt(0) == '+') {
    			nbPlus++;
    		}
    	}
    	if (typeDelaQuestion == '[' && nbPlus < 2) {
        	throw new GiftReaderException("Le nombre de reponses possibles pour un questionnaire à choix multiples ne correspondent pas.");
        } else if (typeDelaQuestion == '(' && nbPlus != 1) {
        	throw new GiftReaderException("Le nombre de reponses possibles pour un questionnaire à choix exclusif ne correspondent pas.");
        }
    }
    
    /**
     * Fonction permettant de récupèrer le block de réponse du quizz
     * @param reader, le contenu du fichier où sont stockés les réponses
     * @return la string contenant l'ensemble des réponses
     * @throws GiftReaderQuestionWithInvalidFormatException
     * @throws IOException
     */
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
    
    /**
     * Fonction permmettant de découper le block de réponse 
     * @param blockAnswer, le block contenant l'ensemble des réponses
     */
    public void decoupeBlockAnswer(String blockAnswer) {
    	String[] decoupe = blockAnswer.split("\n");
    	String comment = null;
    	for (int i = 0; i < decoupe.length; i++) {
    		// c'est une réponse
    		if (decoupe[i].charAt(0) == '+' || decoupe[i].charAt(0) == '-') {
    			if (i > 0) { // pour parer la première question
    				quizContentHandler.onModifAnswer(comment);
    				quizContentHandler.onEndAnswer();
    			}
    			quizContentHandler.onStartAnswer(decoupe[i].charAt(0), decoupe[i].substring(2,decoupe[i].length()-1));
    			comment = null;
    		} else if (decoupe[i].charAt(0) == '|' && decoupe[i].charAt(1) == '|') { // c'est un commentaire
    			comment = decoupe[i].substring(3,decoupe[i].length()-1);
    		} else { // suite d'un commentaire
    			comment += " " + decoupe[i].substring(0,decoupe[i].length()-1);
    		}
    	}
    	// ajoute la dernière question
    	quizContentHandler.onModifAnswer(comment);
    	quizContentHandler.onEndAnswer();
    }
    
    
    
    /**
     * Fonction permettant de déterminer si le quizz contient au mois une question
     * @param reader, le cotnenu du fichier contenant le quizz
     * @return <p><li>true si le quizz contient au moins une réponse</li>
     * 			  <li>false sinn</li></p>
     * @throws IOException
     */
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
