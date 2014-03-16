package org.tsaap.questions.impl;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.tsaap.questions.impl.gift.GiftQuestionService;
import org.tsaap.questions.impl.gift.GiftReaderException;

public class GiftReaderTest {

	@Test
	public void test() {

		GiftQuestionService me = new GiftQuestionService();

		try {
			me.getQuizFromGiftText("toto");
		} catch (GiftReaderException | IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		//fail("Not yet implemented");	
	}

}
