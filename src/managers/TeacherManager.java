package managers;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import schooldatabase.domain.Course;
import schooldatabase.domain.Teacher;

public class TeacherManager {

    public void addTeacher(String name, LocalDate bd) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        Teacher newTeacher = new Teacher(name, bd);

        em.persist(newTeacher);

        em.getTransaction().commit();
        em.close();
    }

    public void updateTeacher(Teacher t, String name, LocalDate bd) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        t.setName(name);
        t.setBirthDate(bd);

        em.merge(t);

        em.getTransaction().commit();
        em.close();
    }

    public List<Teacher> showAllTeachers() {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Teacher> q = em.createQuery("select t from Teacher t", Teacher.class);

        List<Teacher> teachers = q.getResultList();

        em.getTransaction().commit();
        em.close();

        return teachers;
    }

    public void removeTeacher(Teacher t) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        for (Course c : t.getCourses()) {
            c.removeTeacher(t);
            em.merge(c);
        }
        t.setCoursesNull();

        em.remove(em.merge(t));

        em.getTransaction().commit();
        em.close();
    }

    public void addCourseToTeacher(Teacher t, Course c) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        t.addCourse(c);
        c.addTeacher(t);

        em.merge(t);
        em.merge(c);

        em.getTransaction().commit();
        em.close();
    }

    public void removeCourseFromTeacher(Teacher t, Course c) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();
        
        Teacher tea = em.find(Teacher.class, t.getId());
        Course cou = em.find(Course.class, c.getId());

        tea.removeCourse(cou);
        cou.removeTeacher(tea);

        em.merge(cou);
        em.merge(tea);

        em.getTransaction().commit();
        em.close();
    }

    public Teacher findTeacher(int id) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();
        em.getTransaction().begin();

        Teacher t = null;

        try {
            t = em.find(Teacher.class, id);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not find a teacher with that id");
            em.getTransaction().rollback();
            em.close();
            return null;
        }

        em.getTransaction().commit();
        em.close();

        return t;
    }

    public List<Teacher> findTeacher(String name) {
        EntityManager em = schoolmanagementproject.SchoolManagementProject.emf.createEntityManager();

        em.getTransaction().begin();

        TypedQuery<Teacher> q = em.createQuery("select t from Teacher t where t.name like :name", Teacher.class);

        q.setParameter("name", name);

        List<Teacher> teachers = q.getResultList();

        em.getTransaction().commit();
        em.close();

        return teachers;

    }

}
