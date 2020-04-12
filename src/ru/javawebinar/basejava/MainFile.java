package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    static int counter = 0;

    public static void main(String[] args) {
        printDirectory("./src");
    }

    public static void printDirectory(String name) {
        File dir = new File(name);
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                if (!(file.isDirectory())) {
                    for (int i = 0; i < counter; i++) {
                        System.out.print('\t');
                    }
                    System.out.println(file.getName());
                } else {
                    for (int i = 0; i < counter; i++) {
                        System.out.print('\t');
                    }
                    System.out.println(file.getName());
                    counter++;
                    printDirectory(file.getAbsolutePath());
                }
            }
            counter--;
        } else {
            System.out.println("список пуст");
        }
    }
}
