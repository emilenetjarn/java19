package schooldatabase.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private LocalDate birthDate;

    @ManyToOne
    private Education education;

    public Student() {
    }

    public Student(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
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
        if (this.education == null) {
            return "Student{" + "id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", education=none}";
        } else {
            return "Student{" + "id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", education=" + education.getName()+"}";
        }
    }

}
