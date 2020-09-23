package com.huangxi.reflect.model;

import java.util.List;

/**
 * @author huangxi
 * @description TODO
 * @date 2020-09-23
 */
public class Grade {
    private String name;
    private List<Student> students;

    public Grade(){}
    public Grade(String name,List<Student> students){
        this.name=name;
        this.students=students;
    }
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append(name+"\n ");
        for(Student s : students){
            sb.append(s.getNumberId()+"    ");
        }
        return sb.toString();
    }
}
