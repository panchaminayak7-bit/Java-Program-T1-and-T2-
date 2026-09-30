import java.util.Scanner;

public class AIChatbot {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("        AI CHATBOT");
        System.out.println("=================================");
        System.out.println("Hello! I am your AI chatbot.");
        System.out.println("Type 'bye' to exit.");

        while (true) {

            System.out.print("\nYou: ");
            String input = sc.nextLine().toLowerCase();

            String response;

            if (input.contains("hello") ||
                input.contains("hi") ||
                input.contains("hey")) {

                response = "Hello! How can I help you?";

            } else if (input.contains("your name") ||
                       input.contains("who are you")) {

                response = "I am an AI chatbot created using Java.";

            } else if (input.contains("how are you")) {

                response = "I am doing great! Thanks for asking.";

            } else if (input.contains("java")) {

                response = "Java is a popular object-oriented programming language.";

            } else if (input.contains("college")) {

                response = "College is a great place to learn and build your skills.";

            } else if (input.contains("thank")) {

                response = "You're welcome!";

            } else if (input.contains("bye") ||
                       input.contains("exit")) {

                response = "Goodbye! Have a nice day!";

                System.out.println("Bot: " + response);
                break;

            } else {

                response = "Sorry, I don't understand that yet.";

            }

            System.out.println("Bot: " + response);
        }

        sc.close();
    }
}