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
     * Receive notification of a new string
     */
    public void onString(String str);

    /**
     * Receive notification of the beginning of a title
     */
    public void onStartTitle();

    /**
     * Receive notification of the end of a title
     */
    public void onEndTitle();

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
    public void onStartAnswer(String prefix);

    public void onModifAnswer(String com);

    /**
     * Receive notification of the end of an answer
     */
    public void onEndAnswer();

    /**
     * Notification of the beginning of a credit specification
     */
    public void onStartAnswerCredit();


    /**
     * Notification of the end of a credit specification
     */
    public void onEndAnswerCredit();


    /**
     * Receive notification of the beginning feedback
     */
    public void onStartAnswerFeedBack();


    /**
     * Receive notification of the end of a feedback
     */
    public void onEndAnswerFeedBack();

	public void onModifQuestion(String nomDeLaQuestion, char typeDelaQuestion);

}
