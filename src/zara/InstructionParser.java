package zara;
import java.util.ArrayList;
import java.util.List;


public class InstructionParser {
    private final ParserContext context;
    private final ExpressionParser expressionParser;

    public InstructionParser(ParserContext context, ExpressionParser expressionParser) {
        this.context = context;
        this.expressionParser = expressionParser;
    }

    public Instruction parseInstruction() {
        if (context.match(TokenType.SET)) {
            return parseAssignInstruction();
        }

        if (context.match(TokenType.SHOW)) {
            return parsePrintInstruction();
        }

        if (context.match(TokenType.WHEN)) {
            return parseIfInstruction();
        }

        if (context.match(TokenType.LOOP)) {
            return parseRepeatInstruction();
        }

        throw context.error("Expected instruction but found: " + context.peek().getType());
    }

    private Instruction parseAssignInstruction() {
        Token name = context.consume(TokenType.IDENTIFIER, "Expected variable name after 'set'");
        context.consume(TokenType.EQUAL, "Expected '=' after variable name");

        Expression expression = expressionParser.parseExpression();
        return new AssignInstruction(name.getValue(), expression);
    }

    private Instruction parsePrintInstruction() {
        Expression expression = expressionParser.parseExpression();
        return new PrintInstruction(expression);
    }

    private Instruction parseIfInstruction() {
        Expression condition = expressionParser.parseExpression();
        context.consume(TokenType.COLON, "Expected ':' after when condition");

        List<Instruction> body = parseBlock();
        return new IfInstruction(condition, body);
    }

    private Instruction parseRepeatInstruction() {
        Expression count = expressionParser.parseExpression();
        context.consume(TokenType.COLON, "Expected ':' after loop count");

        List<Instruction> body = parseBlock();
        return new RepeatInstruction(count, body);
    }

    private List<Instruction> parseBlock() {
        context.consume(TokenType.NEWLINE, "Expected new line after ':'");
        context.consume(TokenType.INDENT, "Expected indented block");

        List<Instruction> body = new ArrayList<>();
        context.skipNewlines();

        while (!context.check(TokenType.DEDENT) && !context.isAtEnd()) {
            body.add(parseInstruction());
            context.skipNewlines();
        }

        context.consume(TokenType.DEDENT, "Expected end of block");
        return body;
    }
}
