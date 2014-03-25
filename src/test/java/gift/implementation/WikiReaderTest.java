package gift.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import quizz.interfaces.QuizContentHandler;
import wiki.WikiReader;
import wiki.WikiReaderQuestionWithInvalidFormatException;

public class WikiReaderTest {
	WikiReader wikiReader = new WikiReader();
	QuizContentHandler quizContentHandler;

	@Test
	public void testReadFileString() {
		
		fail("Not yet implemented");
	}

	@Test
	public void testReadFileURL() {
		fail("Not yet implemented");
	}

	@Test
	public void testParse() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuestionFromQuizz() {
//		WikiReader wikiReader = new WikiReader();
		StringReader reader = new StringReader("{QuestionTest}blabla");
		int currentChar = 0;
		char start = '{';
		char end = '}';
		String question;
		
		try {
			question = wikiReader.getQuestionFromQuizz(reader, currentChar, start, end);
			assertTrue(question.equals("QuestionTest"));
		} catch (WikiReaderQuestionWithInvalidFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur testGetQuestionFromQuizz : " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur testGetQuestionFromQuizz : " + e.getMessage());
		}
	}

	@Test
	public void testSplitQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBlockAnswer() {
		StringReader reader = new StringReader("+ Correct answer.\n- Incorrect answer.\n");
		String blockAnswer;
		
		try {
			blockAnswer = wikiReader.getBlockAnswer(reader);
			assertTrue(blockAnswer.equals("+ Correct answer.\n- Incorrect answer.\n"));
		} catch (WikiReaderQuestionWithInvalidFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur testGetBlockAnswer : " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur testGetBlockAnswer : " + e.getMessage());
		}
	}

	@Test
	public void testSplitBlockAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuizContentHandler() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetQuizContentHandler() {
		fail("Not yet implemented");
	}

}
