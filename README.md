# Application-1-Rule-Engine-with-AST
public class ASTNode {
    private String type; // "operator" or "operand"
    private ASTNode left; // Left child node
    private ASTNode right; // Right child node
    private String value; // Operand value (like age > 30 for operand nodes)

    // Constructor for operator nodes
    public ASTNode(String type, ASTNode left, ASTNode right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    // Constructor for operand nodes
    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
    }

    // Getters and Setters
    // ...

    @Override
    public String toString() {
        if (type.equals("operand")) {
            return value;
        } else {
            return "(" + left.toString() + " " + type + " " + right.toString() + ")";
        }
    }
}
public ASTNode createRule(String ruleString) {
    // Parse the ruleString to construct ASTNode (could use a parser library or write a custom parser)
    // Example: ((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing'))
    return parseRule(ruleString); // Returns the root of the AST
}
public ASTNode combineRules(List<ASTNode> rules) {
    ASTNode combined = rules.get(0);
    for (int i = 1; i < rules.size(); i++) {
        combined = new ASTNode("AND", combined, rules.get(i)); // Combining with AND operator
    }
    return combined;
}
public boolean evaluateRule(ASTNode ruleAST, Map<String, Object> userData) {
    // Recursively evaluate the AST against userData
    return evaluate(ruleAST, userData);
}

private boolean evaluate(ASTNode node, Map<String, Object> userData) {
    if (node.getType().equals("operand")) {
        return evaluateOperand(node.getValue(), userData);
    } else {
        boolean leftResult = evaluate(node.getLeft(), userData);
        boolean rightResult = evaluate(node.getRight(), userData);
        return node.getType().equals("AND") ? (leftResult && rightResult) : (leftResult || rightResult);
    }
}

private boolean evaluateOperand(String condition, Map<String, Object> userData) {
    // Parse the condition and compare it to the userData (e.g., age > 30)
    // Implementation will vary depending on the complexity of the condition
}
