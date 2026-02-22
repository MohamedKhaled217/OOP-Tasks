import { Student } from "./Student";

export class GradeBook {
  public ClassName: string = "";
  public Students: Student[] = [];

  constructor(ClassName: string) {
    this.ClassName = ClassName;
  }

  addStudent(student: Student): void {
    this.Students.push(student);
  }

  removeStudent(studentId: string): void {
    this.Students = this.Students.filter(
      (st) => st.getStudentId() !== studentId,
    );
  }

  findStudent(studentId: string): void {
    let student = this.Students.find((st) => st.getStudentId() === studentId);
    if (!student) {
      console.log(`Student is not exist`);
      return;
    }
    console.log(student);
  }

  getClassAverage(): void {
    let total = 0,
      cnt = 0;
    for (let student of this.Students) {
      for (let value of student.Grades.values()) {
        total += value;
        cnt++;
      }
    }
    if (cnt) console.log(`Class Average: ${(total * 1.0) / cnt}\n`);
    else console.log("Class is empty");
  }

  getTopStudents(count: number): void {
    let top: Map<string, number> = new Map();
    for (let student of this.Students) {
      top.set(student.Name , student.calculateAverage());
    }
    let topArray = Array.from(top);
    topArray.sort((a, b) => b[1] - a[1]);
    console.log(`=== Top ${count} Students:`);
    for (let student of topArray) {
      console.log(`${student[0]}: ${student[1]}`);
      count--;
      if (!count) {
        console.log("\n");
        return;
      }
    }
  }

  displayAllStudents(): void {
    console.log(`=== ${this.ClassName} - All Students ===\n`);
    for (let student of this.Students) {
      console.log(
        `${student.getStudentId()} - ${student.Name}: ${student.calculateAverage()} (${student.getLetterGrade()})\n`,
      );
    }
  }
  getStudentsByLetterGrade(letterGrade: string): void {
    let students = this.Students.filter(
      (st) => st.getLetterGrade() === letterGrade,
    );
    console.log(`===== Students with (${letterGrade}):`);
    for (let x of students) {
      console.log(x.Name);
    }
  }
}
