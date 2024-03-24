package seminar_4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class CRUD {

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
//            create(sessionFactory);
            System.out.println(search(sessionFactory));
            update(sessionFactory);
//            delete(sessionFactory);
            simpleQuery(sessionFactory);
        }
    }

    private static void create(SessionFactory sessionFactory) {
//        Student student = new Student();
//        student.setId(2L);
//        student.setFirstName("Liana");
//        student.setLastName("Strught");
//        student.setAge(20);
//        try (Session session = sessionFactory.openSession()) {
//            Transaction tx = session.beginTransaction();
//            session.persist(student);
//            tx.commit();
//        }
    }

    private static Student search(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Student.class, 1L);
        }
    }

    private static void update(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Student student = session.find(Student.class, 2L);
            student.setLastName("Vinci");
            session.merge(student);
            tx.commit();
        }
    }

    private static void delete(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Student student = search(sessionFactory);
            Transaction tx = session.beginTransaction();
            session.remove(student);
            tx.commit();
        }
    }

    private static void simpleQuery(SessionFactory sessionFactory) {
        try(Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("select s from Student s where s.age <= :age", Student.class);
            query.setParameter("age", 21);
            System.out.println(query.getSingleResult());
        }
    }
}
