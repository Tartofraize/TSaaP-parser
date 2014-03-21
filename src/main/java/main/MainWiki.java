package main;

import gift.GiftQuizContentHandler;
import gift.WikiReader;



public class MainWiki {

	public static void main(String[] args) {
		GiftQuizContentHandler handler = new GiftQuizContentHandler();
        WikiReader quizReader = new WikiReader();
        quizReader.setQuizContentHandler(handler);
        quizReader.readFichier("/home/alex/workspace2/TSaaP-parser/ressources/TestQuizz.txt");
	}

}
