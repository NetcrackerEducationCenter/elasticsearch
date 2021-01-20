package org.netcracker.educationcenter.elasticsearch.operations;

import org.elasticsearch.action.index.IndexRequest;
import org.netcracker.educationcenter.elasticsearch.model.JiraIssue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JiraIssueOperations {
    public static void insertJiraIssue(JiraIssue jiraIssue) {
        jiraIssue.setId(UUID.randomUUID().toString());
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("jiraIssueId", jiraIssue.getId());
        dataMap.put("jiraIssueTitle", jiraIssue.getIssueTitle());
        dataMap.put("jiraIssueBody", jiraIssue.getIssueBody());

        IndexRequest indexRequest = new IndexRequest("jiraIssues")
                .id(jiraIssue.getId()).source(dataMap);

    }
}
