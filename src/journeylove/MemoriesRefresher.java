/*Editor: Johnson Gao

 * Date This Class: May 25 2019
 * Description Of This Class: This reacts as a displayer.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

/**
 * This class is a photo displayer.
 *
 * @author Johnson Gao
 */
public class MemoriesRefresher extends JFrame implements ActionListener
{

    private static final long serialVersionUID = 1L;

    //Final:font
    /**
     * A constant decleared for the font on the top.
     */
    final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
    final static Font MESSAGE_FONT = new Font("Courier New", Font.PLAIN, 20);
    /**
     * Holds things for the title.
     */
    private JLabel titleLabel, imageLabel;
    /**
     * This button disposes the frame.
     */
    private JButton returnButton;
    //Panels declearing seq: N - S - W - E
    /**
     * Mother panel on the north.
     */
    private JPanel northPanel;
    private JPanel mainControllerPanel;
    private JPanel southPanel;
    private JPanel centralPanel;
    //private LovelyColors lc = new LovelyColors();
    private JButton previousButton;
    final ImageManager IM = new ImageManager();
    private BackgroundMusic bgm = new BackgroundMusic();
    private JButton nextButton;
    private JButton playButton;
    private JButton pauseButton;
    private JButton settingButton;
    private Box playMusicBox;
    private JCheckBox playMusicCheckBox;
    private JButton playMusicButton;
    private JButton pauseMusicButton;
    private JButton nextMusicButton;
    private JButton musicSettingsButton;
    private JButton displayListButton;
    private JButton stopButton;
    private JButton enlargeButton;
    private JButton smallerButton;
    private JSlider delayBar;
    private Timer mainTimer;
    private final JScrollPane mainPane;
    private int currentPosition;
    private int status = 0, maxHeight = 1000, maxWidth = 1450;
    private JLabel feedbackLabel;
    private JPanel settingPanel;
    final ImageList LIST = new ImageList();
    private Clip myClip;
    private JCheckBox doScaleCheckBox;
    private ImageIcon nextOrigin;
    private ImageIcon nextDisplay;
    private boolean scaled;
    private JComboBox<String> scaleRatioComboBox;
    private JTextArea descriptionArea;
    private JSplitPane imageDescriptionSplitPane;
    private JPopupMenu textAreaMenu;
    private JMenuItem descAreaEditItem;
    /**
     * This is the ratio set when scaling icons.
     */
    public static final String[] SCALE_RATIOS =
    {
        "0.25", "0.5", "0.75", "0.9"
    };
    private double scaleRatio = 0.5;

    /**
     * Bulids a door to our past memories. Be happy, smile everyday. Greeting
     * from your sun.
     */
    public MemoriesRefresher()
    {

        /**
         * JFrame.
         */
        super("Memories Refresher");
        this.setBounds(150/*x align R*/, 50/*y align down*/, 1150/*X-WIDTH*/, 800/*Y-Width*/);
        this.getContentPane().setBackground(LovelyColors.GLASS_GALL);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());

        /**
         * JLabel.
         */
        this.titleLabel = new JLabel("Memories Refresher");
        titleLabel.setFont(TITLE_FONT);
        this.imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setEnabled(true);

        this.feedbackLabel = new JLabel("Welcome to Our Journey Memories Refresher");
        feedbackLabel.setFont(MESSAGE_FONT);
        /**
         * JButton.
         */
        this.returnButton = new JButton("return");
        returnButton.addActionListener(this);
        previousButton = new JButton(IM.openIcon("prev.png"));
        previousButton.setActionCommand("<");
        previousButton.addActionListener(this);
        nextButton = new JButton(IM.openIcon("next.png"));
        nextButton.setActionCommand(">");
        nextButton.addActionListener(this);
        playButton = new JButton("Play", IM.openIcon("play.png"));
        playButton.addActionListener(this);
        pauseButton = new JButton("Pause", IM.openIcon("pause.png"));
        pauseButton.setActionCommand("Pause");
        pauseButton.addActionListener(this);
        pauseButton.setVisible(false);
        stopButton = new JButton("Stop", IM.openIcon("Stop.png"));
        stopButton.setBackground(LovelyColors.MERRY_CRANESBILL);
        stopButton.setVisible(false);
        stopButton.addActionListener(this);
        settingButton = new JButton("Settings");
        settingButton.addActionListener(this);
        playMusicButton = new JButton("Start Music");
        playMusicButton.addActionListener(this);
        playMusicButton.setEnabled(false);
        /*
        JPopup
         */
        textAreaMenu = new JPopupMenu();
        descAreaEditItem = new JMenuItem("DIY here");
        descAreaEditItem.addActionListener(this);
        textAreaMenu.add(descAreaEditItem);

        pauseMusicButton = new JButton("Pause Music");
        pauseMusicButton.addActionListener(this);
        pauseMusicButton.setEnabled(false);
        nextMusicButton = new JButton(">>");
        nextMusicButton.addActionListener(this);
        nextMusicButton.setEnabled(false);
        musicSettingsButton = new JButton("My Musiclist");
        musicSettingsButton.addActionListener(this);
        musicSettingsButton.setBackground(LovelyColors.SPRING_GREEN);
        displayListButton = new JButton("My Playlist");
        displayListButton.addActionListener(this);
        displayListButton.setPreferredSize(new Dimension(120, 30));
        enlargeButton = new JButton(IM.openIcon("Enlarge.png"));
        enlargeButton.addActionListener(this);
        smallerButton = new JButton(IM.openIcon("Smaller.png"));
        smallerButton.addActionListener(this);

        //JTextArea
        descriptionArea = new JTextArea();
        //descriptionArea.setOpaque(false);
        descriptionArea.setBackground(LovelyColors.MAGIC_POWDER);
        descriptionArea.setFont(descriptionArea.getFont().deriveFont((float) 20));
        descriptionArea.setEditable(true);
        descriptionArea.setVisible(false);
        descriptionArea.setLineWrap(true);
        //descriptionArea.setWrapStyleWord(true);
        descriptionArea.setComponentPopupMenu(textAreaMenu);
        descriptionArea.setName("Description ");
        descriptionArea.setPreferredSize(new Dimension(200, 600));

        //descriptionArea.setIgnoreRepaint(true);
        //JCombobox
        scaleRatioComboBox = new JComboBox<>(SCALE_RATIOS);
        scaleRatioComboBox.setOpaque(false);
        scaleRatioComboBox.setSelectedIndex(1);//Default: 0.5
        scaleRatioComboBox.setEditable(true);
        scaleRatioComboBox.setBorder(BorderFactory.createTitledBorder("Ratio"));
        //An itemlistener is going to be added to scaleRatioBox using lambda expression.
        scaleRatioComboBox.addItemListener((ItemEvent e) ->
        {
            try
            {

                scaleRatio = Double.parseDouble(scaleRatioComboBox.getSelectedItem().toString());
                if (scaleRatio >= 1 || scaleRatio <= 0)
                {
                    scaleRatio = 0.5;
                    scaleRatioComboBox.setSelectedIndex(1);
                    feedbackLabel.setText("Ratio cannot be greater than 1");
                }
            } catch (NumberFormatException nfe)
            {
                scaleRatio = 0.5;
                scaleRatioComboBox.setSelectedIndex(1);
                feedbackLabel.setText("Please enter a number for ratio");
            }
        });
        //JSlider
        delayBar = new JSlider(SwingConstants.HORIZONTAL, 0, 6000, 0);
        delayBar.setBorder(BorderFactory.createTitledBorder("Delay"));
        delayBar.setValue(3500);
        //A changeListener is going to be added to delaybar using lambda expression.
        delayBar.addChangeListener((ChangeEvent e) ->
        {
            mainTimer.setDelay(delayBar.getValue());
        });
        delayBar.setOpaque(false);
        //delayBar.setSize(300, 30);
        //delayBar.setBounds(new Rectangle(300, 30));
        //delayBar.setPreferredSize(new Dimension(300, 30));
        //delayBar.setOrientation(SwingConstants.HORIZONTAL);
        //checkboxs
        playMusicCheckBox = new JCheckBox("Background Music");
        playMusicCheckBox.setOpaque(false);
        playMusicCheckBox.addActionListener(this);
        doScaleCheckBox = new JCheckBox("Auto Scale");
        doScaleCheckBox.setOpaque(false);
        doScaleCheckBox.setSelected(true);
        doScaleCheckBox.addActionListener(this);

        //JSCROLLPANE
        mainPane = new JScrollPane();
        mainPane.getViewport().setOpaque(false);
        mainPane.getViewport().add(imageLabel, BorderLayout.CENTER, SwingConstants.CENTER);
        mainPane.setOpaque(false);

        //JSPLITPANE
        imageDescriptionSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, descriptionArea, mainPane);
        imageDescriptionSplitPane.setOneTouchExpandable(true);
        imageDescriptionSplitPane.setOpaque(false);
        ///imageDescriptionSplitPane.setDividerSize(2);
        //imageDescriptionSplitPane.setDividerLocation(0.4);

        /*
        Timer
         */
        mainTimer = new Timer(3500, this);//3500 is the default.
        /**
         * JPanel.
         */
        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel, SwingConstants.CENTER);
        northPanel.setOpaque(false);
        this.centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(feedbackLabel, BorderLayout.NORTH);
        centralPanel.add(imageDescriptionSplitPane, BorderLayout.CENTER, SwingConstants.CENTER);
        //centralPanel.add(descriptionArea,BorderLayout.WEST);
        centralPanel.setOpaque(false);
        playMusicBox = Box.createHorizontalBox();
        playMusicBox.setOpaque(false);
        playMusicBox.add(musicSettingsButton);
        playMusicBox.add(playMusicCheckBox);
        playMusicBox.add(playMusicButton);
        playMusicBox.add(pauseMusicButton);
        playMusicBox.add(nextMusicButton);
        playMusicBox.setBorder(BorderFactory.createTitledBorder("Background Music"));

        this.mainControllerPanel = new JPanel(new FlowLayout());
        mainControllerPanel.setOpaque(false);
        mainControllerPanel.add(settingButton);
        mainControllerPanel.add(previousButton);
        mainControllerPanel.add(playButton);
        mainControllerPanel.add(pauseButton);
        mainControllerPanel.add(stopButton);
        mainControllerPanel.add(nextButton);
        mainControllerPanel.add(returnButton);
        mainControllerPanel.add(enlargeButton);
        mainControllerPanel.add(smallerButton);
        centralPanel.add(mainControllerPanel, BorderLayout.SOUTH);

        this.settingPanel = new JPanel(new GridBagLayout());
        settingPanel.add(doScaleCheckBox);
        settingPanel.add(scaleRatioComboBox);
        settingPanel.add(displayListButton);
        settingPanel.add(playMusicBox);
        settingPanel.add(delayBar);

        // settingPanel.add(new JLabel("        "));
        settingPanel.setOpaque(false);

        /*this.southPanel = new JPanel(new FlowLayout());
        //southPanel.add(mainControllerPanel,BorderLayout.NORTH);
        southPanel.add(settingPanel,BorderLayout.CENTER);
        southPanel.setOpaque(false);*/
        /**
         * Add components and finalize frame.
         */
        this.add(northPanel, BorderLayout.NORTH);
        this.add(centralPanel, BorderLayout.CENTER, SwingConstants.CENTER);
        this.add(settingPanel, BorderLayout.SOUTH);

        this.setVisible(true);
        validate();
        repaint();
        bgm.setTitle("Background Music (For Memories Refresher)");
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "亲爱滴 你真的要走了吗", "别离开我主人",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION)
                {
                    clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
                    bgm.dispose();
                    bgm.stop();
                    mainTimer.stop();
                    LIST.dispose();
                    dispose();

                } else
                {
                    clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
                }
            }
        });
    }

    /**
     * Start playing the image.
     */
    public void play()
    {

        status = 1;
//        rightPanel.setVisible(false);
        playButton.setVisible(false);
        pauseButton.setVisible(true);
        stopButton.setVisible(true);
        mainTimer.start();

        // imageLabel.setIcon(IM.openAutoScaledIcon("Ourphotos/showHeartToWZN.jpg", maxWidth, maxHeight));
        if (playMusicCheckBox.isSelected())
        {
            playMusic();
        }
        //statusLabel.setText("Playing");
        if (LIST.getImages().isEmpty())
        {
            stop();
            feedbackLabel.setText("You don't have your photos to play yet. Please create you playlist ^-^");
        } else
        {
            next();
        }
    }

    /**
     * Pause playing the images.
     */
    public void pause()
    {
        status = 2;
        playButton.setVisible(true);
        pauseButton.setVisible(false);
        stopButton.setVisible(true);
//        rightPanel.setVisible(true);
        mainTimer.stop();
        // statusLabel.setText("Paused");

        if (playMusicCheckBox.isSelected())
        {
            bgm.pause();
        }

    }

    /**
     * Stop playing image.
     */
    public void stop()
    {
        status = 0;
        playButton.setVisible(true);
        pauseButton.setVisible(false);
        stopButton.setVisible(false);
//        rightPanel.setVisible(true);
        mainTimer.stop();
        //musicInProgress = false;
        currentPosition = 0;
        descriptionArea.setVisible(false);

        imageLabel.setIcon(null);
        //tatusLabel.setText("Stopped");

        if (playMusicCheckBox.isSelected())
        {
            bgm.stop();
        }

    }

    /**
     * Switch to next image. Warning: Directly invoke this method may jam the
     * player when loading images. It is recommended to run next in a new
     * thread.
     */
    private void doNext()
    {
        currentPosition++;
        /* ImageIcon nextOrigin;
        ImageIcon nextDisplay;*/
        String description = "";
        boolean described;
        if (!LIST.isEmpty())
        {
            try
            {
                int row = LIST.getImageTable().getSelectedRow();
                if (row < LIST.getImages().size() - 1)
                {
                    LIST.getImageTable().setRowSelectionInterval(row + 1, row + 1);
                } else
                {
                    LIST.getImageTable().setRowSelectionInterval(0, 0);//chear if exceeding maximun
                }
                nextOrigin = LIST.getImages().get(LIST.getImageTable().getSelectedRow()).readIcon();
                description = LIST.getImages().get(LIST.getImageTable().getSelectedRow()).getDescription();
                description = description.replace("\\n", "\n");
                described = !description.isEmpty();
                if (doScaleCheckBox.isSelected())
                {
                    //load image with auto scale system
                    scaled = true;
                    nextDisplay = IM.autoScaleIcon(nextOrigin, scaleRatio, maxWidth, maxHeight, Image.SCALE_SMOOTH);
                    this.imageLabel.setIcon(nextDisplay);
                    feedbackLabel.setText("Original Image Size: " + nextOrigin.getIconWidth() + " X " + nextOrigin.getIconHeight() + " scaled to " + nextDisplay.getIconWidth() + " X " + nextDisplay.getIconHeight());
                } else
                {
                    //load the image without scaling
                    scaled = false;
                    this.imageLabel.setIcon(nextOrigin);
                    this.feedbackLabel.setText("Image size: " + nextOrigin.getIconWidth() + " X " + nextOrigin.getIconHeight());
                }
                descriptionArea.setVisible(described);
                if (described)
                {
                    //make the area visible
                    descriptionArea.setText(description);
                    imageDescriptionSplitPane.resetToPreferredSizes();
                }

            } catch (IOException | NullPointerException e)
            {
                //e.printStackTrace();
                pause();
                //Warning warn = new Warning("Oh no nono cannot open your icon. That is not an url.Please check # " + (LIST.getImageTable().getSelectedRow()+1));
                int row = LIST.getImageTable().getSelectedRow();
//                int confirmed = JOptionPane.showConfirmDialog(null,
//                        "We cannot open this image, there must be something wrong with your address, do you want to delete # " + (row + 1) + " ?", "Cannot open image",
//                        JOptionPane.YES_NO_OPTION);
//
//                if (confirmed == JOptionPane.YES_OPTION)
//                {
//                    LIST.removeFromImageList();
//                    LIST.refreshTable();
//                }
                Warning warning = new Warning("Because of the error described below, we are unable to open certain image#" + row, "We have paused your progress, you may want to check the image file. Click on \"solve problem\" for more information.", e, true, () ->
                {
                    LIST.setVisible(true);
                    LIST.getImageTable().requestFocus();
                });
//                play();
            } catch (IllegalArgumentException iae)
            {
                feedbackLabel.setText("Finished your playlist, now going back to first one.");

                LIST.getImageTable().setRowSelectionInterval(0, 0);

            }
        } else
        {
            feedbackLabel.setText("Please create your list first");
            LIST.setVisible(true);
        }

    }

    /**
     * It will let the player play the next image. Which will load the next
     * image in a new thread,
     */
    public void next()
    {
        //notify user.
        feedbackLabel.setText("Image Loading..");
        //create a new thread so the app does not stuck
        java.awt.EventQueue.invokeLater(() ->
        {
            Thread thread = new Thread(() ->
            {
                doNext();
            });
            thread.start();
        });

    }

    /**
     * Go to previous image.
     */
    public void doPrevious()
    {
//        ImageIcon nextOrigin;
//        ImageIcon nextDisplay;
        String description = "";
        boolean described;
        if (!LIST.isEmpty())
        {
            try
            {
                int row = LIST.getImageTable().getSelectedRow();
                if (row != 0)
                {
                    LIST.getImageTable().setRowSelectionInterval(row - 1, row - 1);
                } else
                {
                    LIST.getImageTable().setRowSelectionInterval(LIST.getImageTable().getRowCount() - 1, LIST.getImageTable().getRowCount() - 1);//chear if exceeding maximun
                }
                DisplayImage displayImage = LIST.getImages().get(LIST.getImageTable().getSelectedRow());

                nextOrigin = displayImage.readIcon();
                description = displayImage.getDescription().replace("\\n", "\n");
                described = !description.isEmpty();
                if (doScaleCheckBox.isSelected())
                {
                    scaled = true;
                    nextDisplay = IM.autoScaleIcon(nextOrigin, scaleRatio, maxWidth, maxHeight, Image.SCALE_SMOOTH);
                    this.imageLabel.setIcon(nextDisplay);
                    feedbackLabel.setText("Original Image Size: " + nextOrigin.getIconWidth() + " X " + nextOrigin.getIconHeight() + " scaled to " + nextDisplay.getIconWidth() + " X " + nextDisplay.getIconHeight());
                } else
                {
                    scaled = false;
                    this.imageLabel.setIcon(nextOrigin);
                    this.feedbackLabel.setText("Image size: " + nextOrigin.getIconWidth() + " X " + nextOrigin.getIconHeight());
                }
                descriptionArea.setVisible(described);
                if (described)
                {
                    descriptionArea.setText(description);
                    imageDescriptionSplitPane.resetToPreferredSizes();
                }

            } catch (IOException e)
            {
                e.printStackTrace();
                System.out.println(" Err info " + e.getMessage());
                pause();
                //Warning warn = new Warning("Oh no nono cannot open your icon. That is not an url.Please check # " + (LIST.getImageTable().getSelectedRow()+1));
                int row = LIST.getImageTable().getSelectedRow();
//                int confirmed = JOptionPane.showConfirmDialog(null,
//                        "We cannot open this image, there must be something wrong with your address, do you want to delete # " + (row + 1) + " ?", "Cannot open image",
//                        JOptionPane.YES_NO_OPTION);
//
//                if (confirmed == JOptionPane.YES_OPTION)
//                {
//                    LIST.removeFromImageList();
//                    LIST.refreshTable();
//                }
//                play();
                Warning warning = new Warning("Because of the error described below, we are unable to open certain image#" + row, "We have paused your progress, you may want to check the image file. Click on \"solve problem\" for more information.", e, true, () ->
                {
                    LIST.setVisible(true);
                    LIST.getImageTable().requestFocus();
                });
//                play();
            } catch (IllegalArgumentException iae)
            {
                feedbackLabel.setText("Finished your playlist, now going back to first one.");
                LIST.getImageTable().setRowSelectionInterval(0, 0);
            }
        } else
        {
            feedbackLabel.setText("Please create your list first!");
        }
    }

    public void previous()
    {
        feedbackLabel.setText("Image loading...");
        java.awt.EventQueue.invokeLater(() ->
        {
            Thread prevThread = new Thread(() ->
            {
                doPrevious();
            });
            prevThread.start();
        });

    }

    /**
     * Start to play music.
     */
    public void playMusic()
    {
        try
        {
            if (bgm.getMyMusics().isEmpty())
            {
                feedbackLabel.setText("You don't have any music yet!!!");
            } else
            {
                if (bgm.getMusicTable().getSelectedRow() == -1)
                {
                    bgm.getMusicTable().setRowSelectionInterval(0, 0);
                }
                bgm.play();
            }
        } catch (Exception ex)
        {
            feedbackLabel.setText("Cannot play yr music");
//Warning warning = new Warning("Sorry we cannot play your music due to several exceptions, Please go to music settings.");
        }
    }

    public void resizeImage(double ratio) throws IOException
    {
        int height, width;
        if (nextDisplay != null && nextOrigin != null && imageLabel.getIcon() != null)
        {
            if (scaled)
            {
                height = nextDisplay.getIconHeight();
                width = nextDisplay.getIconWidth();
                height *= ratio;
                width *= ratio;
                nextDisplay = new ImageIcon(LIST.getImages().get(LIST.getImageTable().getSelectedRow()).readIcon().getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                imageLabel.setIcon(nextDisplay);
                feedbackLabel.setText("Original Image Size: " + nextOrigin.getIconWidth() + " X " + nextOrigin.getIconHeight() + " scaled to " + width + " X " + height);
            } else
            {
                height = nextOrigin.getIconHeight();
                width = nextOrigin.getIconWidth();
                height *= ratio;
                width *= ratio;
                nextOrigin = new ImageIcon(LIST.getImages().get(LIST.getImageTable().getSelectedRow()).readIcon().getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
                imageLabel.setIcon(nextOrigin);
                feedbackLabel.setText("Image size:  " + width + " X " + height);
            }
        } else
        {
            feedbackLabel.setText("Please have an image first!");
        }
    }

    /**
     * Action performed.
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
        Object source = e.getSource();
        if (source.equals(playButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            play();
        } else if (source.equals(pauseButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            pause();
        } else if (source.equals(stopButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            stop();
        } else if (source.equals(returnButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            bgm.dispose();
            LIST.dispose();
            dispose();
        } else if (source.equals(mainTimer) || source.equals(nextButton))
        {
            if (source.equals(nextButton))
            {
                clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            }
            next();

        } else if (source.equals(playMusicButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            playMusic();
        } else if (source.equals(pauseMusicButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            bgm.pause();
        } else if (source.equals(nextMusicButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            bgm.next();
        } else if (source.equals(doScaleCheckBox))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            scaleRatioComboBox.setVisible(doScaleCheckBox.isSelected());
        } else if (source.equals(playMusicCheckBox))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            boolean play = playMusicCheckBox.isSelected();
            playMusicButton.setEnabled(play);
            pauseMusicButton.setEnabled(play);
            nextMusicButton.setEnabled(play);
            if (play && status == 1)
            {
                playMusic();
            } else
            {
                bgm.stop();

            }
        } else if (source.equals(musicSettingsButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            bgm.setVisible(true);
        } else if (source.equals(settingButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            settingPanel.setVisible(!settingPanel.isVisible());
        } else if (source.equals(displayListButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            LIST.setVisible(true);
        } else if (source.equals(previousButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            previous();
        } else if (source.equals(enlargeButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            try
            {
                resizeImage(1.15);
            } catch (IOException ex)
            {
                Logger.getLogger(MemoriesRefresher.class.getName()).log(Level.SEVERE, null, ex);
                feedbackLabel.setText("Cannot open your image");
            }
        } else if (source.equals(smallerButton))
        {
            try
            {
                clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
                resizeImage(0.85);
            } catch (IOException ex)
            {
                Logger.getLogger(MemoriesRefresher.class.getName()).log(Level.SEVERE, null, ex);
                feedbackLabel.setText("Cannot open your image");
            }
        } else if (source.equals(descAreaEditItem))
        {
            ComponentEditor componentEditor = new ComponentEditor(descriptionArea);
        }

    }

    public JTextArea getDescriptionArea()
    {
        return descriptionArea;
    }

    /**
     * Test main method.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        MemoriesRefresher standardFrame = new MemoriesRefresher();
    }

    /**
     * Play a little sound effect.
     *
     * @param soundName The name of the sound.(Pkg only)
     */
    public void clickSound(String soundName)
    {
        try
        {
            myClip = AudioSystem.getClip();
            myClip.open(AudioSystem.getAudioInputStream(getClass().getResource(soundName)));
            myClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
        {
            Warning warning = new Warning("Cannot open sound  " + ex.toString(), "", ex, false);
        }

    }
}
