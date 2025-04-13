import org.example.Token;
import org.example.Tokenizer;
import org.example.Parser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void testShuntingYard() throws Exception {
        List<Token> tokens = Tokenizer.tokenize("3+4*(2-5)");
        tokens = Parser.handleUnaryOperators(tokens);
        List<Token> rpn = Parser.shuntingYard(tokens);

        String[] expected = {"3", "4", "2", "5", "-", "*", "+"};
        assertArrayEquals(expected, rpn.stream()
                .map(Token::toString)
                .toArray()
        );
    }

    @Test
    void testUnaryOperator() throws Exception {
        List<Token> tokens = Tokenizer.tokenize("-x+3");
        tokens = Parser.handleUnaryOperators(tokens);
        assertEquals("u", tokens.get(0).value);
    }
}
