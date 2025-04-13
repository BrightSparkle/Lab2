import org.example.Evaluator;
import org.example.Parser;
import org.example.Token;
import org.example.Tokenizer;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    void testFullPipeline() throws Exception {
        String expression = "2*(3+log(2,8))-sqrt(4)";

        List<Token> tokens = Tokenizer.tokenize(expression);
        tokens = Parser.handleUnaryOperators(tokens);
        Set<String> vars = Parser.getVariables(tokens);
        Map<String, Double> varValues = Map.of(); // Нет переменных

        List<Token> rpn = Parser.shuntingYard(tokens);
        double result = Evaluator.evaluate(rpn, varValues);

        assertEquals(10.0, result, 1e-6);
    }
}
