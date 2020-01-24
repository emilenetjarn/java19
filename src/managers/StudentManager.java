package managers;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import schooldatabase.domain.Education;
import schooldatabase.domain.Student;

public class StudentManager {

    public void addStudent(String name, LocalDate ld) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        Student newStudent = new Student(name, ld);

        em.persist(newStudent);

        em.getTransaction().commit();
        em.close();
    }

    public void updateStudent(Student student, String name, LocalDate ld) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        student.setName(name);
        student.setBirthDate(ld);

        em.merge(student);

        em.getTransaction().commit();
        em.close();
    }

    public List<Student> showAllStudents() {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Student> q = em.createQuery("select s from Student s", Student.class);
        
        List<Student> students = q.getResultList();
        
        em.getTransaction().commit();
        em.close();
        
        return students;
    }

    public void removeStudent(Student studentToRemove) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        Education e = studentToRemove.getEducation();
        
        e.removeStudent(studentToRemove);
        
        studentToRemove.setEducationNull();
        
        em.merge(e);
        em.remove(em.merge(studentToRemove));

        em.getTransaction().commit();
        em.close();
    }

    public void addStudentToEducation(Student s, Education e) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        s.setEducation(e);
        e.addStudent(s);
        
        em.merge(s);
        em.merge(e);
        
        em.getTransaction().commit();
        em.close();
    }

    public void removeStudentFromEducation(Student s, Education e) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        e.removeStudent(s);
        s.setEducationNull();
        
        em.merge(s);
        em.merge(e);
        
        em.getTransaction().commit();
        em.close();
    }

    public Student findStudent(int id) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        
        Student foundStudent = null;
        
        try {
        foundStudent = em.find(Student.class, id);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input");
            em.getTransaction().rollback();
            em.close();
            return null;
        }
        
        em.getTransaction().commit();
        em.close();

        return foundStudent;
    }
    
    public List<Student> findStudent(String name) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        TypedQuery<Student> q = em.createQuery("select s from Student s where s.name like :name", Student.class);
        
        q.setParameter("name", name);
        
        List<Student> students = q.getResultList();
        
        em.getTransaction().commit();
        em.close();
        
        return students;
    }

}
