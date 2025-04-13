package org.example;
import java.util.*;

/**
 * Класс для вычисления выражений в обратной польской нотации
 */
public class Evaluator {

    /** Количество аргументов для функций */
    private static final Map<String, Integer> FUNCTION_ARGS = new HashMap<>();
    static {
        FUNCTION_ARGS.put("sin", 1);
        FUNCTION_ARGS.put("cos", 1);
        FUNCTION_ARGS.put("sqrt", 1);
        FUNCTION_ARGS.put("log", 2);
    }

    /**
     * Вычисляет значение выражения в ОПН
     * @param rpn выражение в обратной польской нотации
     * @param variables словарь значений переменных
     * @return результат вычисления
     * @throws Exception при ошибках вычисления
     */
    public static double evaluate(List<Token> rpn, Map<String, Double> variables) throws Exception {
        Deque<Double> stack = new LinkedList<>();

        for (Token token : rpn) {
            switch (token.type) {
                case NUMBER:
                    stack.push(Double.parseDouble(token.value));
                    break;
                case VARIABLE:
                    if (!variables.containsKey(token.value)) {
                        throw new Exception("Undefined variable: " + token.value);
                    }
                    stack.push(variables.get(token.value));
                    break;
                case OPERATOR:
                    if (token.value.equals("u")) {
                        if (stack.isEmpty()) throw new Exception("Invalid expression");
                        stack.push(-stack.pop());
                    } else {
                        if (stack.size() < 2) throw new Exception("Not enough operands");
                        double b = stack.pop();
                        double a = stack.pop();
                        switch (token.value) {
                            case "+": stack.push(a + b); break;
                            case "-": stack.push(a - b); break;
                            case "*": stack.push(a * b); break;
                            case "/":
                                if (b == 0) throw new Exception("Division by zero");
                                stack.push(a / b);
                                break;
                            case "^": stack.push(Math.pow(a, b)); break;
                            default: throw new Exception("Unknown operator");
                        }
                    }
                    break;
                case FUNCTION:
                    String func = token.value;
                    int argsCount = FUNCTION_ARGS.get(func);
                    if (stack.size() < argsCount) throw new Exception("Not enough arguments");
                    List<Double> args = new ArrayList<>();
                    for (int i = 0; i < argsCount; i++) {
                        args.add(0, stack.pop());
                    }
                    double result = evaluateFunction(func, args);
                    stack.push(result);
                    break;
                default:
                    throw new Exception("Unexpected token");
            }
        }

        if (stack.size() != 1) throw new Exception("Invalid expression");
        return stack.pop();
    }

    /**
     * Вычисляет значение математической функции
     * @param name имя функции
     * @param args список аргументов
     * @return результат функции
     * @throws Exception при неподдерживаемой функции или неверных аргументах
     */
    private static double evaluateFunction(String name, List<Double> args) throws Exception {
        switch (name) {
            case "sin": return Math.sin(args.get(0));
            case "cos": return Math.cos(args.get(0));
            case "sqrt":
                if (args.get(0) < 0) throw new Exception("Negative sqrt");
                return Math.sqrt(args.get(0));
            case "log":
                if (args.get(0) <= 0 || args.get(1) <= 0) throw new Exception("Log invalid");
                return Math.log(args.get(1)) / Math.log(args.get(0));
            default: throw new Exception("Unknown function");
        }
    }
}
