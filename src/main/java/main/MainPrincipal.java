package main;

import gift.GiftQuestionService;
import gift.GiftQuizContentHandler;
import gift.GiftReaderException;
import gift.WikiReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.PlatformManagedObject;
import java.net.URL;

import questions.interfaces.Question;
import questions.interfaces.TextBlock;
import quizz.implementation.QuizImpl;
import reponses.interfaces.Answer;



public class MainPrincipal {

	public static void main(String[] args) {
		// TODO Stub de la méthode généré automatiquement
		String quizz;
		String filePath = new File("ressources/TestQuizz.txt").getAbsolutePath();
		
		quizz = recuperationQuestion(filePath);

//		GiftQuizContentHandler handler = new GiftQuizContentHandler();
//		WikiReader quizReader = new WikiReader();
//		quizReader.setQuizContentHandler(handler);
//		quizReader.readFichier(filePath);

        
		
		
		
		

		GiftQuestionService me = new GiftQuestionService();
		try {
			QuizImpl test = (QuizImpl) me.getQuizFromGiftText(quizz);
			for (Question question : test.getQuestionList()) {
				System.out.println(question.getTitle());
				for (TextBlock txt : question.getTextBlockList()) {
					System.out.println(txt.getText());
				}
				for (Answer txt : question.getAnswerBlockList().get(0).getAnswerList()) {
					System.out.println(txt.getTextValue());
				}

			}
			
			//me.getQuizFromGiftText("{Question\n|type=\"()\"}\n+ The correct answer.\n- Distractor.\n- Distractor.\n- Distractor.");
		} catch (GiftReaderException | IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}		
	}

	private static String recuperationQuestion(String filePath) {
		String question = new String();
		
		try {
			BufferedReader buff = new BufferedReader(new FileReader(filePath));	
			
			try {
				String line;
				while ((line = buff.readLine()) != null) {
					question += line;
				}
			} finally {
				buff.close();
			}
		} catch (IOException ioe) {
			System.out.println("Erreur --" + ioe.toString());
		}
		return question;
	}

}
