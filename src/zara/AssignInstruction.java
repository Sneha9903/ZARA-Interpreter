package zara;

public class AssignInstruction implements Instruction {
    private final String variableName;
    private final Expression value;

    public AssignInstruction(String variableName, Expression value) {
        this.variableName = variableName;
        this.value = value;
    }

    @Override
    public void execute(Environment env) {
        env.set(variableName, value.evaluate(env));
    }
}
