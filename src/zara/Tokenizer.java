import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int current = 0;
    private int line = 1;

    public Tokenizer(String source) {
        this.source = source; [cite: 193]
    }

    public List<Token> tokenize() {
        while (!isAtEnd()) {
            char c = advance(); [cite: 196]

            switch (c) {
                // Single-character symbols
                case '=': addToken(TokenType.EQUALS); break;
                case '+': addToken(TokenType.PLUS); break;
                case '-': addToken(TokenType.MINUS); break;
                case '*': addToken(TokenType.STAR); break;
                case '/': addToken(TokenType.SLASH); break;
                case '>': addToken(TokenType.GREATER); break;
                case '<': addToken(TokenType.LESS); break;
                case ':': addToken(TokenType.COLON); break;

                // Whitespace & Line tracking
                case ' ':
                case '\r':
                case '\t':
                    break;
                case '\n':
                    addToken(TokenType.NEWLINE); [cite: 70]
                    line++;
                    break;

                // String Literals (e.g., "hello")
                case '"': scanString(); break;

                default:
                    if (Character.isDigit(c)) {
                        scanNumber(); [cite: 69]
                    } else if (c == '$' || Character.isLetter(c)) {
                        scanIdentifierOrKeyword(); [cite: 69]
                    } else {
                        throw new RuntimeException("Unexpected character '" + c + "' at line " + line);
                    }
                    break;
            }
        }

        tokens.add(new Token(TokenType.EOF, "", line)); [cite: 198]
        return tokens;
    }

    // --- Helper Methods ---

    private void scanNumber() {
        int start = current - 1; 
        while (Character.isDigit(peek())) advance();

        // Handle decimals
        if (peek() == '.' && Character.isDigit(peekNext())) {
            advance(); // consume the '.'
            while (Character.isDigit(peek())) advance();
        }

        String value = source.substring(start, current);
        tokens.add(new Token(TokenType.NUMBER, value, line)); [cite: 72]
    }

    private void scanString() {
        int start = current;
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) throw new RuntimeException("Unterminated string at line " + line);

        advance(); // The closing "
        String value = source.substring(start, current - 1);
        tokens.add(new Token(TokenType.STRING, value, line)); [cite: 72]
    }

    private void scanIdentifierOrKeyword() {
        int start = current - 1;
        while (Character.isLetterOrDigit(peek()) || peek() == '$') advance();

        String text = source.substring(start, current);

        // Check if the word is a ZARA keyword [cite: 29]
        TokenType type = switch (text) {
            case "set"  -> TokenType.SET;
            case "show" -> TokenType.SHOW;
            case "when" -> TokenType.WHEN;
            case "loop" -> TokenType.LOOP;
            default     -> TokenType.IDENTIFIER;
        };

        tokens.add(new Token(type, text, line)); [cite: 72]
    }

    // --- Navigation Methods ---

    private char advance() { return source.charAt(current++); }
    
    private char peek() { 
        if (isAtEnd()) return '\0';
        return source.charAt(current); 
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isAtEnd() { return current >= source.length(); }

    private void addToken(TokenType type) {
        String text = source.substring(current - 1, current);
        tokens.add(new Token(type, text, line)); [cite: 65, 75]
    }
}
