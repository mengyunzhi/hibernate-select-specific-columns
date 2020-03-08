package com.mengyunzhi.demo.hibernatespecification.repository;

import com.mengyunzhi.demo.hibernatespecification.entity.Student;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;

public interface StudentRepository extends CrudRepository<Student, Long>, JpaSpecificationExecutor {
}
