package menus;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import managers.TeacherManager;
import schooldatabase.domain.Course;
import schooldatabase.domain.Teacher;

public class TeacherMenu {

    TeacherManager tManager = new TeacherManager();

    Scanner sc = new Scanner(System.in);

    public void Menu() {

        boolean teacherLoop = true;

        while (teacherLoop) {

            System.out.println("");
            System.out.println("Manage Teachers");
            System.out.println("");
            System.out.println("1. Add a Teacher");
            System.out.println("2. Update information about a Teacher");
            System.out.println("3. View information about a Teacher");
            System.out.println("4. View information about all Teachers");
            System.out.println("5. Remove a Teacher");
            System.out.println("6. View all Teachers in a Course");
            System.out.println("7. Add a Teacher to a Course");
            System.out.println("8. Remove a Teacher from a Course");
            System.out.println("0. Main menu");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                addTeacher();

            } else if (choice == 2) {

                updateTeacher();

            } else if (choice == 3) {

                viewTeacher();

            } else if (choice == 4) {

                viewAllTeachers();

            } else if (choice == 5) {

                removeTeacher();

            } else if (choice == 6) {
                CourseMenu cMenu = new CourseMenu();
                cMenu.viewAllTeachersInCourse();

            } else if (choice == 7) {

                addTeacherToCourse();

            } else if (choice == 8) {

                removeTeacherFromCourse();

            } else if (choice == 0) {
                teacherLoop = false;
            } else {
                System.out.println("Invalid input");
            }

        }

    }

    public void addTeacher() {

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

        tManager.addTeacher(name, LocalDate.of(yob, mob, dob));

        System.out.println("Added " + name);

    }

    public void updateTeacher() {

        System.out.println("Enter name of Teacher to update:");

        String name = sc.nextLine();

        Teacher t = findTeacher(name);
        if (t == null) {
            return;
        }

        System.out.println("Do you want to update: ");
        System.out.println(t);
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

            tManager.updateTeacher(t, newName, LocalDate.of(yob, mob, dob));
            System.out.println("Updated");

        } else {
            System.out.println("No changes made");
        }

    }

    public void viewTeacher() {

        System.out.println("Enter name of Teacher to view:");

        String name = sc.nextLine();

        Teacher t = findTeacher(name);
        if (t == null) {
            return;
        }

        System.out.println(t);

    }

    public void viewAllTeachers() {

        List<Teacher> teachers = tManager.showAllTeachers();

        if (teachers.isEmpty()) {
            System.out.println("Currently there are no teachers");
            return;
        }

        for (Teacher t : teachers) {
            System.out.println(t);
        }

    }

    public void removeTeacher() {

        System.out.println("Enter name of Teacher to remove:");

        String name = sc.nextLine();

        Teacher t = findTeacher(name);
        if (t == null) {
            return;
        }

        System.out.println("Do you want to remove:");
        System.out.println(t);
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {
            tManager.removeTeacher(t);
            System.out.println("Removed teacher");
        } else {
            System.out.println("No changes made");
        }
    }

    public void addTeacherToCourse() {

        System.out.println("Enter name of Teacher: ");

        String name = sc.nextLine();

        Teacher t = findTeacher(name);
        if (t == null) {
            return;
        }

        System.out.println("Enter name of Course: ");

        String courseName = sc.nextLine();

        CourseMenu cMenu = new CourseMenu();
        Course c = cMenu.findCourse(courseName);
        if (c == null) {
            return;
        }

        for (Course course : t.getCourses()) {
            if (course.getId() == c.getId()) {
                System.out.println(t.getName() + " is already teaching: " + c.getName());
                return;
            }
        }

        System.out.println("Do you want to add: " + t.getName() + " as a teacher of course: " + c.getName() + "?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {
            tManager.addCourseToTeacher(t, c);
            System.out.println("Teacher added to course");
        } else {
            System.out.println("No updates made");
        }

    }

    public void removeTeacherFromCourse() {

        System.out.println("Enter name of Course: ");

        String courseName = sc.nextLine();

        CourseMenu cMenu = new CourseMenu();
        Course c = cMenu.findCourse(courseName);

        if (c == null) {
            return;
        }

        if (c.getTeachers().isEmpty()) {
            System.out.println("There are currently no teachers for " + c.getName());
            return;
        }

        Teacher t = null;

        if (c.getTeachers().size() > 1) {
            System.out.println("Select Teacher to remove:");

            int i = 1;
            for (Teacher teacher : c.getTeachers()) {
                System.out.println(i + ": " + teacher.getName());
                i++;
            }
            int getT = sc.nextInt();
            sc.nextLine();

            t = c.getTeachers().get((getT - 1));
        } else {
            t = c.getTeachers().get(0);
        }

        System.out.println("Do you want to remove: " + t.getName() + " as a teacher of course: " + c.getName() + "?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {
            tManager.removeCourseFromTeacher(t, c);
            System.out.println("Teacher removed from course");
        } else {
            System.out.println("No updates made");
        }

    }

    public Teacher findTeacher(String name) {

        List<Teacher> teachers = tManager.findTeacher(name);

        Teacher t = null;

        if (teachers.isEmpty()) {
            System.out.println("Could not find a teacher with that name");
            return null;
        }

        if (teachers.size() > 1) {
            System.out.println("Found " + teachers.size() + " teachers");
            System.out.println("Select: ");

            int i = 1;
            for (Teacher teacher : teachers) {
                System.out.println(i + ": " + teacher);
                i++;
            }

            int getT = sc.nextInt();
            sc.nextLine();

            t = teachers.get((getT - 1));

            System.out.println("Selected: " + t);

        }

        if (teachers.size() == 1) {
            t = teachers.get(0);
            System.out.println("Found: " + t);
        }

        return t;

    }

}
