package menus;

import java.util.List;
import java.util.Scanner;
import managers.EducationManager;
import schooldatabase.domain.Course;
import schooldatabase.domain.Education;
import schooldatabase.domain.Student;

public class EducationMenu {

    EducationManager eManager = new EducationManager();

    Scanner sc = new Scanner(System.in);

    public void Menu() {

        boolean educationLoop = true;

        while (educationLoop) {

            System.out.println("");
            System.out.println("Manage Educations");
            System.out.println("");
            System.out.println("1. Add an Education");
            System.out.println("2. Update information about an Education");
            System.out.println("3. View information about an Education");
            System.out.println("4. View information about all Educations");
            System.out.println("5. Remove an Education");
            System.out.println("6. View all Students in an Education");
            System.out.println("7. Add a Student to an Education");
            System.out.println("8. Remove a Student from an Education");
            System.out.println("9. View all Courses in an Education");
            System.out.println("10. Add a Course to an Education");
            System.out.println("11. Remove a Course From an Education");
            System.out.println("0. Main menu");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                addEducation();

            } else if (choice == 2) {

                updateEducation();

            } else if (choice == 3) {

                viewEducation();

            } else if (choice == 4) {

                viewAllEducations();

            } else if (choice == 5) {

                removeEducation();

            } else if (choice == 6) {

                viewAllStudentsInEducation();

            } else if (choice == 7) {
                StudentMenu sMenu = new StudentMenu();
                sMenu.addStudentToEducation();

            } else if (choice == 8) {
                StudentMenu sMenu = new StudentMenu();
                sMenu.removeStudentFromEducation();

            } else if (choice == 9) {

                viewAllCoursesInEducation();

            } else if (choice == 10) {

                addCourseToEducation();

            } else if (choice == 11) {

                removeCourseFromEducation();

            } else if (choice == 0) {
                educationLoop = false;
            } else {
                System.out.println("Invalid input");
            }
        }

    }

    public void addEducation() {

        System.out.println("Enter name:");

        String name = sc.nextLine();

        eManager.addEducation(name);

        System.out.println("Added " + name);

    }

    public void updateEducation() {

        System.out.println("Enter name of Education to update:");

        String name = sc.nextLine();

        Education e = findEducation(name);
        if (e == null) {
            return;
        }

        System.out.println("Do you want to update:");
        System.out.println(e);
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {

            System.out.println("Enter new name:");

            String newName = sc.nextLine();

            eManager.updateEducation(e, newName);
            System.out.println("Updated");

        } else {
            System.out.println("No changes made");
        }

    }

    public void viewEducation() {

        System.out.println("Enter name of Education to view:");

        String name = sc.nextLine();

        Education e = findEducation(name);
        if (e == null) {
            return;
        }

        System.out.println(e);

    }

    public void viewAllEducations() {

        List<Education> educations = eManager.showAllEducations();

        if (educations.isEmpty()) {
            System.out.println("Currently there are no educations");
            return;
        }

        for (Education e : educations) {
            System.out.println(e);
        }

    }

    public void removeEducation() {

        System.out.println("Enter name of Education to remove:");

        String name = sc.nextLine();

        Education e = findEducation(name);
        if (e == null) {
            return;
        }

        System.out.println("Do you want to remove:");
        System.out.println(e);
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {
            eManager.removeEducation(e);
            System.out.println("Removed education");
        } else {
            System.out.println("No changes made");
        }

    }

    public void viewAllStudentsInEducation() {

        System.out.println("Enter name of education: ");

        String name = sc.nextLine();

        Education e = findEducation(name);
        if (e == null) {
            return;
        }

        if (e.getStudents().isEmpty()) {
            System.out.println("Currently there are no students in " + e.getName());
            return;
        }

        System.out.println("Students in " + e.getName() + ":");

        for (Student student : e.getStudents()) {
            System.out.println(student);
        }

    }

    public void viewAllCoursesInEducation() {

        System.out.println("Enter education name:");

        String name = sc.nextLine();

        Education e = findEducation(name);
        if (e == null) {
            return;
        }

        if (e.getCourses().isEmpty()) {
            System.out.println("Currently there are no courses in " + e.getName());
            return;
        }

        System.out.println("Courses in " + e.getName() + ":");

        for (Course course : e.getCourses()) {
            System.out.println(course);
        }

    }

    public void addCourseToEducation() {

        System.out.println("Enter name of education: ");

        String name = sc.nextLine();

        Education e = findEducation(name);
        if (e == null) {
            return;
        }

        System.out.println("Enter name of course to add: ");

        String courseName = sc.nextLine();

        CourseMenu cMenu = new CourseMenu();
        Course c = cMenu.findCourse(courseName);
        if (c == null) {
            return;
        }

        if (c.getEducation() == null) {

            System.out.println("Do you want to add course: " + c.getName() + " to education: " + e.getName() + "?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            int yesno = sc.nextInt();
            sc.nextLine();

            if (yesno == 1) {
                eManager.addCourseToEducation(c, e);
                System.out.println("Added course to education");
            } else {
                System.out.println("No updates made");
            }
        } else {
            System.out.println(c.getName() + " is already part of education: " + c.getEducation().getName());
        }
    }

    public void removeCourseFromEducation() {

        System.out.println("Enter name of education: ");
        String name = sc.nextLine();

        Education e = findEducation(name);
        if (e == null) {
            return;
        }

        if (e.getCourses().isEmpty()) {
            System.out.println(e.getName() + " currently does not have any courses");
            return;
        } else {
            System.out.println("Courses in " + e.getName() + ":");
            for (Course course : e.getCourses()) {
                System.out.println(course);
            }
        }

        System.out.println("");
        System.out.println("Enter name of course to remove: ");

        String courseName = sc.nextLine();

        CourseMenu cMenu = new CourseMenu();
        Course c = cMenu.findCourse(courseName);
        if (c == null) {
            return;
        }

        System.out.println("Do you want to remove course: " + c.getName() + " from education: " + e.getName() + "?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {
            eManager.removeCourseFromEducation(c, e);
            System.out.println("Removed course from education");
        } else {
            System.out.println("No updates made");
        }

    }

    public Education findEducation(String name) {

        List<Education> educations = eManager.findEducation(name);

        Education e = null;

        if (educations.isEmpty()) {
            System.out.println("Could not find an education with that name");
            return null;
        }

        if (educations.size() > 1) {
            System.out.println("Found " + educations.size() + " educations");
            System.out.println("Select: ");

            int i = 1;
            for (Education education : educations) {
                System.out.println(i + ": " + education);
                i++;
            }

            int getE = sc.nextInt();
            sc.nextLine();

            e = educations.get((getE - 1));

            System.out.println("Selected: " + e);

        }

        if (educations.size() == 1) {
            e = educations.get(0);
            System.out.println("Found: " + e);
        }

        return e;

    }

}
