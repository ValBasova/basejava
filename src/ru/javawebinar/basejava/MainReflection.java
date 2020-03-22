package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws Exception {
        Resume r = new Resume("Bill");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        // TODO : invoke r.toString via reflection
        Method method = r.getClass().getMethod("toString", null);
        System.out.println(method.invoke(r));
    }

}
