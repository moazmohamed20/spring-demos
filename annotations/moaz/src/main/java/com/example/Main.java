package com.example;

import java.util.logging.Logger;

import com.roma.annotation.Hack;

@Hack("Entry Class of The App")
public class Main {

    static Logger logger = Logger.getLogger("Main");

    @Hack("RoMa")
    private int i;

    public static void main(String[] args) {
        System.out.println("Hello world!");
        logger.info("Hello world!");

        PersonBuilder builder = new PersonBuilder();
        builder.age(25);
        builder.name("John");
        Person myClass = builder.build();

        System.out.println(myClass);
    }
}

// @Marker
// class Example1 {
// }

// @Marker
// interface Example2 {
// void foo();
// }
