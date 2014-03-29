package wiki.implementation;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import wiki.WikiQuizContentHandler;
import wiki.WikiReader;
import wiki.WikiReaderException;
import wiki.WikiReaderQuestionWithInvalidFormatException;

public class WikiReaderTest {
	WikiReader wikiReader;
	WikiQuizContentHandler handler;

	@Before
	public void initialize() {
		handler = new WikiQuizContentHandler();
		wikiReader = new WikiReader();
		wikiReader.setQuizContentHandler(handler);
	}

	@After
	public void clean() {
		wikiReader = null;
	}
	
	@Test
	public void testReadFile() {
		wikiReader.readFile("ressources/TestQuizz.txt");
	}
	
	@Test
	public void testReadFileNotFoundException() {
		String fileName = "NotFound";
		wikiReader.readFile(fileName);
	}
	
	@Test
	public void testReadFileNoRespectForm() {
		wikiReader.readFile("ressources/testReadFileNoRespectForm.txt");
	}
	
	@Test
	public void testReadFileNoQuestions() {
		wikiReader.readFile("ressources/testReadFileNoQuestions.txt");
	}

	@Test
	public void testParse() throws WikiReaderException{
		StringReader reader = new StringReader("<quiz display=\"simple\">\n{Question\nsur plusieurs\nlignes\n|type=\"[]\"}\n"
				+ "+ reponse 1.|| com 1 \nsur 2 lignes\n- reponse 2.\n+ reponse 3.\n"
				+ "|| commentaires\n- reponse 4.\n|| com 333333\n</quiz>");
		wikiReader.parse(reader);
	}
	
	@Test
	public void testGetQuestionFromQuizz() throws WikiReaderQuestionWithInvalidFormatException {
		StringReader reader = new StringReader("{QuestionTest}blabla");
		int currentChar = 0;
		char start = '{';
		char end = '}';
		String question;
		
		question = wikiReader.getQuestionFromQuizz(reader, currentChar, start, end);
		assertTrue(question.equals("QuestionTest"));

	}
	
	@Test(expected=WikiReaderQuestionWithInvalidFormatException.class)
	public void testGetQuestionFromQuizzInvalidFormat() throws WikiReaderQuestionWithInvalidFormatException {
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
	public void testGetBlockAnswer() {
		StringReader reader = new StringReader("+ Correct answer.\n- Incorrect answer.\n");
		String blockAnswer;
		
		blockAnswer = wikiReader.getBlockAnswer(reader);
		assertTrue(blockAnswer.equals("+ Correct answer.\n- Incorrect answer.\n"));
	}
	
	@Test
	public void testSplitBlockAnswer() {
		handler.onStartAnswerBlock();
		String blockAnswer2 = "+ reponse 1.\n|| com 1 sur 2 lignes\n- reponse 2.\n+ reponse 3.\n|| commentaires\n";
		wikiReader.splitBlockAnswer(blockAnswer2);
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
