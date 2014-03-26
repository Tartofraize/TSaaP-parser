package wiki.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import wiki.WikiReader;
import wiki.WikiReaderQuestionWithInvalidFormatException;

public class WikiReaderTest {
	WikiReader wikiReader = new WikiReader();

	@Test
	public void testReadFile() {
		fail("Pas encore implémenté");
	}

	@Test
	public void testParse() {
		fail("Pas encore implémenté");
	}

	@Test
	public void testGetQuestionFromQuizz() {
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
	public void testGetQuestionName() {
		String questionToSplit = "QuestionName|QuestionType=\"[]\"";
		String result = new String();
		result = wikiReader.getQuestionName(questionToSplit);
		
		assertTrue(result.equals("QuestionName"));
	}

	@Test
	public void testGetQuestionType() {
		String questionToSplit = "QuestionName|QuestionType=\"[]\"";
		char result = 0;
		result = wikiReader.getQuestionType(questionToSplit);
		
		assertTrue(result == '[');
	}

	@Test
	public void testCheckNumberOfAnwsers() {
		fail("Pas encore implémenté");
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
		fail("Pas encore implémenté");
	}

	@Test
	public void testContainsLeastOneQuestion() {
		String quiz = new String("{QuestionTest}QuestionName|QuestionType=\"[]\"");
		
		assertTrue(wikiReader.containsLeastOneQuestion(quiz));
	}

	@Test
	public void testSetQuizContentHandler() {
		fail("Pas encore implémenté");
	}

}
