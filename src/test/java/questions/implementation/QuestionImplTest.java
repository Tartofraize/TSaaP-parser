package questions.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import questions.interfaces.QuestionType;
import questions.interfaces.TextBlock;
import reponses.implementation.AnswerBlockImpl;

public class QuestionImplTest {

	private QuestionImpl question;

	@Before
	public void initialize() {
		question = new QuestionImpl();
	}

	@After
	public void clean() {
		question = null;
	}

	@Test
	public void testGetTitle() {
		assertEquals(question.getTitle(), null);

		question.setTitle("A title");
		assertEquals(question.getTitle(), "A title");
	}

	@Test
	public void testGetQuestionType() {
		assertEquals(question.getQuestionType(), QuestionType.Undefined);

		question.setQuestionType(QuestionType.ExclusiveChoice);
		assertEquals(question.getQuestionType(), QuestionType.ExclusiveChoice);
	}

	@Test
	public void testSetQuestionType() {
		assertEquals(question.getQuestionType(), QuestionType.Undefined);

		question.setQuestionType(QuestionType.ExclusiveChoice);
		assertEquals(question.getQuestionType(), QuestionType.ExclusiveChoice);

		assertNotEquals(question.getQuestionType(), QuestionType.Undefined);

		// Added for code coverage
		QuestionType qType = QuestionType.TrueFalse;
		assertNotEquals(qType.getCode(), 0);
	}

	@Test
	public void testSetTitle() {
		assertEquals(question.getTitle(), null);

		question.setTitle("A title");
		assertEquals(question.getTitle(), "A title");

		assertNotEquals(question.getTitle(), null);
	}

	@Test
	public void testGetBlockList() {
		assertTrue(question.getBlockList().isEmpty());

		question.addAnswerBlock(new AnswerBlockImpl());
		assertFalse(question.getBlockList().isEmpty());
	}

	@Test
	public void testAddAnswerBlock() {
		assertTrue(question.getAnswerBlockList().isEmpty());

		question.addAnswerBlock(new AnswerBlockImpl());
		assertFalse(question.getAnswerBlockList().isEmpty());
	}

	@Test
	public void testAddTextBlock() {
		assertTrue(question.getTextBlockList().isEmpty());

		question.addTextBlock(new TextBlock() {
			@Override
			public String getText() {
				return "A text";
			}
		});
		assertFalse(question.getTextBlockList().isEmpty());
	}

	@Test
	public void testGetAnswerBlockList() {
		assertTrue(question.getAnswerBlockList().isEmpty());

		question.addAnswerBlock(new AnswerBlockImpl());
		assertFalse(question.getAnswerBlockList().isEmpty());
	}

	@Test
	public void testGetTextBlockList() {
		assertTrue(question.getTextBlockList().isEmpty());

		question.addTextBlock(new TextBlock() {
			@Override
			public String getText() {
				return "A text";
			}
		});
		assertFalse(question.getTextBlockList().isEmpty());
	}

}
