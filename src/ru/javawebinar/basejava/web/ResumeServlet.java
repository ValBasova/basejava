package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    public Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new SqlStorage(Config.get().getDb_url(), Config.get().getDb_user(), Config.get().getDb_password());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (uuid == null || uuid.length() == 0) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.getContacts().put(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection ts = r.getSection(type);
                        ts.setText(value);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = r.getSection(type);
                        ls.setTextList(Arrays.asList(value.split("\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        OrganizationSection organizationSection = r.getSection(type);
                        ArrayList<Organization> organizations = new ArrayList<>();
                        String[] values = request.getParameterValues(type.name());
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            String url = request.getParameter(type.name() + i + "url");
                            Organization organization = new Organization(name, url);
                            String[] posValues = request.getParameterValues(type.name() + i + "description");
                            for (int j = 0; j < posValues.length; j++) {
                                String startDate = request.getParameter(type.name() + i + "startDate" + j);
                                String endDate = request.getParameter(type.name() + i + "endDate" + j);
                                String title = request.getParameter(type.name() + i + "title" + j);
                                String description = posValues[j];
                                organization.addPosition(new Organization.Position(DateUtil.parse(startDate),
                                        DateUtil.parse(endDate), title, description));
                            }
                            organizations.add(organization);
                        }
                        organizationSection.setOrganizationList(organizations);
                        break;
                }
            } else {
                r.getSections().remove(type);
            }
        }
        if ((uuid == null || uuid.length() == 0)) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "add":
                r = Resume.EMPTY;
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(Organization.EMPTY);
                            if (section != null) {
                                for (Organization org : orgSection.getOrganizationList()) {
                                    List<Organization.Position> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Organization.Position.EMPTY);
                                    emptyFirstPositions.addAll(org.getPositions());
                                    emptyFirstOrganizations.add(new Organization(org.getName(), org.getUrl(), emptyFirstPositions));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganizations);
                            break;
                    }
                    r.getSections().put(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}