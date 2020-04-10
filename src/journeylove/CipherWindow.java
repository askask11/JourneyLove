/*Editor: Johnson Gao

 * Date This Project Created:
 * Description Of This Class: To allow users gennerate Cipher using gui.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
//import static journeylove.ImageList.IM;

/**
 * Allow the users to make ciphers.
 *
 * @author Johnson Gao
 */
public class CipherWindow extends JFrame implements ActionListener, DocumentListener
{

    private JLabel keyLabel,
            typeLabel,
            titleLabel,
            copyLabel,
            feedbackLabel,
            rotateLabel;

    private JPopupMenu outputMenu,
            keyMenu;

    private JMenuItem copyResultItem, copyKeyItem;

    public static final String[] TYPES_STRINGS =
    {
        "Caesars Cipher",
        "Encrypt Vigenere Cipher",
        "Encrypt Vigenere Cipher(Password equal-length)",
        "Decrypt Vigenere Cipher",//3
        "Decrypt Vigenere Cipher(Password Equal-length)",//4
        "Generate Vigenere Cipher"
    };

    public static final String[] GENERATED_PASSWORD_LENGTHS =
    {
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10"
    };

    private JComboBox<String> typeComboBox, passwordLengthComboBox;

    private JTextField keyField;

    private JTextArea inputArea,
            outputArea;

    private JPanel northPanel, centralPanel, southPanel, inputPanel;

    private JButton confirmButton, clearButton;
    private Box typeBox, keyBox;

    private Clip effectClip;
    public static final ImageManager IM = new ImageManager();
    public final ImageIcon ROTATE_BLUEICON = IM.openIcon("rotateBlue.png"), ROTATE_DARKBLUE = IM.openIcon("rotateDarkblue.png");
    private JScrollPane inputPane,outputPane;
    //private Timer closeTimer;
    public CipherWindow()
    {
        super("Cipher Maker(beta)");
        initComponents();
    }

    /**
     * Initalize the components of this frame.
     */
    public void initComponents()
    {

        setBounds(300, 300, 505, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.pink);

        //jlabel
        titleLabel = new JLabel("Beta Cipher Translator");
        titleLabel.setToolTipText("<html>You can encode or decode some ciphers here."
                + "<br>More ciphers will be supported later </html>");
        typeLabel = new JLabel("The type of your code:");
        keyLabel = new JLabel("Enter key here: ");
        feedbackLabel = new JLabel("Welcome to use Cipher Factory!");
        feedbackLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        copyLabel = new JLabel(IM.openIcon("listDarkBlue.png"));
        copyLabel.setToolTipText("Copy the result.");
        copyLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                copyLabel.setIcon(IM.openIcon("listBlue.png"));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                copyLabel.setIcon(IM.openIcon("listDarkBlue.png"));
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                clickSound(SoundOracle.TINY_BUTTON_SOUND);
                String copy = outputArea.getText();
                clipboardCopy(copy);
                feedbackLabel.setText("Result Copied!");
            }

        });

        rotateLabel = new JLabel(ROTATE_DARKBLUE);
        rotateLabel.setToolTipText("Put your result for the input.");
        rotateLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                rotateLabel.setIcon(ROTATE_BLUEICON);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                rotateLabel.setIcon(ROTATE_DARKBLUE);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                reverse();
                clickSound(SoundOracle.WATER_PRESS_1);
            }
        });

        //jbutton
        confirmButton = new JButton(IM.openIcon("V.png", 16, 16));
        confirmButton.addActionListener(this);
        confirmButton.setOpaque(false);
        confirmButton.setToolTipText("Confirm your operation.");
        clearButton = new JButton(IM.openIcon("X.png", 16, 16));
        clearButton.addActionListener(this);
        clearButton.setOpaque(false);
        clearButton.setToolTipText("Clear all the fields.");

        //JMenuItem
        copyKeyItem = new JMenuItem("Copy");
        copyKeyItem.addActionListener(this);
        copyResultItem = new JMenuItem("Copy");
        copyResultItem.addActionListener(this);

        //JMenu
        outputMenu = new JPopupMenu();
        outputMenu.add(copyResultItem);
        keyMenu = new JPopupMenu();
        keyMenu.add(copyKeyItem);

        //JCombobox
        typeComboBox = new JComboBox<>(TYPES_STRINGS);
        typeComboBox.setToolTipText("Choose the cipher you want to encode/decode");

        typeComboBox.addItemListener((ItemEvent e) ->
        {
            int index = typeComboBox.getSelectedIndex();
            clickSound(SoundOracle.WATER_PRESS_2);
            //System.out.println(e.getItem());
            switch (index)
            {
                case 0:
                    keyLabel.setText("Enter offset:");
                    break;
                case 5:
                    keyLabel.setText("Generated Key: ");
                    keyField.setText("");
                    // passwordLengthComboBox.setVisible(true);
                    break;
                default:
                    keyLabel.setText("Enter Key:");
                    //passwordLengthComboBox.setVisible(false);
                    break;
            }
            feedbackLabel.setText("");
            passwordLengthComboBox.setVisible(index == 5);
            keyField.setEditable(index != 5);
        });

        passwordLengthComboBox = new JComboBox<>(GENERATED_PASSWORD_LENGTHS);
        passwordLengthComboBox.setToolTipText("<html>The length of the password to be generated."
                + "<br> Feel welcome to directly enter your perferred length.</html>");
        passwordLengthComboBox.setEditable(true);
        passwordLengthComboBox.setOpaque(false);
        passwordLengthComboBox.setBorder(new TitledBorder("Length: "));
        passwordLengthComboBox.setVisible(false);
        passwordLengthComboBox.addItemListener((e) ->
        {
            clickSound(SoundOracle.WATER_PRESS_3);
            try
            {
                int index = Integer.parseInt(passwordLengthComboBox.getSelectedItem().toString());
                if (index <= 0)
                {
                    Warning warning = new Warning("Number you entered is invalid", "Please enter an positive integer");
                    passwordLengthComboBox.setSelectedIndex(0);
                }
            } catch (NumberFormatException ex)
            {
                Warning.createWarningDialog(ex);
                // JOptionPane.showMessageDialog(this ,"Warning: please enter an positive integer. Detail: " + ex.toString(), "Warning", JOptionPane.ERROR_MESSAGE);
                passwordLengthComboBox.setSelectedIndex(1);
            }
        });

        //JTextfield
        keyField = new JTextField(15);
        keyField.setBorder(BorderFactory.createTitledBorder("Key:"));
        keyField.setOpaque(false);
        keyField.getDocument().addDocumentListener(this);
        keyField.setComponentPopupMenu(keyMenu);
        keyField.setToolTipText("The password of your cipher.");

        //JTextarea
        inputArea = new JTextArea(2, 30);
        inputArea.setBorder(BorderFactory.createTitledBorder("Enter your code here: "));
        inputArea.setOpaque(false);
        inputArea.getDocument().addDocumentListener(this);
        inputArea.setLineWrap(true);

        outputArea = new JTextArea(2, 30);
        outputArea.setBorder(new TitledBorder("Result:"));
        outputArea.setOpaque(false);
        outputArea.getDocument().addDocumentListener(this);
        outputArea.setLineWrap(true);
        outputArea.setEditable(false);
        outputArea.setComponentPopupMenu(outputMenu);
        
        //JScrollPane
        inputPane = new JScrollPane(inputArea);
        inputPane.setOpaque(false);
        inputPane.getViewport().setOpaque(false);
        
        outputPane = new JScrollPane(outputArea);
        outputPane.setOpaque(false);
        outputPane.getViewport().setOpaque(false);

        //Swing Box JPanel
        typeBox = Box.createHorizontalBox();
        typeBox.add(typeLabel);
        typeBox.add(typeComboBox);
        typeBox.setOpaque(false);
        keyBox = Box.createHorizontalBox();
        keyBox.add(keyLabel);
        keyBox.add(keyField);
        keyBox.add(passwordLengthComboBox);
        keyBox.setOpaque(false);
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.add(feedbackLabel);
        feedbackPanel.setOpaque(false);

        Box centralNorthBox = new Box(BoxLayout.Y_AXIS);
        centralNorthBox.add(typeBox);
        centralNorthBox.add(keyBox);
        centralNorthBox.add(feedbackPanel);
        centralNorthBox.setOpaque(false);

        inputPanel = new JPanel(new GridLayout(2, 0));
        inputPanel.add(inputPane);
        inputPanel.add(outputPane);
        inputPanel.setOpaque(false);

        centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(centralNorthBox, BorderLayout.NORTH);
        centralPanel.add(inputPanel, BorderLayout.CENTER);
        //centralPanel.add(inputPanel,BorderLayout.SOUTH);
        centralPanel.setOpaque(false);

        northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel, SwingConstants.CENTER);
        northPanel.setOpaque(false);

        southPanel = new JPanel(new FlowLayout());
        southPanel.add(confirmButton);
        southPanel.add(clearButton);
        southPanel.add(rotateLabel);
        southPanel.add(copyLabel);

        southPanel.setOpaque(false);

        add(northPanel, BorderLayout.NORTH);
        add(centralPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        setVisible(true);

//        closeTimer = new Timer(7000, this);
//        closeTimer.setRepeats(false);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source.equals(confirmButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            confirm();
        } else if (source.equals(clearButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            clear();
            feedbackLabel.setText("All cleared!");
        } else if (source.equals(copyKeyItem))
        {
            clickSound(SoundOracle.WATER_PRESS_4);
            clipboardCopy(keyField.getText());
            feedbackLabel.setText("Yē Yé Yě Yè Yê The key has been copied to your clipboard.");
        } else if (source.equals(copyResultItem))
        {
            clickSound(SoundOracle.WATER_PRESS_4);
            clipboardCopy(outputArea.getText());
            feedbackLabel.setText("Yē Yé Yě Yè Yê The result has been copied to your clipboard.");
        }
    }

    /**
     * Start to translate the code.
     */
    public void confirm()
    {
        int selection = typeComboBox.getSelectedIndex();//Get user selected index.
        switch (selection)
        {
            case 0:
                try
                {
                    translateCaesarsCipher(inputArea.getText(), Integer.parseInt(keyField.getText()));
                    feedbackLabel.setText("Completed! You can click the icon to copy.");//"Caesars Cipher",
                } catch (Exception e)
                {
                    Warning showInputError = showInputError(e);
                    showInputError.setSolution(() ->
                    {
                        keyField.requestFocus();
                        keyField.selectAll();
                    });
                }
                break;
            case 1:

                try
                {
                    encryptVigenereCode(inputArea.getText(), CipherFactory.parseVigenereCode(keyField.getText(), inputArea.getText().length()));
                    feedbackLabel.setText("Completed!");//VigenereCode
                } catch (Exception e)
                {
                    showInputError(e);
                }
                break;
            case 2:
                try
                {
                    encryptVigenereCode(inputArea.getText(), keyField.getText());
                    feedbackLabel.setText("Completed!");//VigenereCode password equal-length
                } catch (Exception e)
                {
                    showInputError(e);
                }
                break;
            case 3:
                try
                {
                    decryptVigenereCode(inputArea.getText(), CipherFactory.parseVigenereCode(keyField.getText(), inputArea.getText().length()));
                    feedbackLabel.setText("Completed!");
                } catch (Exception e)
                {
                    showInputError(e);
                }

                break;
            case 4:
                try
                {
                    decryptVigenereCode(inputArea.getText(), keyField.getText());
                    feedbackLabel.setText("Completed!");
                } catch (Exception e)
                {
                    showInputError(e);
                }
                break;
            case 5:
                try
                {
                    generateVigenereCode(inputArea.getText());
                    feedbackLabel.setText("Completed! Please remember to copy the key.");
                } catch (Exception e)
                {
                    showInputError(e);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Set all textfields to empty.
     */
    public void clear()
    {
        keyField.setText("");
        inputArea.setText("");
        outputArea.setText("");
    }

    public void translateCaesarsCipher(String in, int offset)
    {
        String out = CipherFactory.encryptCaesarsCode(in, offset);
        output(out);
    }

    public void encryptVigenereCode(String in, String password)
    {
        String out = CipherFactory.encryptVigenereCipher(in, password);
        output(out);
    }

    public void decryptVigenereCode(String in, String password)
    {
        output(CipherFactory.decryptVigenereCipher(in, password));
    }

    /**
     * Generate a code for vigenere code.
     *
     * @param code The words to encrypt.
     * @throws NumberFormatException User entered a non-numberic value in the combobox.
     */
    public void generateVigenereCode(String code) throws NumberFormatException
    {
        String password = Randomizer.randomLetters(CipherFactory.ALL_ALPHABETS_UPPERCASE, Integer.parseInt(passwordLengthComboBox.getSelectedItem().toString()));
        String key = CipherFactory.parseVigenereCode(password, code.length());
        encryptVigenereCode(code, key);
        keyField.setText(password);//Generate a password for user entered.
    }

    /**
     * Output the result.
     *
     * @param out The result.
     */
    public void output(String out)
    {
        outputArea.setText(out);
        clickSound(SoundOracle.UI_DINGDONG);
    }

    /**
     * Produce a small sound.
     *
     * @param soundName The name of the sound.
     * @see SoundOracle
     */
    public void clickSound(String soundName)
    {
        try
        {
            effectClip = AudioSystem.getClip();
            effectClip.open(AudioSystem.getAudioInputStream(getClass().getResource(soundName)));
            effectClip.start();
            //closeTimer.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
        {
            Warning warning = new Warning("Cannot open sound  " + ex.toString(), "", ex, false);
        }
    }

    private Warning showInputError(Throwable cause)
    {
        return Warning.createWarningDialog(cause);
//        JOptionPane.showMessageDialog(this, "<html>Error! <br>"
//                + "For CaesarsCipher. Please make sure you have entered an integer for key."
//                + "<br>Please double check your input "
//                + "<br> Detail:  " + cause.toString() + " </html>", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void reverse()
    {
        //String in = inputArea.getText();
        String out = outputArea.getText();
        if (!out.isEmpty())
        {
            inputArea.setText(out);
        }
        outputArea.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        CipherWindow cipherWindow = new CipherWindow();
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        clickSound(SoundOracle.PHONE_TYPING_SOUND);
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        clickSound(SoundOracle.PHONE_TYPING_SOUND);
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
    }

    /**
     * Copy a text into clipboard.
     *
     * @param toCopy The String to be copied into clipboard.
     */
    public void clipboardCopy(Object toCopy)
    {
        StringSelection selection = new StringSelection(toCopy.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }

    /**
     * Get the text from user's clipboard.
     *
     * @return The text in users clipboard.
     */
    public String paste()
    {
        String str = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this).toString();
        if (str == null)
        {
            return "";
        } else
        {
            return str;
        }
    }

}
