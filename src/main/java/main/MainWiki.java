package main;

import gift.GiftQuestionService;
import gift.GiftQuizContentHandler;
import gift.GiftReaderException;
import gift.WikiReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import questions.implementation.QuestionImpl;
import questions.interfaces.Question;
import questions.interfaces.QuestionBlock;
import questions.interfaces.TextBlock;
import quizz.implementation.QuizImpl;
import quizz.interfaces.Quiz;
import reponses.interfaces.Answer;
import reponses.interfaces.AnswerBlock;



public class MainWiki {

	public static void main(String[] args) throws IOException {
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		URL input = classLoader.getResource("main/TestQuizz.txt");
//		GiftQuizContentHandler handler = new GiftQuizContentHandler();
//        WikiReader quizReader = new WikiReader();
//        quizReader.setQuizContentHandler(handler);
//        quizReader.readFichier(input);
        
        
		GiftQuestionService me = new GiftQuestionService();
		
		try {
			Quiz testQuestion = me.getQuizFromWikiText("main/TestQuizz.txt");
			
			for (Question quest : testQuestion.getQuestionList()) {
				System.out.println("\nNom : " + quest.getTextBlockList().get(0).getText());
				System.out.println("Reponses : ");
				for (AnswerBlock txt : quest.getAnswerBlockList()) {
					for (Answer ans : txt.getAnswerList()) {
						System.out.println("- "+ans.getTextValue());
					}
				}
			}
			//me.getQuizFromGiftText("{Question\n|type=\"()\"}\n+ The correct answer.\n- Distractor.\n- Distractor.\n- Distractor.");
		} catch (GiftReaderException | IOException e) {
			System.out.println("erreur");
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
	}

}
