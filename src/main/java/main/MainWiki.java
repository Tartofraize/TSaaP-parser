package main;

import java.io.IOException;

import wiki.WikiQuestionService;
import wiki.WikiReaderException;



public class MainWiki {

	public static void main(String[] args) {
        
		WikiQuestionService me = new WikiQuestionService();
		
		try {
			me.getQuizFromWikiText("ressources/TestQuizz.txt");
			
		} catch (WikiReaderException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
	}

}
