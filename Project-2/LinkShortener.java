import java.util.HashMap;
import java.util.Scanner;

public class LinkShortener  {
    private static final String BASE_URL = "http://short.ly/";
    private static final HashMap<String, String> shortToLongMap = new HashMap<>();
    private static final HashMap<String, String> longToShortMap = new HashMap<>();
    private static int counter = 1;

    // Method to generate a short URL
    public static String shortenURL(String longURL) {
        if (longToShortMap.containsKey(longURL)) {
            return longToShortMap.get(longURL);
        }
        String shortURL = BASE_URL + Integer.toHexString(counter++);
        shortToLongMap.put(shortURL, longURL);
        longToShortMap.put(longURL, shortURL);
        return shortURL;
    }

    // Method to expand a short URL
    public static String expandURL(String shortURL) {
        if (shortToLongMap.containsKey(shortURL)) {
            return shortToLongMap.get(shortURL);
        }
        return "Error: Short URL not found!";
    }

    // CLI for user interaction
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Link Shortener!");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the long URL: ");
                    String longURL = scanner.nextLine();
                    String shortURL = shortenURL(longURL);
                    System.out.println("Shortened URL: " + shortURL);
                    break;
                case 2:
                    System.out.print("Enter the short URL: ");
                    String shortURLInput = scanner.nextLine();
                    String expandedURL = expandURL(shortURLInput);
                    System.out.println("Expanded URL: " + expandedURL);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
