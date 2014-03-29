package wiki.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import questions.interfaces.QuestionType;
import wiki.WikiQuizContentHandler;

public class WikiQuizContentHandlerTest {

	private WikiQuizContentHandler wiki;
	
	@Before
	public void initialize() {
		wiki = new WikiQuizContentHandler();
	}

	@After
	public void clean() {
		wiki = null;
	}

	@Test
	public void testGetQuiz() {
		assertEquals(wiki.getQuiz(), null);		
	}

	@Test
	public void testOnStartQuiz() {
		assertEquals(wiki.getQuiz(), null);
		wiki.onStartQuiz();
		assertNotEquals(wiki.getQuiz(), null);
	}

	@Test
	public void testOnModifQuestionMultipleChoice() {
		final String nomDeLaQuestion = "Nom de la question";
		char typeDelaQuestion = '[';
		
		wiki.onStartQuestion();
		assertEquals(wiki.getCurrentQuestion().getQuestionType(), QuestionType.Undefined);
		
		wiki.onModifQuestion(nomDeLaQuestion, typeDelaQuestion);
		assertEquals(wiki.getCurrentQuestion().getQuestionType(), QuestionType.MultipleChoice);
	}
	
	@Test
	public void testOnModifQuestionExclusiveChoice() {
		final String nomDeLaQuestion = "Nom de la question";
		char typeDelaQuestion = '(';
		
		wiki.onStartQuestion();
		assertEquals(wiki.getCurrentQuestion().getQuestionType(), QuestionType.Undefined);

		wiki.onModifQuestion(nomDeLaQuestion, typeDelaQuestion);
		assertEquals(wiki.getCurrentQuestion().getQuestionType(), QuestionType.ExclusiveChoice);
	}
	
	@Test
	public void testOnModifQuestionUnhandledChoice() {
		final String nomDeLaQuestion = "Nom de la question";
		char typeDelaQuestion = 'ù';
		
		wiki.onStartQuestion();
		assertEquals(wiki.getCurrentQuestion().getQuestionType(), QuestionType.Undefined);

		wiki.onModifQuestion(nomDeLaQuestion, typeDelaQuestion);
		assertEquals(wiki.getCurrentQuestion().getQuestionType(), QuestionType.Undefined);
	}
}
