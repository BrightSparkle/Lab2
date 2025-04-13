package org.example;
import java.util.*;
import java.util.regex.*;

public class Token {

    public enum Type {
        NUMBER, VARIABLE, FUNCTION, OPERATOR, LEFT_PAREN, RIGHT_PAREN, COMMA
    }

    public final Type type;
    public final String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
