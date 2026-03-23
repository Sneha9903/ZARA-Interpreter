package zara;
public enum TokenType {
	// Keywords
    SET, SHOW, WHEN, LOOP,
    
    // Literals and Identifiers
    NUMBER, STRING, IDENTIFIER,
    
    // Operators and Punctuation
    EQUALS, PLUS, MINUS, STAR, SLASH, 
    GREATER, LESS, COLON, NEWLINE,
    
    // End of File
    EOF
}
