package zara;
import java.util.List;

public class ParserContext {
    private final List<Token> tokens;
    private int current;

    public ParserContext(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    public boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    public Token consume(TokenType type, String message) {
        if (check(type)) {
            return advance();
        }
        throw error(message + " (found " + peek().getType() + ")");
    }

    public boolean check(TokenType type) {
        if (isAtEnd()) {
            return type == TokenType.EOF;
        }
        return peek().getType() == type;
    }

    public Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    public boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    public Token peek() {
        return tokens.get(current);
    }

    public Token previous() {
        return tokens.get(current - 1);
    }

    public void skipNewlines() {
        while (match(TokenType.NEWLINE)) {
            // skip blank lines
        }
    }

    public ParserException error(String message) {
        return new ParserException("Parser error at line " + peek().getLine() + ": " + message);
    }
}