package managers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import schooldatabase.domain.Course;
import schooldatabase.domain.Education;
import schooldatabase.domain.Student;

public class EducationManager {

    public void addEducation(String name) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        Education newEducation = new Education(name);

        em.persist(newEducation);

        em.getTransaction().commit();
        em.close();
    }

    public void updateEducation(Education e, String name) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        e.setName(name);

        em.merge(e);

        em.getTransaction().commit();
        em.close();
    }

    public List<Education> showAllEducations() {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Education> q = em.createQuery("select e from Education e", Education.class);

        List<Education> educations = q.getResultList();

        em.getTransaction().commit();
        em.close();

        return educations;
    }

    public void removeEducation(Education e) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        for (Course c : e.getCourses()) {
            c.setEducationNull();
            em.merge(c);
        }
        
        for (Student s : e.getStudents()) {
            s.setEducationNull();
            em.merge(s);
        }
        
        em.remove(em.merge(e));

        em.getTransaction().commit();
        em.close();
    }

    public Education findEducation(int id) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        Education e = em.find(Education.class, id);

        em.getTransaction().commit();
        em.close();

        return e;
    }

    public List<Education> findEducation(String name) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Education> q = em.createQuery("select e from Education e where e.name like :name", Education.class);

        q.setParameter("name", name);

        List<Education> found = q.getResultList();
        
        em.getTransaction().commit();
        em.close();

        return found;
    }

    public void addCourseToEducation(Course c, Education e) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        c.setEducation(e);
        e.addCourse(c);

        em.merge(c);
        em.merge(e);

        em.getTransaction().commit();
        em.close();
    }

    public void removeCourseFromEducation(Course c, Education e) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        Course co = em.find(Course.class, c.getId());
        Education edu = em.find(Education.class, e.getId());
        
        co.setEducationNull();
        edu.removeCourse(co);
        em.merge(co);
        em.merge(edu);
        
        em.getTransaction().commit();
        em.close();
    }

}
