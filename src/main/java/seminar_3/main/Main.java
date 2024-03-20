package seminar_3.main;

import seminar_3.dbobjects.Student;
import seminar_3.model.DBTransit;
import seminar_3.model.JDBC;

public class Main {

    public static void main(String[] args) {

        DBTransit<Student> dbTransit = new DBTransit<>(new JDBC());
        Student student = new Student(1, "Lone", "Wane", 32);
        System.out.println(dbTransit.saveOrUpdate(student));
        System.out.println(dbTransit.simpleSelect());

    }
}
