package menus;

import java.util.List;
import java.util.Scanner;
import managers.CourseManager;
import schooldatabase.domain.Course;
import schooldatabase.domain.Teacher;

public class CourseMenu {

    CourseManager cManager = new CourseManager();

    Scanner sc = new Scanner(System.in);

    public void Menu() {

        boolean courseLoop = true;

        while (courseLoop) {

            System.out.println("");
            System.out.println("Manage Courses");
            System.out.println("");
            System.out.println("1. Add a Course");
            System.out.println("2. Update information about a Course");
            System.out.println("3. View information about a Course");
            System.out.println("4. View information about all Courses");
            System.out.println("5. Remove a Course");
            System.out.println("6. View all Teachers in a Course");
            System.out.println("7. Add a Teacher to a Course");
            System.out.println("8. Remove a Teacher from a Course");
            System.out.println("9. Add a Course to an Education");
            System.out.println("10. Remove a Course from an Education");
            System.out.println("0. Main menu");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                addCourse();

            } else if (choice == 2) {

                updateCourse();

            } else if (choice == 3) {

                viewCourse();

            } else if (choice == 4) {

                viewAllCourses();

            } else if (choice == 5) {

                removeCourse();

            } else if (choice == 6) {

                viewAllTeachersInCourse();

            } else if (choice == 7) {
                TeacherMenu tMenu = new TeacherMenu();
                tMenu.addTeacherToCourse();

            } else if (choice == 8) {
                TeacherMenu tMenu = new TeacherMenu();
                tMenu.removeTeacherFromCourse();

            } else if (choice == 9) {
                EducationMenu eMenu = new EducationMenu();
                eMenu.addCourseToEducation();

            } else if (choice == 10) {
                EducationMenu eMenu = new EducationMenu();
                eMenu.removeCourseFromEducation();

            } else if (choice == 0) {
                courseLoop = false;
            } else {
                System.out.println("Invalid input");
            }
        }

    }

    public void addCourse() {

        System.out.println("Enter name of the Course:");

        String name = sc.nextLine();

        cManager.addCourse(name);

        System.out.println("Added " + name);

    }

    public void updateCourse() {

        System.out.println("Enter name of Course to update:");

        String name = sc.nextLine();

        Course c = findCourse(name);
        if (c == null) {
            return;
        }

        System.out.println("Do you want to update: ");
        System.out.println(c);
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {

            System.out.println("Enter new name:");

            String newName = sc.nextLine();

            cManager.updateCourse(c, newName);

            System.out.println("Updated");

        } else {
            System.out.println("No changes made");
        }

    }

    public void viewCourse() {

        System.out.println("Enter name of Course to view:");

        String name = sc.nextLine();

        Course c = findCourse(name);
        if (c == null) {
            return;
        }

        System.out.println(c);

    }

    public void viewAllCourses() {

        List<Course> courses = cManager.showAllCourses();

        if (courses.isEmpty()) {
            System.out.println("Currently there are no courses");
            return;
        }

        for (Course c : courses) {
            System.out.println(c);
        }

    }

    public void removeCourse() {

        System.out.println("Enter name of Course to remove:");

        String name = sc.nextLine();

        Course c = findCourse(name);
        if (c == null) {
            return;
        }

        System.out.println("Do you want to remove:");
        System.out.println(c);
        System.out.println("1. Yes");
        System.out.println("2. No");

        int yesno = sc.nextInt();
        sc.nextLine();

        if (yesno == 1) {
            cManager.removeCourse(c);
            System.out.println("Removed course");
        } else {
            System.out.println("No changes made");
        }

    }

    public void viewAllTeachersInCourse() {

        System.out.println("Enter name of course: ");

        String name = sc.nextLine();

        Course c = findCourse(name);
        if (c == null) {
            return;
        }

        if (c.getTeachers().isEmpty()) {
            System.out.println("There are currently no teachers who teaches " + c.getName());
            return;
        }

        System.out.println("Teachers who teaches " + c.getName() + ":");
        for (Teacher teacher : c.getTeachers()) {
            System.out.println(teacher);
        }

    }

    public Course findCourse(String name) {

        List<Course> courses = cManager.findCourse(name);

        Course c = null;

        if (courses.isEmpty()) {
            System.out.println("Could not find a course with that name");
            return null;
        }

        if (courses.size() > 1) {
            System.out.println("Found " + courses.size() + " courses");
            System.out.println("Select: ");

            int i = 1;
            for (Course course : courses) {
                System.out.println(i + ": " + course);
                i++;
            }

            int getC = sc.nextInt();
            sc.nextLine();

            c = courses.get((getC - 1));

            System.out.println("Selected: " + c);

        }

        if (courses.size() == 1) {
            c = courses.get(0);
            System.out.println("Found: " + c);
        }

        return c;

    }

}
