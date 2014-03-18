package main;

import gift.GiftQuestionService;
import gift.GiftReaderException;

import java.io.IOException;

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
		// TODO Stub de la méthode généré automatiquement

		GiftQuestionService me = new GiftQuestionService();
		
		try {
			Quiz testQuestion = me.getQuizFromGiftText("::Grant's tomb ::Qui repose dans la Grant's tomb à New-York? {=Grant~Personne#C'était vrai pendant 12 ans, mais la dépouille de Grant a été enterrée dans cette tombe en 1897.~Napoléon#Il a été enterré en France.~Churchill#Il a été enterré en Angleterre.~Mère Teresa#Elle a été enterrée en Inde.}");
			
			for (Question quest : testQuestion.getQuestionList()) {
				System.out.println("Titre Question : " + quest.getTitle());
				System.out.println("Question : " + quest.getTextBlockList().get(0).getText());
				System.out.println("Reponses : ");
				for (AnswerBlock txt : quest.getAnswerBlockList()) {
					for (Answer ans : txt.getAnswerList()) {
						System.out.println("- "+ans.getTextValue());
					}
				}
			}
			//me.getQuizFromGiftText("{Question\n|type=\"()\"}\n+ The correct answer.\n- Distractor.\n- Distractor.\n- Distractor.");
		} catch (GiftReaderException | IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
	}

}
