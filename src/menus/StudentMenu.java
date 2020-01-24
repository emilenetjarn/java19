package menus;

import java.util.Scanner;
import managers.StudentManager;
import java.time.LocalDate;
import java.util.List;
import schooldatabase.domain.Education;
import schooldatabase.domain.Student;

public class StudentMenu {

    StudentManager sManager = new StudentManager();

    Scanner sc = new Scanner(System.in);

    public void Menu() {

        boolean studentLoop = true;

        while (studentLoop) {

            System.out.println("");
            System.out.println("Manage Students");
            System.out.println("");
            System.out.println("1. Add a Student");
            System.out.println("2. Update information about a Student");
            System.out.println("3. View information about a Student");
            System.out.println("4. View information about all students");
            System.out.println("5. Remove a Student");
            System.out.println("6. Add a Student to an Education");
            System.out.println("7. Remove a Student from an Education");
            System.out.println("0. Main menu");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                addStudent();

            } else if (choice == 2) {

                updateStudent();

            } else if (choice == 3) {

                viewStudent();

            } else if (choice == 4) {

                viewAllStudents();

            } else if (choice == 5) {

                removeStudent();

            } else if (choice == 6) {

                addStudentToEducation();

            } else if (choice == 7) {

                removeStudentFromEducation();

            } else if (choice == 0) {
                studentLoop = false;
            } else {
                System.out.println("Invalid input");
            }
        }

    }

    public void addStudent() {

        System.out.println("Enter name:");
        String name = sc.nextLine();

        System.out.println("Enter year of birth(YYYY):");
        int yob = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter month of birth(1-12):");
        int mob = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter day of birth(1-31):");
        int dob = sc.nextInt();
        sc.nextLine();

        sManager.addStudent(name, LocalDate.of(yob, mob, dob));

        System.out.println("Added " + name);

    }

    public void updateStudent() {

        System.out.println("Enter name of Student to update:");
        String name = sc.nextLine();

        Student foundStudent = findStudent(name);

        if (foundStudent == null) {
            return;
        }

        System.out.println("Do you want to update: ");
        System.out.println(foundStudent);
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {

            System.out.println("Enter new name: ");
            String newName = sc.nextLine();

            System.out.println("Enter new year of birth(YYYY):");
            int yob = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter new month of birth(1-12):");
            int mob = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter new day of birth(1-31):");
            int dob = sc.nextInt();
            sc.nextLine();

            sManager.updateStudent(foundStudent, newName, LocalDate.of(yob, mob, dob));
            System.out.println("Updated");

        } else {
            System.out.println("No updates made");
        }
    }

    public void viewStudent() {

        System.out.println("Enter name of Student to view:");

        String name = sc.nextLine();

        Student foundStudent = findStudent(name);

        if (foundStudent == null) {
            return;
        }

        System.out.println(foundStudent);

    }

    public void viewAllStudents() {

        List<Student> students = sManager.showAllStudents();

        if (students.isEmpty()) {
            System.out.println("Currently there are no students");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }

    }

    public void removeStudent() {

        System.out.println("Enter name of Student to delete:");

        String name = sc.nextLine();

        Student foundStudent = findStudent(name);

        if (foundStudent == null) {
            return;
        }

        System.out.println("Do you want to delete:");
        System.out.println(foundStudent);
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {
            sManager.removeStudent(foundStudent);
            System.out.println("Removed student");
        } else {
            System.out.println("No changes made");
        }

    }

    public void addStudentToEducation() {

        System.out.println("Enter name of student:");

        String name = sc.nextLine();

        Student s = findStudent(name);
        if (s == null) {
            return;
        }

        if (s.getEducation() != null) {
            System.out.println(s.getName() + " is already enrolled in education: " + s.getEducation().getName());
            return;
        }

        System.out.println("Enter name of education to add the student to:");

        String educationName = sc.nextLine();

        EducationMenu eMenu = new EducationMenu();
        Education e = eMenu.findEducation(educationName);
        if (e == null) {
            return;
        }

        System.out.println("Do you want to add " + s.getName() + " to " + e.getName() + "?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {
            sManager.addStudentToEducation(s, e);
            System.out.println("Added");
        } else {
            System.out.println("No changes made");
        }

    }

    public void removeStudentFromEducation() {

        System.out.println("Enter name of student: ");

        String name = sc.nextLine();

        Student s = findStudent(name);
        if (s == null) {
            return;
        }

        if (s.getEducation() == null) {
            System.out.println(s.getName() + " is currently not enrolled in an education");
            return;
        }

        Education e = s.getEducation();

        System.out.println("Do you want to remove student: " + s.getName() + " from education: " + s.getEducation().getName() + "?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {
            sManager.removeStudentFromEducation(s, e);
            System.out.println("Removed");
        } else {
            System.out.println("No updates made");
        }

    }

    public Student findStudent(String name) {

        List<Student> students = sManager.findStudent(name);

        Student s = null;

        if (students.isEmpty()) {
            System.out.println("Could not find a student with that name");
            return null;
        }

        if (students.size() > 1) {
            System.out.println("Found " + students.size() + " students");
            System.out.println("Select: ");

            int i = 1;
            for (Student student : students) {
                System.out.println(i + ": " + student);
                i++;
            }

            int getS = sc.nextInt();
            sc.nextLine();

            s = students.get((getS - 1));

            System.out.println("Selected: " + s);

        }

        if (students.size() == 1) {
            s = students.get(0);
            System.out.println("Found: " + s);
        }

        return s;

    }

}
