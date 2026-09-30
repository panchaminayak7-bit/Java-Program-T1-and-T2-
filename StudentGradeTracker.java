import java.util.ArrayList;
import java.util.Scanner;
class Student {
    int id;
    String name;
    double[] marks;

    Student(int id, String name, double[] marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
    // Calculate average
    double average() {
        double total = 0;
        for (double mark : marks) {
            total += mark;
        }
        return total / marks.length;
    }
    // Find highest mark
    double highest() {
        double high = marks[0];

        for (double mark : marks) {
            if (mark > high) {
                high = mark;
            }
        }
        return high;
    }
    // Find lowest mark
    double lowest() {
        double low = marks[0];

        for (double mark : marks) {
            if (mark < low) {
                low = mark;
            }
        }
        return low;
    }
    // Check pass/fail
    String status() {
        for (double mark : marks) {
            if (mark < 40) {
                return "FAIL";
            }
        }
        return "PASS";
    }
}
public class StudentGradeTracker {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static String[] subjects = {
        "Mathematics",
        "English",
        "Java",
        "DBMS",
        "Computer Networks"
    };
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n==========================================");
            System.out.println("        STUDENT GRADE TRACKER");
            System.out.println("==========================================");
            System.out.println("1. Add Student");
            System.out.println("2. Search Student");
            System.out.println("3. Display Summary Report");
            System.out.println("4. Show Student Status");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.println("==========================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    searchStudent();
                    break;

                case 3:
                    displayStudents();
                    break;

                case 4:
                    showStatus();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    System.out.println("\nThank you! Program exited.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }
    // Add Student
    static void addStudent() {
        System.out.print("\nEnter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        double[] marks = new double[5];
        System.out.println("\nEnter marks:");
        for (int i = 0; i < subjects.length; i++) {
            System.out.print(subjects[i] + ": ");
            marks[i] = sc.nextDouble();
        }
        students.add(new Student(id, name, marks));
        System.out.println("\nStudent added successfully!");
    }
    // Search Student
    static void searchStudent() {

        System.out.print("\nEnter Student ID to search: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.id == id) {
                System.out.println("\n========== STUDENT DETAILS ==========");
                System.out.println("Student ID : " + s.id);
                System.out.println("Name       : " + s.name);
                System.out.println("\nSubject Marks:");
                for (int i = 0; i < subjects.length; i++) {
                    System.out.println(
                        subjects[i] + " : " + s.marks[i]
                    );
                }
                System.out.printf( "\nAverage : %.2f%n",s.average());

                System.out.println("Highest : " + s.highest());

                System.out.println("Lowest  : " + s.lowest());

                System.out.println("Status  : " + s.status());

                return;
            }
        }
        System.out.println("Student not found!");
    }
    // Display Summary Report
    static void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("\nNo students available.");
            return;
        }

        System.out.println("\n================ STUDENT SUMMARY REPORT ================" );

        System.out.printf(
            "%-10s %-20s %-10s %-10s %-10s %-10s%n",
            "ID",
            "Name",
            "Average",
            "Highest",
            "Lowest",
            "Status"
        );

        System.out.println("--------------------------------------------------------------------------");

        for (Student s : students) {

            System.out.printf(
                "%-10d %-20s %-10.2f %-10.2f %-10.2f %-10s%n",
                s.id,
                s.name,
                s.average(),
                s.highest(),
                s.lowest(),
                s.status()
            );
        }
    }

    // Show Status
    static void showStatus() {

        if (students.isEmpty()) {
            System.out.println("\nNo students available.");
            return;
        }

        System.out.println("\n========== STUDENT STATUS ==========");
        for (Student s : students) {
            System.out.println(
                "ID: " + s.id +
                " | Name: " + s.name +
                " | Status: " + s.status()
            );
        }
    }

    // Delete Student
    static void deleteStudent() {
        System.out.print("\nEnter Student ID to delete: ");
        int id = sc.nextInt();
        for (int i = 0; i < students.size(); i++) {

            if (students.get(i).id == id) {

                students.remove(i);

                System.out.println("Student deleted successfully!");

                return;
            }
        }

        System.out.println("Student not found!");
    }
}