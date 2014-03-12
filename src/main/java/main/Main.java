package main;

import java.io.IOException;

import org.tsaap.questions.Answer;
import org.tsaap.questions.Question;
import org.tsaap.questions.Quiz;
import org.tsaap.questions.QuizContentHandler;
import org.tsaap.questions.TextBlock;
import org.tsaap.questions.impl.DefaultQuestion;
import org.tsaap.questions.impl.DefaultQuiz;
import org.tsaap.questions.impl.gift.GiftQuestionService;
import org.tsaap.questions.impl.gift.GiftQuizContentHandler;
import org.tsaap.questions.impl.gift.GiftReaderException;

public class Main {

	public static void main(String[] args) {
		// TODO Stub de la méthode généré automatiquement

		GiftQuestionService me = new GiftQuestionService();
		
		try {
			DefaultQuiz test = (DefaultQuiz) me.getQuizFromGiftText("::Grant's tomb::Qui repose dans la Grant's tomb à New-York? {=Grant~Personne#C'était vrai pendant 12 ans, mais la dépouille de Grant a été enterrée dans cette tombe en 1897.~Napoléon#Il a été enterré en France.~Churchill#Il a été enterré en Angleterre.~Mère Teresa#Elle a été enterrée en Inde.}");
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

}
