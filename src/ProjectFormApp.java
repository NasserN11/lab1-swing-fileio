import javax.swing.*;

public class ProjectFormApp {
    public static void main(String[] args) {

        // Main Frame
        JFrame frame = new JFrame("Software Project Registration Form");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        ProjectFormPanel panel = new ProjectFormPanel();

        frame.add(panel);


        frame.setVisible(true);
    }
}
