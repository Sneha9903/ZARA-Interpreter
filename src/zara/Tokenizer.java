package zara;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tokenizer {
    private final String source;
    private int current = 0;
    private int line = 1;
    private final List<Token> tokens = new ArrayList<>();
    private final Stack<Integer> indentStack = new Stack<>();
    private boolean atLineStart = true;

    public Tokenizer(String source) {
        this.source = source;
        this.indentStack.push(0);
    }

    public List<Token> tokenize() {
        while (!isAtEnd()) {
            if (atLineStart) {
                handleIndentation();
                atLineStart = false;
                if (isAtEnd()) break;
            }

            char c = advance();

            switch (c) {
                case ' ': case '\t': case '\r':
                    // Just ignore inline whitespace
                    break;
                case '\n':
                    tokens.add(new Token(TokenType.NEWLINE, "\\n", line));
                    line++;
                    atLineStart = true;
                    break;
                case '=':
                    if (match('=')) {
                        tokens.add(new Token(TokenType.EQUAL_EQUAL, "==", line));
                    } else {
                        tokens.add(new Token(TokenType.ASSIGN, "=", line));
                    }
                    break;
                case ':': tokens.add(new Token(TokenType.COLON, ":", line)); break;
                case '+': tokens.add(new Token(TokenType.PLUS, "+", line)); break;
                case '-': tokens.add(new Token(TokenType.MINUS, "-", line)); break;
                case '*': tokens.add(new Token(TokenType.MULTIPLY, "*", line)); break;
                case '/': tokens.add(new Token(TokenType.DIVIDE, "/", line)); break;
                case '>': tokens.add(new Token(TokenType.GREATER, ">", line)); break;
                case '<': tokens.add(new Token(TokenType.LESS, "<", line)); break;
                case '"': string(); break;
                default:
                    if (isDigit(c)) {
                        number();
                    } else if (isAlpha(c)) {
                        identifier();
                    } else {
                        throw new RuntimeException("Unexpected character '" + c + "' at line " + line);
                    }
            }
        }

        // Add trailing dedents
        while (indentStack.size() > 1) {
            indentStack.pop();
            tokens.add(new Token(TokenType.DEDENT, "", line));
        }

        tokens.add(new Token(TokenType.EOF, "", line));
        return tokens;
    }

    private void handleIndentation() {
        int spaces = 0;
        int peekCurrent = current;
        while (peekCurrent < source.length() && source.charAt(peekCurrent) == ' ') {
            spaces++;
            peekCurrent++;
        }
        
        // Skip empty lines or lines with only comments (none in Zara but ignoring empty lines is good)
        if (peekCurrent < source.length() && (source.charAt(peekCurrent) == '\n' || source.charAt(peekCurrent) == '\r')) {
            // It's an empty line, do not process indentation
            return;
        }

        current = peekCurrent;

        int currentIndent = indentStack.peek();
        if (spaces > currentIndent) {
            indentStack.push(spaces);
            tokens.add(new Token(TokenType.INDENT, "", line));
        } else if (spaces < currentIndent) {
            while (indentStack.peek() > spaces) {
                indentStack.pop();
                tokens.add(new Token(TokenType.DEDENT, "", line));
            }
            if (indentStack.peek() != spaces) {
                throw new RuntimeException("Inconsistent indentation at line " + line);
            }
        }
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        current++;
        return true;
    }

    private char advance() {
        return source.charAt(current++);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void string() {
        int start = current - 1;
        while (!isAtEnd() && peek() != '"') {
            if (peek() == '\n') line++;
            advance();
        }
        if (isAtEnd()) {
            throw new RuntimeException("Unterminated string at line " + line);
        }
        advance(); // consume the closing "
        String value = source.substring(start + 1, current - 1);
        tokens.add(new Token(TokenType.STRING, value, line));
    }

    private void number() {
        int start = current - 1;
        while (isDigit(peek())) advance();
        
        if (peek() == '.' && isDigit(peekNext())) {
            advance(); // consume the "."
            while (isDigit(peek())) advance();
        }
        
        String value = source.substring(start, current);
        tokens.add(new Token(TokenType.NUMBER, value, line));
    }

    private void identifier() {
        int start = current - 1;
        while (isAlphaNumeric(peek())) advance();
        
        String text = source.substring(start, current);
        TokenType type;
        switch (text) {
            case "set": type = TokenType.SET; break;
            case "show": type = TokenType.SHOW; break;
            case "when": type = TokenType.WHEN; break;
            case "loop": type = TokenType.LOOP; break;
            default: type = TokenType.IDENTIFIER; break;
        }
        tokens.add(new Token(type, text, line));
    }
}
