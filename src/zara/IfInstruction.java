package zara;

import java.util.List;

public class IfInstruction implements Instruction {
    private final Expression condition;
    private final List<Instruction> body;

    public IfInstruction(Expression condition, List<Instruction> body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(Environment env) {
        Object isTrue = condition.evaluate(env);
        if (isTrue instanceof Boolean && (Boolean) isTrue) {
            for (Instruction instr : body) {
                instr.execute(env);
            }
        }
    }
}
