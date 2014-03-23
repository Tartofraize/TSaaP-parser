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

package quizz.implementation;


import java.util.ArrayList;
import java.util.List;

import questions.interfaces.Question;
import questions.interfaces.TextBlock;
import quizz.interfaces.Quiz;
import reponses.interfaces.Answer;
import reponses.interfaces.AnswerBlock;

/**
 * @author franck Silvestre
 */
public class QuizImpl implements Quiz {

    private List<Question> questionList = new ArrayList<Question>();


    /**
     * Add a question to the quiz
     * @param question the question to add
     */
    public void addQuestion(Question question) {
        questionList.add(question);
    }

    /**
     * Get the question list of the quiz
     *
     * @return the question list
     */
    public List<Question> getQuestionList() {
        return questionList;
    }

    @Override
    public void getGraphe() {
        for (Question question : this.getQuestionList()) {
            System.out.println("Voici la question ");
            for (TextBlock text : question.getTextBlockList()) {
                System.out.println("-> "+text.getText());
            }
            System.out.println("Reponses possibles :");
            for (AnswerBlock answerB : question.getAnswerBlockList()) {
                for (Answer answer : answerB.getAnswerList()) {
					System.out.println("-> "+answer.getTextValue()+". Com: "+answer.getFeedBack());
                }
            }
            System.out.println("");
        }
    }
}
