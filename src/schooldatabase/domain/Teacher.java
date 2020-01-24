package schooldatabase.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private LocalDate birthDate;

    @ManyToMany
    private List<Course> courses;

    public Teacher() {
    }

    public Teacher(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    @Override
    public String toString() {
        if (this.courses.isEmpty()) {
            return "Teacher{" + "id = " + id + ", name = " + name + ", birthDate = " + birthDate + ", courses = none}";
        } else {
            String c = "";

            for (Course course : courses) {
                c = c + course.getName() + ", ";
            }

            return "Teacher{" + "id = " + id + ", name = " + name + ", birthDate = " + birthDate + ", courses = " + c + "}";
        }
    }

    public void addCourse(Course c) {
        this.courses.add(c);
    }

    public void removeCourse(Course c) {
        this.courses.remove(c);
    }

}
