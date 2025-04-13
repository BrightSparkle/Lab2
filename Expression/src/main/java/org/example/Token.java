package org.example;

/**
 * Класс, представляющий лексический токен в выражении.
 * Каждый токен имеет тип и строковое значение.
 */
public class Token {

    /**
     * Перечисление возможных типов токенов
     */
    public enum Type {
        NUMBER, VARIABLE, FUNCTION, OPERATOR, LEFT_PAREN, RIGHT_PAREN, COMMA
    }

    /** Тип токена */
    public final Type type;

    /** Строковое значение токена */
    public final String value;

    /**
     * Конструктор токена
     * @param type тип токена
     * @param value строковое значение
     */
    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
