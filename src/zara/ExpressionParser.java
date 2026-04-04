package zara;

import java.beans.Expression;

public class ExpressionParser {
    private final ParserContext context;
    public ExpressionParser(ParserContext context) {
        this.context = context;
    }
    public Expression parse() {
        return parseExpression();
    }
    private Expression parseExpression() {
    return parseEquality();
}
    private Expression parseEquality() {
        Expression expression = parseComparison();

        while (context.match(TokenType.EQUAL_EQUAL, TokenType.BANG_EQUAL)) {
            Token operator = context.previous();
            Expression right = parseComparison();
            expression = new BinaryOpNode(expression, operator.getValue(), right);
        }

        return expression;
    }

    private Expression parseComparison(){
        Expression expression=parseTerm();
        while (context.match(TokenType.GREATER, TokenType.GREATER_EQUAL,
                TokenType.LESS, TokenType.LESS_EQUAL)) {
            Token operator = context.previous();
            Expression right = parseTerm();
            expression = new BinaryOpNode(expression, operator.getValue(), right);
        }

        return expression;
    }

    private Expression parseTerm(){
        Expression expression=parseFactor();
        while(context.match(TokenType.PLUS, TokenType.MINUS)){
            Token operator = context.previous();
            Expression right = parseFactor();
            expression = new BinaryOpNode(expression, operator.getValue(), right);
        }
        return expression;
    }

    private Expression parseFactor(){
        Expression expression=parseUnary();
        while (context.match(TokenType.STAR, TokenType.SLASH)) {
            Token operator = context.previous();
            Expression right = parseUnary();
            expression = new BinaryOpNode(expression, operator.getValue(), right);
        }

        return expression;
    }

    private Expression parseUnary() {
        if (context.match(TokenType.MINUS)) {
            Token operator = context.previous();
            Expression right = parseUnary();
            return new BinaryOpNode(new NumberNode(0), operator.getValue(), right);
        }
        return parsePrimary();
    }

    private Expression parsePrimary() {
        if (context.match(TokenType.NUMBER)) {
            return new NumberNode(Double.parseDouble(context.previous().getValue()));
        }

        if (context.match(TokenType.STRING)) {
            return new StringNode(context.previous().getValue());
        }

        if (context.match(TokenType.IDENTIFIER)) {
            return new VariableNode(context.previous().getValue());
        }

        if (context.match(TokenType.LEFT_PAREN)) {
            Expression expression = parseExpression();
            context.consume(TokenType.RIGHT_PAREN, "Expected ')' after expression");
            return expression;
        }

        throw context.error("Expected number, string, identifier, or '(' but found: " + context.peek().getType());
    }
}
