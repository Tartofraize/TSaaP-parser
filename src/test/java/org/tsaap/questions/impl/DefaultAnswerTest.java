package org.tsaap.questions.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import reponses.implementation.AnswerImpl;

public class DefaultAnswerTest {

	@Test
	public void test() {
		AnswerImpl d = new AnswerImpl();
		d.getTextValue();
		assertTrue(true);
	}

}
