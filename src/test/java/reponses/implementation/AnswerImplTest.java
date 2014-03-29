package reponses.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AnswerImplTest {

	private AnswerImpl answer;
	
	@Before
	public void initialize() {
		answer = new AnswerImpl();
	}

	@After
	public void clean() {
		answer = null;
	}

	@Test
	public void testGetTextValue() {
		assertEquals(answer.getTextValue(), null);

		answer.setTextValue("A value");
		assertEquals(answer.getTextValue(), "A value");
	}

	@Test
	public void testSetTextValue() {
		assertEquals(answer.getTextValue(), null);

		answer.setTextValue("A value");
		assertEquals(answer.getTextValue(), "A value");

		assertNotEquals(answer.getTextValue(), "A new value");
	}

	@Test
	public void testGetPercentCredit() {
		assertEquals(answer.getPercentCredit(), null);

		answer.setPercentCredit(3.f);
		assertEquals(answer.getPercentCredit(), 3.f, 0.0001);
	}

	@Test
	public void testSetPercentCredit() {
		assertEquals(answer.getPercentCredit(), null);

		answer.setPercentCredit(3.f);
		assertEquals(answer.getPercentCredit(), 3.f, 0.0001);
		
		assertNotEquals(answer.getPercentCredit(), 3.2f, 0.0001);
	}
	
	
	@Test
	public void testGetIdentifier() {
		assertEquals(answer.getIdentifier(), null);

		answer.setIdentifier("An id");
		assertEquals(answer.getIdentifier(), "An id");
	}

	@Test
	public void testSetIdentifier() {
		assertEquals(answer.getIdentifier(), null);

		answer.setIdentifier("An id");
		assertEquals(answer.getIdentifier(), "An id");
		
		assertNotEquals(answer.getIdentifier(), "A new id");		
	}

	@Test
	public void testGetFeedBack() {
		assertEquals(answer.getFeedBack(), null);

		answer.setFeedback("A com");
		assertEquals(answer.getFeedBack(), "A com");	
	}
	
	@Test
	public void testSetFeedback() {
		assertEquals(answer.getFeedBack(), null);

		answer.setFeedback("A com");
		assertEquals(answer.getFeedBack(), "A com");
		
		assertNotEquals(answer.getFeedBack(), "A new com");
	}

}
