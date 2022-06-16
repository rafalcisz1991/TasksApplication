package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("preview_message", "Crud Application running");
        context.setVariable("goodbye_message", "E-mail sent correctly");
        context.setVariable("company_details", adminConfig.getCompanyName());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail.html", context);
    }

    public String buildTasksCountEmail(String message) {
        Context context1 = new Context();
        context1.setVariable("message", message);
        context1.setVariable("tasks_url", "https://rafalcisz1991.github.io/");
        context1.setVariable("button", "Visit website");
        context1.setVariable("admin_name", adminConfig.getAdminName());
        context1.setVariable("preview_message", "Crud Application running");
        context1.setVariable("goodbye_message", "E-mail sent correctly");
        context1.setVariable("company_details", adminConfig.getCompanyName());
        return templateEngine.process("taskMail/how_many_tasks_reminder.html", context1);
    }
}

