package quizz.implementation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import questions.implementation.QuestionImpl;
import quizz.interfaces.QuizReaderException;
import wiki.WikiQuizContentHandler;
import wiki.WikiReader;
import wiki.WikiReaderException;

public class QuizImplTest {

	private QuizImpl quizz;

	@Before
	public void setUp() throws Exception {
		quizz = new QuizImpl();
	}

	@After
	public void tearDown() throws Exception {
		quizz = null;
	}

	@Test
	public void testAddQuestion() {
		assertTrue(quizz.getQuestionList().isEmpty());
		
		quizz.addQuestion(new QuestionImpl());
		assertFalse(quizz.getQuestionList().isEmpty());
	}

	@Test
	public void testGetQuestionList() {
		assertTrue(quizz.getQuestionList().isEmpty());
		
		quizz.addQuestion(new QuestionImpl());
		assertFalse(quizz.getQuestionList().isEmpty());
	}

	@Test
	public void testGetGraphe() throws WikiReaderException, IOException {
		quizz.getGraphe();
		
		quizz.addQuestion(new QuestionImpl());
		quizz.getGraphe();
		
		WikiQuizContentHandler handler = new WikiQuizContentHandler();
        WikiReader reader = new WikiReader();
        reader.setQuizContentHandler(handler);
        // Method getGraphe() is called in parse()
        reader.parse(new StringReader("{Question"
					        		+ "\n|type=\"()\"}"
					        		+ "\n+ The correct answer."
					        		+ "\n- Distractor."
					        		+ "\n- Distractor."
					        		+ "\n- Distractor."));    	
	}
	
	@Test(expected=QuizReaderException.class)
	public void testCoverageQuizReaderException() throws QuizReaderException {
		throw new QuizReaderException("Error");
	}

}
