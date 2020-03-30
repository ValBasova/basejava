package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.util.ArrayList;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        resume.fillContacts(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.fillContacts(ContactType.GITHUB, "https://github.com/gkislin");

        String personal = "Аналитический склад ума, сильная логика, креативность, инициативность." +
                " Пурист кода и архитектуры";
        String objective = "Ведущий стажировок и корпоративного обучения по Java Web " +
                "и Enterprise технологиям";

        ArrayList<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java Enterprise\", \"Многомодульный maven.\" ");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM.");

        ArrayList<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");

        Organization alkatel = new Organization("Alkatel",
                "1997-09", "2005-01", "Инженер по аппаратному и программному тестированию");

        Organization siemens = new Organization("Siemens AG", "2007-03", "2008-06",
                "Разработка информационной модели, проектирование интерфейсов, реализация" +
                        " и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)");

        TextSection section = resume.getSection(SectionType.PERSONAL);
        section.addInfo(personal);
        TextSection section1 = resume.getSection(SectionType.OBJECTIVE);
        section1.addInfo(objective);

        ListSection section2 = resume.getSection(SectionType.ACHIEVEMENT);
        section2.addInfo(achievement);

        ListSection section3 = resume.getSection(SectionType.QUALIFICATIONS);
        section3.addInfo(qualifications);

        System.out.println(resume);
    }
}
