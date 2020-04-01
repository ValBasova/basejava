package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ResumeTestData {
    public static void main(String[] args) throws MalformedURLException {
        Resume resume = new Resume("Григорий Кислин");

        resume.getContacts().put(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");

        String personal = "Аналитический склад ума, сильная логика, креативность, инициативность." +
                " Пурист кода и архитектуры";
        TextSection personalSection = resume.getSection(SectionType.PERSONAL);
        personalSection.setText(personal);


        String objective = "Ведущий стажировок и корпоративного обучения по Java Web " +
                "и Enterprise технологиям";
        TextSection objectiveSection = resume.getSection(SectionType.OBJECTIVE);
        objectiveSection.setText(objective);

        ArrayList<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java Enterprise\", \"Многомодульный maven.\" ");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM.");
        ListSection achievementSection = resume.getSection(SectionType.ACHIEVEMENT);
        achievementSection.setTextList(achievement);

        ArrayList<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        ListSection qualificationSection = resume.getSection(SectionType.QUALIFICATIONS);
        qualificationSection.setTextList(qualifications);

        Organization alkatel = new Organization("Alkatel",new URL("https://www.pega.com/products/pega-platform/robotic-automation"),
                "1997-09", "2005-01", "Инженер по аппаратному и программному тестированию");
        Organization siemens = new Organization("Siemens AG", new URL("https://new.siemens.com/ru/ru.html") ,"2007-03", "2008-06",
                "Разработка информационной модели, проектирование интерфейсов, реализация" +
                        " и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)");
        ArrayList<Organization> organizations = new ArrayList<>();
        organizations.add(alkatel);
        organizations.add(siemens);
        OrganizationSection organizationSection = resume.getSection(SectionType.EXPERIENCE);
        organizationSection.setOrganizationList(organizations);

        System.out.println(resume);
    }
}
