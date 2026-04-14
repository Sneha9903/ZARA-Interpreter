package zara;
public enum TokenType {
	// Keywords
    SET, SHOW, WHEN, LOOP,
    
    // Literals and Identifiers
    NUMBER, STRING, IDENTIFIER,
    
    ASSIGN, PLUS, MINUS, MULTIPLY, DIVIDE, // Operators
    GREATER, LESS, EQUAL_EQUAL, 		   // Relational
    COLON,                                 // Punctuation
    NEWLINE, INDENT, DEDENT, EOF          // Structural
}
