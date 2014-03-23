package main;

import gift.GiftReaderException;
import gift.WikiQuestionService;

import java.io.IOException;

import questions.interfaces.Question;
import quizz.interfaces.Quiz;
import reponses.interfaces.Answer;
import reponses.interfaces.AnswerBlock;



public class MainWiki {

	public static void main(String[] args) throws IOException {
        
		WikiQuestionService me = new WikiQuestionService();
		
		try {
			Quiz testQuestion = me.getQuizFromWikiText("ressources/TestQuizz.txt");
			
		} catch (GiftReaderException | IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
	}

}
