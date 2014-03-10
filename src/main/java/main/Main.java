package main;

import java.io.IOException;

import org.tsaap.questions.impl.gift.GiftQuestionService;
import org.tsaap.questions.impl.gift.GiftReaderException;

public class Main {

	public static void main(String[] args) {
		// TODO Stub de la méthode généré automatiquement

		GiftQuestionService me = new GiftQuestionService();
		
		try {
			me.getQuizFromGiftText("toto");
		} catch (GiftReaderException | IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		
	}

}
