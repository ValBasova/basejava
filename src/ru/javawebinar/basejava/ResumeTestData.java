package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class ResumeTestData {
    public static void main(String[] args) throws MalformedURLException {
        Resume resume = new Resume("Григорий Кислин");

        resume.getContacts().put(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");

        TextSection personalSection = resume.getSection(SectionType.PERSONAL);
        personalSection.setText("Аналитический склад ума, сильная логика, креативность, инициативность." +
                " Пурист кода и архитектуры");

        TextSection objectiveSection = resume.getSection(SectionType.OBJECTIVE);
        objectiveSection.setText("Ведущий стажировок и корпоративного обучения по Java Web " +
                "и Enterprise технологиям");

        ListSection achievementSection = resume.getSection(SectionType.ACHIEVEMENT);
        achievementSection.setTextList(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\",",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM."));

        ListSection qualificationSection = resume.getSection(SectionType.QUALIFICATIONS);
        qualificationSection.setTextList(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce"));

        Organization alcatel = new Organization("Alcatel", new URL("https://www.pega.com/products/pega-platform/robotic-automation"));
        alcatel.addPosition("1997-09", "2005-01", "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)");

        Organization siemens = new Organization("Siemens AG", new URL("https://new.siemens.com/ru/ru.html"));
        siemens.addPosition("2005-01", "2007-02", "Разработчик ПО", "Разработка информационной" +
                " модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)");

        OrganizationSection experienceSection = resume.getSection(SectionType.EXPERIENCE);
        experienceSection.setOrganizationList(Arrays.asList(alcatel,siemens));

        Organization alcatel2 = new Organization("Alcatel", new URL("https://www.pega.com/products/pega-platform/robotic-automation"));
        alcatel2.addPosition("1997-09", "1998-03", null, "6 месяцев обучения цифровым телефонным сетям (Москва)");

        OrganizationSection educationSection = resume.getSection(SectionType.EDUCATION);
        educationSection.setOrganizationList(Arrays.asList(alcatel2));

        System.out.println(resume);
    }
}
