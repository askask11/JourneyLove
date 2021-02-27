/*Editor: Johnson Gao

 * Date This Class: July 2019
 * Description Of This Class: To allow user to see the lyric of a song.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import static journeylove.ImageList.IM;

/**
 * This class is a model of standard JFrames.
 *
 * @author Johnson Gao
 */
public class LyricWindow extends JFrame implements ActionListener, ItemListener
{

    //Final:font
    /**
     * A constant decleared for the font on the top.
     */
    final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
    final static Font LYRIC_FONT = new Font(null, Font.PLAIN, 17);
    final static Font CURRENT_LURIC_FONT = new Font(null, Font.PLAIN, 25);
    /**
     * Holds things for the title.
     */
    private JLabel previousLyricLabel,
            currentLyricLabel,
            nextLyricLabel,
            settingLabel,
            musicIconLabel, delayLabel;
    /**
     * This button disposes the frame.
     */
    private JButton returnButton,
            foreGroundColorButton,
            backgroundColorButton,
            addSizeButton,
            reduceSizeButton,
            defaultDelayButton;

    // private JTextField offsetField;
    //Panels declearing seq: N - S - W - E
    /**
     * Mother panel on the north.
     */
    //private JPanel northPanel;
    private JPanel southPanel,
            settingPanel,
            southButtonPanel;
    //private JPanel eastPanel;
    private JPanel centralPanel;
    private JPopupMenu contentPaneMenu,
            previousLineMenu,
            currentLineMenu,
            nextLineMenu;
    private JMenuItem contentPaneItem,
            previousLineSetItem,
            currentLineSetItem,
            nextLienSetItem;

    private JComboBox<String> fontFamilyComboBox,
            line1FontSizeComboBox,
            line2FontSizeComboBox,
            line3FontSizeComboBox;
    private JSlider delaySlider;
    public static final String[] FONTSIZE_SELECTIONS =
    {
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "15.5",
        "16",
        "16.5",
        "17",
        "17.5",
        "18",
        "18.5",
        "19",
        "19.5",
        "20",
        "20.5",
        "21",
        //"21.5",
        "22",
        // "22.5",
        "23",
        // "23.5",
        "24",
        //  "24.5",
        "25",
        "26",
        "27",
        "28",
        "30",
        "32",
        "36",
        "39",
        "40"
    };
    public static final String[] FONT_SELECTION = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    public static final String SETTINGICON_BLUE = "settingBlue.png",
            SETTINGICON_DARKBLUE = "settingDarkBlue.png";
    private javax.sound.sampled.Clip effectClip;
    private Rectangle boundsShortCut;
    private BackgroundMusic backgroundMusic;
    private JTextField nameField, artistField, editorField, albumField;
    private JPanel infoPanel;

    /**
     * Test mode.
     */
    public LyricWindow()
    {
        this(null);
    }

    /**
     * This is a sub frame based on BackgroundMusic class, which offers the
     * function of viewing lyric with the song and do some adjustment to the
     * main musicplayer. This must be displayed with the main musicplayer.
     *
     * @param bgmFrame
     */
    public LyricWindow(BackgroundMusic bgmFrame)
    {
        /**
         * JFrame.
         */
        super("Lyrics");
        this.setBounds(400/*x align R*/, 650/*y align down*/, 900/*X-WIDTH*/, 150/*Y-Width*/);
        this.getContentPane().setBackground(Color.PINK);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        backgroundMusic = bgmFrame;
        //boundsShortCut = getBounds();

        /**
         * JPopup
         */
        contentPaneItem = new JMenuItem("DIY Lyric Window");
        contentPaneItem.addActionListener(this);
        previousLineSetItem = new JMenuItem("DIY the pervious line");
        previousLineSetItem.addActionListener(this);
        currentLineSetItem = new JMenuItem("DIY the current line.");
        currentLineSetItem.addActionListener(this);
        nextLienSetItem = new JMenuItem("DIY the next line.");
        nextLienSetItem.addActionListener(this);
        previousLineMenu = new JPopupMenu();
        previousLineMenu.add(previousLineSetItem);
        currentLineMenu = new JPopupMenu();
        currentLineMenu.add(currentLineSetItem);
        nextLineMenu = new JPopupMenu();
        nextLineMenu.add(nextLienSetItem);
        contentPaneMenu = new JPopupMenu();
        contentPaneMenu.add(contentPaneItem);

        /**
         * JLabel.
         */
//        this.titleLabel = new JLabel("My Frame");
//        titleLabel.setFont(TITLE_FONT);
        previousLyricLabel = new JLabel("Play My Style! Click to DIY my player>>");
        previousLyricLabel.setFont(LYRIC_FONT);
        previousLyricLabel.setComponentPopupMenu(previousLineMenu);
        previousLyricLabel.setName("Previus Line");
        previousLyricLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        previousLyricLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previousLyricLabel.setForeground(new Color(102, 102, 255));

        currentLyricLabel = new JLabel("Welcome to use Journey's player!");
        currentLyricLabel.setFont(CURRENT_LURIC_FONT);
        currentLyricLabel.setName("Current Line");
        currentLyricLabel.setComponentPopupMenu(currentLineMenu);
//        currentLyricLabel.addMouseListener(new MouseAdapter()
//        {
//            @Override
//            public void mousePressed(MouseEvent e)
//            {
//                 ComponentEditor componentEditor = new ComponentEditor(currentLyricLabel);
//                 componentEditor.removeBackgroundOption();
//            }
//            
//});
        currentLyricLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        currentLyricLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentLyricLabel.setForeground(new Color(0, 0, 255));
        nextLyricLabel = new JLabel("Select a song and play!");
        nextLyricLabel.setFont(LYRIC_FONT);
        nextLyricLabel.setComponentPopupMenu(nextLineMenu);

        nextLyricLabel.setName("Next Line");
        nextLyricLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        nextLyricLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nextLyricLabel.setForeground(new Color(102, 102, 255));

        settingLabel = new JLabel(IM.openIcon(SETTINGICON_DARKBLUE));
        settingLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                settingLabel.setIcon(IM.openIcon(SETTINGICON_BLUE));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                settingLabel.setIcon(IM.openIcon(SETTINGICON_DARKBLUE));
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
                clickSound(SoundOracle.TINY_BUTTON_SOUND);
                boolean flag = settingPanel.isVisible();
                if (flag)
                {
                    setBounds(boundsShortCut);//Restore the shortcut.
                    settingPanel.setVisible(false);
                } else
                {
                    boundsShortCut = getBounds();//Take a shortcut before expanding its size.
                    settingPanel.setVisible(true);
                    pack();
                    Rectangle hereBound = getBounds();
                    setBounds((int) hereBound.getX(), (int) hereBound.getY(), (int) boundsShortCut.getWidth(), (int) hereBound.getHeight());
                }
            }
        });
        musicIconLabel = new JLabel(IM.openIcon("musicDarkBlue.png"));
        musicIconLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                clickSound(SoundOracle.TINY_BUTTON_SOUND);
                infoPanel.setVisible(!infoPanel.isVisible());
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                musicIconLabel.setIcon(IM.openIcon("musicBlue.png"));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                musicIconLabel.setIcon(IM.openIcon("musicDarkBlue.png"));
            }

            @Override
            public String toString()
            {
                return super.toString();
            }
        });
        delayLabel = new JLabel("Delay: 500");
        //offsetLabel = new JLabel("|| Offset: ");

        /**
         * JButton.
         */
        this.returnButton = new JButton("return");
        returnButton.addActionListener(this);
        this.foreGroundColorButton = new JButton("Text Color");
        foreGroundColorButton.addActionListener(this);
        this.backgroundColorButton = new JButton("Background Color");
        backgroundColorButton.addActionListener(this);
        this.addSizeButton = new JButton(IM.openIcon("+.png"));
        addSizeButton.addActionListener(this);
        this.reduceSizeButton = new JButton(IM.openIcon("-.png"));
        reduceSizeButton.addActionListener(this);
        this.defaultDelayButton = new JButton("Default");
        defaultDelayButton.addActionListener(this);

        /**
         * JTextfield.
         */
        nameField = new JTextField();
        nameField.setBorder(new TitledBorder("Name: "));
        nameField.setOpaque(false);
        nameField.setEditable(false);
//        nameField.setText(bgmFrame.getLyricReader().getLyricTitle());
        artistField = new JTextField();
        artistField.setBorder(new TitledBorder("Artist:"));
        artistField.setOpaque(false);
        artistField.setEditable(false);
//        artistField.setText(bgmFrame.getLyricReader().getArtist());
        editorField = new JTextField();
        editorField.setBorder(new TitledBorder("Editor:"));
        editorField.setOpaque(false);
        editorField.setEditable(false);
//        editorField.setText(bgmFrame.getLyricReader().getEditor());
        albumField = new JTextField();
        albumField.setBorder(new TitledBorder("Album: "));
        albumField.setOpaque(false);
        albumField.setEditable(false);
//        albumField.setText(bgmFrame.getLyricReader().getAlbum());
//        offsetField = new JTextField("0");
//        offsetField.setOpaque(false);
//        offsetField.setBorder(new TitledBorder("Offset:"));
//        offsetField.addKeyListener(new KeyAdapter()
//        {
//            @Override
//            public void keyPressed(KeyEvent e)
//            {
//                int code = e.getKeyCode();
//                if(code == KeyEvent.VK_ENTER)
//                {
//                    applyOffset();
//                }
//            }
//});

        /**
         * JCombobox.
         */
        fontFamilyComboBox = new JComboBox<>(FONT_SELECTION);
        fontFamilyComboBox.addItemListener(this);
        fontFamilyComboBox.setOpaque(false);
        fontFamilyComboBox.setBorder(BorderFactory.createTitledBorder("Font family:"));

        line1FontSizeComboBox = new JComboBox<>(FONTSIZE_SELECTIONS);
        line1FontSizeComboBox.setSelectedItem(previousLyricLabel.getFont().getSize2D());
        line1FontSizeComboBox.addItemListener(this);
        line1FontSizeComboBox.setEditable(true);
        line1FontSizeComboBox.setBorder(BorderFactory.createTitledBorder("Previous Line Size:"));
        line1FontSizeComboBox.setOpaque(false);
        line2FontSizeComboBox = new JComboBox<>(FONTSIZE_SELECTIONS);
        line2FontSizeComboBox.setSelectedItem(currentLyricLabel.getFont().getSize2D());
        line2FontSizeComboBox.addItemListener(this);
        line2FontSizeComboBox.setEditable(true);
        line2FontSizeComboBox.setBorder(BorderFactory.createTitledBorder("Current Line Size:"));
        line2FontSizeComboBox.setOpaque(false);
        line3FontSizeComboBox = new JComboBox<>(FONTSIZE_SELECTIONS);
        line3FontSizeComboBox.setSelectedItem(nextLyricLabel.getFont().getSize2D());
        line3FontSizeComboBox.addItemListener(this);
        line3FontSizeComboBox.setEditable(true);
        line3FontSizeComboBox.setBorder(BorderFactory.createTitledBorder("Next Line Size:"));
        line3FontSizeComboBox.setOpaque(false);
//line1FontSizeComboBox.addItemListener(new I);
        /**
         * JSlider.
         */
        delaySlider = new JSlider(0, 950);
        delaySlider.setValue(500);
        delaySlider.setBorder(BorderFactory.createTitledBorder("Refreshing Speed:"));
        delaySlider.setOpaque(false);
        delaySlider.addChangeListener(((e) ->
        {
            int delay = 1000 - delaySlider.getValue();
            bgmFrame.getTimer().setDelay(delay);
            delayLabel.setText("Lyric Delay: " + delay);
        }));
        delaySlider.setToolTipText("<html>Faster speed(lower delay) will allow your lyric to be more percise, but will also use more system resource"
                + "<br>(Speed-Priority/Resource-Priority)</html>");

        /**
         * JPanel.
         */
//        this.northPanel = new JPanel(new FlowLayout());
//        northPanel.add(titleLabel,SwingConstants.CENTER);
//        northPanel.setOpaque(false);
        Box setFontBox = Box.createHorizontalBox();
        setFontBox.add(new JLabel("Font:  "));
        setFontBox.add(fontFamilyComboBox);
        setFontBox.add(line1FontSizeComboBox);
        setFontBox.add(line2FontSizeComboBox);
        setFontBox.add(line3FontSizeComboBox);
        setFontBox.add(addSizeButton);
        setFontBox.add(reduceSizeButton);
        setFontBox.setOpaque(false);
        Box setForegroundBox = Box.createHorizontalBox();
        setForegroundBox.add(new JLabel("Set the overall textcolor."));
        setForegroundBox.add(foreGroundColorButton);
        setForegroundBox.setOpaque(false);
        Box setBackgroundBox = Box.createHorizontalBox();
        setBackgroundBox.add(new JLabel("Set the background color."));
        setBackgroundBox.add(backgroundColorButton);
        setBackgroundBox.setOpaque(false);
        Box delayBox = Box.createHorizontalBox();
        delayBox.add(delaySlider);
        delayBox.add(delayLabel);
        delayBox.add(defaultDelayButton);
//        delayBox.add(offsetLabel);
//        delayBox.add(offsetField);

        delayBox.setOpaque(false);

        this.southButtonPanel = new JPanel(new FlowLayout());
        southButtonPanel.add(settingLabel);
        southButtonPanel.add(musicIconLabel);
        southButtonPanel.setOpaque(false);

        this.settingPanel = new JPanel(new GridLayout(4/*ROWS*/, 0/*COLUMN*/));
        settingPanel.add(setFontBox);
        settingPanel.add(setForegroundBox);
        settingPanel.add(setBackgroundBox);
        settingPanel.add(delayBox);
        settingPanel.setOpaque(false);
        settingPanel.setVisible(false);

        this.infoPanel = new JPanel(new GridLayout(0, 4));
        infoPanel.add(nameField);
        infoPanel.add(artistField);
        infoPanel.add(editorField);
        infoPanel.add(albumField);
        infoPanel.setOpaque(false);
        infoPanel.setVisible(false);

        this.southPanel = new JPanel(new BorderLayout());
//southPanel.add(returnButton);
        southPanel.setComponentPopupMenu(contentPaneMenu);
        southPanel.add(southButtonPanel, BorderLayout.NORTH);
        southPanel.add(infoPanel, BorderLayout.CENTER);
        southPanel.add(settingPanel, BorderLayout.SOUTH);
        southPanel.setOpaque(false);
        centralPanel = new JPanel(new GridLayout(3, 0, 2, 0));

        centralPanel.add(nextLyricLabel, SwingConstants.CENTER);
        centralPanel.add(currentLyricLabel, SwingConstants.CENTER);
        centralPanel.add(previousLyricLabel, SwingConstants.CENTER);
//centralPanel.setComponentPopupMenu(contentPaneMenu);
        centralPanel.setOpaque(false);

        /**
         * Add components and finalize frame.
         */
//        this.add(northPanel,BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        add(centralPanel, BorderLayout.CENTER, SwingConstants.CENTER);
        getContentPane().setName("Background");
        setAlwaysOnTop(true);

//setUndecorated(true);
        this.setVisible(true);
    }

   
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source.equals(returnButton))
        {
            this.setVisible(false);
        } else if (source.equals(contentPaneItem))
        {
            JCheckBox alwaysOnTopBox = new JCheckBox("Always On Top");

            JLabel alwaysOnTopLabel = new JLabel("Set Always On Top");
            ComponentEditor ce = new ComponentEditor(getContentPane());
            ce.removeForegroundOption();
            alwaysOnTopBox.addActionListener((ActionEvent e1) ->
            {
                this.setAlwaysOnTop(alwaysOnTopBox.isSelected());
                // System.out.println("journeylove.LyricWindow.actionPerformed()");
            });
            alwaysOnTopBox.setOpaque(false);
            ce.getWestPanel().add(alwaysOnTopLabel);
            ce.getWestPanel().add(alwaysOnTopBox);
        } else if (source.equals(previousLineSetItem))
        {
            ComponentEditor componentEditor = new ComponentEditor(previousLyricLabel);
            componentEditor.removeBackgroundOption();
        } else if (source.equals(currentLineSetItem))
        {
            ComponentEditor componentEditor = new ComponentEditor(currentLyricLabel);
            componentEditor.removeBackgroundOption();
        } else if (source.equals(nextLienSetItem))
        {
            ComponentEditor componentEditor = new ComponentEditor(nextLyricLabel);
            componentEditor.removeBackgroundOption();
        } else if (source.equals(addSizeButton))
        {
            line1FontSizeComboBox.setSelectedItem(previousLyricLabel.getFont().getSize2D() + 2);
//            setFontSize(previousLyricLabel, );
            line2FontSizeComboBox.setSelectedItem(currentLyricLabel.getFont().getSize2D() + 2);
            line3FontSizeComboBox.setSelectedItem(nextLyricLabel.getFont().getSize2D() + 2);
//            setFontSize(currentLyricLabel, currentLyricLabel.getFont().getSize2D()+2);
//            setFontSize(nextLyricLabel,);
        } else if (source.equals(reduceSizeButton))
        {
            line1FontSizeComboBox.setSelectedItem(previousLyricLabel.getFont().getSize2D() - 2);
            line2FontSizeComboBox.setSelectedItem(currentLyricLabel.getFont().getSize2D() - 2);
            line3FontSizeComboBox.setSelectedItem(nextLyricLabel.getFont().getSize2D() - 2);
        } else if (source.equals(foreGroundColorButton))
        {
            Color color = JColorChooser.showDialog(this, "Choose Color For Lyrics", null);
            if (color != null)
            {
                previousLyricLabel.setForeground(color.brighter().brighter());
                currentLyricLabel.setForeground(color);
                nextLyricLabel.setForeground(color.brighter().brighter());
            }
        } else if (source.equals(backgroundColorButton))
        {
            Color color = JColorChooser.showDialog(this, "Choose Background", this.getContentPane().getBackground());
            if (color != null)
            {
                this.getContentPane().setBackground(color);
            }
        } else if (source.equals(defaultDelayButton))
        {
            delaySlider.setValue(500);
            backgroundMusic.getTimer().setDelay(500);
            delayLabel.setText("500");
        }

    }

    public JTextField getNameField()
    {
        return nameField;
    }

    public JTextField getArtistField()
    {
        return artistField;
    }

    public JTextField getEditorField()
    {
        return editorField;
    }

    public JTextField getAlbumField()
    {
        return albumField;
    }

    public void ClearAllFields()
    {
        nameField.setText("");
        artistField.setText("");
        editorField.setText("");
        albumField.setText("");
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        Object source = e.getSource();
        if (source.equals(fontFamilyComboBox))
        {
            previousLyricLabel.setFont(new Font(fontFamilyComboBox.getItemAt(fontFamilyComboBox.getSelectedIndex()), Font.PLAIN, previousLyricLabel.getFont().getSize()));
            currentLyricLabel.setFont(new Font(fontFamilyComboBox.getItemAt(fontFamilyComboBox.getSelectedIndex()), Font.PLAIN, currentLyricLabel.getFont().getSize()));
            nextLyricLabel.setFont(new Font(fontFamilyComboBox.getItemAt(fontFamilyComboBox.getSelectedIndex()), Font.PLAIN, nextLyricLabel.getFont().getSize()));
        } else if (source.equals(line1FontSizeComboBox))
        {
            applyLine1Size();
        } else if (source.equals(line2FontSizeComboBox))
        {
            applyLine2Size();
        } else if (source.equals(line3FontSizeComboBox))
        {
            applyLine3Size();
        }
    }

    public void applyLine1Size()
    {
        try
        {
            setFontSize(previousLyricLabel, Float.parseFloat(line1FontSizeComboBox.getSelectedItem().toString()));
        } catch (NumberFormatException ex)
        {
            new Warning("Please enter a positive number. For "
                    + line1FontSizeComboBox.getSelectedItem()
                    + " \n because of " + ex.toString() + "", "Check your number.", ex, true, () ->
            {
                line1FontSizeComboBox.requestFocus();
                line1FontSizeComboBox.setPopupVisible(true);
            });
        }
    }

    /**
     * Apply the user selected size to the second line.
     */
    public void applyLine2Size()
    {
        try
        {
            setFontSize(currentLyricLabel, Float.parseFloat(line2FontSizeComboBox.getSelectedItem().toString()));
        } catch (NumberFormatException nfe)
        {
            new Warning("<html>Please enter a positive number. For "
                    + line2FontSizeComboBox.getSelectedItem()
                    + " <br> because of " + nfe.toString() + "</html>", "Check your number.", nfe, true, () ->
            {
                line2FontSizeComboBox.requestFocus();
                line2FontSizeComboBox.setPopupVisible(true);
            });
        }
    }

    /**
     * Apply the user selected size to the third line.
     */
    public void applyLine3Size()
    {
        try
        {
            setFontSize(nextLyricLabel, Float.parseFloat(line3FontSizeComboBox.getSelectedItem().toString()));
        } catch (NumberFormatException nfe)
        {
            Warning warning = new Warning("<html>Please enter a positive number. For "
                    + line3FontSizeComboBox.getSelectedItem()
                    + " <br> because of " + nfe.toString() + "</html>", "Check your number.", nfe, true, () ->
            {
                line3FontSizeComboBox.requestFocus();
                line3FontSizeComboBox.setPopupVisible(true);
            });
        }
    }

    /**
     * A short cut to set the size of the font of a component.
     *
     * @param component The component to be edited.
     * @param size The target size.
     */
    public void setFontSize(Component component, float size)
    {
        component.setFont(component.getFont().deriveFont(size));
    }

    ///////////setter and getters./////////////////
    public void setPreviousLine(String text)
    {
        this.previousLyricLabel.setText(text);
    }

    public void setPreviousLine(LyricLine line)
    {
        this.previousLyricLabel.setText(line.getLyric());
    }

    public void setCurrentLine(String text)
    {
        currentLyricLabel.setText(text);
    }

    public void setCurrentLine(LyricLine line)
    {
        currentLyricLabel.setText(line.getLyric());
    }

    public void setNextLine(String text)
    {
        nextLyricLabel.setText(text);
    }

    public void setNextLine(LyricLine line)
    {
        nextLyricLabel.setText(line.getLyric());
    }

    /**
     * This method is for produce short sound effects, the clip will not under
     * monitor or be operated.
     *
     * @param soundName The name of the sound to be displayed.
     */
    public void clickSound(String soundName)
    {
        try
        {
            effectClip = AudioSystem.getClip();
            effectClip.open(AudioSystem.getAudioInputStream(getClass().getResource(soundName)));
            effectClip.addLineListener((LineEvent e) ->
            {
                LineEvent.Type type = e.getType();
                if (type.equals(LineEvent.Type.STOP))
                {
                    effectClip.close();
                }
            });
            effectClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
        {
            Warning warning = new Warning("Cannot open sound  " + ex.toString(), "", ex, false);
        }

    }

    /**
     * Test main method.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        BackgroundMusic backgroundMusic1 = new BackgroundMusic();
        backgroundMusic1.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //standardFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
