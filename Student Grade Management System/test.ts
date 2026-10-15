import { Student } from "./Student";
import { GradeBook } from "./GradeBook";

// Create a gradebook
let gradeBook = new GradeBook("Computer Science 101");

// Create students
let student1 = new Student("S001", "Alice Johnson", "alice@school.com");
let student2 = new Student("S002", "Bob Smith", "bob@school.com");
let student3 = new Student("S003", "Charlie Brown", "charlie@school.com");

// Add grades for students
student1.addGrade("Math", 95.0);
student1.addGrade("English", 88.0);
student1.addGrade("Science", 92.0);

student2.addGrade("Math", 78.0);
student2.addGrade("English", 85.0);
student2.addGrade("Science", 80.0);

student3.addGrade("Math", 90.0);
student3.addGrade("English", 92.0);
student3.addGrade("Science", 89.0);

// Add students to gradebook
gradeBook.addStudent(student1);
gradeBook.addStudent(student2);
gradeBook.addStudent(student3);

// Display all students
gradeBook.displayAllStudents();

// Get class average
gradeBook.getClassAverage();

// Get top students
gradeBook.getTopStudents(2);

// Get student info
student1.getStudentInfo();
