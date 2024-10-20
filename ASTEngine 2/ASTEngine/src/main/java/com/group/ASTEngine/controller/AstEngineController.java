package com.group.ASTEngine.controller;

import com.group.ASTEngine.service.AstEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group.ASTEngine.model.Rule;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class AstEngineController {

    @Autowired
    private AstEngineService astEngineController;

    // Create a new rule
    @PostMapping
    public ResponseEntity<Rule> createRule(@RequestBody Rule rule) {
        Rule createdRule = astEngineController.createRule(rule.getRuleString());
        return new ResponseEntity<>(createdRule, HttpStatus.CREATED);
    }

    // Get all rules

    @GetMapping
    public ResponseEntity<List<Rule>> getAllRules() {
        List<Rule> rules = astEngineController.getAllRules();
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    // Evaluate a rule
    @PostMapping("/evaluate")
    public ResponseEntity<Map<String, Object>> evaluateRule(@RequestBody Map<String, Object> requestBody) {
        String ruleString = (String) requestBody.get("ruleString");
        Map<String, Object> data = (Map<String, Object>) requestBody.get("data");

        boolean result = astEngineController.evaluateRule(ruleString, data);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }
}
