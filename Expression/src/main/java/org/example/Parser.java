package org.example;
import java.util.*;
import java.util.regex.*;

public class Parser {

    private static final Map<String, Integer> FUNCTION_ARGS = new HashMap<>();
    static {
        FUNCTION_ARGS.put("sin", 1);
        FUNCTION_ARGS.put("cos", 1);
        FUNCTION_ARGS.put("sqrt", 1);
        FUNCTION_ARGS.put("log", 2);
    }

    private static final Map<String, Integer> OPERATOR_PRECEDENCE = new HashMap<>();
    static {
        OPERATOR_PRECEDENCE.put("^", 4);
        OPERATOR_PRECEDENCE.put("u", 4);
        OPERATOR_PRECEDENCE.put("*", 3);
        OPERATOR_PRECEDENCE.put("/", 3);
        OPERATOR_PRECEDENCE.put("+", 2);
        OPERATOR_PRECEDENCE.put("-", 2);
    }

    public static List<Token> handleUnaryOperators(List<Token> tokens) {
        List<Token> processed = new ArrayList<>();
        Token prevToken = null;
        for (Token token : tokens) {
            if (token.type == Token.Type.OPERATOR && token.value.equals("-")) {
                if (prevToken == null || prevToken.type == Token.Type.OPERATOR ||
                        prevToken.type == Token.Type.LEFT_PAREN || prevToken.type == Token.Type.COMMA) {
                    processed.add(new Token(Token.Type.OPERATOR, "u"));
                } else {
                    processed.add(token);
                }
            } else {
                processed.add(token);
            }
            prevToken = processed.get(processed.size() - 1);
        }
        return processed;
    }

    public static List<Token> shuntingYard(List<Token> tokens) throws Exception {
        List<Token> output = new ArrayList<>();
        Deque<Token> stack = new LinkedList<>();

        for (Token token : tokens) {
            switch (token.type) {
                case NUMBER:
                case VARIABLE:
                    output.add(token);
                    break;
                case FUNCTION:
                    stack.push(token);
                    break;
                case COMMA:
                    while (!stack.isEmpty() && stack.peek().type != Token.Type.LEFT_PAREN) {
                        output.add(stack.pop());
                    }
                    if (stack.isEmpty()) {
                        throw new Exception("Mismatched comma");
                    }
                    break;
                case OPERATOR:
                    String currOp = token.value;
                    while (!stack.isEmpty()) {
                        Token top = stack.peek();
                        if (top.type == Token.Type.OPERATOR) {
                            int currPrec = OPERATOR_PRECEDENCE.get(currOp);
                            int topPrec = OPERATOR_PRECEDENCE.get(top.value);
                            if ((currPrec < topPrec) || (currPrec == topPrec && !currOp.equals("^") && !currOp.equals("u"))) {
                                output.add(stack.pop());
                            } else {
                                break;
                            }
                        } else if (top.type == Token.Type.FUNCTION) {
                            output.add(stack.pop());
                        } else {
                            break;
                        }
                    }
                    stack.push(token);
                    break;
                case LEFT_PAREN:
                    stack.push(token);
                    break;
                case RIGHT_PAREN:
                    boolean foundLeft = false;
                    while (!stack.isEmpty()) {
                        Token top = stack.pop();
                        if (top.type == Token.Type.LEFT_PAREN) {
                            foundLeft = true;
                            break;
                        } else {
                            output.add(top);
                        }
                    }
                    if (!foundLeft) throw new Exception("Mismatched parentheses");
                    if (!stack.isEmpty() && stack.peek().type == Token.Type.FUNCTION) {
                        output.add(stack.pop());
                    }
                    break;
                default:
                    throw new Exception("Unexpected token: " + token.type);
            }
        }

        while (!stack.isEmpty()) {
            Token token = stack.pop();
            if (token.type == Token.Type.LEFT_PAREN || token.type == Token.Type.RIGHT_PAREN) {
                throw new Exception("Mismatched parentheses");
            }
            output.add(token);
        }

        return output;
    }

    public static Set<String> getVariables(List<Token> tokens) {
        Set<String> variables = new HashSet<>();
        for (Token token : tokens) {
            if (token.type == Token.Type.VARIABLE) {
                variables.add(token.value);
            }
        }
        return variables;
    }

    public static Map<String, Double> readVariables(Set<String> variables) {
        Map<String, Double> varMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        for (String var : variables) {
            System.out.print("Enter value for " + var + ": ");
            while (true) {
                try {
                    double value = scanner.nextDouble();
                    varMap.put(var, value);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
            }
        }
        return varMap;
    }
}
