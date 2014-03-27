package wiki;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

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
     * @param input The file with quiz
     */
    public void readFile(String input) {
		try {
			File fXmlFile = new File(input);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			if (!doc.getDocumentElement().getAttribute("display").equals("simple")) {
				throw new WikiReaderException("File don't respect the required form.");
			}
			
			String fileContent = doc.getDocumentElement().getTextContent();
			
			if (containsLeastOneQuestion(fileContent)) {
				parse(new StringReader(fileContent));
	        } else {
	        	throw new WikiReaderException("No questions in the quizz.");
	        }	
		} catch (Exception e) {
			System.out.println(e.toString());
		}  	
    }
    
    /**
     * Parse the file content
     * @param reader the file content to parse
     * @throws WikiReaderException 
     * @throws IOException 
     */
    public void parse(Reader reader) throws WikiReaderException {
    	quizContentHandler.onStartQuiz();
    	
        int currentChar;
        char leftBracketCharacter = '{';
    	char rightBracketCharacter = '}';
    	String questionToSplit = "";
    	String blockAnswer = "";
    	char questionType;
    	String questionName;
    	
        try {
			while ((currentChar = reader.read()) != -1) {
				System.out.println("debDUneBoucle");

				questionToSplit = getQuestionFromQuizz(reader, currentChar, leftBracketCharacter, rightBracketCharacter);
				
				quizContentHandler.onStartQuestion();
				       	
				questionName = getQuestionName(questionToSplit);
				questionType = getQuestionType(questionToSplit);
				
				quizContentHandler.onModifQuestion(questionName, questionType);
				
				currentChar = reader.read();
				
				blockAnswer = getBlockAnswer(reader);
				
				checkNumberOfAnwsers(blockAnswer, questionType);
				
				quizContentHandler.onStartAnswerBlock();
				
				splitBlockAnswer(blockAnswer);       	
				
				quizContentHandler.onEndAnswerBlock();
				
				quizContentHandler.onEndQuestion();
				
				System.out.println("finDUneBoucle");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        quizContentHandler.onEndQuiz();
        System.out.println("\n--------Methode du graphe----------\n");
        quizContentHandler.getQuiz().getGraphe();
    }
    
    /**
     * Get the question of the quiz
     * @param reader 		File to parse
     * @param currentChar	Current character
     * @param start 		Start character
     * @param finish 		End character
     * @return the question of the quiz
     * @throws WikiReaderQuestionWithInvalidFormatException
     * @throws IOException
     */
    public String getQuestionFromQuizz(Reader reader, int currentChar, char start, char finish) throws WikiReaderQuestionWithInvalidFormatException {
    	String questionToSplit = "";
    	
    	try {
			if (currentChar != '{') {
				while (((currentChar = reader.read()) != -1) && (currentChar != start));
				if (currentChar == -1) {
					throw new WikiReaderQuestionWithInvalidFormatException();
				}
			}
			while (((currentChar = reader.read()) != -1) && (currentChar != finish)) {
				questionToSplit += (char) currentChar;
				
			} 
//			if (currentChar == -1) {			
//				throw new WikiReaderQuestionWithInvalidFormatException();
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return questionToSplit;
    }
    
    /**
     * Split a string to get the name and the type of the question
     * @param questionToSplit The string to split
     * @return the type of the question
     */
    public String getQuestionName(String questionToSplit) {  	   	
    	questionToSplit = questionToSplit.replaceAll("\n", " ");
    	String[] result = questionToSplit.split("\\|");    	    	
    	return result[0];
    }
    
    /**
     * Split a string to get the name and the type of the question
     * @param questionToSplit The string to split
     * @return the type of the question
     */
    public char getQuestionType(String questionToSplit) {  	   	
    	questionToSplit = questionToSplit.replaceAll("\n", " ");
    	String[] result = questionToSplit.split("\\|");
    	
    	if (result[1].contains("[")) {
			return '[';
    	}
    	
		return '(';
    }
    
    
    /**
     * Checks whether the type of question is consistent with the number of responses
     * @param blockAnswer The set of responses
     * @param questionType The type of the question
     */
    public void checkNumberOfAnwsers(String blockAnswer, char questionType) throws WikiReaderException {
    	int nbPlus = 0;
    	String[] result = blockAnswer.split("\n");
    	for (int i = 0; i < result.length; i++) {
    		if (result[i].charAt(0) == '+') {
    			nbPlus++;
    		}
    	}
    	if (questionType == '[' && nbPlus < 2) {
        	throw new WikiReaderException("The number of potential responses to a multiple choice quiz don't match.");
        } else if (questionType == '(' && nbPlus != 1) {
        	throw new WikiReaderException("The number of potential responses to a exclusif choice quiz don't match.");
        }
    }
    
    /**
     * Get the answer of the quiz
     * @param reader	File to parse
     * @return the answer of the quiz
     * @throws WikiReaderQuestionWithInvalidFormatException
     * @throws IOException
     */
    public String getBlockAnswer(Reader reader) {
    	char lastChar = ' ';
    	StringBuilder blockAnswer = new StringBuilder();
    	
    	int currentChar;
    	try {
			while (((currentChar = reader.read()) != -1) && (currentChar != '\n' || lastChar != '\n'))  {
				if (currentChar == '\n') {
					lastChar = '\n';
				} else {
					lastChar = ' ';
				}
				
				blockAnswer.append((char) currentChar);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return blockAnswer.toString();
    }
    
    /**
     * Get all the answers of the quiz
     * @param blockAnswer contains all the answers to split
     */
    public void splitBlockAnswer(String blockAnswer) {
    	String[] result = blockAnswer.split("\n");
    	String comment = null;
    	
    	for (int i = 0; i < result.length; i++) {
    		if (result[i].contains("+") || result[i].contains("-")) {
    			if (i > 0) {
    				quizContentHandler.onModifAnswer(comment);
    				quizContentHandler.onEndAnswer();
    			}
    			quizContentHandler.onStartAnswer(result[i].charAt(0), result[i].substring(2, result[i].length() - 1));
    			comment = null;
    		} else if (result[i].contains("||")) { 
    			comment = result[i].substring(3, result[i].length() - 1);
    		} else {
    			comment.concat(" " + result[i].substring(0, result[i].length() - 1));
    		}
    	}
    	
    	quizContentHandler.onModifAnswer(comment);
    	quizContentHandler.onEndAnswer();
    }
        
    /**
     * Determines whether the quiz contains at least one question
     * @param fileContent the quiz file
     * @return
     */
    public boolean containsLeastOneQuestion(String fileContent) {
    	return fileContent.contains("{");
    }

	/**
	 * Set the QuizContentHandler
	 * @param quizContentHandler
	 */
    public void setQuizContentHandler(QuizContentHandler quizContentHandler) {
        this.quizContentHandler = quizContentHandler;
    }    
    
    public QuizContentHandler getQuizContentHandler() {
    	return this.quizContentHandler;
    }

}
