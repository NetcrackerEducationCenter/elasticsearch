package org.netcracker.educationcenter.elasticsearch;

import org.netcracker.educationcenter.elasticsearch.model.JiraIssue;
import org.netcracker.educationcenter.elasticsearch.operations.JiraIssueOperations;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Connection connection = new Connection();
        connection.makeConnection();
        JiraIssueOperations.insertJiraIssue(new JiraIssue("NO TIME", "ZZZ"));
        connection.closeConnection();
    }
}
