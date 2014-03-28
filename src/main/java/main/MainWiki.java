package main;

import java.io.IOException;

import wiki.WikiQuestionService;
import wiki.WikiReaderException;



public class MainWiki {

	
	/**
	 * 
	 * @param args Argument executable
	 * @throws IOException Input/Output exeption
	 */
	
	public static void main(String[] args) throws IOException {

        
		WikiQuestionService me = new WikiQuestionService();
		
		try {
			me.getQuizFromWikiText("ressources/TestQuizz.txt");
			
		} catch (WikiReaderException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
	}

}
