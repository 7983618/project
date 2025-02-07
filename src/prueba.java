  // Import the IOException class to handle errors
public class prueba {
    public static void main(String[] args) {
        try {
            FileWriter myWriter = new FileWriter("./assets/files/users_test.txt", true);
            myWriter.write("\nFiles in Java might be tricky, but it is fun enough!");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        
    }
}
