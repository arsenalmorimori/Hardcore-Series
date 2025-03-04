package com.pack.hardcoreinteam_07;

public class Student {
    private int id;
    private String name;
    private int age;
    private String course;
    private Float gwa;

    public Student(){

    }

    public Student(int id, String name, int age, String course, Float gwa) {
        this.age = age;
        this.course = course;
        this.gwa = gwa;
        this.id = id;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Float getGwa() {
        return gwa;
    }

    public void setGwa(Float gwa) {
        this.gwa = gwa;
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

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", gwa=" + String.format("%.2f ",gwa) +
                "}";
    }
}
