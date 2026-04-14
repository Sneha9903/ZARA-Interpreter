package zara;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final ParserContext context;
    private final InstructionParser instructionParser;

    public Parser(List<Token> tokens) {
        this.context = new ParserContext(tokens);
        ExpressionParser expressionParser = new ExpressionParser(context);
        this.instructionParser = new InstructionParser(context, expressionParser);
    }

    public List<Instruction> parse() {
        List<Instruction> instructions = new ArrayList<>();

        context.skipNewlines();

        while (!context.isAtEnd()) {
            instructions.add(instructionParser.parseInstruction());
            context.skipNewlines();
        }

        return instructions;
    }
}