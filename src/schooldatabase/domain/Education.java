package schooldatabase.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @OneToMany(mappedBy = "education")
    private List<Student> students;

    @OneToMany(mappedBy = "education")
    private List<Course> courses;

    public Education() {
    }

    public Education(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void setCoursesNull() {
        this.courses.clear();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setStudentsNull() {
        this.students.clear();
    }

    @Override
    public String toString() {
        
        if (this.students.isEmpty() && this.courses.isEmpty()) {

            return "Education{" + "id = " + id + ", name = " + name + ", courses = none" + ", students = none " + "}";

        } else if (this.students.isEmpty()) {

            String c = "";

            for (Course course : courses) {
                c = c + course.getName() + ", ";
            }

            return "Education{" + "id = " + id + ", name = " + name + ", courses = " + c + "students = none " + "}";
        } else if (this.courses.isEmpty()) {

            String s = "";

            for (Student student : students) {
                s = s + student.getName() + ", ";
            }
            return "Education{" + "id = " + id + ", name = " + name + ", courses = none" + ", students = " + s + "}";
        } else {

            String c = "";

            for (Course course : courses) {
                c = c + course.getName() + ", ";
            }

            String s = "";

            for (Student student : students) {
                s = s + student.getName() + ", ";
            }

            return "Education{" + "id = " + id + ", name = " + name + ", courses = " + c + "students = " + s + "}";

        }
    }

    public void addCourse(Course c) {
        this.courses.add(c);
    }

    public void removeCourse(Course c) {
        this.courses.remove(c);
    }

    public void addStudent(Student s) {
        this.students.add(s);
    }

    public void removeStudent(Student s) {
        this.students.remove(s);
    }

}
