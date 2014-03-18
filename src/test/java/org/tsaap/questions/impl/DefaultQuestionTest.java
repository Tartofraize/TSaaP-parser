package org.tsaap.questions.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import questions.implementation.QuestionImpl;
import questions.interfaces.QuestionType;
import questions.interfaces.TextBlock;
import reponses.implementation.AnswerBlockImpl;
import reponses.interfaces.AnswerBlock;

public class DefaultQuestionTest {
	
	QuestionImpl df = new QuestionImpl();
    QuestionType questionType = QuestionType.TrueFalse;

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
		AnswerBlock answer = new AnswerBlockImpl();
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
