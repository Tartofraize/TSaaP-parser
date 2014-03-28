package wiki.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import quizz.interfaces.QuizContentHandler;
import wiki.WikiQuizContentHandler;
import wiki.WikiReader;
import wiki.WikiReaderException;
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
	public void testGetQuestionFromQuizz() throws WikiReaderQuestionWithInvalidFormatException, IOException {
		StringReader reader = new StringReader("{QuestionTest}blabla");
		int currentChar = 0;
		char start = '{';
		char end = '}';
		String question;
		
		question = wikiReader.getQuestionFromQuizz(reader, currentChar, start, end);
		assertTrue(question.equals("QuestionTest"));

	}
	
	@Test(expected=WikiReaderQuestionWithInvalidFormatException.class)
	public void testGetQuestionFromQuizzInvalidFormat() throws WikiReaderQuestionWithInvalidFormatException, IOException {
		StringReader reader = new StringReader("QuestionTest}blabla");
		int currentChar = 0;
		char start = '{';
		char end = '}';
		String question;
		
		question = wikiReader.getQuestionFromQuizz(reader, currentChar, start, end);
		assertTrue(question.equals("QuestionTest"));
	}
	
	@Test
	public void testGetQuestionName() {
		String questionToSplit = "QuestionName|QuestionType=\"[]\"";
		String result = new String();
		result = wikiReader.getQuestionName(questionToSplit);
		
		assertTrue(result.equals("QuestionName"));
	}

	@Test
	public void testGetQuestionTypeMulti() {
		String questionToSplit = "QuestionName|QuestionType=\"[]\"";
		char result = 0;
		result = wikiReader.getQuestionType(questionToSplit);
		
		assertTrue(result == '[');
	}
	
	@Test
	public void testGetQuestionTypeExclu() {
		String questionToSplit = "QuestionName|QuestionType=\"()\"";
		char result = 0;
		result = wikiReader.getQuestionType(questionToSplit);
		
		assertTrue(result == '(');
	}
	
	@Test
	public void testCheckNumberOfAnwsers() throws WikiReaderException{
		String blockAnswer = "+ reponse 1.|| com 1 sur 2 lignes\n- reponse 2.\n+ reponse 3.|| commentaires\n- reponse 4.|| com 333333";
		char questionType = '[';
		
		wikiReader.checkNumberOfAnwsers(blockAnswer, questionType);
	}

	@Test(expected=WikiReaderException.class)
	public void testCheckNumberOfAnwsersMultiChoice() throws WikiReaderException{
		String blockAnswer = "+ reponse 1.|| com 1 sur 2 lignes\n- reponse 2.\n+ reponse 3.|| commentaires\n- reponse 4.|| com 333333";
		char questionType = '(';
		
		wikiReader.checkNumberOfAnwsers(blockAnswer, questionType);
	}
	
	@Test(expected=WikiReaderException.class)
	public void testCheckNumberOfAnwsersExclusifChoice() throws WikiReaderException{
		String blockAnswer = "+ reponse 1.|| com 1 sur 2 lignes\n- reponse 2.\n- reponse 3.|| commentaires\n- reponse 4.|| com 333333";
		char questionType = '[';
		
		wikiReader.checkNumberOfAnwsers(blockAnswer, questionType);
	}

	@Test
	public void testGetBlockAnswer() throws WikiReaderQuestionWithInvalidFormatException, IOException {
		StringReader reader = new StringReader("+ Correct answer.\n- Incorrect answer.\n");
		String blockAnswer;
		
		blockAnswer = wikiReader.getBlockAnswer(reader);
		assertTrue(blockAnswer.equals("+ Correct answer.\n- Incorrect answer.\n"));
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
		wikiReader.setQuizContentHandler(new WikiQuizContentHandler());
		
		assertTrue(wikiReader.getQuizContentHandler() != null);
	}

}
