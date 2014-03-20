package reponses.implementation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import reponses.interfaces.Answer;

public class AnswerBlockImplTest {

	@SuppressWarnings("null")
	@Test
	public void testGetAnswerList() {
	    List<AnswerBlockImpl> answerList = new ArrayList<AnswerBlockImpl>();
	    AnswerBlockImpl answer = new AnswerBlockImpl();
		AnswerImpl a2 = new AnswerImpl();
		a2.setTextValue("Test de reponse");
		answer.addAnswer(a2);
		answerList.add(answer);
		assertTrue(new String("Test de reponse").equals(answerList.get(0).getAnswerList().get(0).getTextValue()));
		
	}

	@Test
	public void testAddAnswer() {
		List<AnswerBlockImpl> answerList = new ArrayList<AnswerBlockImpl>();
	    AnswerBlockImpl answer1 = new AnswerBlockImpl();
	    AnswerBlockImpl answer2 = new AnswerBlockImpl();
	    AnswerBlockImpl answer3 = new AnswerBlockImpl();
	    AnswerImpl a1 = new AnswerImpl();
	    AnswerImpl a2 = new AnswerImpl();
	    AnswerImpl a3 = new AnswerImpl();
		a1.setTextValue("Test de reponse 1");
		a2.setTextValue("Test de reponse 2");
		a3.setTextValue("Test de reponse 3");
		answer1.addAnswer(a1);
		answer2.addAnswer(a2);
		answer3.addAnswer(a3);
		answerList.add(answer1);
		answerList.add(answer2);
		answerList.add(answer3);
		assertTrue(answerList.size() == 3);
	}

}
