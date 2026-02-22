export class Student {
  private StudentId: string = "";
  public Name: string = "";
  public Email: string = "";
  public Grades: Map<string, number> = new Map();

  constructor(StudentId: string, Name: string, Email: string) {
    this.Name = Name;
    this.StudentId = StudentId;
    this.Email = Email;
  }

  getStudentId(): string {
    return this.StudentId;
  }

  addGrade(subject: string, grade: number): void {
    if (grade >= 0 && grade <= 100) {
      this.Grades.set(subject, grade);
    } else console.log(`Grade You Added isn't Accepted`);
  }

  getGrade(subject: string): void {
    let grade = this.Grades.get(subject);
    if (grade) {
      console.log(`${subject} Grade is ${grade}`);
    } else {
      console.log(`Student Does Not attend This Course`);
    }
  }

  calculateAverage(): number {
    if (this.Grades.size === 0) {
      return 0;
    }
    let total = 0.0;
    for (let x of this.Grades) total += x[1];
    return total / this.Grades.size;
  }

  getLetterGrade(): string {
    let avg = this.calculateAverage();
    if (avg >= 90) return "A";
    else if (avg >= 80) return "B";
    else if (avg >= 70) return "C";
    else if (avg >= 60) return "D";
    else return "F";
  }

  getStudentInfo(): void {
    console.log(`=== Student Information ===`);
    console.log(`ID:${this.StudentId}`);
    console.log(`Name:${this.Name}`);
    console.log(`Email:${this.Email}`);
    console.log(`Grades:`);
    this.Grades.forEach((value, key) => {
      console.log(` ${key}: ${value}`);
    });
    console.log(`Average: ${this.calculateAverage()}`);
  }
}
