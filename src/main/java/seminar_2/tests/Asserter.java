package seminar_2.tests;

import seminar_2.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Asserter {



    public static void assertEquals(int actual,int expected) {
      if (actual != expected) {
          throw new RuntimeException();
      }
    }
}
