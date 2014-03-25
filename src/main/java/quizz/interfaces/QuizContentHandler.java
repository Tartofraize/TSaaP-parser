package quizz.interfaces;

/**
 * @author franck Silvestre
 */
public interface QuizContentHandler {

    /**
     * Receive notification of the beginning of a quiz
     */
    public void onStartQuiz();

    /**
     * Receive notification of the end of a quiz
     */
    public void onEndQuiz();

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
     */
    public void onStartAnswer(char prefix, String nom);

    public void onModifAnswer(String com);

    /**
     * Receive notification of the end of an answer
     */
    public void onEndAnswer();

	public void onModifQuestion(String nomDeLaQuestion, char typeDelaQuestion);
	
	public Quiz getQuizz();

}
