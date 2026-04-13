package zara;

public class BinaryOpNode implements Expression {
    private final Expression left;
    private final String operator;
    private final Expression right;

    public BinaryOpNode(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object evaluate(Environment env) {
        Object leftVal = left.evaluate(env);
        Object rightVal = right.evaluate(env);

        if (operator.equals("==")) {
            if (leftVal == null) return rightVal == null;
            return leftVal.equals(rightVal);
        }

        if (leftVal instanceof Double && rightVal instanceof Double) {
            double l = (Double) leftVal;
            double r = (Double) rightVal;
            switch (operator) {
                case "+": return l + r;
                case "-": return l - r;
                case "*": return l * r;
                case "/": return l / r;
                case ">": return l > r;
                case "<": return l < r;
            }
        }
        
        if (leftVal instanceof String || rightVal instanceof String) {
            if (operator.equals("+")) {
                String lStr = formatIfDouble(leftVal);
                String rStr = formatIfDouble(rightVal);
                return lStr + rStr;
            }
        }

        throw new RuntimeException("Unsupported operation: " + operator + " between " + leftVal + " and " + rightVal);
    }
    
    private String formatIfDouble(Object obj) {
        if (obj instanceof Double) {
            double d = (Double) obj;
            if (d == (long) d) {
                return String.format("%d", (long) d);
            }
            return String.valueOf(d);
        }
        return String.valueOf(obj);
    }
}
