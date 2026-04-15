//package zara;
//
//public class Main {
//    public static void main(String[] args) {
//
//        Interpreter interpreter = new Interpreter();
//
//        if (args.length == 0) {
//            String sourceCode = """
//                    set i = 1
//                    loop 3:
//                        when i < 3:
//                            show "small"
//                        show i
//                        set i = i + 1
//                    """;
//            interpreter.run(sourceCode);
//            return;
//        }
//    }
//
//}
package zara;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java zara.Interpreter <source.zara>");
            System.exit(1);
        }

        try {
            // 1. Force Java to read the bytes as UTF-8
            String sourceCode = java.nio.file.Files.readString(
                    java.nio.file.Paths.get(args[0]),
                    java.nio.charset.StandardCharsets.UTF_8
            );

            // 2. Safely slice off the BOM if Windows snuck one in
            if (sourceCode.startsWith("\uFEFF")) {
                sourceCode = sourceCode.substring(1);
            }

            Interpreter interpreter = new Interpreter();
            interpreter.run(sourceCode);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
