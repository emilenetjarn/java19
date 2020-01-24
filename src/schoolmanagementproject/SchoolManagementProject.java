package schoolmanagementproject;

import java.time.LocalDate;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import menus.CourseMenu;
import menus.EducationMenu;
import menus.StudentMenu;
import menus.TeacherMenu;
import schooldatabase.domain.Course;
import schooldatabase.domain.Education;
import schooldatabase.domain.Student;
import schooldatabase.domain.Teacher;

public class SchoolManagementProject {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    public static void main(String[] args) {

        setupData();

        mainMenu();

    }

    public static void mainMenu() {

        StudentMenu sm = new StudentMenu();
        TeacherMenu tm = new TeacherMenu();
        CourseMenu cm = new CourseMenu();
        EducationMenu em = new EducationMenu();

        Scanner sc = new Scanner(System.in);

        boolean loop = true;

        while (loop) {
            System.out.println("");
            System.out.println("School Management Program");
            System.out.println("Main menu");
            System.out.println("");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Teachers");
            System.out.println("3. Manage Courses");
            System.out.println("4. Manage Educations");
            System.out.println("0. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    sm.Menu();
                    break;
                case 2:
                    tm.Menu();
                    break;
                case 3:
                    cm.Menu();
                    break;
                case 4:
                    em.Menu();
                    break;
                case 0:
                    System.out.println("Exiting");
                    emf.close();
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    public static void setupData() {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Student s1 = new Student("aaa", LocalDate.of(1993, 3, 3));
        Student s2 = new Student("bbb", LocalDate.of(1994, 3, 3));
        Student s3 = new Student("ccc", LocalDate.of(1992, 11, 4));
        Student s4 = new Student("ddd", LocalDate.of(1999, 1, 1));

        Teacher t1 = new Teacher("zzz", LocalDate.of(1989, 4, 4));
        Teacher t2 = new Teacher("sss", LocalDate.of(1988, 5, 14));
        Teacher t3 = new Teacher("vvv", LocalDate.of(1990, 3, 6));

        Course c1 = new Course("Biology 1");
        Course c2 = new Course("Biology 2");
        Course c3 = new Course("Math 1");
        Course c4 = new Course("Math 2");

        Education e1 = new Education("Biology");
        Education e2 = new Education("Math");

        s1.setEducation(e1);
        s2.setEducation(e1);
        s3.setEducation(e2);
        s4.setEducation(e2);

        t1.addCourse(c1);
        t1.addCourse(c2);
        t2.addCourse(c3);
        t2.addCourse(c4);
        t3.addCourse(c1);
        t3.addCourse(c3);

        c1.addTeacher(t1);
        c1.addTeacher(t3);
        c2.addTeacher(t1);
        c3.addTeacher(t2);
        c3.addTeacher(t3);
        c4.addTeacher(t2);

        c1.setEducation(e1);
        c2.setEducation(e1);
        c3.setEducation(e2);
        c4.setEducation(e2);

        e1.addStudent(s1);
        e1.addStudent(s2);
        e2.addStudent(s3);
        e2.addStudent(s4);
        e1.addCourse(c1);
        e1.addCourse(c2);
        e2.addCourse(c3);
        e2.addCourse(c4);

        em.persist(s1);
        em.persist(s2);
        em.persist(s3);
        em.persist(s4);
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.persist(e1);
        em.persist(e2);

        em.getTransaction().commit();
        em.close();

    }

}
