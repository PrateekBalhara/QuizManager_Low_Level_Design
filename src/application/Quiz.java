/**
 * 
 */
package application;

import java.util.Scanner;

import database.Database;

/**
 * @author Prateek Balhara c0830002
 */
public class Quiz {
	private static int QUIZ_CODE = 1;
	private String code; 
	private int duration, numberOfQuestions;
	private Question[] questions;
	
	/**
	 * Constructor with default values
	 * default quiz code is self incrementing
	 * default duration is 20 minutes
	 * default numberOfQuestions are 10
	 */
	Quiz(){
		this.code = getNextQuizCode();
		this.duration = 10;
		this.numberOfQuestions = 5;
	}
	
	/**
	 * OverLoaded Constructor
	 * @param code : The quiz code (Unique for every quiz)
	 * @param duration : Duration of the Quiz in minutes
	 * @param numberOfQuestions : Number of Questions in the quiz
	 * @param questions: Questions to be asked in the quiz
	 */
	Quiz(int duration, int numberOfQuestions, Question[] questions){
		this.code = getNextQuizCode();
		this.duration = duration;
		this.numberOfQuestions = numberOfQuestions;
		this.questions = questions;
		
	}
	
	
	// Adds quiz to the Database
	public void addToDatabase() {
		System.out.println("Adding quiz to database ");
		System.out.println("Questions: " + this.questions);
		Database.addQuiz(this.code, this.duration, this.numberOfQuestions, this.questions);
	}
	
	/**
	 * @return default next quiz code
	 */
	public static String getNextQuizCode() {
		return Integer.toString(QUIZ_CODE++);
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the numberOfQuestions
	 */
	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	/**
	 * @param numberOfQuestions the numberOfQuestions to set
	 */
	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}
	
	/**
	 * Add Questions to existing quiz
	 * @return Question object
	 */
	public static Question addQuestiontoQuiz() {
		Scanner scan_obj = new Scanner(System.in);
		System.out.print("Enter the question: ");
		String questionStatement = scan_obj.nextLine();
		System.out.print("Enter number of options: ");
		int numberOfOptions = scan_obj.nextInt();
		scan_obj.nextLine();
		System.out.println("Enter the options below: ");
		String[] options = new String[numberOfOptions];
		for(int i=0; i<numberOfOptions; i++) {
			System.out.println("Option " + (i+1) + ": ");
			options[i] = scan_obj.nextLine();
		}
		System.out.print("Enter the option/answer number that is correct: ");
		int answer = scan_obj.nextInt();
		scan_obj.nextLine();
		return new Question(questionStatement, numberOfOptions, options, answer);
	}
	
	/**
	 * Allows Students to attempt quiz
	 * @param quizCode - Code for the quiz
	 * @param user - User attempting the quiz
	 * @return marks - Total marks obtained in quiz
	 */
	public static int attemptQuiz(String quizCode, User user) {
		int marks = Database.attemptQuiz(quizCode);
		System.out.println("***** Total Marks: " + marks + " *****");
		Database.storeMarks(user.getName(), user.getCollege_id(), quizCode, marks);
		return marks;
	}
	
	/**
	 * View result of all students who attempted the quiz
	 * @param quizCode - Code for the quiz
	 */
	public static void viewQuizResult(String quizCode) {
		Database.displayQuizResult(quizCode);
		System.out.println("-----------------------------------------");
		
	}
	
}
