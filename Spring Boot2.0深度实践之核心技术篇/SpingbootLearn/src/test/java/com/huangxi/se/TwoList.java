package com.huangxi.se;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TwoList {

    public static void main(String[] args) {

        List<Person> list1 = new ArrayList<>();
        //数据库中查找得到
        List<Person> list2 = new ArrayList<>();
        List<Person> listDelete = new ArrayList<>();
        List<Person> listExist = new ArrayList<>();
        List<Person> listNew = new ArrayList<>();

        Map<String, Person> personMap = list1.stream().collect(Collectors.toMap(data ->
            data.getCar() + "_" + data.getBook(), Function.identity(),(o1,o2)->o1));
        String key = null;
        for(Person person: list2){
            //原
            key = person.getBook()+"_"+person.getCar();
            if(!personMap.containsKey(key)){
                listDelete.add(person);
            }else{
                // add to exist list
                listExist.add(person);
            }
        }

        for(Person person: listExist){
            key = person.getBook()+"_"+person.getCar();
            if(!personMap.containsKey(key)){
                listNew.add(person);
            }
        }
        //批量删除

        //批量插入
    }
}

@Data
class Person{

    private int id;

    private String name;

    private String book;

    private String car;
}