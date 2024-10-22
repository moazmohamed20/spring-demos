package com.example;

import com.roma.annotation.Builder;
import com.roma.annotation.Hack;
import com.roma.annotation.Logger;

@Hack
@Builder
@Logger
public class Person {
  public int age;
  @Hack("Zoz")
  public String name;

  @Override
  public String toString() {
    return "I am " + name + " and I am " + age + " years old.";
  }

  @Hack
  public void foo() {
  }
}
