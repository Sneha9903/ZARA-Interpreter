package zara;

import java.util.List;

public class RepeatInstruction implements Instruction {
    private final Expression countExpression;
    private final List<Instruction> body;

    public RepeatInstruction(Expression countExpression, List<Instruction> body) {
        this.countExpression = countExpression;
        this.body = body;
    }

    @Override
    public void execute(Environment env) {
        Object countObj = countExpression.evaluate(env);
        if (!(countObj instanceof Double)) {
            throw new RuntimeException("Repeat count must be a number");
        }
        int count = ((Double) countObj).intValue();
        for (int i = 0; i < count; i++) {
            for (Instruction instr : body) {
                instr.execute(env);
            }
        }
    }
}
