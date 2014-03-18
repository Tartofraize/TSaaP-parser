package main;

import gift.GiftQuestionService;
import gift.GiftReaderException;

import java.io.IOException;

import questions.implementation.QuestionImpl;
import questions.interfaces.Question;
import questions.interfaces.QuestionBlock;
import questions.interfaces.TextBlock;
import quizz.implementation.QuizImpl;
import reponses.interfaces.Answer;
import reponses.interfaces.AnswerBlock;



public class Main {

	public static void main(String[] args) {
		// TODO Stub de la méthode généré automatiquement

		GiftQuestionService me = new GiftQuestionService();
		
		try {
			QuestionImpl test = (QuestionImpl) me.getQuestionFromGiftText("::Grant's tomb ::Qui repose dans la Grant's tomb à New-York? {=Grant~Personne#C'était vrai pendant 12 ans, mais la dépouille de Grant a été enterrée dans cette tombe en 1897.~Napoléon#Il a été enterré en France.~Churchill#Il a été enterré en Angleterre.~Mère Teresa#Elle a été enterrée en Inde.}");
			
			System.out.println(test.getTitle());
			System.out.println(test.getTextBlockList().get(0).getText());
			
			for (AnswerBlock txt : test.getAnswerBlockList()) {
				for (Answer ans : txt.getAnswerList()) {
					System.out.println(ans.getTextValue());
				}
			}
			
			//me.getQuizFromGiftText("{Question\n|type=\"()\"}\n+ The correct answer.\n- Distractor.\n- Distractor.\n- Distractor.");
		} catch (GiftReaderException | IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
	}

}
