import org.example.Token;
import org.example.Tokenizer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizerTest {

    @Test
    void testBasicTokens() throws Exception {
        List<Token> tokens = Tokenizer.tokenize("3 + 4.2*x - sin(0)");
        assertTokens(tokens,
                Token.Type.NUMBER, Token.Type.OPERATOR, Token.Type.NUMBER,
                Token.Type.OPERATOR, Token.Type.VARIABLE, Token.Type.OPERATOR,
                Token.Type.FUNCTION, Token.Type.LEFT_PAREN, Token.Type.NUMBER,
                Token.Type.RIGHT_PAREN
        );
    }

    @Test
    void testInvalidToken() {
        Exception exception = assertThrows(Exception.class,
                () -> Tokenizer.tokenize("3 # 4")
        );
        assertTrue(exception.getMessage().contains("Invalid token"));
    }

    private void assertTokens(List<Token> tokens, Token.Type... types) {
        assertEquals(types.length, tokens.size());
        for (int i = 0; i < types.length; i++) {
            assertEquals(types[i], tokens.get(i).type);
        }
    }
}
