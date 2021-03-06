package ro.ucv.inf.ead.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ro.ucv.inf.ead.jpa.model.Student;

@Repository("studentRepository")
public class StudentRepositoryImpl implements StudentRepository {

  @PersistenceContext
  private EntityManager em;

  public StudentRepositoryImpl() {
    System.out.println("Create a StudentRepositoryImpl object.");
  }

  public Student findOne(Long id) {
    return em.find(Student.class, id);
  }

  public Student findByName(String name) {
    Query query = em.createQuery("SELECT s FROM Student s  WHERE s.name = :name");
    query.setParameter("name", name);
    List<Student> students = (List<Student>) query.getResultList();
    if (students.size() > 0){
      return students.get(0);
    } else {
      return null;
    }
  }
  
  public List<Student> findAll() {
    Query query = em.createQuery("SELECT s FROM Student s");
    List<Student> students = (List<Student>) query.getResultList();
    return students;
  }

  public Student save(Student student) {
    if (student.getId() == null) {
      em.persist(student);
      em.flush();
    } else {
      student = em.merge(student);
    }
    return student;
  }

  public void delete(Long id) {
    Student s = em.find(Student.class, id);
    if (id != null) {
      em.remove(s);
    }

  }

}
