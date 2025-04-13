package org.example;
import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter expression:");
        String input = scanner.nextLine();

        try {
            List<Token> tokens = Tokenizer.tokenize(input);
            tokens = Parser.handleUnaryOperators(tokens);
            Set<String> variables = Parser.getVariables(tokens);
            Map<String, Double> varValues = variables.isEmpty() ? new HashMap<>() : Parser.readVariables(variables);
            List<Token> rpn = Parser.shuntingYard(tokens);
            double result = Evaluator.evaluate(rpn, varValues);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}