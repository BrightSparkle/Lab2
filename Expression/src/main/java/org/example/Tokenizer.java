package org.example;
import java.util.*;
import java.util.regex.*;

public class Tokenizer {

    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "(\\d+\\.?\\d*|\\.\\d+)|" +
                    "(\\+|-|\\*|/|\\^)|" +
                    "(\\()|" +
                    "(\\))|" +
                    "(,)|" +
                    "(sin|cos|tan|sqrt|log)|" +
                    "([a-zA-Z_][a-zA-Z0-9_]*)|" +
                    "(\\S)"
    );

    public static List<Token> tokenize(String expression) throws Exception {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(expression);
        int lastIndex = 0;

        while (matcher.find()) {
            if (matcher.start() != lastIndex) {
                throw new Exception("Unexpected character at position " + (lastIndex));
            }

            String number = matcher.group(1);
            String operator = matcher.group(2);
            String leftParen = matcher.group(3);
            String rightParen = matcher.group(4);
            String comma = matcher.group(5);
            String function = matcher.group(6);
            String variable = matcher.group(7);
            String unknown = matcher.group(8);

            if (number != null) {
                tokens.add(new Token(Token.Type.NUMBER, number));
            } else if (operator != null) {
                tokens.add(new Token(Token.Type.OPERATOR, operator));
            } else if (leftParen != null) {
                tokens.add(new Token(Token.Type.LEFT_PAREN, "("));
            } else if (rightParen != null) {
                tokens.add(new Token(Token.Type.RIGHT_PAREN, ")"));
            } else if (comma != null) {
                tokens.add(new Token(Token.Type.COMMA, ","));
            } else if (function != null) {
                tokens.add(new Token(Token.Type.FUNCTION, function.toLowerCase()));
            } else if (variable != null) {
                tokens.add(new Token(Token.Type.VARIABLE, variable));
            } else if (unknown != null) {
                throw new Exception("Invalid token: " + unknown);
            }

            lastIndex = matcher.end();
        }

        if (lastIndex != expression.length()) {
            throw new Exception("Unexpected character at position " + (lastIndex));
        }

        return tokens;
    }
}
