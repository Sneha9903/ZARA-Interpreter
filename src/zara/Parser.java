package zara;
import java.util.List;
public class Parser {
// Store: the token list and a current index tracking which token
// you are looking at right now.
public Parser(List<Token> tokens) {
// Store the token list.
}
public List<Instruction> parse() {
// Read through tokens and build the list of Instructions.
// Return the completed list.
}
// You will need several private helper methods here.
// A natural split for handling operator precedence:
// parseExpression() -- handles + and -
// parseTerm() -- handles * and /
// parsePrimary() -- handles a single number, string, or variable
// parseExpression calls parseTerm; parseTerm calls parsePrimary.
// This chain gives * and / higher priority than + and - automatically.
}