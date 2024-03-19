package seminar_3;

import seminar_3.Annotation.Column;
import seminar_3.Annotation.Id;
import seminar_3.Annotation.Table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class DBTransit<T> {

    public JDBC connection;


    public DBTransit(JDBC connection) {
        this.connection = connection;
    }


//    /**
//     * Проверяем, является ли переданный объект
//     * подходящим для сохранения в базу данных
//     *
//     * @return - true если объект подходит, false в ином случае
//     */
//    private boolean checkClass(Class<?> expectedClass) {
//        boolean isTableAnnotation = false;
//        boolean isColumnAnnotation = false;
//        boolean isIdAnnotation = false;
//        for (Method method : expectedClass.getDeclaredMethods()) {
//            if (method.getAnnotation(Table.class) != null) {
//                isTableAnnotation = true;
//            }
//            if (method.getAnnotation(Column.class) != null) {
//                isColumnAnnotation = true;
//            }
//            if (method.getAnnotation(Id.class) != null) {
//                isIdAnnotation = true;
//            }
//
//        }
//        return isTableAnnotation && isColumnAnnotation && isIdAnnotation;
//    }

    public boolean save(T object) {
            Class<?> objectClass = object.getClass();
            int id = getId(objectClass, object);
            String firstName;
            String lastName;
            int age;

    }

    /**
     * По аннотации ищет значение ID объекта
     *
     * @param objectClass - класс объекта
     * @return если у аннотации есть параметр, то присваивает его новому полю объекта,
     * если нет, то выполняет метод, возвращая значение id
     */
    private int getId(Class<?> objectClass, T obj) {
        int id = 0;
        for (Method method : objectClass.getMethods()) {
            if (method.getAnnotation(Id.class) != null) {
                if (method.getAnnotation(Id.class).id() > 0) return method.getAnnotation(Id.class).id();
                else {
                    try {
                        id = (int) method.invoke(obj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return id;
    }

    private String getFirstName(Class<?> objectClass, T obj) {
        for (Method method : objectClass.getMethods()) {
            if (method.getAnnotation(Column.class) != null && method.getName().equals("getFirstName")) {
                if (!method.getAnnotation(Column.class).name().equals(""))
                    return method.getAnnotation(Column.class).name();
                else {
                    try {
                        return (String) method.invoke(obj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }

}
