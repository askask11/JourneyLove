/*Editor: Johnson Gao

 * Date This Class: June 2019
 * Description Of This Class: A class to help user convert their music from mp3 to wav, or even download their music.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javazoom.jl.decoder.JavaLayerException;

/**
 * A class to help user convert their music from mp3 to wav, or even download
 * their music.
 *
 * @author Johnson Gao
 */
public class MusicConvertTool extends JFrame
{

    //Final:font
    /**
     * A constant decleared for the font on the top.
     */
    final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
    final static Font MESSAGE_FONT = new Font("Comic Sans MS", Font.BOLD, 15);
    /**
     * Holds things for the title.
     */
    private JLabel titleLabel;
    private JLabel introduceLabel;
    /**
     * This button disposes the frame.
     */
    private JButton returnButton;
    //Panels declearing seq: N - S - W - E
    /**
     * Mother panel on the north.
     */
    private JPanel northPanel;
    private JPanel southPanel;
    private Box westBox;
    private JPanel operationPanel, centerPanel;
    private JRadioButton fromListRadioButton, fromFileRadioButton, fromOnlineRadioButton, toUpdateRadioButton, toAddButton, doNothingButton;
    final ImageManager IM = new ImageManager();
    private BackgroundMusic backgroundMusic;
    private JButton selectButton, fileOutputButton, chooseFileButton, confirmButton;
    private JLabel selectedInfoLabel, messageLabel;
    //private Box urlNameBox;
    private JTextField urlField, nameField;
    private ButtonGroup fromGroup, toGroup, saveGroup;
    private Box eastBox;
    private JPanel saveNConvertPanel;
    //final LovelyColors LOVELY_COLORS = new LovelyColors();
    private JFileChooser chooser;
    private int type = MyMusic.TYPE_UNDEFINED;
    final MusicConverter CONVERTER = new MusicConverter();
    final SecretGardenConnection CONNECTION = new SecretGardenConnection();
    private JRadioButton saveToDefaultRadioButton, saveToDestRadioButton;
    private String saveAddress = "", lyricAddress = "", time = "";
    private JMenuBar myBar;
    private JMenu preferencesMenu;
    private JMenuItem savingPathItem;
    private int id;

    //private File chosenFile = null;
    /**
     * A class to help user convert their music from mp3 to wav, or even
     * download their music.
     *
     * @param backgroundMusic The backagound music comes together.
     */
    public MusicConvertTool(BackgroundMusic backgroundMusic)
    {
        /**
         * Constructing sequence: JFrame JLabel JButtom JPanel ADDING ?PACK?
         * Rule: When constructing, call"this",otherwise, no "this"unless
         * necessary.
         */

        /**
         * JFrame.
         */
        super("Music Converter");
        this.setBounds(300/*x align R*/, 200/*y align down*/, 800/*X-WIDTH*/, 500/*Y-Width*/);
        this.getContentPane().setBackground(LovelyColors.GLASS_GALL);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.backgroundMusic = backgroundMusic;

        /**
         * JLabel.
         */
        this.titleLabel = new JLabel("Music Convert Tool", IM.openIcon("Convertmusic.png"), SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setVerticalTextPosition(SwingConstants.CENTER);
//        introduceLabel = new JLabel("<html>Welcome to use music converter! <br>"
//                + "You can import music in 3 ways:<br>"
//                + "1.Choose your local file.<br>"
//                + "2.Enter online url<br>"
//                + "3.Select from your existing music table."
//                + "Enjoy your music !");
//        introduceLabel.setFont(MESSAGE_FONT);
        selectedInfoLabel = new JLabel("Info: ");
        messageLabel = new JLabel("Welcome to use converter ");
        messageLabel.setFont(MESSAGE_FONT);
        /**
         * JButton.
         */
        this.returnButton = new JButton("return", IM.openIcon("next.png", 16, 16));
        returnButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            this.dispose();
        });
        selectButton = new JButton("Select", IM.openIcon("V.png", 16, 16));
        selectButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            selectFromList();
        });
        fileOutputButton = new JButton("Save At", IM.openIcon("save.png", 16, 16));
        fileOutputButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            chooseSaveAddress();
        });
        chooseFileButton = new JButton("Choose Your File", IM.openIcon("newFile.png", 16, 16));
        chooseFileButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            chooseFile();
        });
        confirmButton = new JButton("Convert!", IM.openIcon("V.png", 16, 16));
        confirmButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            convert();
        });
        /**
         * jmenu
         */
        savingPathItem = new JMenuItem("Default Saving Path");
        savingPathItem.addActionListener((e) ->
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            JFileChooser saver;
            try
            {
                saver = new JFileChooser(CONNECTION.getDefaultSavingPath());
                saver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int confirmed = saver.showSaveDialog(null);
                if (confirmed == JFileChooser.APPROVE_OPTION)
                {
                    CONNECTION.updateDefaultSavingPath(saver.getSelectedFile().getAbsolutePath());
                }
            } catch (SQLException ex)
            {
                Logger.getLogger(MusicConvertTool.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        );
        preferencesMenu = new JMenu("Perferences");
        preferencesMenu.add(savingPathItem);

        myBar = new JMenuBar();
        myBar.add(preferencesMenu);
        myBar.setBackground(LovelyColors.BEWITCHED_TREE);
        setJMenuBar(myBar);

        /**
         * JRBUTTON.
         */
        fromFileRadioButton = new JRadioButton("Choose your own file"/*,IM.openIcon("newFile.png", 16, 16)*/);
        fromFileRadioButton.setOpaque(false);
        fromFileRadioButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            validateInput();
        });
        fromListRadioButton = new JRadioButton("From your musiclist"/*,IM.openIcon("register.png")*/);
        fromListRadioButton.setOpaque(false);
        fromListRadioButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            validateInput();
        });
        fromOnlineRadioButton = new JRadioButton("Enter URL"/*,IM.openIcon("InternetSearch.png")*/);
        fromOnlineRadioButton.setOpaque(false);
        fromOnlineRadioButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            validateInput();
        });
        toUpdateRadioButton = new JRadioButton("Update selected music"/* , IM.openIcon("↑.png")*/);
        toUpdateRadioButton.setOpaque(false);
        toUpdateRadioButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
        });
        toAddButton = new JRadioButton("Add into list"/*,IM.openIcon("+.png")*/);
        toAddButton.setOpaque(false);
        toAddButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
        });
        doNothingButton = new JRadioButton("Don't save into list"/*,IM.openIcon("X.png")*/);
        doNothingButton.setOpaque(false);
        doNothingButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
        });
        saveToDefaultRadioButton = new JRadioButton("Use your default address.");
        saveToDefaultRadioButton.setOpaque(false);
        saveToDefaultRadioButton.setSelected(true);
        saveToDefaultRadioButton.addActionListener((ActionEvent e) ->
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
        });

        saveToDestRadioButton = new JRadioButton("Save To Selected Directory");
        saveToDestRadioButton.setOpaque(false);
        saveToDestRadioButton.addActionListener((e) ->
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
        });

        /**
         * Group them.
         */
        fromGroup = new ButtonGroup();
        fromGroup.add(fromFileRadioButton);
        fromGroup.add(fromListRadioButton);
        fromGroup.add(fromOnlineRadioButton);
        toGroup = new ButtonGroup();
        toGroup.add(toAddButton);
        toGroup.add(toUpdateRadioButton);
        toGroup.add(doNothingButton);
        saveGroup = new ButtonGroup();
        saveGroup.add(saveToDefaultRadioButton);
        saveGroup.add(saveToDestRadioButton);

        /**
         * JTextfield.
         */
        urlField = new JTextField(30);
        urlField.setOpaque(false);
        urlField.setBorder(BorderFactory.createTitledBorder("URL"));
        nameField = new JTextField(20);
        nameField.setOpaque(false);
        nameField.setBorder(BorderFactory.createTitledBorder("Name"));

        /**
         * Boxes.
         */
        westBox = Box.createVerticalBox();
//        westBox.add(new JLabel(IM.openIcon("LookDown.jpg", 163, 163)));
        westBox.add(fromFileRadioButton);
        westBox.add(fromOnlineRadioButton);
        westBox.add(fromListRadioButton);
        westBox.add(new JLabel("Save At -->"));
        westBox.add(saveToDefaultRadioButton);
        westBox.add(saveToDestRadioButton);
        westBox.add(fileOutputButton);
        westBox.setBorder(BorderFactory.createTitledBorder("Where does your file comes from"));
        westBox.setOpaque(false);

        eastBox = Box.createVerticalBox();
//        eastBox.add(new JLabel(IM.openIcon("HeIsCool.jpg", 112, 112)));
        eastBox.add(toAddButton);
        eastBox.add(toUpdateRadioButton);
        eastBox.add(doNothingButton);
        eastBox.setBorder(BorderFactory.createTitledBorder("To Do With The List"));
        eastBox.setOpaque(false);

        /**
         * JPanel. Add component inside.
         */
        saveNConvertPanel = new JPanel(new GridLayout(2, 0));
        // saveNConvertPanel.add(fileOutputButton);
        saveNConvertPanel.add(confirmButton);
        saveNConvertPanel.setOpaque(false);
        this.northPanel = new JPanel();
        northPanel.add(titleLabel, SwingConstants.CENTER);
//        northPanel.add(introduceLabel,BorderLayout.CENTER,SwingConstants.CENTER);

        northPanel.setOpaque(false);
        this.southPanel = new JPanel(new FlowLayout());
        //southPanel.add(fileOutputButton);
        //southPanel.add(confirmButton);
        southPanel.add(returnButton);
        southPanel.setOpaque(false);

        operationPanel = new JPanel(new FlowLayout());
        operationPanel.add(selectButton);
        operationPanel.add(selectedInfoLabel);
        operationPanel.add(urlField);
        operationPanel.add(nameField);
        operationPanel.add(chooseFileButton);
        //centralPanel.add(fileOutputButton);
        operationPanel.setOpaque(false);
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(operationPanel, BorderLayout.CENTER, SwingConstants.CENTER);
        centerPanel.add(messageLabel, BorderLayout.NORTH, SwingConstants.CENTER);
        centerPanel.add(saveNConvertPanel, BorderLayout.SOUTH);
        centerPanel.setOpaque(false);
        /**
         * Add components and finalize frame.
         */
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(westBox, BorderLayout.WEST);
        this.add(eastBox, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        validateInput();
        this.setVisible(true);
        chooser = new JFileChooser();
        checkDefaultAddress();
    }

    /**
     * Check if the user have any default address.
     */
    public void checkDefaultAddress()
    {
        try
        {
            if (CONNECTION.getDefaultSavingPath().isEmpty())
            {
                messageLabel.setText("You don't have an saved download address yet!");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            messageLabel.setText("Unable to contact db");
        }
    }

    /**
     * Refresh the control borad.
     */
    public void validateInput()
    {
        urlField.setVisible(fromOnlineRadioButton.isSelected() || fromFileRadioButton.isSelected());
        nameField.setVisible(fromOnlineRadioButton.isSelected() || fromFileRadioButton.isSelected());
        chooseFileButton.setVisible(fromFileRadioButton.isSelected());
        selectButton.setVisible(fromListRadioButton.isSelected());
        selectedInfoLabel.setVisible(fromListRadioButton.isSelected());
        toUpdateRadioButton.setEnabled(fromListRadioButton.isSelected());
        toAddButton.setSelected(!fromListRadioButton.isSelected());
        toUpdateRadioButton.setSelected(fromListRadioButton.isSelected());
        urlField.setText("");
        nameField.setText("");
//        saveAddress = "";
    }

    /**
     * Allow user to choosr their own file using JFilechoose.
     */
    public void chooseFile()
    {
        try
        {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(BackgroundMusic.MUSIC_FILTER);
            int confirmed = chooser.showOpenDialog(null);
            if (confirmed == JFileChooser.APPROVE_OPTION)
            {
                String address = chooser.getSelectedFile().getAbsolutePath();
                urlField.setText(address);
                nameField.setText(address.substring(address.lastIndexOf("\\"), address.lastIndexOf(".")).replace("\\", ""));

                if (nameField.getText().length() < 3)
                {
                    nameField.setText(nameField.getText() + "   ");
                }
                //chosenFile = chooser.getSelectedFile();
                type = MyMusic.TYPE_LOCAL;
                messageLabel.setText("File are selected!");
            }
        } catch (Exception e)
        {
            Warning warning = new Warning("Sorry we cannot open your file Detail: " + e.toString(), "Please try again.",e,false);
            //e.printStackTrace();
        }
    }

    /**
     * To read the selected list in the background music.
     */
    public void selectFromList()
    {
        backgroundMusic.setVisible(true);
        int row = backgroundMusic.getMusicTable().getSelectedRow();

        if (!backgroundMusic.isEmpty())
        {
            if (row == -1)
            {
                backgroundMusic.getMusicTable().setRowSelectionInterval(0, 0);
            }
            try
            {
                MyMusic music = backgroundMusic.getMyMusics().get(row);
                urlField.setText(music.getUrl().replace("\\", "/").replace("\"", "").trim());
                nameField.setText(music.getName());
                id = music.getId();
                type = music.getType();
                time = music.getTime();
                lyricAddress = music.getLyricAddress();

                selectedInfoLabel.setText("Info: Name -> " + nameField.getText() + " ; Type -> " + backgroundMusic.getMyMusics().get(row).getTypeString());
            } catch (Exception e)
            {
               Warning warning = Warning.createWarningDialog(e);
                //Warning warning = new Warning("Failed to select from the list detail: " + e.toString(),"");
            }

        } else
        {
            Warning warning = new Warning("You don't have any musics yet!");
            warning.pack();
        }
    }

    /**
     * Read music from online.
     *
     * @return The converted file.
     * @throws SQLException
     * @throws IOException
     * @throws JavaLayerException
     */
    public File confirmReadFromOnline() throws SQLException, IOException, JavaLayerException
    {
        String outPath;
        File convertedFile = null;
        if (!urlField.getText().isEmpty() && !nameField.getText().isEmpty())
        {
            if (saveToDefaultRadioButton.isSelected())
            {
                outPath = CONNECTION.getDefaultSavingPath();
                if (!outPath.isEmpty())
                {

                    convertedFile = CONVERTER.convertAndSave(new URL(urlField.getText()), nameField.getText(), new File(outPath));
                    System.out.println("Converted!");
                    messageLabel.setText("Converted & Saved.");
                    urlField.setText("");
                    nameField.setText("");
                    type = MyMusic.TYPE_ONLINE;

                } else
                {
                    Warning warn = new Warning("You don't have a default saving path yet.");
                    warn.setSolution(()->{chooseSaveAddress();});
                    warn.pack();
                }
            } else
            {
                if (!saveAddress.isEmpty())
                {
                    convertedFile = CONVERTER.convertAndSave(new URL(urlField.getText()), nameField.getText(), new File(saveAddress));
                    urlField.setText("");
                    nameField.setText("");
                    messageLabel.setText("Converted and saved.");
                } else
                {
                    messageLabel.setText("You must select a saving path.");
                }
            }
        } else
        {
            Warning warning = new Warning("You must enter URL!!");
            warning.pack();
        }
        return convertedFile;
    }

    /**
     * Allow user to choose a saving address.
     */
    public void chooseSaveAddress()
    {
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int confirmed = chooser.showSaveDialog(null);
        if (confirmed == JFileChooser.APPROVE_OPTION)
        {
            saveAddress = chooser.getSelectedFile().getAbsolutePath();
            messageLabel.setText("new saving address are assigned!");
        }

    }

    /**
     * Convert .mp3 music to .wav and save.
     */
    public void convert()
    {
        String address = saveAddress;
        File convertedFile = null;
        if (saveToDefaultRadioButton.isSelected())
        {
            try
            {
                address = CONNECTION.getDefaultSavingPath();

            } catch (SQLException ex)
            {
                Warning.createWarningDialog(ex);
                //Logger.getLogger(MusicConvertTool.class.getName()).log(Level.SEVERE, null, ex);
                //new Warning("Cannot contact database");
            }
        }

        if (fromFileRadioButton.isSelected())
        {
            if (!urlField.getText().isEmpty() && !nameField.getText().isEmpty())
            {
                try
                {

                    if (!address.isEmpty())
                    {
                        //Invoke the converter.
                        convertedFile = CONVERTER.convertAndSave(new File(urlField.getText()).toURI().toURL(), nameField.getText(), new File(address));
                        urlField.setText("");
                        nameField.setText("");
                        messageLabel.setText("Converted successfully!");
                    } else
                    {
                        Warning warning = new Warning("Please select an address or use your default address.");
                    }

                } catch (IOException ex)
                {
                    Warning.createWarningDialog(ex);
                    //Logger.getLogger(MusicConvertTool.class.getName()).log(Level.SEVERE, null, ex);
                    //Warning warning = new Warning("Cannot find your file!! " + ex.getMessage());
                } catch (JavaLayerException ex)
                {
                    Warning createWarningDialog = Warning.createWarningDialog(ex);
                    //
                    //Logger.getLogger(MusicConvertTool.class.getName()).log(Level.SEVERE, null, ex);
                    //Warning warning = new Warning("Cannot Convert " + ex.getMessage());
                }
//                } catch (SQLException ex)
//                {
//                    Logger.getLogger(MusicConvertTool.class.getName()).log(Level.SEVERE, null, ex);
//                     Warning warning = new Warning("Cannot connect to database "+ ex.getMessage());
//                }
            } else
            {
                messageLabel.setText("Information Incomplete");
            }
        } else if (fromOnlineRadioButton.isSelected())
        {
            try
            {
                convertedFile = confirmReadFromOnline();
            } catch (SQLException ex)
            {
                Logger.getLogger(MusicConvertTool.class.getName()).log(Level.SEVERE, null, ex);
                messageLabel.setText("Cannot connect to your database.");
            } catch (IOException ex)
            {
                Logger.getLogger(MusicConvertTool.class.getName()).log(Level.SEVERE, null, ex);
                messageLabel.setText("Cannot find your file.");
            } catch (JavaLayerException ex)
            {
                Logger.getLogger(MusicConvertTool.class.getName()).log(Level.SEVERE, null, ex);
                messageLabel.setText("Cannot convert");
            }
        } else if (fromListRadioButton.isSelected())
        {
            try
            {
                if (!address.isEmpty())
                {
                    switch (type)
                    {
                        case MyMusic.TYPE_ONLINE:
                            convertedFile = CONVERTER.convertAndSave(new URL(urlField.getText()), nameField.getText(), new File(address));
                            selectedInfoLabel.setText("Converted!");
                            messageLabel.setText("Converted and saved to local!");
                            break;
                        case MyMusic.TYPE_LOCAL:
                            convertedFile = CONVERTER.convertAndSave(new File(urlField.getText()).toURI().toURL(), nameField.getText(), new File(address));
                            selectedInfoLabel.setText("Converted!");
                            messageLabel.setText("Converted and saved to local!");
                            break;
                        case MyMusic.TYPE_PACKAGE:
                        {
                            Warning warn = new Warning("You cannot convert music from package");
                            break;
                        }
                        default:
                        {
                            Warning warn = new Warning("Undefined type!");
                            break;
                        }
                    }
                } else
                {
                    messageLabel.setText("Cannot find your address.");
                }

            } catch (IOException | JavaLayerException e)
            {
                Warning warning = new Warning(e.getMessage());
            }

        } else
        {
            Warning warning = new Warning("Oh please select where is your file come from");
        }

        if (convertedFile != null)
        {
            if (toUpdateRadioButton.isSelected())
            {
                try
                {
                    MyMusic music = new MyMusic(nameField.getText(), convertedFile.getAbsolutePath(), MyMusic.TYPE_LOCAL);
                    music.setLyricAddress(lyricAddress);
                    music.setTime(time);
                    CONNECTION.updateMusicList(id, music);
                    backgroundMusic.refreshTabel();
                } catch (Exception ex)
                {
                    Logger.getLogger(MusicConvertTool.class.getName()).log(Level.SEVERE, null, ex);
                    Warning warn = new Warning("Cannot save to your db.");
                }
            } else if (toAddButton.isSelected())
            {
                try
                {
                    id = Randomizer.randomInt(1000, 9999);

                    backgroundMusic.insertIntoMusicList(nameField.getText(), convertedFile.getAbsolutePath(), MyMusic.TYPE_LOCAL, id, "");
                    // CONNECTION.addIntoMusicList(new MyMusic(nameField.getText(), convertedFile.getAbsolutePath(), MyMusic.TYPE_LOCAL, id));
                    backgroundMusic.refreshTabel();
                } catch (Exception ex)
                {
                    Logger.getLogger(MusicConvertTool.class.getName()).log(Level.SEVERE, null, ex);
                    Warning warn = new Warning(ex.getMessage());
                }
            }
        } else
        {
            messageLabel.setText("Cannot convert your file");
        }
    }

    /**
     * Produces an unstoppable little sound.
     *
     * @param soundName The name of the clip in the package.
     */
    public void clickSound(String soundName)
    {
        try
        {
            Clip effectClip = AudioSystem.getClip();
            effectClip.open(AudioSystem.getAudioInputStream(getClass().getResource(soundName)));
            effectClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
        {
            Warning warning = new Warning("Cannot open sound  " + ex.toString());
        }

    }

    /**
     * Test main method.
     *
     * @param args Lines command argument.
     */
    public static void main(String[] args)
    {
        BackgroundMusic backgroundMusic = new BackgroundMusic();
        backgroundMusic.setDefaultCloseOperation(EXIT_ON_CLOSE);
        backgroundMusic.setVisible(true);
        MusicConvertTool standardFrame = new MusicConvertTool(backgroundMusic);
        standardFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
