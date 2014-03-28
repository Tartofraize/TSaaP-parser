package wiki.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	public void testOnEndQuiz() {
		wiki.onEndQuiz();
	}

	@Test
	public void testOnStartQuestion() {
		assertEquals(wiki.getCurrentQuestion(), null);
		wiki.onStartQuestion();
		assertNotEquals(wiki.getCurrentQuestion(), null);
	}

	@Test
	public void testOnModifQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnEndQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnStartAnswerBlock() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnEndAnswerBlock() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testOnStartAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnModifAnswer() {
		assertEquals(wiki.getCurrentAnswer(), null);

		wiki.onModifAnswer("Commentaire ajouté");
		assertEquals(wiki.getCurrentAnswer(), "Commentaire ajouté");
		assertNotEquals(wiki.getCurrentAnswer(), "Pas de commentaire");
	}

	@Test
	public void testOnEndAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCurrentQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentAnswerBlock() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCurrentAnswerBlock() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCurrentAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAnswerCounter() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAnswerCounter() {
		fail("Not yet implemented");
	}    

}
