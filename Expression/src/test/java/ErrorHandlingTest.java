import org.example.Evaluator;
import org.example.Parser;
import org.example.Token;
import org.example.Tokenizer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorHandlingTest {

    @Test
    void testMismatchedParentheses() {
        assertThrows(Exception.class,
                () -> Parser.shuntingYard(
                        Tokenizer.tokenize("(3+4"))
        );
    }

    @Test
    void testUndefinedVariable() {
        List<Token> rpn = List.of(
                new Token(Token.Type.VARIABLE, "y")
        );
        assertThrows(Exception.class,
                () -> Evaluator.evaluate(rpn, Map.of("x", 1.0))
        );
    }
}
