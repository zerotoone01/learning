package io.renren;

import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        System.out.println(9999999);
        List<String> names = Arrays.asList(" 张三 ", " 李四 ", " 王老五 ", " 李三 ", " 刘老四 ", " 王小二 ", " 张四 ", " 张五六七 ");

        String maxLenStartWithZ = names.stream()
                .filter(name -> name.startsWith(" 张 "))
                .mapToInt(String::length)
                .max()
                .toString();
        System.out.println(maxLenStartWithZ);
    }
}
