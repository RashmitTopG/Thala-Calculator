import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

// import java.util.Arrays;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

class ThalaCalculator {

    public boolean checkThala(String s) {
        s = s.trim();

        if (s.isEmpty()) {
            return false;
        }

        if (isNumeric(s)) {
            int sum = 0;
            int num = Integer.parseInt(s);

            char[] arr = s.toCharArray();

            Arrays.sort(arr);

            for (char c : arr) {
                sum += Character.getNumericValue(c);
            }

            int diff = Character.getNumericValue(arr[arr.length - 1]);
            for (int i = 0; i < arr.length - 1; i++) {
                diff -= Character.getNumericValue(arr[i]);
            }

            return sum % 7 == 0 || num % 7 == 0 || diff % 7 == 0;
        } else {
            return s.length() == 7 || s.length() % 7 == 0;
        }
    }

    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}

public class Thala {

    String path = "src\\images\\BoleJoKoyal.wav";
    Clip audioClip; // Keep track of the audio clip

    JFrame frame;
    JLabel thalaLabel;
    JLabel inputLabel;
    JTextField inputField;
    String input;
    JButton submit;
    JButton reset; // New button for reset
    JLabel reasaonLabel1;
    JLayeredPane pane;
    JLabel backGroundLabel;
    ImageIcon back;
    JLabel imgLabel1;
    JLabel imgLabel2;
    ImageIcon thala;
    JLabel dhoniLabel;
    ImageIcon dhoni;

    Thala() {

        frame = new JFrame("THALA CALCULATOR");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        pane = new JLayeredPane();
        pane.setPreferredSize(new Dimension(600, 600));

        back = new ImageIcon("src\\images\\Ground.png");
        thala = new ImageIcon("src\\images\\Thala2.jpg");
        dhoni = new ImageIcon("src\\images\\Dhoni2.png");

        backGroundLabel = new JLabel(back);
        backGroundLabel.setBounds(0, 0, 600, 600);
        pane.add(backGroundLabel, JLayeredPane.DEFAULT_LAYER);

        thalaLabel = new JLabel("WELCOME TO THALA CALCULATOR");
        thalaLabel.setBounds(75, 20, 475, 50);
        thalaLabel.setFont(new Font("Impact", Font.BOLD, 35));
        thalaLabel.setForeground(Color.BLACK);

        imgLabel1 = new JLabel(thala);
        imgLabel2 = new JLabel(thala);
        dhoniLabel = new JLabel(dhoni);

        imgLabel1.setBounds(50, 300, 100, 179);
        imgLabel1.setVisible(false);
        imgLabel2.setBounds(450, 300, 100, 179);
        imgLabel2.setVisible(false);
        dhoniLabel.setBounds(250, 300, dhoni.getIconWidth(), dhoni.getIconHeight());
        dhoniLabel.setVisible(false);

        pane.add(thalaLabel, JLayeredPane.PALETTE_LAYER);

        inputLabel = new JLabel("ENTER A STRING OR NUMBER");
        inputLabel.setBounds(100, 80, 450, 100);
        inputLabel.setFont(new Font("Consolas", Font.BOLD, 32));
        inputLabel.setForeground(Color.ORANGE);
        pane.add(inputLabel, JLayeredPane.MODAL_LAYER);

        inputField = new JTextField();
        inputField.setBounds(215, 165, 150, 27);
        inputField.setFont(new Font("Consolas", Font.BOLD, 20));
        pane.add(inputField, JLayeredPane.MODAL_LAYER);

        reasaonLabel1 = new JLabel("THALA FOR A REASON");
        reasaonLabel1.setBounds(125, 480, 400, 100);
        reasaonLabel1.setFont(new Font("Impact", Font.BOLD, 40));
        reasaonLabel1.setForeground(Color.YELLOW);
        reasaonLabel1.setVisible(false);
        pane.add(reasaonLabel1, JLayeredPane.MODAL_LAYER);

        submit = new JButton("SUBMIT");
        submit.setBounds(190, 235, 85, 27);
        submit.setFocusable(false);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input = inputField.getText();
                boolean answer = new ThalaCalculator().checkThala(input);

                if (answer) {
                    playAudio(path);
                    reasaonLabel1.setVisible(true);
                    imgLabel1.setVisible(true);
                    imgLabel2.setVisible(true);
                    dhoniLabel.setVisible(true);
                }
                inputField.setText(null);
            }
        });
        pane.add(submit, JLayeredPane.MODAL_LAYER);

        reset = new JButton("RESET");
        reset.setBounds(310, 235, 85, 27);
        reset.setFocusable(false);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText(null);
                stopAudio();
                reasaonLabel1.setVisible(false);
                imgLabel1.setVisible(false);
                imgLabel2.setVisible(false);
                dhoniLabel.setVisible(false);
            }
        });
        pane.add(reset, JLayeredPane.MODAL_LAYER);

        pane.add(imgLabel1, JLayeredPane.MODAL_LAYER);
        pane.add(imgLabel2, JLayeredPane.MODAL_LAYER);
        pane.add(dhoniLabel, JLayeredPane.MODAL_LAYER);

        frame.add(pane);
        frame.setVisible(true);
    }

    private void playAudio(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void stopAudio() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
        }
    }

    public static void main(String[] args) {
        new Thala();
    }
}
