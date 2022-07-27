# QuizManager_Low_Level_Design
Repository contains a low level design for a Quiz Manager Application
##Description
Here is a low level design for a Quiz Management application. 
The functionality includes:
  1.	There should be 2 types of accessing modes. Professor and Student.
  2.	Professors and Students must be able to login with their college ids and password.
  3.	Professor must be able to add a question bank with correct answers.
  4.	Students must be able to login and attempt quiz.
  5.	System must be able to store and put forth a random set of questions for each student.
  6.	System should be able to evaluate students’ response and grade them.
  7.	System must also be able to store student’s information like student information, start time and end time of quiz, correct answers for each question and student grades.
  8.	Professor should be to view result of all students.

## Use Case Diagram:
<img width="260" alt="image" src="https://user-images.githubusercontent.com/33585244/181153375-454b9bad-9a79-486d-936a-b03494360ad8.png">

## Database Schema:
<img width="337" alt="image" src="https://user-images.githubusercontent.com/33585244/181153464-c5e739f3-f8dd-498f-88a9-8fdc0ce65a7e.png">

## Class Diagram:
<img width="310" alt="image" src="https://user-images.githubusercontent.com/33585244/181153491-a103a2f1-75c0-4b23-8363-4fbf40e6a800.png">

## Pending Features:
  1. Integration with database is pending. I used files to store data and fetch data.
  2. Tracking attendance and end time for each student is pending.
  3. Security enhancements like encrypting password, can be done.

