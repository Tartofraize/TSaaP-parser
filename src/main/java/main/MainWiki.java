package main;

import gift.GiftQuestionService;
import gift.GiftQuizContentHandler;
import gift.GiftReaderException;
import gift.WikiReader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import questions.implementation.QuestionImpl;
import questions.interfaces.Question;
import questions.interfaces.QuestionBlock;
import questions.interfaces.TextBlock;
import quizz.implementation.QuizImpl;
import quizz.interfaces.Quiz;
import reponses.interfaces.Answer;
import reponses.interfaces.AnswerBlock;



public class MainWiki {

	public static void main(String[] args) {
		GiftQuizContentHandler handler = new GiftQuizContentHandler();
        WikiReader quizReader = new WikiReader();
        quizReader.setQuizContentHandler(handler);
        quizReader.readFichier("C:\\Users\\user\\git\\TSaaP-parser\\src\\main\\java\\main\\TestQuizz.txt");
//		GiftQuestionService me = new GiftQuestionService();
//		
//		try {
//			Quiz testQuestion = me.getQuizFromWikiText("C:\\Users\\user\\git\\TSaaP-parser\\src\\main\\java\\main\\TestQuizz.txt");
//			
//			for (Question quest : testQuestion.getQuestionList()) {
//				System.out.println("Titre Question : " + quest.getTitle());
//				System.out.println("Question : " + quest.getTextBlockList().get(0).getText());
//				System.out.println("Reponses : ");
//				for (AnswerBlock txt : quest.getAnswerBlockList()) {
//					for (Answer ans : txt.getAnswerList()) {
//						System.out.println("- "+ans.getTextValue());
//					}
//				}
//			}
//			//me.getQuizFromGiftText("{Question\n|type=\"()\"}\n+ The correct answer.\n- Distractor.\n- Distractor.\n- Distractor.");
//		} catch (GiftReaderException | IOException e) {
//			// TODO Bloc catch généré automatiquement
//			e.printStackTrace();
//		}
	}

}
