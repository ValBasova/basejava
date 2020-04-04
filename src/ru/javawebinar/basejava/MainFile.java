package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        printDirectory(".");
    }

    public static void printDirectory(String name) {
        File dir = new File(name);
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                if (!(file.isDirectory())) {
                    System.out.println(file.getName());
                } else {
                    printDirectory(file.getAbsolutePath());
                }
            }
        } else {
            System.out.println("список пуст");
        }
    }
}
