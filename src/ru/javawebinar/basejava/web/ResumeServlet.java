package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    public Storage storage = new SqlStorage(Config.get().getDb_url(), Config.get().getDb_user(), Config.get().getDb_password());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(
                "<html>\n" +
                        "<head>\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                        "    <title>Список всех резюме</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<style>                  " +
                        "table, th, td { " +
                        "border: 1px solid black; " +
                        "}" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<h2> Список резюме </h2>" +
                        "<table style = \"width:100%\" >" +
                        "<tr >" +
                        "<th > UUID </th >" +
                        "<th > Full name </th >" +
                        "</tr >");
        for (Resume resume : storage.getAllSorted()) {
            writer.write(
                    "<tr>\n" +
                            "<td>" + resume.getUuid() + "</td>\n" +
                            "<td>" + resume.getFullName() + "</td>\n" +
                            "</tr>\n");
        }
        writer.write("</table>\n" +
                "</body>\n" +
                "</html>\n");
    }
}