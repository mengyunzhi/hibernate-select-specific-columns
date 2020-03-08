package com.mengyunzhi.demo.hibernatespecification.entity;

import javax.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Clazz clazz;

    public Student() {
    }

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
        System.out.println("student construct");
    }

    public Student(Long id, String name, Long clazzId, Long teacherId) {
        this.id = id;
        this.name = name;
        this.clazz = new Clazz();
        this.clazz.setId(clazzId);
        this.clazz.setTeacher(new Teacher());
        this.clazz.getTeacher().setId(teacherId);
        System.out.println("student construct invoked");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
}
