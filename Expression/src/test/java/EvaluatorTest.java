import org.example.Evaluator;
import org.example.Token;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluatorTest {

    private final Map<String, Double> vars = Map.of("x", 2.0);

    @Test
    void testBasicExpression() throws Exception {
        List<Token> rpn = List.of(
                new Token(Token.Type.NUMBER, "3"),
                new Token(Token.Type.NUMBER, "4"),
                new Token(Token.Type.OPERATOR, "+")
        );
        assertEquals(7.0, Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testFunctionEvaluation() throws Exception {
        List<Token> rpn = List.of(
                new Token(Token.Type.NUMBER, "0"),
                new Token(Token.Type.FUNCTION, "sin")
        );
        assertEquals(0.0, Evaluator.evaluate(rpn, Map.of()));
    }

    @Test
    void testDivisionByZero() {
        List<Token> rpn = List.of(
                new Token(Token.Type.NUMBER, "5"),
                new Token(Token.Type.NUMBER, "0"),
                new Token(Token.Type.OPERATOR, "/")
        );
        assertThrows(Exception.class,
                () -> Evaluator.evaluate(rpn, Map.of())
        );
    }
}
