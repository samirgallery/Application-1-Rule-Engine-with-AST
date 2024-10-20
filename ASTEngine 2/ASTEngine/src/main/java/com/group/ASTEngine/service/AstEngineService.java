package com.group.ASTEngine.service;

import com.group.ASTEngine.repo.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group.ASTEngine.model.Rule;

import java.util.List;
import java.util.Map;

@Service
public class AstEngineService {

    @Autowired
    private RuleRepository ruleRepository;

    public Rule createRule(String ruleString) {
        Rule rule = new Rule(ruleString);
        return ruleRepository.save(rule);
    }

    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    }

    public boolean evaluateRule(String ruleString, Map<String, Object> data) {
        // Trim and check for empty rule strings
        if (ruleString == null || ruleString.trim().isEmpty()) {
            throw new IllegalArgumentException("Rule string cannot be null or empty.");
        }
        return evaluateCondition(ruleString, data);
    }

    private boolean evaluateCondition(String condition, Map<String, Object> data) {
        condition = condition.trim();

        // Remove outer parentheses if present
        if (condition.startsWith("(") && condition.endsWith(")")) {
            condition = condition.substring(1, condition.length() - 1).trim();
        }

        String[] orConditions = condition.split("OR");

        for (String orCondition : orConditions) {
            boolean orResult = evaluateAndConditions(orCondition.trim(), data);
            if (orResult) {
                return true; // If any OR condition is true, return true
            }
        }
        return false; // All OR conditions failed
    }

    private boolean evaluateAndConditions(String condition, Map<String, Object> data) {
        String[] andConditions = condition.split("AND");

        for (String andCondition : andConditions) {
            if (!evaluateSingleCondition(andCondition.trim(), data)) {
                return false; // If any AND condition is false, return false
            }
        }
        return true; // All AND conditions passed
    }

    private boolean evaluateSingleCondition(String condition, Map<String, Object> data) {
        // Log the initial condition
        System.out.println("Evaluating condition: " + condition);

        // Trim and clean the condition
        condition = condition.trim();

        // Check if condition starts with '(' and ends with ')'
        if (condition.startsWith("(") && condition.endsWith(")")) {
            condition = condition.substring(1, condition.length() - 1).trim();
        }

        // Log the cleaned condition
        System.out.println("Cleaned condition: " + condition);

        String[] parts = condition.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid condition format: " + condition);
        }

        String attribute = parts[0].trim().trim();
        String operator = parts[1].trim();
        String value = parts[2].trim().replace("'", ""); // Remove quotes if present

        // Log the extracted parts
        System.out.println("Attribute: " + attribute + ", Operator: " + operator + ", Value: " + value);

        Object attributeValue = data.get(attribute);

        // Handle null attribute values
        if (attributeValue == null) {
            throw new IllegalArgumentException("Attribute not found in data: " + attribute);
        }

        return evaluateAttributeCondition(attributeValue, operator, value);
    }


    private boolean evaluateAttributeCondition(Object attributeValue, String operator, String value) {
        switch (operator) {
            case ">":
                return ((Number) attributeValue).doubleValue() > Double.parseDouble(value);
            case "<":
                return ((Number) attributeValue).doubleValue() < Double.parseDouble(value);
            case "=":
                return attributeValue.toString().equals(value);
            case "!=":
                return !attributeValue.toString().equals(value);
            // Add more operators as needed
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}
