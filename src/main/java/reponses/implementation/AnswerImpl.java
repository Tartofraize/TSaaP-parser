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

package reponses.implementation;

import reponses.interfaces.Answer;


/**
 * @author franck Silvestre
 */
public class AnswerImpl implements Answer {

    private String textValue;
    private Float percentCredit;
    private String identifier;
    private String feedback;

    /**
     * Get the text value of the answer
     *
     * @return the text value of the answer
     */
    public String getTextValue() {
        return textValue;
    }

    /**
     * Set te text value of the answer
     * @param _textValue the new text value
     */
    public void setTextValue(String _textValue) {
        this.textValue = _textValue;
    }

    /**
     * Get the percent credit the answer represents in the answer set
     *
     * @return the percent credit
     */
    public Float getPercentCredit() {
        return percentCredit;
    }

    /**
     * Set the percent credit of the answer
     * @param _percentCredit the percent credit
     */
    public void setPercentCredit(Float _percentCredit) {
        this.percentCredit = _percentCredit;
    }

    /**
     * Get the identifier of the answer relative to the question
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Set the identifier
     * @param _identifier  the new identifier
     */
    public void setIdentifier(String _identifier) {
        this.identifier = _identifier;
    }

    /**
     * Set the feedback
     * @param _feedback the feedback of an answer
     */
    public void setFeedback(String _feedback) {
        this.feedback = _feedback;
    }

    /**
     * Get the feedback to present to a student for this answer
     *
     * @return String feedback
     */
    public String getFeedBack() {
        return feedback;
    }
}
