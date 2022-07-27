/**
 * 
 */
package database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

import application.Question;

/**
 * @author Prateek Balhara c0830002
 * Using text files to store data
 */
public class Database {
	final static String USER_FILE = "./Data/user.txt";
	final static String QUIZ_FILE = "./Data/quiz.txt";
	final static String RESULT_FILE = "./Data/result.txt";
	
	/**
	 * @param name - Name of the user
	 * @param password - Password for the user
	 * @param role - Role of the user (Professor/Student)
	 * @param college_id - College id of the user
	 * @return true/false - Based on if user was added or not 
	 */
	public static boolean addUser(String name, String password, String role, String college_id){
		//Storing User as: Name,Password,Role,CollegeId 
		try {
			FileWriter user_fileWriter = new FileWriter(USER_FILE, true);
			PrintWriter user_printWriter = new PrintWriter(user_fileWriter);
			user_printWriter.println(name+","+password+","+role+","+college_id);
			user_printWriter.close();
			System.out.println(name + " added to users");
			
			return true;
		}catch(Exception e) {
			System.out.println("Error occurred while adding user. Error Message: "+ e.getMessage());
			return false;
		}
		
	}
	
	/**
	 * @param college_id - College_id
	 * @param password - User password
	 * @return UserDetails - Comma Separated User Details 
	 */
	public static String[] findUser(String college_id, String password) {
		try{
			Scanner file_scanner = new Scanner(new FileReader(USER_FILE));
			while(file_scanner.hasNext()) {
				String[] user_details = file_scanner.nextLine().split(",");
				if(user_details[3].equalsIgnoreCase(college_id) && user_details[1].equals(password)) {
					return user_details; 
				}
			}
			return null;
		}catch(Exception e) {
			System.out.println("Error occurred while searching for user. Error Message: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param quizCode - Unique code for the quiz
	 * @param duration - Duration of the quiz (in minutes)
	 * @param numberOfQuestions - Number of questions in the Quiz
	 * @param questions - Array of questions
	 * @return boolean - flag to show if quiz was added to database 
	 */	
	public static boolean addQuiz(String quizCode, int duration, int numberOfQuestions, Question[] questions) {
		//Storing Quiz As:
		// QuizCode,Duration,NumberOfQuestion (Questions in following lines)
		// Question1Code,QuestionStatement,NumberOfOptions,Comma Seperated Options,Answer
		// Question2 ...
		// ..so on
		// --- Next Quiz ---
		try {
			PrintWriter quiz_printWriter = new PrintWriter(new FileWriter(QUIZ_FILE, true));
			quiz_printWriter.println(quizCode+","+Integer.toString(duration)+","+Integer.toString(numberOfQuestions));
			for(int i=0; i<numberOfQuestions; i++) {
				quiz_printWriter.println(questions[i].toString());
			}
			quiz_printWriter.close();
			System.out.println("Quiz Successfully added to Database.");
			return true;
		}catch(Exception e) {
			System.out.println("Error occured while adding quiz. Error message: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Display questions of the quiz
	 * @param quizCode - Code for the quiz
	 */
	public static void viewQuiz(String quizCode) {
		try{
			Scanner file_scanner = new Scanner(new FileReader(QUIZ_FILE));
			while(file_scanner.hasNext()) {
				String[] quiz_details = file_scanner.nextLine().split(",");
				if(quiz_details[0].equals(quizCode)) {
					System.out.println("Quiz Code: " + quiz_details[0] + "\t Duration: " + quiz_details[1] + " minutes.");
					for(int i=0; i< Integer.parseInt(quiz_details[2]); i++) {
						String[] question_details = file_scanner.nextLine().split(",");
						System.out.println("\nQuestion " + (i+1)+ ": " + question_details[1]);
						System.out.println("Options: \n");
						for(int j=1; j<=Integer.parseInt(question_details[2]); j++) {
							System.out.println((j) + ". " + question_details[2+j]);
						}
						System.out.println("------- Answer: " + question_details[question_details.length-1] +" -------");
						
					}
				}else { // If quiz code doesn't match, then skip all questions
					for(int i=0; i< Integer.parseInt(quiz_details[2]); i++) {
						file_scanner.nextLine();
					}
				}
			}
		}catch(Exception e) {
			System.out.println("Error occurred while searching for quiz. Error Message: " + e.getMessage());
		}
	}
	
	//Timer for the quiz
	//Returns true - if quiz time is over
	public static boolean quizTimeExceeded(long startTime, int quizDuration) {
		long now = new Timestamp(System.currentTimeMillis()).getTime() + 28800; //Epoch Time
		if(now>startTime+(quizDuration*60*1000)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Allows Student to attempt quiz
	 * @param quizCode - Code for the quiz
	 * @return Marks Obtained by student in the quiz
	 */
	public static int attemptQuiz(String quizCode) {
		try {
			int marks = 0;
			Scanner file_scanner = new Scanner(new FileReader(QUIZ_FILE));
			Scanner ans_scanner = new Scanner(System.in);
			while(file_scanner.hasNext()) {
				String[] quiz_details = file_scanner.nextLine().split(",");
				if(quiz_details[0].equals(quizCode)) {
					long startTime = new Timestamp(System.currentTimeMillis()).getTime(); //Epoch Time
					startTime += 28800; //Epoch time at Toronto Timezone
					Date startDate = new Date(startTime);
					System.out.println("Quiz Code: " + quiz_details[0] + "\t Duration: " + quiz_details[1] + " minutes. \t Start time: " + startDate);;
					for(int i=0; i< Integer.parseInt(quiz_details[2]); i++) {
						String[] question_details = file_scanner.nextLine().split(",");
						System.out.println("\nQuestion " + (i+1)+ ": " + question_details[1]);
						System.out.println("Options: \n");
						for(int j=1; j<=Integer.parseInt(question_details[2]); j++) {
							System.out.println((j) + ". " + question_details[2+j]);
						}
						System.out.println("------- Enter your choice: ");
						String answer = ans_scanner.nextLine();
						if( quizTimeExceeded(startTime, Integer.parseInt(quiz_details[1])) ) {
							System.out.println("Sorry, Timer ran out!!");
							break;
						}
						if(answer.equals(question_details[question_details.length-1])) {
							System.out.println("Good Job! Right Answer.");
							marks++;
						}else {
							System.out.println("Ahh! Wrong Answer.");
						}
						
					}
				}else { // If quiz code doesn't match, then skip all questions
					for(int i=0; i< Integer.parseInt(quiz_details[2]); i++) {
						file_scanner.nextLine();
					}
				}
			}
			return marks;
		}catch(Exception e) {
			System.out.println("Error occurred while searching for quiz. Error Message: " + e.getMessage());
			return 0;
		}
	}
	
	/**
	 * Stores marks obtained by student in a text file
	 * @param userName - Name of student
	 * @param college_id - College id of student
	 * @param quizCode - Quiz Code
	 * @param marks - Marks obtained in quiz
	 */
	public static void storeMarks(String userName, String college_id, String quizCode, int marks) {
		//Storing result as:
		//QuizCode,CollegeId, UserName, Marks
		try {
			PrintWriter result_printWriter = new PrintWriter(new FileWriter(RESULT_FILE, true));
			result_printWriter.println(quizCode + "," + college_id + "," + userName + "," + Integer.toString(marks));
			result_printWriter.close();
		}catch(Exception e) {
			System.out.println("Error occured while storing result. Error Message: " + e.getMessage());
		}
		
	}
	
	/**
	 * Displays result of quiz 
	 * @param quizCode - Code for the quiz
	 */
	public static void displayQuizResult(String quizCode) {
		Scanner file_scanner;
		try {
			file_scanner = new Scanner(new FileReader(RESULT_FILE));
			System.out.println("Result for Quiz " + quizCode);
			while(file_scanner.hasNext()) {
				String[] result_details = file_scanner.nextLine().split(",");
				if(result_details[0].equals(quizCode)) {
					System.out.println("Student Name: " + result_details[2] +"\t    Marks: " + result_details[3]);
				}else {
					continue;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
