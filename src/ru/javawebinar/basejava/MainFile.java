package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        printDirectory("./src", new StringBuilder());
    }

    public static void printDirectory(String name, StringBuilder s) {
        File dir = new File(name);
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                if (!(file.isDirectory())) {
                    System.out.println(s + file.getName());
                } else {
                    System.out.println(s + file.getName());
                    printDirectory(file.getAbsolutePath(), s.append("...."));
                }
            }
            if (s.length() >= 3) {
                s.delete(0, 4);
            }
        } else {
            System.out.println("список пуст");
        }
    }
}
