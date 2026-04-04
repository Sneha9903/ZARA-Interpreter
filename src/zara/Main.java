package zara;

public class Main {
    public static void main(String[] args) {

        Interpreter interpreter = new Interpreter();

        if (args.length == 0) {
            String sourceCode = """
                    set i = 1
                    loop 3:
                        when i < 3:
                            show "small"
                        show i
                        set i = i + 1
                    """;
            interpreter.run(sourceCode);
            return;
        }
    }
    
}
