/*
 * Copyright 2013 Tsaap Development Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package wiki;

import questions.implementation.QuestionImpl;
import questions.interfaces.Question;
import questions.interfaces.QuestionType;
import questions.interfaces.TextBlock;
import quizz.implementation.QuizImpl;
import quizz.interfaces.QuizContentHandler;
import reponses.implementation.AnswerBlockImpl;
import reponses.implementation.AnswerImpl;

/**
 * @author franck Silvestre
 */
public class WikiQuizContentHandler implements QuizContentHandler {

    private QuizImpl quiz;
    private QuestionImpl currentQuestion;
   	private AnswerBlockImpl currentAnswerBlock;
    private AnswerImpl currentAnswer;
    private int answerCounter;

    /**
     * Get the quiz
     *
     * @return the quiz
     */
    public QuizImpl getQuiz() {
        return quiz;
    }

    /**
     * Receive notification of the beginning of a quiz
     */
    public void onStartQuiz() {
        quiz = new QuizImpl();
        System.out.println("Debut du Quizz");
    }

    /**
     * Receive notification of the end of a quiz
     */
    public void onEndQuiz() {
    	System.out.println("Fin reception quizz");
    }

    /**
     * Receive notification of the beginning of a question
     */
    public void onStartQuestion() {
        currentQuestion = new QuestionImpl();
    }
    
    /**
     * Receive notification of the beginning of a question
     * @param nomDeLaQuestion Name of the question
     * @param typeDelaQuestion Type of the question
     */
    public void onModifQuestion(final String nomDeLaQuestion, char typeDelaQuestion) {
    	currentQuestion.addTextBlock(new TextBlock() {
            public String getText() {
                return nomDeLaQuestion;
            }
        });
        if (typeDelaQuestion == '[') {
        	currentQuestion.setQuestionType(QuestionType.MultipleChoice);
        } else if (typeDelaQuestion == '(') {
        	currentQuestion.setQuestionType(QuestionType.ExclusiveChoice);
        }
    }

    /**
     * Receive notification of the end of a question
     */
    public void onEndQuestion() {
        postProcess(currentQuestion);
        quiz.addQuestion(currentQuestion);
        currentQuestion = null;
    }
    
    /**
     * Receive notification of the beginning of an answer fragment
     */
    public void onStartAnswerBlock() {
        currentAnswerBlock = new AnswerBlockImpl();
        answerCounter = 0;
    }

    /**
     * Receive notification of the end of an answer fragment
     */
    public void onEndAnswerBlock() {
        currentQuestion.addAnswerBlock(currentAnswerBlock);
        currentAnswerBlock = null;
    }
    
    /**
     * Receive notification of the beginning of an answer
     * @param prefix prefix of the answer
     * @param nom name of the answer
     */
    public void onStartAnswer(char prefix, String nom) {
        currentAnswer = new AnswerImpl();
        currentAnswer.setIdentifier(String.valueOf(answerCounter++));
        currentAnswer.setTextValue(nom);
        
        if ('+' == prefix) {
            currentAnswer.setPercentCredit(100f);
        } else {
            currentAnswer.setPercentCredit(0f);
        }        
    }
    
    /**
     * Receive notification on modification
     * @param com the Modification
     */
    public void onModifAnswer(String com) {
        currentAnswer.setFeedback(com);
    }

    /**
     * Receive notification of the end of an answer
     */
    public void onEndAnswer() {
        currentAnswerBlock.addAnswer(currentAnswer);
        currentAnswer = null;
    } 

    /**
     * 
     * @param question the question
     */
    
    private void postProcess(Question question) {
    }   
    
    public QuestionImpl getCurrentQuestion() {
		return currentQuestion;
	}

//	public void setCurrentQuestion(QuestionImpl currentQuestion) {
//		this.currentQuestion = currentQuestion;
//	}
    
}
