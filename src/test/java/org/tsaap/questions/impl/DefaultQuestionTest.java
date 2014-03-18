package org.tsaap.questions.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.tsaap.questions.AnswerBlock;
import org.tsaap.questions.QuestionBlock;
import org.tsaap.questions.QuestionType;
import org.tsaap.questions.TextBlock;

public class DefaultQuestionTest {
	
	DefaultQuestion df;
    QuestionType questionType;

    @Before
    public void setUp() {
    	df = new DefaultQuestion();
    	questionType = QuestionType.TrueFalse;
    }
    
	@Test
	public void testGetTitle() {
		df.getTitle();
		assertTrue(true);
	}

	@Test
	public void testGetQuestionType() {
		df.getQuestionType();
		assertTrue(true);
	}

	@Test
	public void testSetQuestionType() {
		df.setQuestionType(questionType);
		assertEquals(QuestionType.TrueFalse, df.getQuestionType());
	}

	@Test
	public void testSetTitle() {
		df.setTitle("Titre");
		assertEquals("Titre", df.getTitle());
	}

	@Test
	public void testGetBlockList() {
		df.getBlockList();
		assertTrue(true);
	}

	@Test
	public void testAddAnswerBlock() {
		AnswerBlock answer = new DefaultAnswerBlock();
		df.addAnswerBlock(answer);
		assertEquals(1, df.getBlockList().size());
		assertEquals(1, df.getAnswerBlockList().size());
	}

	@Test
	public void testAddTextBlock() {
		TextBlock text = null;
		df.addTextBlock(text);
		assertEquals(1, df.getBlockList().size());
		assertEquals(1, df.getTextBlockList().size());
	}

	@Test
	public void testGetAnswerBlockList() {
		df.getAnswerBlockList();
		assertTrue(true);
	}

	@Test
	public void testGetTextBlockList() {
		df.getTextBlockList();
		assertTrue(true);
	}

}
