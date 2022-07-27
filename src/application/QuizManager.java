/**
 * Quiz Manager allows admin to store MCQ question and conduct a quiz. 
 */
package application;

import java.util.Scanner;
import javax.swing.JOptionPane;
import database.Database;

/**
 * @author Prateek Balhara (C0830002)
 * Final Project - Programming Java SE (CSD_3464_3)
 */
public class QuizManager {
	public static enum USER_ROLES{
		Professor,
		Student
	};
		
	
	// Allows users to Login using their college id and password.
	public static User Login() {
		Scanner scan_obj = new Scanner(System.in);
		System.out.println("Please Login -");
		System.out.print("College Id: ");
		String college_id = scan_obj.nextLine();
		System.out.print("Password: ");
		String password = scan_obj.nextLine();
		return User.userLogin(college_id, password);
	}
	
	// Display Menu to Professor
	public static int displayProfessorMenu() {
		Scanner scan_obj = new Scanner(System.in);
		System.out.println("\nPlease choose an option from below menu: ");
		System.out.println("1. Setup Quiz");
		System.out.println("2. View Quiz Questions");
		System.out.println("3. View Quiz Results");
		System.out.println("4. Edit Username");
		System.out.println("5. Exit");
		int option = scan_obj.nextInt();
		scan_obj.nextLine();
		return option;
	}
	
	//View Results for the Quiz
	public static void viewQuizResult() {
		Scanner scan_obj = new Scanner(System.in);
		System.out.println("Enter the Quiz Code: ");
		String quizCode = scan_obj.nextLine();
		Quiz.viewQuizResult(quizCode);
	}
	
	//Allow professor to add new Quiz
	public static void setupNewQuiz() {
		Scanner scan_obj = new Scanner(System.in);
		System.out.println("Setup a New Quiz- ");
		System.out.print("Enter the number of questions in the quiz: ");
		int numberOfQuestions = scan_obj.nextInt();
		scan_obj.nextLine();
		System.out.print("Enter the duration of quiz (in minutes): ");
		int duration = scan_obj.nextInt();
		scan_obj.nextLine();
		System.out.println("\nAdd questions to the questions bank: ");
		Question[] questions = new Question[numberOfQuestions];
		for(int i=0; i<numberOfQuestions; i++) {
			System.out.println("\nEnter Question "+ (i+1));
			questions[i] = Quiz.addQuestiontoQuiz();
			System.out.println(" ---- Question Added Successfully. ---- ");
		}
		Quiz quiz = new Quiz(duration, numberOfQuestions, questions);
		quiz.addToDatabase();
		System.out.println("\n\nQuiz "+ quiz.getCode() + " Successfully Setup!\n");
	}
	
	//Allow professor to view all questions in the Quiz
	public static void viewQuiz() {
		Scanner scan_obj = new Scanner(System.in);
		System.out.println("Enter the code for the quiz that you want to review: ");
		String quizCode = scan_obj.nextLine();
		Database.viewQuiz(quizCode);
	}
	
	// Use case for professor role
	public static void Professor(User prof) {
		int option = displayProfessorMenu();
		switch(option) {
		case 1: 
			setupNewQuiz();
			break;
		case 2:
			viewQuiz();
			break;
		case 3:
			viewQuizResult();
			break;
		case 4:
			Scanner scan_obj = new Scanner(System.in);
			System.out.print("\nEnter your name: ");
			String new_name = scan_obj.nextLine();
			prof.setName(new_name);
			System.out.println("Name Updated! User's new name: " + prof.getName());
			break;
		case 5:
			System.out.println("\nBye " + prof.getName() + " !!");
			System.exit(0);
			break;
		default:
			System.out.println("Error: Enter a Valid Option.");
		}
		Professor(prof);
	}
	
	
	
	// Use cases for Student role
	public static void Student(User user) {
		System.out.println("Enter the quiz code: ");
		Scanner scan_obj = new Scanner(System.in);
		String quizCode = scan_obj.nextLine();
		int marks = Quiz.attemptQuiz(quizCode, user);
		System.out.println("See you next time!! \n Have a good day " + user.getName());
		
	}
	
	// Adds Dummy Users to the system
	public static void addDummyUsers() {
		Database.addUser("Cryston Heaven", "password", USER_ROLES.Professor.name(), "c083002");
		Database.addUser("Student1", "password", USER_ROLES.Student.name(), "c083001");
		Database.addUser("Student2", "pass", USER_ROLES.Student.name(), "c083004");
	}
	
	
	public static void main(String[] args) {
		try {
			System.out.println("--------------- WELCOME TO QUIZ MAKER ---------------");
			//Function call to add dummy users to the system.
			//addDummyUsers();
			User user = null;
			//Try to login till not successful
			while(user == null) {
				user = Login();
			}
			System.out.println("--------------------------\nHello "+ user.getName());
			System.out.println(user.getRole());
			
			if(user.getRole().equals("Professor")){
				Professor(user);
			}else {
				Student(user);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Oops!! The system crashed. Please wait till the time AI rises to power, to fix this error.");
			System.out.println("Meanwhile, you should go out to get a coffee from Tims.");
		}

	}

}
