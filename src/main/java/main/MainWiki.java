package main;

import java.io.File;

import gift.GiftQuizContentHandler;
import gift.WikiReader;



public class MainWiki {

	public static void main(String[] args) {
		String filePath = new File("ressources/TestQuizz.txt").getAbsolutePath();
		
		GiftQuizContentHandler handler = new GiftQuizContentHandler();
        WikiReader quizReader = new WikiReader();
        quizReader.setQuizContentHandler(handler);
        quizReader.readFileFromString(filePath);
	}
}
