package managers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import schooldatabase.domain.Course;
import schooldatabase.domain.Teacher;

public class CourseManager {

    public void addCourse(String name) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        Course newCourse = new Course(name);
        
        em.persist(newCourse);
        
        em.getTransaction().commit();
        em.close();
    }

    public void updateCourse(Course c, String name) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        c.setName(name);
        
        em.merge(c);
        
        em.getTransaction().commit();
        em.close();
    }

    public List<Course> showAllCourses() {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        TypedQuery<Course> q = em.createQuery("select c from Course c", Course.class);
        
        List<Course> courses = q.getResultList();
        
        em.getTransaction().commit();
        em.close();
        
        return courses;
    }

    public void removeCourse(Course c) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        for (Teacher t : c.getTeachers()) {
            t.removeCourse(c);
            em.merge(t);
        }
        
        c.getEducation().removeCourse(c);
        c.setEducationNull();
        
        em.remove(em.merge(c));
        
        em.getTransaction().commit();
        em.close();
    }
    
    public Course findCourse(int id) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        Course c = null;
        
        try {
        c = em.find(Course.class, id);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input");
            em.getTransaction().rollback();
            em.close();
            return null;
        }
        
        em.getTransaction().commit();
        em.close();
        
        return c;
    }
    
    public List<Course> findCourse(String name) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        TypedQuery<Course> q = em.createQuery("select c from Course c where c.name like :name", Course.class);
        
        q.setParameter("name", name);
        
        List<Course> courses = q.getResultList();
        
        em.getTransaction().commit();
        em.close();
        
        return courses;
        
    }
    
    

}
