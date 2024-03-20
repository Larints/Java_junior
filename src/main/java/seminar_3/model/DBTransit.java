package seminar_3.model;

import seminar_3.annotation.Column;
import seminar_3.annotation.Id;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DBTransit<T> {

    public JDBC connection;


    public DBTransit(JDBC connection) {
        this.connection = connection;
    }


    /**
     * Метод сохранения или обновления в базе.
     * Происходит проверка найденых аннотаций, если их нет, возвращаем false
     * Иначе сохраняем объект в базе
     * @param object - передаём нужный объект для сохранения в базе данных
     * @return - возвращаем true или false в зависимости от результата
     */
    public boolean saveOrUpdate(T object) {
        Class<?> objectClass = object.getClass();
        int id = getId(objectClass, object);
        String firstName = getFirstName(objectClass, object);
        String lastName = getLastName(objectClass, object);
        int age = getAge(objectClass, object);
        if (id == -1 || firstName == null || lastName == null || age == -1) return false;
        else {
            connection.saveOrUpdateObject(id, firstName, lastName, age);
            return true;
        }
    }

    /**
     * По аннотации ищет значение ID объекта
     * @param objectClass - класс объекта
     * @return если у аннотации есть параметр, то присваивает его новому полю объекта,
     * если нет, то выполняет метод, возвращая значение id
     * если аннотация не найдена, возвращает -1
     */
    private int getId(Class<?> objectClass, T obj) {
        int id = -1;
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

    /**
     * По аннотации ищет имя объекта (класса)
     * @param objectClass - класс объекта
     * @param obj - объект у которого мы вызываем метод
     * @return возвращает имя объекта, если у аннотации есть переданный параметр, возвращает его
     * иначе возвращает null
     */
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

    /**
     * По аннотации ищет фамилию объекта (класса)
     * @param objectClass - класс объекта
     * @param obj - объект у которого мы вызываем метод
     * @return возвращает имя объекта, если у аннотации есть переданный параметр, возвращает его
     * иначе возвращает null
     */
    private String getLastName(Class<?> objectClass, T obj) {
        for (Method method : objectClass.getMethods()) {
            if (method.getAnnotation(Column.class) != null && method.getName().equals("getLastName")) {
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

    /**
     * Возвращает по аннотации возраст объекта
     * @param objectClass - класс переданного объекта
     * @param obj - объект у которого вызывается метод
     * @return - возвращает либо найденный возраст объекта, либо -1
     */
    private int getAge(Class<?> objectClass, T obj) {
        int age = -1;
        for (Method method : objectClass.getMethods()) {
            if (method.getAnnotation(Column.class) != null && method.getName().equals("getAge")) {
                try {
                    age = (int) method.invoke(obj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return age;
    }

    /**
     * Простой запрос для представления
     * @return - возвращает все поля из базы
     */
    public String simpleSelect() {
        return connection.simpleSelect();
    }
}
