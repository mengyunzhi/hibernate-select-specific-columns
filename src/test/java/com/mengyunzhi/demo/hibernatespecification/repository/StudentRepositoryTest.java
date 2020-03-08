package com.mengyunzhi.demo.hibernatespecification.repository;

import com.mengyunzhi.demo.hibernatespecification.entity.Clazz;
import com.mengyunzhi.demo.hibernatespecification.entity.School;
import com.mengyunzhi.demo.hibernatespecification.entity.Student;
import com.mengyunzhi.demo.hibernatespecification.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class StudentRepositoryTest {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private EntityManager entityManager;

    Student student;

    @BeforeEach
    public void beforeEach() {
        School school = new School();
        school.setName("测试学校");
        Teacher teacher = new Teacher();
        teacher.setName("测试教师");
        Clazz clazz = new Clazz();
        clazz.setName("测试班级");
        this.student = new Student();
        student.setName("测试学生");
        teacher.setSchool(school);
        clazz.setTeacher(teacher);
        student.setClazz(clazz);
        this.studentRepository.save(student);
    }

    @Test
    public void find() {
        this.studentRepository.findById(student.getId()).get();
    }

    @Test
    public void findByColumn() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);

        criteriaQuery
                .multiselect(root.get("id"), root.get("name"))
                .where(criteriaBuilder.equal(root.get("id").as(Long.class), student.getId().toString()));
        TypedQuery<Student> query = this.entityManager.createQuery(criteriaQuery);

        List<Student> students = query.getResultList();
    }

    @Test
    public void findByColumnWithJoin() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);

        criteriaQuery
                .multiselect(root.get("id"),
                        root.get("name"),
                        root.get("clazz").get("id"),
                        root.get("clazz").get("teacher").get("id"))
                .where(criteriaBuilder.equal(root.get("id").as(Long.class), student.getId().toString()));

        TypedQuery<Student> query = this.entityManager.createQuery(criteriaQuery);

        List<Student> students = query.getResultList();
    }

    @Test
    public void findByColumnWithJoinAndTuple() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
        Root<Student> root = criteriaQuery.from(Student.class);

        criteriaQuery
                .multiselect(root.get("id"),
                        root.get("name"),
                        root.get("clazz").get("id"),
                        root.get("clazz").get("teacher").get("id"))
                .where(criteriaBuilder.equal(root.get("id").as(Long.class), student.getId().toString()));

        TypedQuery<Tuple> query = this.entityManager.createQuery(criteriaQuery);

        List<Tuple> tuples = query.getResultList();

        List<Student> students = new ArrayList<>();
        tuples.forEach(tuple -> {
            Student student = new Student();
            student.setId((Long) tuple.get(0));
            student.setName((String) tuple.get(1));
            student.setClazz(new Clazz());
            student.getClazz().setId((Long) tuple.get(2));
            student.getClazz().setTeacher(new Teacher());
            student.getClazz().getTeacher().setId((Long) tuple.get(3));
            students.add(student);
        });

    }


}