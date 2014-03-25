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

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import questions.interfaces.Question;
import quizz.interfaces.Quiz;
import reponses.implementation.AnswerImpl;

/**
 * @author Heiva
 */
public class WikiQuestionService {

    public static final String NO_RESPONSE = "_NO_RESPONSE_";


    /**
     * Get quiz from its wiki text specification
     *
     * @param wikiText the wiki text
     * @return the result quiz
     */
    public Quiz getQuizFromWikiText(String wikiText) throws IOException, WikiReaderException {
		WikiQuizContentHandler handler = new WikiQuizContentHandler();
        WikiReader quizReader = new WikiReader();
        quizReader.setQuizContentHandler(handler);
        quizReader.readFile(wikiText);    	
        return handler.getQuiz();
    }

}
