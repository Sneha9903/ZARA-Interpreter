package zara;

public class PrintInstruction implements Instruction {
    private final Expression expression;

    public PrintInstruction(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute(Environment env) {
        Object value = expression.evaluate(env);
        if (value instanceof Double) {
            double d = (Double) value;
            if (d == (long) d) {
                System.out.println((long) d);
                return;
            }
        }
        System.out.println(value);
    }
}
