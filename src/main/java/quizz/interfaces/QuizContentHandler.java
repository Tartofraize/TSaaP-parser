package quizz.interfaces;

import quizz.implementation.QuizImpl;

/**
 * @author franck Silvestre
 */
public interface QuizContentHandler {

	/**
	 * Receive notification of the beginning of a quiz
	 */
	public void onStartQuiz();

	/**
	 * Receive notification of the beginning of a question
	 */
	public void onStartQuestion();

	/**
	 * Receive notification of the end of a question
	 */
	public void onEndQuestion();

	/**
	 * Receive notification of the beginning of an answer block
	 */
	public void onStartAnswerBlock();

	/**
	 * Receive notification of the end of an answer block
	 */
	public void onEndAnswerBlock();

	/**
	 * Receive notification of the beginning of an answer
	 * @param prefix Prefix of the answer : a '+' for good answer or a '-'
	 * @param nom Text of the answer
	 */
	public void onStartAnswer(char prefix, String nom);

	/**
	 * Receive notification of an answer commentary
	 * @param com Commentary of the answer
	 */
	public void onModifAnswer(String com);

	/**
	 * Receive notification of the end of an answer
	 */
	public void onEndAnswer();

	/**
	 * Receive notification of the beginning of a question
	 * @param questionName String containing the question
	 * @param questionType Character specifying the question type.
	 * Here '[' for multi-choice and '(' for exclusive choice
	 */
	public void onModifQuestion(String questionName, char questionType);

	/**
	 * @return The current quizz handled
	 */
	public QuizImpl getQuiz();

}
