import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EncoderDecoder {
    private final String referenceTable;

    public EncoderDecoder(String referenceTable) {
        this.referenceTable = referenceTable;
    }

    public String encode(String plainText, char offset) {
        int offsetIndex = referenceTable.indexOf(offset);
        if (offsetIndex == -1) {
            throw new IllegalArgumentException("Offset character not found in reference table");
        }

        StringBuilder encodedText = new StringBuilder();
        encodedText.append(offset); // Set the first character as the offset character
        for (char c : plainText.toCharArray()) {
            int index = referenceTable.indexOf(c);
            if (index != -1) {
                int newIndex = (index - offsetIndex + referenceTable.length()) % referenceTable.length();
                encodedText.append(referenceTable.charAt(newIndex));
            } else {
                encodedText.append(c);
            }
        }
        return encodedText.toString();
    }

    public String decode(String encodedText) {
        char offset = encodedText.charAt(0);
        int offsetIndex = referenceTable.indexOf(offset);
        if (offsetIndex == -1) {
            throw new IllegalArgumentException("Offset character not found in reference table");
        }

        StringBuilder decodedText = new StringBuilder();
        for (int i = 1; i < encodedText.length(); i++) {
            char c = encodedText.charAt(i);
            int index = referenceTable.indexOf(c);
            if (index != -1) {
                int newIndex = (index + offsetIndex + referenceTable.length()) % referenceTable.length();
                decodedText.append(referenceTable.charAt(newIndex));
            } else {
                decodedText.append(c);
            }
        }
        return decodedText.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String referenceTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";
        EncoderDecoder encoderDecoder = new EncoderDecoder(referenceTable);

        String plainText = "HELLO WORLD";
        String encodedTextB = encoderDecoder.encode(plainText, 'B');
        System.out.println("Encoded text with offset B: " + encodedTextB); // BGDKKN VNQKC

        String decodedTextB = encoderDecoder.decode(encodedTextB);
        System.out.println("Decoded text with offset B: " + decodedTextB); // HELLO WORLD

        String encodedTextF = encoderDecoder.encode(plainText, 'F');
        System.out.println("Encoded text with offset F: " + encodedTextF); // FC/GGJ RJMGC

        String decodedTextF = encoderDecoder.decode(encodedTextF);
        System.out.println("Decoded text with offset F: " + decodedTextF); // HELLO WORLD

        System.out.print("Enter plain text: ");
        plainText = scanner.nextLine().toUpperCase();

        System.out.print("Enter offset character: ");
        char offset = Character.toUpperCase(scanner.next().charAt(0));

        String encodedText = encoderDecoder.encode(plainText, offset);
        System.out.println("Encoded text: " + encodedText);

        String decodedText = encoderDecoder.decode(encodedText);
        System.out.println("Decoded text: " + decodedText);

        scanner.close();
    }
}
