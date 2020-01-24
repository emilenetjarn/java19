package schooldatabase.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Teacher> teachers;

    @ManyToOne
    private Education education;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
        this.teachers = new ArrayList<>();
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

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public void setEducationNull() {
        this.education = null;
    }

    @Override
    public String toString() {
        if (this.education == null && this.teachers.isEmpty()) {
            return "Course{" + "id = " + id + ", name = " + name + ", education = none" + ", teachers = none" + "}";
        } else if (this.teachers.isEmpty()) {
            return "Course{" + "id = " + id + ", name = " + name + ", education = " + education.getName() + ", teachers = none" + "}";
        } else if (this.education == null) {
            String t = "";

            for (Teacher teacher : teachers) {
                t = t + teacher.getName() + ", ";
            }

            return "Course{" + "id = " + id + ", name = " + name + ", education = none" + ", teachers = " + t + "}";
        } else {
            String t = "";

            for (Teacher teacher : teachers) {
                t = t + teacher.getName() + ", ";
            }

            return "Course{" + "id = " + id + ", name = " + name + ", education = " + education.getName() + ", teachers = " + t + "}";
        }
    }

    public void addTeacher(Teacher t) {
        this.teachers.add(t);
    }

    public void removeTeacher(Teacher t) {
        this.teachers.remove(t);
    }

}
