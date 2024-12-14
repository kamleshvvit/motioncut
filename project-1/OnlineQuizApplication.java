import java.util.*;

// Class to represent a single Question
class Question {
    private String questionText;
    private String[] options;
    private int correctOption;

    // Constructor to initialize the question
    public Question(String questionText, String[] options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    // Getters
    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

// Class to manage the Quiz
class Quiz {
    private List<Question> questions;
    private int score;

    // Constructor
    public Quiz() {
        questions = new ArrayList<>();
        score = 0;
    }

    // Method to add a question
    public void addQuestion(Question question) {
        questions.add(question);
    }

    // Method to start the quiz
    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Quiz!\n");

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);

            // Display the question
            System.out.println("Question " + (i + 1) + ": " + q.getQuestionText());
            String[] options = q.getOptions();

            // Display options
            for (int j = 0; j < options.length; j++) {
                System.out.println((j + 1) + ". " + options[j]);
            }

            // Get user input
            int userAnswer = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.print("Your answer (1-" + options.length + "): ");
                    userAnswer = scanner.nextInt();
                    if (userAnswer >= 1 && userAnswer <= options.length) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                }
            }

            // Check answer
            if (userAnswer == q.getCorrectOption()) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + options[q.getCorrectOption() - 1] + "\n");
            }
        }

        // Display final score
        System.out.println("Quiz Over!\nYour final score is: " + score + "/" + questions.size());
        scanner.close();
    }
}

// Main Class
public class OnlineQuizApplication {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();
    // Adding questions to the quiz
        quiz.addQuestion(new Question(
            "What is the capital of France?",
            new String[]{"Berlin", "Paris", "Madrid", "Rome"},
            2
        ));

        quiz.addQuestion(new Question(
            "Which programming language is known as the 'Write Once, Run Anywhere' language?",
            new String[]{"Python", "Java", "C++", "JavaScript"},
            2
        ));

        quiz.addQuestion(new Question(
            "Which planet is known as the Red Planet?",
            new String[]{"Earth", "Mars", "Jupiter", "Saturn"},
            2
        ));

        // Start the quiz
        quiz.startQuiz();
    }
}
