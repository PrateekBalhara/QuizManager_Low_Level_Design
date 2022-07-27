/**
 * 
 */
package application;

/**
 * @author Prateek Balhara c0830002
 */
public class Question {
	private static int QUESTION_CODE = 1;
	private String questionCode, questionStatement;
	private int numberOfOptions, answer;
	private String[] options;
	
	Question(String questionStatement, 
			int numberOfOptions, String[] options, int answer){
		this.questionCode = Integer.toString(QUESTION_CODE++);
		this.questionStatement = questionStatement;
		this.numberOfOptions = numberOfOptions;
		this.options = options;
		this.answer = answer;
	}
	
	
	/**
	 * Overriding toString method for Question Object
	 */
	public String toString() {
		String questionString = this.questionCode + "," + this.questionStatement +"," 
	+ Integer.toString(numberOfOptions) + "," + String.join(",", options) + "," + answer;
		return questionString;
	}
	
	/**
	 * @return the answer
	 */
	public int getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	
	/**
	 * @return the questionStatement
	 */
	public String getQuestionStatement() {
		return questionStatement;
	}
	/**
	 * @param questionStatement the questionStatement to set
	 */
	public void setQuestionStatement(String questionStatement) {
		this.questionStatement = questionStatement;
	}
	/**
	 * @return the numberOfOptions
	 */
	public int getNumberOfOptions() {
		return numberOfOptions;
	}
	/**
	 * @param numberOfOptions the numberOfOptions to set
	 */
	public void setNumberOfOptions(int numberOfOptions) {
		this.numberOfOptions = numberOfOptions;
	}
	/**
	 * @return the options
	 */
	public String[] getOptions() {
		return options;
	}
	/**
	 * @param options the options to set
	 */
	public void setOptions(String[] options) {
		this.options = options;
	}

}
