/*Editor: Johnson Gao
 * Date This Project Created:May 2019
 * Description Of This Class: This is a embedded music player. This stores the music list of the user and display it.
 */
package journeylove;

//Hot key registered F7,F8,F9
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javazoom.jl.decoder.JavaLayerException;
import static journeylove.BackgroundMusic.STATUS_PAUSING;//752793

/**
 * This is a embedded music player. This stores the music list of the user and
 * display it.
 *
 * @author Johnson Gao
 */
public class BackgroundMusic extends JFrame implements ActionListener, LineListener, DocumentListener, HotkeyListener
{
//<editor-fold>

    /**
     * Default message font.
     */
    final Font MESSAGE_FONT = new Font("Courier New", Font.PLAIN, 20);
    /**
     * Default serial version UID.
     */
    public static final long serialVersionUID = 1L;

    /**
     * This state indicates that the status of this player is stopping. The clip
     * have closed and haven't read any media file as its own line.
     */
    public static final int STATUS_STOPPING = 0;

    /**
     * Thie indicates this player is actively playing.
     */
    public static final int STATUS_PLAYING = 1;

    /**
     * This means that this player is pausing.
     */
    public static final int STATUS_PAUSING = 2;

    private int sequenceNumberPerferredWidth = 70,
            namePerferredWidth = 226,
            typePerferredWidth = 85,
            timePerferredWidth = 85,
            addressPerferredWidth = 184,
            lyricAddressPerferredWidth = 180;

    /**
     * The database connection obj.
     */
    //private SecretGardenConnection database = new SecretGardenConnection();
    /**
     * The table header of the music table.
     */
    final String[] MUSIC_TABLEHEADER =
    {
        "#", "Name", "Type", "Time Length", "Address", "LyricAddress"
    };
    /**
     * The list of musics.
     */
    private JTable musicTable;
    /**
     * The data will be ship into this arraylist and transfer to Object[][],
     * display in a JTabel.
     */
    private ArrayList<MyMusic> musicList = new ArrayList<>();

    private JLabel titleLabel,
            messageLabel,
            swapObj1Label,
            swapObj2Label,
            nowPlayingLabel,
            rotateMethodLabel,
            totalTimeLabel;

    private JTableHeader header;
    private JButton applyButton,
            playButton,
            pauseButton,
            stopButton,
            swapObj1AddButton,
            swapObj2AddButton,
            swapConfirmButton,
            chooseFileButton,
            chooseLyrButton,
            chooseFilesButton;
    private JTextField nameField,
            urlField,
            lyricUrlField;
    final String[] TYPES_STRING =
    {
        "Local", "Online"
    };
    private JComboBox<String> typeBox;

    private JRadioButton addRadioButton,
            removeRadioButton,
            updateRadioButton,
            swapRadioButton,
            listenRadioButton,
            addBatchRadioButton;
    private ButtonGroup operationGroup;
    private Box operationBox,
            swapObj1Box,
            swapObj2Box,
            swapBox,
            lyricUrlBox;
    private JButton nextButton, showLyricButton;

    private JPanel northPanel,
            centralPanel,
            southPanel,
            buttomPanel;

    private JScrollPane myScrollPane;

    // private LovelyColors lovelyColors = new LovelyColors();
    private Clip myClip;

    private int status = 0, swapId1 = 0, swapId2 = 0;
    // private Clip effectClip;

    public static final FileNameExtensionFilter MUSIC_FILTER = new FileNameExtensionFilter(".mp3 and .wav files only", "mp3", "wav");
    private JFileChooser chooser;
    private JMenuBar myMenuBar;
    private JMenu moreMenu,
            preferencesMenu,
            feedbackMenu;
    private JPopupMenu tableMenu;
    private JMenuItem moreMusicCnMenuItem,
            defaultSaveItem,
            feedbackItem,
            tableCopyItem,
            moreMusic2Item,
            moreMusic3Item;//need to construct.
    private JLabel timeLabel;
    private Timer myTimer;
    private JSlider positionSlider;
    private JCheckBox saveCheckBox, deleteFileCheckBox;
    //private JComboBox<String> subListsComboBox;
    private JButton converterButton;
    private LyricWindow lyricWindow;
    private LyricReader lyricReader;
    //private javax.swing.JDialog dialog;
    private WaitDialog dialog;
    private JIntellitype hotkeys;//Register hot keys
    final ImageManager IMANAGER = new ImageManager();

    private boolean isLyricFound;
    final public static java.awt.Cursor HAND_CURSOR = java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR);
    private JLabel loadingLabel;
    /**
     * How does the player decide what to play next. How does the player rotate.
     *
     * @see SEQUENCE_ROTATE
     * @see SINGLE_ROTATE
     * @see RAMDOM_ROTATE#RANDOM_ROTATE
     * @see NO_ROTATE
     */
    private String rotatingMethod;
    /**
     * The player play songs follow the sequence on the list.
     */
    public static final String SEQUENCE_ROTATE = "Sequence", SEQUENCE_ROTATE_TOOLTIP = "Following the sequence of your playlist.";
    /**
     * The player stay playing one song.
     */
    public final static String SINGLE_ROTATE = "Single", SINGLE_ROTATE_TOOLTIP = "Keep playing one song.";
    /**
     * The player play random songs on the list.
     */
    static public final String RANDOM_ROTATE = "Random", RANDOM_ROTATE_TOOLTIP = "Play songs on your list randomly.";
    /**
     * The player doesn't rotate.
     */
    final static public String NO_ROTATE = "No";

    public static final String SEQUENCE_ROTATE_ICON_BLUE = "rotateBlue.png",
            SEQUENCE_ROTATE_ICON_DARKBLUE = "rotateDarkBlue.png",
            SINGLE_ROTATE_ICON_BLUE = "1blue.png",
            SINGLE_ROTATE_ICON_DARKBLUE = "1darkblue.png",
            RANDOM_ROTATE_ICON_BLUE = "randomBlue.png",
            RANDOM_ROTATE_ICON_DARKBLUE = "randomDarkBlue.png";

    final static public String LISTICON_DARKBLUE = "listDarkBlue.png",
            LISTICON_BLUE = "listBlue.png",
            MUSICICON_DARKBLUE = "musicDarkBlue.png",
            MUSICICON_BLUE = "musicBlue.png";

    static final int KEY_F7 = 1, KEY_F8 = 2, KEY_F9 = 3;

    private MyMusic snapshot, currentPlaying;

    //</editor-fold>
    /**
     * The main constructor of this player.
     */
    public BackgroundMusic()
    {
        super("My Music Player");
        dialog = new WaitDialog("Music Player", IMANAGER.openIcon("playmusic.png"));
        /**
         * Start to initalize the component after the tip information (Wait
         * Dialog) is ALL SETTLED.
         */
        java.awt.EventQueue.invokeLater(() ->
        {
            initComponents();
        });
    }

    /**
     * Initalize the frame.
     */
    private void initComponents()
    {
        //Set some important information of this frame.
        this.setBounds(400, 100, 900, 600);
        this.getContentPane().setBackground(LovelyColors.TRUE_BLUSH);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //JLabel
        titleLabel = new JLabel("Journey's Music Player", IMANAGER.openIcon("playmusic.png"), SwingConstants.CENTER);
        titleLabel.setFont(ImageList.TITLE_FONT);
        messageLabel = new JLabel("<html>This is a list of Background musics, "
                + "<br>click the member you wish to start playing."
                + "<br>To edit, you can use the tools below.</html>");
        messageLabel.setFont(MESSAGE_FONT.deriveFont(10));
        messageLabel.setOpaque(false);
        swapObj1Label = new JLabel("");
        swapObj1Label.setFont(MESSAGE_FONT.deriveFont(7));
        swapObj1Label.setToolTipText("A member on your list to be exchanged.");
        swapObj2Label = new JLabel("");
        swapObj2Label.setFont(MESSAGE_FONT.deriveFont(7));
        swapObj2Label.setToolTipText("Another member on your list to be exchanged");
        nowPlayingLabel = new JLabel("Select a music & play!");
        nowPlayingLabel.setHorizontalTextPosition(JLabel.LEFT);
        timeLabel = new JLabel("0:0");
        timeLabel.setFont(MESSAGE_FONT);
        timeLabel.setToolTipText("You can adjust your time position using the position scroll bar");
        try (Preference p = Preference.getInstance())
        {
            rotatingMethod = p.getRotateMethod();
        } catch (IOException e)
        {
            rotatingMethod = SEQUENCE_ROTATE;
            Warning.createWarningDialog(e);
            e.printStackTrace();
        }
        
        rotateMethodLabel = new JLabel();
        //rotateMethodLabel.setToolTipText(SEQUENCE_ROTATE_TOOLTIP);
        loadRotateMethod();
        rotateMethodLabel.addMouseListener(new MouseAdapter()//Add a mouth listener
        {
            /**
             * {@inheritDoc}
             */
            @Override
            public void mouseEntered(MouseEvent e)
            {
                //clickSound(SoundOracle.TINY_BUTTON_SOUND);
                switch (rotatingMethod)
                {//Swich the icon according to user's current mode.
                    case SEQUENCE_ROTATE:
                        rotateMethodLabel.setIcon(IMANAGER.openIcon(SEQUENCE_ROTATE_ICON_BLUE));
                        break;
                    case RANDOM_ROTATE:
                        rotateMethodLabel.setIcon(IMANAGER.openIcon(RANDOM_ROTATE_ICON_BLUE));
                        break;
                    case SINGLE_ROTATE:
                        rotateMethodLabel.setIcon(IMANAGER.openIcon(SINGLE_ROTATE_ICON_BLUE));
                        break;
                    default:
                    {//In case of non-sence, use default value "sequence";
                        try (Preference p = Preference.getInstance())
                        {
                            //Replace the unknown value to sequence rotate..
                            p.setRotateMethod(SEQUENCE_ROTATE);
                        } catch (IOException ex)
                        {
                            Warning.createWarningDialog(ex);
                            Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }//final update with this
                    rotatingMethod = SEQUENCE_ROTATE;
                    break;
                }
            }

            //Invoked as the mouse exited the label.
            @Override
            public void mouseExited(MouseEvent e)
            {
                switch (rotatingMethod)
                {//Switch the icon back from highlighted to normal.
                    case SEQUENCE_ROTATE:
                        rotateMethodLabel.setIcon(IMANAGER.openIcon(SEQUENCE_ROTATE_ICON_DARKBLUE));
                        break;
                    case RANDOM_ROTATE:
                        rotateMethodLabel.setIcon(IMANAGER.openIcon(RANDOM_ROTATE_ICON_DARKBLUE));
                        break;
                    case SINGLE_ROTATE:
                        rotateMethodLabel.setIcon(IMANAGER.openIcon(SINGLE_ROTATE_ICON_DARKBLUE));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                clickSound(SoundOracle.TINY_BUTTON_SOUND);

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                //Update the icon and mode as if user clicked on the icon.
                switch (rotatingMethod)
                {
                    case SEQUENCE_ROTATE:
                        updateMusicRotateMethod(RANDOM_ROTATE);
                        break;
                    case RANDOM_ROTATE:
                        updateMusicRotateMethod(SINGLE_ROTATE);
                        break;
                    case SINGLE_ROTATE:
                        updateMusicRotateMethod(SEQUENCE_ROTATE);
                        break;
                    default:
                        System.out.println(".mouseReleased()");
                        System.err.println(rotatingMethod + " wrong rotating method.");
                        break;
                }
            }

            @Override
            public String toString()
            {
                return "Rotating method is: " + rotatingMethod;
            }

        });
//        <editor-fold>
//        viewListLabel = new JLabel(IMANAGER.openIcon(LISTICON_DARKBLUE));
//        viewListLabel.addMouseListener(new MouseAdapter()
//        {
//
//            //If the user is currently full-list mode.
//            @Override
//            public void mouseEntered(MouseEvent e)
//            {
//                //When mouse enter the region, the icon color changes according to users persent icon.
//                if(addressPerferredWidth == 0)
//                {
//                    viewListLabel.setIcon(IMANAGER.openIcon(LISTICON_BLUE));
//                }else
//                {
//                    viewListLabel.setIcon(IMANAGER.openIcon(MUSICICON_BLUE));
//                }
//            }
//            
//            @Override
//            public void mouseExited(MouseEvent e)
//            {
//                if(addressPerferredWidth == 0)
//                {
//                    viewListLabel.setIcon(IMANAGER.openIcon(LISTICON_DARKBLUE));
//                }else
//                {
//                    viewListLabel.setIcon(IMANAGER.openIcon(MUSICICON_DARKBLUE));
//                }
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e)
//            {
//                clickSound(SoundOracle.TINY_BUTTON_SOUND);
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e)
//            {
//                //do the action as the user release the mouse.
//                if(addressPerferredWidth == 0)
//                {
//                    resetTable();
//                }else
//                {
//                    simplifyTable();
//                }
//            }
//});</editor-fold>
        totalTimeLabel = new JLabel("0:0");
        totalTimeLabel.setFont(MESSAGE_FONT);

        //checkbox
        saveCheckBox = new JCheckBox("Download & Convert");
        saveCheckBox.setOpaque(false);
        saveCheckBox.setVisible(false);
        saveCheckBox.addActionListener(this);
        saveCheckBox.setToolTipText("Convert your music into \".wav\" format and download it into your default saving address.");

        deleteFileCheckBox = new JCheckBox("Local -> Delete file as well.");
        deleteFileCheckBox.setOpaque(false);
        deleteFileCheckBox.setVisible(false);
        deleteFileCheckBox.addActionListener(this);
        deleteFileCheckBox.setToolTipText("<html>detete music+file from your computer"
                + "<br>This will only work if your music type is \"local\""
                + "<br>this operation will not delete lyric file.</html>");

        //Constructing and formatting JButtons.
        applyButton = new JButton("Confirm");
        applyButton.addActionListener(this);
        applyButton.setToolTipText("Apply any changes made.");
        chooseFilesButton = new JButton("Choose Files");
        chooseFilesButton.addActionListener(this);
        chooseFilesButton.setToolTipText("Click me to continue add musics in batch.");

        playButton = new JButton("Play", IMANAGER.openIcon("play.png", 16, 16));
        playButton.addActionListener(this);
        pauseButton = new JButton("Pause", IMANAGER.openIcon("pause.png", 16, 16));
        pauseButton.addActionListener(this);
        pauseButton.setVisible(false);
        stopButton = new JButton("Stop", IMANAGER.openIcon("Stop.png", 16, 16));
        stopButton.addActionListener(this);
        stopButton.setVisible(false);
        swapObj1AddButton = new JButton("For No.1");
        swapObj1AddButton.addActionListener(this);
        swapObj1AddButton.setBackground(LovelyColors.SILLY_FIZZ);
        swapObj1AddButton.setToolTipText("Confirm your selection for one member.");

        swapObj2AddButton = new JButton("For No.2");
        swapObj2AddButton.addActionListener(this);
        swapObj2AddButton.setBackground(LovelyColors.SILLY_FIZZ);
        swapObj2AddButton.setToolTipText("Confirm you selection for another member.");

        swapConfirmButton = new JButton("SWAP!");
        swapConfirmButton.addActionListener(this);
        swapConfirmButton.setBackground(LovelyColors.SPRING_GREEN);
        swapConfirmButton.setToolTipText("<html>Swap two musics."
                + "<br>Please make sure you have already selected two musics to swap.</html>");

        nextButton = new JButton(">>");
        nextButton.addActionListener(this);
        nextButton.setToolTipText("Play next music on the list.");

        chooseFileButton = new JButton("Open Your File", IMANAGER.openIcon("newFile.png", 16, 16));
        chooseFileButton.addActionListener(this);
        chooseFileButton.setToolTipText("Open a new music in your computer.");

        converterButton = new JButton("Convert & Update");
        converterButton.addActionListener(this);
        converterButton.setToolTipText("More options for saving your music to .wav file.");

        chooseLyrButton = new JButton("Choose Lyric", IMANAGER.openIcon("newFile.png", 16, 16));
        chooseLyrButton.addActionListener(this);
        chooseLyrButton.setToolTipText("Open the lyric file for the music.");

        showLyricButton = new JButton("Lyric");
        showLyricButton.addActionListener(this);
        showLyricButton.setToolTipText("see/hide your lyric window.");

        //JMenuForTable
        tableCopyItem = new JMenuItem("Copy");
        tableCopyItem.addActionListener(this);

        tableMenu = new JPopupMenu();
        tableMenu.add(tableCopyItem);
        //dialog.updateValue(35);
        //table
        try
        {
            musicTable = new JTable(new Object[1][MUSIC_TABLEHEADER.length], MUSIC_TABLEHEADER);
            musicTable.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    clickSound(SoundOracle.TINY_BUTTON_SOUND);
                }

                @Override
                public String toString()
                {
                    return super.toString();
                }

            });
            musicTable.setComponentPopupMenu(tableMenu);
            musicTable.setAutoCreateRowSorter(true);

//            musicTable.getCellEditor().
        } catch (Exception e)
        {
            Warning warning = new Warning(e.toString(), "please close and open again.", e);
            warning.setDefaultCloseOperation(EXIT_ON_CLOSE);//If an exception encounter here, user must exit the program.
            this.dispose();
            loadingLabel.setText("<html> ERROR HAPPENS!!!!!!!"
                    + "<br>" + e.toString() + "</html>");
//            dialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

//        musicTable.setRowSelectionInterval(0, 0);
        formatMusicTable();
////        javafx.embed.swing.JFXPanel

        //timer
        myTimer = new Timer(500, (ActionEvent e) ->
        {
            //refresh user's lyric and music positon on the slider timely.
            refreshTime();
            refreshLyric();
        });

        //slider
        positionSlider = new JSlider(SwingConstants.HORIZONTAL);
        positionSlider.setOpaque(false);
        positionSlider.setBorder(BorderFactory.createTitledBorder("Position"));
        positionSlider.setValue(0);

        positionSlider.addChangeListener((ChangeEvent e) ->
        {
            if (positionSlider.getValueIsAdjusting())
            {
                //As if user is adjusting the value.
                long position = positionSlider.getValue() * 1000000;//Convert the second to microsecond.
                myClip.setMicrosecondPosition(position);
                lyricReader.tracePosition(position);

                refreshLyricLines();//Reload the current lyric according to current position.
                refreshTime();//Reload time.
            }

        });
        header = musicTable.getTableHeader();
        header.setBackground(LovelyColors.MERRY_CRANESBILL);//set table header

        //jscrollpane for the table.
        myScrollPane = new JScrollPane();
        myScrollPane.getViewport().add(musicTable);
        myScrollPane.getViewport().setOpaque(false);
        myScrollPane.setOpaque(false);
        //myScrollPane.getViewport().setOpaque(false);

        //JComboBox for user to select the address type for the music
        typeBox = new JComboBox<>(TYPES_STRING);
        typeBox.setOpaque(false);
        typeBox.setBorder(BorderFactory.createTitledBorder("Type (Online/Offline) : "));
        typeBox.setVisible(false);
        typeBox.setBackground(LovelyColors.GLASS_GALL);
        typeBox.addItemListener((ItemEvent e) ->
        {
            clickSound(SoundOracle.WATER_PRESS_4);
            int index = typeBox.getSelectedIndex();
            chooseFileButton.setVisible(index == 0);
            saveCheckBox.setVisible(index == 1);
        });//set help text.
        typeBox.setToolTipText("This indicates the type for your music address(online/offline)");

        //JTextfield
        nameField = new JTextField(15);
        nameField.setBorder(BorderFactory.createTitledBorder("Name : (Local only -- when you are adding, Leave here blank will use file name)"));
        nameField.setOpaque(false);
        nameField.setVisible(false);
        nameField.getDocument().addDocumentListener(this);
        nameField.setToolTipText("<html>Name of your music."
                + "<br>If your address type is \"Local\" the default name will be the file name."
                + "</html>");
        urlField = new JTextField(100);
        urlField.setBorder(BorderFactory.createTitledBorder("URL: "));
        urlField.setOpaque(false);
        urlField.setVisible(false);
        urlField.setToolTipText("The address of your music.");
        urlField.getDocument().addDocumentListener(this);
        lyricUrlField = new JTextField();
        lyricUrlField.setBorder(BorderFactory.createTitledBorder("LyricAddress"));
        lyricUrlField.setOpaque(false);
        //lyricUrlField.setVisible(false);
        lyricUrlField.getDocument().addDocumentListener(this);
        lyricUrlField.setToolTipText("The lyric file of the music, must be offline.");

        //dialog.updateValue(60);
        chooseFileButton.setVisible(false);
        //JMenu
        moreMusicCnMenuItem = new JMenuItem("More Online Music(Chinese)", new ImageManager().openIcon("InternetSearch.png"));
        moreMusicCnMenuItem.setBackground(LovelyColors.MOMO_PINK);
        //moreMusicCnMenuItem.setForeground(lovelyColors.MOMO_FONTCOLOR);
        moreMusicCnMenuItem.addActionListener(this);

        moreMusicCnMenuItem.setCursor(HAND_CURSOR);
        feedbackItem = new JMenuItem("Leave Feedback", new ImageManager().openIcon("feedback.png"));
        feedbackItem.addActionListener(this);
        feedbackItem.setBackground(LovelyColors.MOMO_PINK);
        feedbackItem.setCursor(HAND_CURSOR);
        defaultSaveItem = new JMenuItem("Default Saving Address");
        defaultSaveItem.setBackground(LovelyColors.MOMO_PINK);
        defaultSaveItem.addActionListener(this);
        defaultSaveItem.setCursor(HAND_CURSOR);

        moreMusic2Item = new JMenuItem("Free Music Address2(CN)");
        moreMusic2Item.addActionListener(this);
        moreMusic2Item.setBackground(getBackground());
        moreMusic2Item.setCursor(HAND_CURSOR);
        moreMusic3Item = new JMenuItem("Free Music Address3(CN)-No lyric");
        moreMusic3Item.addActionListener(this);
        moreMusic3Item.setBackground(getBackground());
        moreMusic3Item.setCursor(HAND_CURSOR);
        moreMenu = new JMenu("More Musics");
        moreMenu.add(moreMusicCnMenuItem);
        moreMenu.add(moreMusic2Item);
        moreMenu.add(moreMusic3Item);

        moreMenu.addActionListener(this);
        //moreMenu.setCursor(HAND_CURSOR);
        feedbackMenu = new JMenu("Leave Feedback");
        feedbackMenu.add(feedbackItem);
        //feedbackMenu.setCursor(HAND_CURSOR);
        preferencesMenu = new JMenu("Preferences");
        preferencesMenu.add(defaultSaveItem);

        //preferencesMenu.setCursor(HAND_CURSOR);
        myMenuBar = new JMenuBar();
        myMenuBar.setBackground(LovelyColors.MOMO_PINK);
        //myMenuBar.setForeground(lovelyColors.MOMO_FONTCOLOR);
        myMenuBar.add(moreMenu);
        myMenuBar.add(preferencesMenu);
        myMenuBar.add(feedbackMenu);
        setJMenuBar(myMenuBar);

        //radioButton
        addRadioButton = new JRadioButton("Add");
        addRadioButton.addActionListener(this);
        addRadioButton.setOpaque(false);
        addRadioButton.setToolTipText("Add a new music into your list.");
        addRadioButton.setCursor(HAND_CURSOR);
        addBatchRadioButton = new JRadioButton("Add In Batch");
        addBatchRadioButton.addActionListener(this);
        addBatchRadioButton.setOpaque(false);
        addBatchRadioButton.setToolTipText("Add musics in batch!");
        addBatchRadioButton.setCursor(HAND_CURSOR);
        removeRadioButton = new JRadioButton("Remove");
        removeRadioButton.addActionListener(this);
        removeRadioButton.setOpaque(false);
        removeRadioButton.setToolTipText("<html>Remove selected music from your list."
                + "<br>Select \" Delete file as well\" if you also wish to delete your music file.</html>");
        removeRadioButton.setCursor(HAND_CURSOR);
        updateRadioButton = new JRadioButton("Change");
        updateRadioButton.addActionListener(this);
        updateRadioButton.setOpaque(false);
        updateRadioButton.setToolTipText("Update the selected music in your musiclist.");
        updateRadioButton.setCursor(HAND_CURSOR);
        swapRadioButton = new JRadioButton("Swap");
        swapRadioButton.addActionListener(this);
        swapRadioButton.setOpaque(false);
        swapRadioButton.setToolTipText("Swap two musics in your list.");
        swapRadioButton.setCursor(HAND_CURSOR);
        listenRadioButton = new JRadioButton("Listen");
        listenRadioButton.setOpaque(false);
        listenRadioButton.addActionListener(this);
        listenRadioButton.setToolTipText("Let the beautiful melody flow through your heart.");
        listenRadioButton.setCursor(HAND_CURSOR);

        //Boxes + JPanel constructing and ready for final setvisiable
        Box urlBox = Box.createHorizontalBox();
        urlBox.add(urlField);
        urlBox.add(saveCheckBox);
        urlBox.add(chooseFileButton);
        urlBox.setOpaque(false);

        Box lastLineBox = Box.createHorizontalBox();
        lastLineBox.add(applyButton);
        lastLineBox.add(chooseFilesButton);
        lastLineBox.add(deleteFileCheckBox);
        lastLineBox.setOpaque(false);

        Box topTipBox = Box.createVerticalBox();
        topTipBox.add(nowPlayingLabel);
        topTipBox.setOpaque(false);

        lyricUrlBox = Box.createHorizontalBox();
        lyricUrlBox.add(lyricUrlField);
        lyricUrlBox.add(chooseLyrButton);
        lyricUrlBox.setOpaque(false);
        lyricUrlBox.setVisible(false);

        operationGroup = new ButtonGroup();//Group the JRadioButtons.
        operationGroup.add(addRadioButton);
        operationGroup.add(addBatchRadioButton);
        operationGroup.add(removeRadioButton);
        operationGroup.add(updateRadioButton);
        operationGroup.add(swapRadioButton);
        operationGroup.add(listenRadioButton);

        operationBox = Box.createHorizontalBox();//create a box to contain those radio buttons.
        operationBox.add(addRadioButton);
        operationBox.add(addBatchRadioButton);
        operationBox.add(removeRadioButton);
        operationBox.add(updateRadioButton);
        operationBox.add(swapRadioButton);
        operationBox.add(listenRadioButton);
        //operationBox.add(viewListLabel);
        operationBox.setOpaque(false);
        operationBox.setBorder(BorderFactory.createTitledBorder("What do you want to do with this list?"));

        swapObj1Box = Box.createHorizontalBox();
        swapObj1Box.add(swapObj1AddButton);

        swapObj1Box.add(swapObj1Label);
        swapObj1Box.setOpaque(false);
        swapObj2Box = Box.createHorizontalBox();
        swapObj2Box.add(swapObj2AddButton);
        swapObj2Box.add(swapObj2Label);
        swapObj2Box.setOpaque(false);
        swapBox = Box.createVerticalBox();
        swapBox.add(swapObj1Box);
        swapBox.add(swapObj2Box);
        swapBox.add(swapConfirmButton);
        swapBox.setOpaque(false);
        swapBox.setVisible(false);

        /**
         * This controls the whole button where user could do operation to the
         * list.
         */
        Box editingBox = Box.createVerticalBox();
        editingBox.add(nameField);
        editingBox.add(urlBox);
        editingBox.add(typeBox);
        editingBox.add(lyricUrlBox);
        editingBox.add(lastLineBox);
        editingBox.add(swapBox);
        editingBox.setOpaque(false);

        buttomPanel = new JPanel(new GridBagLayout());
        buttomPanel.add(rotateMethodLabel);
        buttomPanel.add(playButton);
        buttomPanel.add(pauseButton);
        buttomPanel.add(stopButton);
        buttomPanel.add(nextButton);
        buttomPanel.add(timeLabel);
        buttomPanel.add(positionSlider);
        buttomPanel.add(totalTimeLabel);
        buttomPanel.add(converterButton);
        buttomPanel.add(showLyricButton);
        buttomPanel.setOpaque(false);

        northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel, SwingConstants.CENTER);
        northPanel.setOpaque(false);
        centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(topTipBox, BorderLayout.NORTH);
        centralPanel.add(myScrollPane, BorderLayout.CENTER);
        centralPanel.add(messageLabel, BorderLayout.SOUTH);
        centralPanel.setOpaque(false);
        southPanel = new JPanel(new BorderLayout());
        southPanel.add(operationBox, BorderLayout.NORTH);
        southPanel.add(editingBox, BorderLayout.CENTER);
        southPanel.add(buttomPanel, BorderLayout.SOUTH);
        southPanel.setOpaque(false);

        //Construct the clip and get it ready.
        initalizeClip();

        //JFilechooser.
        chooser = new JFileChooser();
        chooser.setFileFilter(MUSIC_FILTER);

        //Add listenerL listen to the keyboard.
        this.getContentPane().addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_F8)
                {
                    if (status == STATUS_PLAYING)
                    {
                        pause();
                    } else
                    {
                        play();
                    }
                } else if (code == KeyEvent.VK_F9)
                {
                    next();
                }
            }
        });

        //Add the window listener
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {

                stop();
                clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
                lyricWindow.dispose();

                try
                {
                    hotkeys.unregisterHotKey(KEY_F7);
                    hotkeys.unregisterHotKey(KEY_F8);
                    hotkeys.unregisterHotKey(KEY_F9);
                } catch (Exception hke1)
                {
                    System.out.println("Failed to unregister hot key!");
                }
            }

            @Override
            public String toString()
            {
                return super.toString();
            }

        });

        //Finally construct "this", which everything is ready to be added.
        add(northPanel, BorderLayout.NORTH);
        add(centralPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        dialog.dispose();//Close the loading dialog and show the frame.
        setVisible(true);
        lyricWindow = new LyricWindow(this);//show the lyric window last.

        //REGISTER HOT KEY!
        try
        {
            hotkeys = JIntellitype.getInstance();
            hotkeys.registerHotKey(KEY_F7, "F7");
            hotkeys.registerHotKey(KEY_F8, "F8");
            hotkeys.registerHotKey(KEY_F9, "F9");
            hotkeys.addHotKeyListener(this);
        } catch (Exception e)
        {
            System.out.println("Failed to register hot key.");
        }

        refreshTabel();

    }

    public void loadRotateMethod()
    {
        switch (rotatingMethod)
        {
            case RANDOM_ROTATE:
                rotateMethodLabel.setIcon(IMANAGER.openIcon(RANDOM_ROTATE_ICON_DARKBLUE));
                rotateMethodLabel.setToolTipText(RANDOM_ROTATE_TOOLTIP);
                break;
            case SINGLE_ROTATE:
                rotateMethodLabel.setIcon(IMANAGER.openIcon(SINGLE_ROTATE_ICON_DARKBLUE));
                rotateMethodLabel.setToolTipText(SINGLE_ROTATE_TOOLTIP);
                break;
            case SEQUENCE_ROTATE:
            default:
                rotateMethodLabel.setIcon(IMANAGER.openIcon(SEQUENCE_ROTATE_ICON_DARKBLUE));
                rotateMethodLabel.setToolTipText(SEQUENCE_ROTATE_TOOLTIP);
                break;

        }
        
    }

    /**
     * Test main method.
     *
     * @param args Lines command argument.
     */
    public static void main(String[] args)
    {

        //Create a new obj of bgm.
        BackgroundMusic bgm = new BackgroundMusic();
        bgm.setDefaultCloseOperation(EXIT_ON_CLOSE);
        bgm.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                bgm.getClip().close();
                System.exit(0);
            }

            @Override
            public String toString()
            {
                return super.toString();
            }

        });
        //bgm.setVisible(true);
    }

    public void updateMusicRotateMethod(String mode)
    {
        rotatingMethod = mode;
        try (Preference p = Preference.getInstance())
        {
            p.setRotateMethod(mode);
        } catch (IOException ex)
        {
//            Warning warning = new Warning(ex.toString());
//            warning.getContentPane().setBackground(Color.white);
            Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
            Warning.createWarningDialog(ex);
        }
    }

    /**
     * Returns an 2d array od data in musicList.
     *
     * @param musicList The arraylist of the musicList.
     * @return An 2D array of data.
     */
    public Object[][] toObjectses(ArrayList<MyMusic> musicList)
    {
        Object[][] music = new Object[musicList.size()][6];
        for (int i = 0; i < musicList.size(); i++)
        {
            try
            {
                music[i][0] = i + 1;
                music[i][1] = musicList.get(i).getName();
                music[i][2] = musicList.get(i).getTypeString();
                music[i][3] = musicList.get(i).getTime();
                music[i][4] = musicList.get(i).getUrl();
                music[i][5] = musicList.get(i).getLyricAddress();
            } catch (UnsupportedAudioFileException | JavaLayerException | LineUnavailableException | IOException ex)
            {
                //Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
                //Warning warning = new Warning(ex.toString());
                Warning.createWarningDialog(ex);
            }
        }
        return music;
    }

    /**
     * Put the musictable in a more perferrable format.
     */
    public void formatMusicTable()
    {
        musicTable.getColumnModel().getColumn(0).setPreferredWidth(sequenceNumberPerferredWidth);
        musicTable.getColumnModel().getColumn(1).setPreferredWidth(namePerferredWidth);
        musicTable.getColumnModel().getColumn(2).setPreferredWidth(typePerferredWidth);
        musicTable.getColumnModel().getColumn(3).setPreferredWidth(timePerferredWidth);
        musicTable.getColumnModel().getColumn(4).setPreferredWidth(addressPerferredWidth);
        musicTable.getColumnModel().getColumn(5).setPreferredWidth(lyricAddressPerferredWidth);
    }

    /**
     * Only show the name and the sequence column.
     *
     * @deprecated This method is not working.
     */
    public void simplifyTable()
    {
        typePerferredWidth = timePerferredWidth = addressPerferredWidth = lyricAddressPerferredWidth = 0;
        formatMusicTable();
    }

    /**
     * Reset the table columns to default width.
     *
     * @deprecated
     */
    public void resetTable()
    {
        typePerferredWidth = 85;
        timePerferredWidth = 85;
        addressPerferredWidth = 184;
        lyricAddressPerferredWidth = 180;
        formatMusicTable();
    }

    /**
     * Refresh the attribute of this class while returning data read from
     * database.
     *
     * @return The musiclist stored in database.
     */
    public ArrayList<MyMusic> getMyMusics()
    {
        try (SecretGardenConnection database = SecretGardenConnection.getDefaultInstance())
        {
            return musicList = database.getMyMusics();
        } catch (SQLException | ClassNotFoundException e)
        {
//            Warning warning = new Warning(e.getMessage());//call the warning class
//            warning.setSuggestion("Please check if you have opened 2 apps in the same time.");
            Warning.createWarningDialog(e);
            return new ArrayList<>();
        }
    }

    /**
     * Returns if the music is an empty list or not. This method reduce
     * unnessary read from database.
     *
     * @return <code>true</code> if the user is holding an empty list,
     * <code>false</code> otherwise.
     */
    public boolean isEmpty()
    {
        return this.musicList.isEmpty();
    }

    /**
     * Refresh the current position of the music player to the user.
     */
    public void refreshTime()
    {
        TimeConverter converter = new TimeConverter(myClip.getMicrosecondPosition());
        timeLabel.setText(converter.getMinutes() + ":" + converter.getSeconds());
        positionSlider.setValue(converter.convertToSeconds());
    }

    /**
     * Default refreshing algorism, guide the lyricviewer to the nextline.
     */
    public void refreshLyric()
    {
        long position = myClip.getMicrosecondPosition();//-lyricReader.getOffset();
        //System.out.println("journeylove.BackgroundMusic.refreshLyric(): position = " + position);
        if (isLyricFound)
        {
            if (position >= lyricReader.getNextLine().getMicrosecondPosition())
            {
                lyricReader.nextLine();
                refreshLyricLines();

            }
        }

    }

    /**
     * Assign lines from the lyricreader to the lyricwindow according to the
     * current position.
     */
    public void refreshLyricLines()
    {
        if (isLyricFound)
        {
            lyricWindow.setPreviousLine(lyricReader.getPreviousLine());
            lyricWindow.setCurrentLine(lyricReader.getCurrentLine());
            lyricWindow.setNextLine(lyricReader.getNextLine());
        }
    }

    /**
     * Action perform.
     *
     * @param e Action event.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source.equals(addRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            clearAllFields();
            validateInput();
            messageLabel.setText("Import your favourite music here --> ");
        } else if (source.equals(addBatchRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            validateInput();
            messageLabel.setText("Please click \"Choose files\", press \"ctrl\" to select multiple files.");
        } else if (source.equals(removeRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            clearAllFields();
            messageLabel.setText("<html>Click the member you want to delete and click \"apply\""
                    + "<br>You cannot delete music from package!<br>"
                    + "--If you wish, please call Johnson and say \"I miss U\" for 9 times.</html>");
            /*+ "<br>If you want to add more music into package "
                    + "<br>Please call your cutie and ask if Johnson has missed you."
                    + "<br>If the answer is yes, you may continue.</html>");*/
            validateInput();
        } else if (source.equals(swapRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            messageLabel.setText("Please select two members you would like to switch, click apply.");
            validateInput();
        } else if (source.equals(updateRadioButton))
        {
            int index = musicTable.getSelectedRow();
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            clearAllFields();
            messageLabel.setText("<html><center> *Change Value* </center>"
                    + "Select the row you want to change, enter alternative info below, click \"apply\""
                    + "<br> Note: id cannot be changed.");
            validateInput();
            if (index != -1)
            {
                typeBox.setSelectedIndex(musicList.get(index).getType() - 2);
            } else
            {
                messageLabel.setText("Please select a member to edit.");
            }
        } else if (source.equals(swapObj1AddButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            int row = musicTable.getSelectedRow();
            try
            {
                swapId1 = musicList.get(row).getId();
                swapObj1Label.setText(musicTable.getValueAt(row, 0) + " # will be switched with");
            } catch (Exception ex)
            {
                swapObj1Label.setText("Failed to get No.1, make sure you chose one");
            }
        } else if (source.equals(swapObj2AddButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            int row = musicTable.getSelectedRow();
            try
            {
                swapId2 = musicList.get(row).getId();
                swapObj2Label.setText(musicTable.getValueAt(row, 0) + " #, click \"swap\" to switch positions.");
            } catch (Exception ex)
            {
                swapObj2Label.setText("Failed to get No.2, make sure you chose one");
            }
        } //functional action perform.
        else if (source.equals(applyButton))
        {
            apply();
        } else if (source.equals(chooseFilesButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            addInBatch();
        } else if (source.equals(swapConfirmButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            swap();
        } else if (source.equals(playButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            if (status == STATUS_STOPPING)
            {
                messageLabel.setText("Please Wait...");
            }
            EventQueue.invokeLater(() ->
            {
                play(musicTable.getSelectedRow());//I was considering about creating a new thread to unfreeze the window, but it may confuse users.
            });
        } else if (source.equals(pauseButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            pause();
        } else if (source.equals(stopButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            stop();
        } else if (source.equals(nextButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            messageLabel.setText("Please Wait...");

            EventQueue.invokeLater(() ->
            {
                next(rotatingMethod);
            });
        } else if (source.equals(chooseFileButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            try
            {
                chooser.setFileFilter(MUSIC_FILTER);
                int confirmed = chooser.showOpenDialog(null);
                if (confirmed == JFileChooser.APPROVE_OPTION)
                {
                    urlField.setText(chooser.getSelectedFile().getAbsolutePath());

                } else if (confirmed == JFileChooser.CANCEL_OPTION)
                {
                    urlField.setText("");
                }
            } catch (HeadlessException hpe)
            {
                messageLabel.setText("Cannot open your file.");
            } catch (NullPointerException npe)
            {
                messageLabel.setText("Pleasr choose a file.");
            }
        } else if (source.equals(moreMusicCnMenuItem))
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            browseWebsite("http://music.sonimei.cn");
        } else if (source.equals(moreMusic2Item))
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            browseWebsite("http://www.769sc.com");
        } else if (source.equals(moreMusic3Item))
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            browseWebsite("http://music.sonimei.cn/yinyue");
        } else if (source.equals(moreMenu))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
        } else if (source.equals(saveCheckBox))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            try (Preference p = Preference.getInstance();)
            {
                String dfp = p.getSavingPath();
                if (StrUtil.isBlank(dfp))
                {
                    messageLabel.setText("You don't have your default address!! Please set it up.");
                    setDefaultSavingAddress();
                }
            } catch (IOException ex)
            {
                Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, "Cannot contact your database.", ex);
                messageLabel.setText("Cannot read file.");
                Warning.createWarningDialog(ex);
            }
        } else if (source.equals(defaultSaveItem))
        {
            setDefaultSavingAddress();
        } else if (source.equals(converterButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            MusicConvertTool musicConvertTool = new MusicConvertTool(this);
        } else if (source.equals(deleteFileCheckBox))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
        } else if (source.equals(listenRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            validateInput();
            messageLabel.setText("Enjoy your music ^-^");
        } else if (source.equals(feedbackItem))
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            browseWebsite("https://www.wjx.cn/hj/as2bmm7pnu6a1mv7mt54ka.aspx");
        } else if (source.equals(chooseLyrButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            chooser.setFileFilter(TestLyricReader.LYRIC_FILTER);
            int confirmed = chooser.showOpenDialog(null);
            if (confirmed == JFileChooser.APPROVE_OPTION)
            {
                lyricUrlField.setText(chooser.getSelectedFile().getAbsolutePath());
                clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            } else
            {
                clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            }

        } else if (source.equals(showLyricButton))
        {
            lyricWindow.setVisible(!lyricWindow.isVisible());
        } else if (source.equals(tableCopyItem))
        {
            copyTableSelection();
        }
    }

    /**
     * Event fired when a WM_HOTKEY message is received that was initiated by
     * this application.
     *
     * @param identifier The unique identifier that was assigned to the hotkey.
     */
    @Override
    public void onHotKey(int identifier)
    {
        switch (identifier)
        {
            case KEY_F7:
                //previous();
                System.err.println("Unsupported.");
                break;
            case KEY_F8:
                if (status == STATUS_PLAYING)
                {
                    pause();
                } else
                {
                    play();
                }
                break;
            //if(identifier == KEY_F9)
            default:
                next();
                break;
        }

    }

    /**
     * Apply changes user made. e.g. When user click "Confirm" button.
     */
    public void apply()
    {

        clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
        if (addRadioButton.isSelected())
        {
            //addIntoMusicList();
            if (saveCheckBox.isSelected())
            {

                messageLabel.setText("Please Wait A Moment... You can play a music while it's adding.");
                Runnable runnable = () ->
                {
                    try
                    {
                        saveAndInsertIntoMusicList(nameField.getText(), urlField.getText(), lyricUrlField.getText());
                    } catch (SQLException ex)
                    {
                        Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
                        messageLabel.setText("Cannot connect to db");
                    } catch (IOException ex)
                    {
                        //Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
                        messageLabel.setText("Cannot find your file! " + ex.toString());

                    } catch (JavaLayerException ex)
                    {
                        //Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
                        messageLabel.setText("Cannot convert! " + ex.toString());
                    } catch (IllegalArgumentException iae)
                    {
                        messageLabel.setText("Name too short or illegal. At least 3 characters.");
                    }
                };
                java.awt.EventQueue.invokeLater(() ->
                {
                    Thread thread = new Thread(runnable);
                    thread.start();
                }
                );
            } else
            {//Exception Handling
                messageLabel.setText("Please wait a moment.");
                java.awt.EventQueue.invokeLater(() ->
                {
                    Thread thread = new Thread(() ->
                    {
                        insertIntoMusicList(4, nameField.getText(), urlField.getText(), purseSelectedType(), lyricUrlField.getText());
                    });

                    thread.start();
                });
            }
        } else if (removeRadioButton.isSelected())
        {
            if (musicList.get(musicTable.getSelectedRow()).getType() == MyMusic.TYPE_LOCAL && deleteFileCheckBox.isSelected())
            {
                deleteFileAndMusic();
            } else
            {
                deleteFromMusicList();
            }

        } else if (updateRadioButton.isSelected())
        {
            if (saveCheckBox.isSelected())
            {
                messageLabel.setText("Please wait a moment!");
                java.awt.EventQueue.invokeLater(() ->
                {
                    try
                    {
                        saveAndUpdateMusicList();
                    } catch (SQLException ex)
                    {
                        Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
                        messageLabel.setText("Cannot contact db");
                    } catch (IOException ex)
                    {
                        Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
                        messageLabel.setText("Cannot find your file!");
                    } catch (JavaLayerException ex)
                    {
                        Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
                        messageLabel.setText("Cannot convert");
                    } catch (UnsupportedAudioFileException | LineUnavailableException ex)
                    {
                        Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
                        messageLabel.setText(ex.toString());
                    }
                });
            } else
            {
                messageLabel.setText("Please wait a moment...");
                EventQueue.invokeLater(() ->
                {
                    updateMusicList();
                });
            }
            //getSelectedType();
        } else
        {
            messageLabel.setText("Please select an option to do. ");
        }
    }

    /**
     * To play the previous music the user played.
     */
    @SuppressWarnings("This method is unchecked and not running well.")
    public void previous()
    {
        try
        {
            if (snapshot == null || snapshot.equals(currentPlaying))
            {
                playPrev();
            } else
            {
                play(snapshot);
            }

        } catch (Exception e)
        {
//            Warning warning = new Warning(e.toString());
            Warning.createWarningDialog(e);
        }
    }

    /**
     * Allow user to reset the default saving address.
     */
    public void setDefaultSavingAddress()
    {
        try (Preference p = Preference.getInstance())
        {
            JFileChooser saver = new JFileChooser(p.getSavingPath());
            saver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int confirmed = saver.showSaveDialog(null);
            if (confirmed == JFileChooser.APPROVE_OPTION)
            {
                p.setSavingPath(saver.getSelectedFile().getAbsolutePath());
                messageLabel.setText("Your default saving address has been updated!");
            }
        } catch (IOException ex)
        {
            Warning.createWarningDialog(ex);
            Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, "Cannot contact your database.", ex);
        }
    }

    /**
     * Reset input components that is visiable in the frame according to
     * selected radioButton.
     */
    public void validateInput()
    {
        boolean addUpdateGroup = addRadioButton.isSelected() || updateRadioButton.isSelected();
        nameField.setVisible(addUpdateGroup);
        urlField.setVisible(addUpdateGroup);
        typeBox.setVisible(addUpdateGroup);
        swapBox.setVisible(swapRadioButton.isSelected());
        applyButton.setVisible(!swapRadioButton.isSelected() && !listenRadioButton.isSelected() && !addBatchRadioButton.isSelected());
        chooseFileButton.setVisible(addUpdateGroup && typeBox.getSelectedIndex() == 0);
        saveCheckBox.setVisible(addUpdateGroup && typeBox.getSelectedIndex() == 1);
        saveCheckBox.setSelected(false);
        deleteFileCheckBox.setVisible(removeRadioButton.isSelected());
        lyricUrlBox.setVisible(addUpdateGroup);
        chooseFilesButton.setVisible(addBatchRadioButton.isSelected());
    }

    /**
     * Delete a file located in the given path.
     *
     * @param path The path of the file needs to be deleted.
     * @return If the deletion is successful or not.
     */
    public boolean deleteFile(String path)
    {
        File toTrash = new File(path);

        messageLabel.setText("Removed from your list and file are deleted.");
        System.out.println("File at" + path + " is going to be deleted. ");
        return toTrash.delete();
    }

    /**
     * Browsr a website using user's default browser.
     *
     * @param uri The address of the website to be browsed.
     */
    public void browseWebsite(String uri)
    {
        Desktop desktop = Desktop.getDesktop();
        try
        {
            desktop.browse(new URI(uri));
        } catch (URISyntaxException ex)
        {
            Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
            messageLabel.setText("Sorry we cannot open this website.");
        } catch (IOException ex)
        {
            Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, "IO EXCEPTION", ex);//log error.
        }
    }

    /**
     * Add a new member into the musiclist.
     *
     * @param length the length of the id
     * @param name Name of the new music.
     * @param url The address of the music.
     * @param type The type of the url.
     * @param lyricUrl The address of the lyric file.
     */
    public void insertIntoMusicList(int length, String name, String url, int type, String lyricUrl)
    {
        insertIntoMusicList(name, url, type, Integer.parseInt(Randomizer.randomStringId(length)), lyricUrl);
    }

    /**
     * Get the file name from a local file.
     *
     * @param address The address of the file.
     * @return The name of the file.
     */
    public String getFileName(String address)
    {
        String name = "";
        try
        {
            if (typeBox.getSelectedIndex() == 0)
            {
                name = address.substring(address.lastIndexOf("\\"), address.indexOf(".")).replace("\\", "");
            } else
            {
                name = "Online Music";
            }

        } catch (Exception e)
        {
            messageLabel.setText("Sorry, we cannot assign name to the file you provided.");
        }
        return name;
    }

    /**
     * Add a new music into the database.
     *
     * @param name Name of the music to be added.
     * @param url The address of the music.
     * @param type The type of the music online/offline.
     * @param id The id identifies the music.
     * @param lyricUrl Where lyric are stored.
     */
    public void insertIntoMusicList(String name, String url, int type, int id, String lyricUrl)
    {
        // messageLabel.setText("Verifying your file...");
        if (name.isEmpty())
        {
            name = getFileName(url);
        }

        MyMusic music = new MyMusic(name, url, type, id, "", lyricUrl);
        if (music.isLegal())
        {
            try (SecretGardenConnection database = SecretGardenConnection.getDefaultInstance())
            {
                database.addIntoMusicList(music);
                refreshTabel();
                messageLabel.setText("Music successfully added.");
                clearAllFields();
                clickSound(SoundOracle.UI_DINGDONG);
            } catch (SQLException | ClassNotFoundException ex)
            {
                //Warning warning = new Warning(ex.toString()+ "Failed to save your lovely music.",);
                //ex.printStackTrace(System.err);
                Warning.createWarningDialog(ex);
                //Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, "Failed to add into list", ex);
            } catch (ArrayIndexOutOfBoundsException aioobe)
            {
                messageLabel.setText("Please select a member.");
            } catch (UnsupportedAudioFileException | JavaLayerException | LineUnavailableException | IOException ex)
            {
                Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
                //Warning warning = new Warning(ex.getMessage());
                Warning.createWarningDialog(ex);
            }
        } else
        {
            messageLabel.setText("Address illgal, make sure you selected a currect type");
//            if (urlField.getText().contains("mp3"))
//            {
//                messageLabel.setText("Address or file illegal, MP3 is NOT SUPPORTED, .wav ONLY!!!");
//            }
        }

    }

    /**
     * Download the music into user's default saving address while adding to the
     * list. The new music will be local type and in .wav format.
     *
     * @param name The name of the new music.
     * @param url The address of the new music.
     * @param lyricUrl The address of the lyric url.
     * @throws MalformedURLException Url format exception.
     * @throws SQLException Failed to connect to database.
     * @throws IOException
     * @throws JavaLayerException Unable to convert, mostly because the input
     * stream are cut for some reason.
     */
    public void saveAndInsertIntoMusicList(String name, String url, String lyricUrl) throws MalformedURLException, SQLException, IOException, JavaLayerException
    {

        //read info
        MyMusic music = new MyMusic(name, url, MyMusic.TYPE_ONLINE);
        String path;
        boolean hasSavingAddress = true;
        try (Preference p = Preference.getInstance())
        {
            path = p.getSavingPath();
            if (StrUtil.isBlank(path))
            {
                hasSavingAddress = false;
            }
        } catch (IOException e)
        {
            Warning.createWarningDialog(e);
            path = "./";
            hasSavingAddress = false;
        }

        File convertedFile;

        ///see if the msuci is legal and read the time of the music.
        if (music.isLegal())
        {
            if (hasSavingAddress)
            {//create a music format converter.
                MusicConverter converter = new MusicConverter();
                convertedFile = converter.convertAndSave(new java.net.URL(url), name, new File(path));
                insertIntoMusicList(4, name, convertedFile.getAbsolutePath(), MyMusic.TYPE_LOCAL, lyricUrl);
                messageLabel.setText("Your music has successfully downloaded and saved into your list!");
                clickSound(SoundOracle.UI_DINGDONG);
                //msgFrame.dispose();
            } else
            {
                messageLabel.setText("You don't have a saving address yet, please set one and retry!");
                setDefaultSavingAddress();
                //msgFrame.dispose();
            }
        } else
        {
            messageLabel.setText("Cannot open your music file!");
            //msgFrame.getCentralLabel().setText("Cannot open your musicfile, please try again!");
            // msgFrame.getCentralLabel().setIcon(null);
        }
    }

    /**
     * Get the text from current entered data, save to local and update.
     *
     * @throws MalformedURLException
     * @throws SQLException
     * @throws IOException
     * @throws JavaLayerException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    public void saveAndUpdateMusicList() throws MalformedURLException, SQLException, IOException, JavaLayerException, UnsupportedAudioFileException, LineUnavailableException
    {
//            int row = musicTable.getSelectedRow();
//            int id = musicList.get(row).getId();
        //int index = typeBox.getSelectedIndex()+1;
        //read information
        String name = nameField.getText();
        String url = urlField.getText();
        int index = musicTable.getSelectedRow();
        int id = musicList.get(index).getId();
        String path = "./";
        boolean hasSavingAddress = true;
        //get the default saving path
        try (Preference p = Preference.getInstance())
        {
            path = p.getSavingPath();
            hasSavingAddress = StrUtil.isNotBlank(path);
        } catch (IOException e)
        {
            Warning.createWarningDialog(e);
            hasSavingAddress = false;
        }
        File convertedFile;
        if (name.isEmpty())
        {
            //get the name of the previous music.
            name = musicList.get(index).getName();//musicTable.getValueAt(musicTable.getSelectedRow(), 1).toString();
        }
        if (url.isEmpty())
        {
            //get the prev. url.
            url = musicList.get(index).getUrl();
        }
        MyMusic music = new MyMusic(name, url, MyMusic.TYPE_ONLINE);
        if (music.isLegal())//see if it is a real readable music format, and calculate time length.
        {
            if (hasSavingAddress)
            {
                MusicConverter converter = new MusicConverter();
                convertedFile = converter.convertAndSave(new java.net.URL(url), name, new File(path));
                urlField.setText(convertedFile.getAbsolutePath());
                typeBox.setSelectedIndex(0);
                try (SecretGardenConnection database = SecretGardenConnection.getDefaultInstance())
                {
                    database.updateMusicList(id, new MyMusic(name, url, MyMusic.TYPE_LOCAL));
                    messageLabel.setText("Your music are successfully downloaded and saved into your list!");

                } catch (SQLException | ClassNotFoundException sqle)
                {
                    Warning.createWarningDialog(sqle);
                }
                //src.addIntoMusicList(new MyMusic(name, url, MyMusic.TYPE_LOCAL, randomInt(1000, 9999)));
                //updateMusicList();
                //MyMusic music = new MyMusic(name, url,0);

                clickSound(SoundOracle.UI_DINGDONG);
            } else
            {
                Warning warning = new Warning("You don't have a saving address yet", "please select one. Please click on\"fix problem\" to select your default saving address.");
                warning.setSolution(() ->
                {
                    setDefaultSavingAddress();
                });
                messageLabel.setText("You don't have a saving address yet, please set one and retry!");

            }

        } else
        {
            messageLabel.setText("Cannot open your music file! For music " + music.getName() + " Type : " + music.getType());
        }
    }

    /**
     * Update the musiclist to database.
     */
    public void updateMusicList()
    {
        //messageLabel.setText("Verifying your file...");
        try (SecretGardenConnection database = SecretGardenConnection.getDefaultInstance())
        {
            int row = musicTable.getSelectedRow();
            int id = musicList.get(row).getId();
            //int index = typeBox.getSelectedIndex()+1;
            String name = nameField.getText();
            String url = urlField.getText();
            String lyricUrl = lyricUrlField.getText();
            //read info
            if (name.isEmpty())
            {
                name = musicTable.getValueAt(musicTable.getSelectedRow(), 1).toString();
            }
            if (url.isEmpty())
            {
                url = musicTable.getValueAt(musicTable.getSelectedRow(), 4).toString();
            }
            if (lyricUrl.isEmpty())
            {
                lyricUrl = musicList.get(musicTable.getSelectedRow()).getLyricAddress();
            }

            MyMusic tempMusic = new MyMusic(name, url, purseSelectedType());
            tempMusic.setLyricAddress(lyricUrl);
            if (tempMusic.isLegal())
            {
                database.updateMusicList(id, tempMusic);
                messageLabel.setText("Your list are updated.");
                refreshTabel();
                clearAllFields();
                clickSound(SoundOracle.UI_DINGDONG);
            } else
            {
                messageLabel.setText("<html>Warning: The music address you entered is illegal! Please check:"
                        + "<br> 1. You have selected wrong type of address."
                        + "<br> 2. File you entered does not exists.</html>");

            }

        } catch (NumberFormatException | SQLException | ClassNotFoundException e)
        {
//            Warning warning = new Warning(e.toString());
            Warning.createWarningDialog(e);
            messageLabel.setText("An exception has occured.");
        } catch (ArrayIndexOutOfBoundsException aioobe)
        {
            messageLabel.setText("<html>Please select a member to update."
                    + "<br>" + aioobe.toString() + "</html>");
        } catch (UnsupportedAudioFileException | JavaLayerException | LineUnavailableException | IOException ex)
        {
            Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
            messageLabel.setText(ex.toString());
            Warning.createWarningDialog(ex);
        }

    }

    /**
     * Delete selected member from musicList.
     */
    public void deleteFromMusicList()
    {
        try (SecretGardenConnection database = SecretGardenConnection.getDefaultInstance())
        {
            int row = musicTable.getSelectedRow();
            int id = musicList.get(row).getId();
            //****Rewrite in amother method.****
            //int type = musicList.get(row).getType();
//            if(type == MyMusic.TYPE_LOCAL && deleteFileCheckBox.isSelected())
//            {
//                if(deleteFile(musicList.get(row).getUrl().replace("\"", "").replace("\\", "/")))
//                {
//                    messageLabel.setText("File and music are removed successfully!");
//                }else
//                {
//                    messageLabel.setText("Only removed from list, file not deleted.");
//                }
//            }else
//            {
//                messageLabel.setText("File are deleted from your list.");
//            }
            database.removeFromMusicList(id);
            //messageLabel.setText("Removed successfully!!");
            refreshTabel();

        } catch (SQLException | ClassNotFoundException ex)
        {
//            Warning wn = new Warning(ex.toString(), "Cannot get your sql connected.");
            Warning.createWarningDialog(ex);
            messageLabel.setText("Cannot connect to SQL DB");
            //Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException nfe)
        {
//            Warning warning = new Warning(nfe.toString(), "Please select a row");
            Warning.createWarningDialog(nfe);
            messageLabel.setText("Please select a row to edit.");
        }
    }

    /**
     * Delete selected music with the file.
     */
    public void deleteFileAndMusic()
    {
        int row = musicTable.getSelectedRow();
        //int id = musicList.get(row).getId();
        // int type = musicList.get(row).getType();
//            if(type == MyMusic.TYPE_LOCAL && deleteFileCheckBox.isSelected())
        if (deleteFile(musicList.get(row).getUrl().replace("\"", "").replace("\\", "/")))
        {
            messageLabel.setText("File and music are removed successfully!");
            deleteFromMusicList();
            clickSound(SoundOracle.UI_DINGDONG);
        } else
        {
            messageLabel.setText("File deletion are failed! Please try again.");
        }

    }

    /**
     * Inport musics in batch.
     *
     * @param files The files of musics to add.
     */
    public void addInBatch(File[] files)
    {
        MyMusic[] musics = new MyMusic[files.length];
        for (int i = 0; i < files.length; i++)
        {
            musics[i] = new MyMusic(files[i].getName(), files[i].getAbsolutePath(), MyMusic.TYPE_LOCAL, Randomizer.randomInt(1000, 9999));
        }
        try (SecretGardenConnection database = SecretGardenConnection.getDefaultInstance())
        {
            database.addIntoMusicListInBatch(musics);
            System.out.println("journeylove.BackgroundMusic.addInBatch()ye ye ye ye ye");
            refreshTabel();
            messageLabel.setText("A bunch of musics have been added into your list.");
        } catch (SQLException | UnsupportedAudioFileException | JavaLayerException | LineUnavailableException | IOException | ClassNotFoundException ex)
        {
            //Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
//            new Warning("Cannot add music since  " + ex.toString());
            Warning.createWarningDialog(ex);
        }
    }

    /**
     * Open a file chooser to allow user to choose file,.
     */
    public void addInBatch()
    {
        Thread addThread;
        JFileChooser chooser = new JFileChooser();
        int confirmed;
        chooser.setFileFilter(new FileNameExtensionFilter("Supported Format", "mp3", "wav"));
        chooser.setMultiSelectionEnabled(true);
        confirmed = chooser.showOpenDialog(this);
        if (confirmed == JFileChooser.APPROVE_OPTION)
        {
            JOptionPane.showMessageDialog(this, "Your music is being added, please wait patiently.");
            messageLabel.setText("Your music is being added.");
            addThread = new Thread(() ->
            {
                addInBatch(chooser.getSelectedFiles());

            });
            addThread.start();
        }

    }

    /**
     * Ger the type of music user selected in the typebox.
     *
     * @return The music type that user selected.
     * @see MyMusic.TYPE.LOCAL
     * @see MyMusic.TYPE.ONLINE
     */
    public int purseSelectedType()
    {
        int index = this.typeBox.getSelectedIndex();
        System.out.println("journeylove.BackgroundMusic.getSelectedType() : index;" + index);
        int type = 0;
        switch (index)
        {
            case 0:
                type = 2;
                break;
            case 1:
                type = 3;
                break;
        }
        return type;
    }

    /**
     * Refresh the table with new data from database.
     */
    public void refreshTabel()
    {
        JTable soulTable = new JTable(toObjectses(getMyMusics()), MUSIC_TABLEHEADER);
        //soulTable.setColumnModel(mainColumnModel);
        TableModel tm = soulTable.getModel();
        this.musicTable.setModel(tm);
        formatMusicTable();
    }

    /**
     * Get the table,
     *
     * @return The table.
     */
    public JTable getMusicTable()
    {
        return musicTable;
    }

    public ArrayList<MyMusic> getMusicList()
    {
        return musicList;
    }

    /**
     * Set the table.
     *
     * @param musicTable musictable.
     */
    public void setMusicTable(JTable musicTable)
    {
        this.musicTable = musicTable;
    }

    public LyricReader getLyricReader()
    {
        return this.lyricReader;
    }

    /**
     * Cleat all fields in this window.
     */
    public void clearAllFields()
    {
        nameField.setText("");
        urlField.setText("");
        lyricUrlField.setText("");
    }

    /**
     * Play the music in a row.
     *
     * @param row The row on the musicTable to play.
     */
    public void play(int row)
    {
        try
        {
            if (row >= 0)
            {
                play(musicList.get(musicTable.getSelectedRow()));
            } else
            {
                if (!musicList.isEmpty())
                {//If the user has music.
                    musicTable.setRowSelectionInterval(0, 0);
                    play(musicList.get(0));//Play the first .
                } else
                {
                    messageLabel.setText("You don't have any music yet.");
                }
            }

        } catch (ArrayIndexOutOfBoundsException ex)
        {
            messageLabel.setText("Please select a music to play.");
        }

    }

    /**
     * Start playing music.
     *
     * @param music
     */
    public void play(MyMusic music)
    {
        //int row = musicTable.getSelectedRow();
        try
        {
            String lyricAddress = music.getLyricAddress();
            if (status != STATUS_PAUSING)
            {
                snapshot = currentPlaying;
                currentPlaying = music;
                myClip.open(music.getAudioInputStream());
                positionSlider.setMaximum(new journeylove.TimeConverter(myClip.getMicrosecondLength()).convertToSeconds());
                if (lyricAddress != null)
                {
                    if (!lyricAddress.isEmpty())
                    {
                        try
                        {
                            //read the lyric file
                            if (StrUtil.startWithAny(lyricAddress, "https://", "http://"))
                            {
                                File tempLrcFile = File.createTempFile("templrc", ".lrc");
                                HttpUtil.downloadFile(lyricAddress, tempLrcFile);
                                lyricReader = new LyricReader(tempLrcFile);
                            } else
                            {
                                lyricReader = new LyricReader(new File(lyricAddress));
                            }

                            isLyricFound = true;
                            refreshLyricLines();
                            lyricWindow.getNameField().setText(lyricReader.getLyricTitle());
                            lyricWindow.getArtistField().setText(lyricReader.getArtist());
                            lyricWindow.getEditorField().setText(lyricReader.getEditor());
                            lyricWindow.getAlbumField().setText(lyricReader.getAlbum());
                        } catch (Exception e)
                        {
                            // Warning warning = new Warning("cannot play lyric since " + e.toString());
                            Warning warning = Warning.createWarningDialog(e);
                            warning.getSuggestionArea().append("In this case, please check your lyric file.");
                            warning.setSolution(() ->
                            {
                                File f = new File(lyricAddress);
                                try
                                {
                                    if (!f.exists())
                                    {
                                        f.mkdirs();
                                    }
                                    Desktop.getDesktop().open(f.getParentFile());
                                } catch (Exception ex)
                                {
                                    Warning.createWarningDialog(ex);
                                }
                            });
                        }
                    } else
                    {
                        isLyricFound = false;
                        lyricWindow.setPreviousLine("Journey's music player, play my style!");
                        lyricWindow.setCurrentLine("Enjoy your music! ^-^");
                        lyricWindow.setNextLine("No lyric file found.");
                        lyricWindow.ClearAllFields();
                    }
                } else
                {
                    lyricWindow.setCurrentLine("Enjoy your music!");
                    lyricWindow.setNextLine("No lyric file found");
                    lyricWindow.setPreviousLine("Journey's music player, play my style!");
                    isLyricFound = false;
                    lyricWindow.ClearAllFields();
                }

            }
            if (status == STATUS_STOPPING)
            {
                // System.out.println("journeylove.BackgroundMusic.play()");
                String time = music.getTime().replace(" ", "");
                nowPlayingLabel.setText("Now Playing:" + music.getName());
                messageLabel.setText("Now Playing Music, total time ->  " + time);
                totalTimeLabel.setText(time);

            }

            myClip.start();
            myTimer.start();
            status = STATUS_PLAYING;
            playButton.setVisible(false);
            pauseButton.setVisible(true);
            stopButton.setVisible(true);
        } catch (LineUnavailableException ex)
        {
            Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex)
        {
            messageLabel.setText("Failed to play your music, consider edit it.");

            //Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            messageLabel.setText("Cannot open the file, make sure the file is sill in its origional place.");
            //Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex)
        {
            messageLabel.setText("JL ex. Cannot convert.");
            //Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.ArrayIndexOutOfBoundsException aioobe)
        {
            messageLabel.setText("Please select an index.");
        } catch (java.lang.IllegalStateException ise)
        {
            String message = ise.getMessage();
            if (message.contains("Clip is already open"))
            {
                myClip.close();
                play();
            }
        }

    }

    /**
     * Load the detail information of this song
     *
     * @param reader
     */
    public void loadAuthorInfo(LyricReader reader)
    {
        lyricWindow.getNameField().setText(reader.getLyricTitle());
        lyricWindow.getArtistField().setText(reader.getArtist());
        lyricWindow.getEditorField().setText(reader.getEditor());
        lyricWindow.getAlbumField().setText(reader.getAlbum());
    }

    /**
     * Play the music which user selected on the table.
     *
     * @throws ArrayIndexOutOfBoundsException When user have selected none.
     */
    public void play() throws ArrayIndexOutOfBoundsException
    {
        play(musicTable.getSelectedRow());
    }

    /**
     * Play a previous song accoring to the sequence.
     */
    public void playPrev()
    {
        int row = musicTable.getSelectedRow();
        if (row > 0)
        {
            musicTable.setRowSelectionInterval(row - 1, row - 1);
            play();
        } else if (row == 0)
        {
            musicTable.setRowSelectionInterval(musicList.size() - 1, musicList.size() - 1);
            play();
        } else
        {
            if (musicList.isEmpty())
            {
                messageLabel.setText("Please try to add your first music!");
            } else
            {
                musicTable.setRowSelectionInterval(0, 0);
                play();
            }
        }

    }

    public void forcePlaySelectedSong()
    {
        stop();
        // snapshot = currentPlaying;
        play();
    }

    /**
     * Stop the music that is currently playing, but this will not close the
     * clip.
     *
     * @see STATUS_PAUSING
     */
    public void pause()
    {
        try
        {
            status = STATUS_PAUSING;
            myClip.stop();
            myTimer.stop();
            playButton.setVisible(true);
            pauseButton.setVisible(false);
            stopButton.setVisible(true);

            //nowPlayingLabel.setText("Paused");
        } catch (Exception e)
        {
            messageLabel.setText("Cannot pause your music");
        }
    }

    /**
     * Stop and close the music that is cuttently playing, close the clip.
     */
    public void stop()
    {
        try
        {
            status = STATUS_STOPPING;
            myClip.stop();
            myClip.close();
            myTimer.stop();

            playButton.setVisible(true);
            pauseButton.setVisible(false);
            stopButton.setVisible(false);
            messageLabel.setText("Music stopped");
            nowPlayingLabel.setText("Stopped");

            timeLabel.setText("00:00");
            totalTimeLabel.setText("00:00");
            positionSlider.setValue(0);
            lyricWindow.setPreviousLine("");
            lyricWindow.setCurrentLine("Journey's music player, play my style!");
            lyricWindow.setNextLine("");
            lyricWindow.ClearAllFields();
        } catch (Exception e)
        {
            messageLabel.setText("Cannot close your music");
        }
    }

    /**
     * Exchange the positions of two musics on the list.
     *
     * @param id1 The first member to be exchanged.
     * @param id2 Another member to be exchanged.
     */
    public void swap(int id1, int id2)
    {

        try (SecretGardenConnection database = SecretGardenConnection.getDefaultInstance())
        {
            database.swapMusicList(id1, id2);
            messageLabel.setText("Swamped!");
            swapId1 = 0;
            swapId2 = 0;
            swapObj1Label.setText("");
            swapObj2Label.setText("");
            clickSound(SoundOracle.UI_DINGDONG);
        } catch (Exception e)
        {
            messageLabel.setText("Failed to swamp.");
            Warning.createWarningDialog(e);
        }
    }

    /**
     * The final command of swap.
     *
     * @see swap(int id1, int id2)
     */
    public void swap()
    {
        if (swapId1 != 0 && swapId2 != 0 && swapId1 != swapId2)
        {
            swap(swapId1, swapId2);
            refreshTabel();
        } else
        {
            messageLabel.setText("Please select two different members to swap!");
        }
    }

    /**
     * Get the clip of background music.
     *
     * @return The clip of this.
     */
    public Clip getClip()
    {
        return myClip;
    }

    public Timer getTimer()
    {
        return this.myTimer;
    }

    /**
     * Initalize the main clip with try-and-catch.
     */
    public void initalizeClip()
    {
        try
        {
            myClip = AudioSystem.getClip();
            myClip.addLineListener(this);
        } catch (LineUnavailableException ex)
        {
            Warning w = new Warning("Cannnot get cllllip", "Please re-try later", ex);
            //Logger.getLogger(BackgroundMusic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fired when the status of the clip has changed.
     *
     * @param event The line event received.
     */
    @Override
    public void update(LineEvent event)
    {
        LineEvent.Type type = event.getType();

        if (type.equals(LineEvent.Type.STOP) && status == 1)
        {
            next(rotatingMethod);

        } else if (type.equals(LineEvent.Type.START))
        {
            //TimeConverter tc = new TimeConverter(myClip.getMicrosecondPosition());

        }
    }

    public String getRotatingMethod()
    {
        return rotatingMethod;
    }

    public void setRotatingMethod(String rotatingMethod)
    {
        this.rotatingMethod = rotatingMethod;
    }

    /**
     * Automatically play the next music user selected.
     */
    public void next()
    {
        next(rotatingMethod);
    }

    /**
     * Play the next music according to the rotating method.
     *
     * @param rotateMethod the value of rotateMethod
     */
    public void next(String rotateMethod)
    {
        status = STATUS_STOPPING;
        //System.out.println("journeylove.BackgroundMusic.next():status stopped.");
        switch (rotateMethod)
        {
            case SEQUENCE_ROTATE:
                myClip.close();
                //snapshot = currentPlaying;
                selectNext();
                play(musicTable.getSelectedRow());
                break;
            case RANDOM_ROTATE:
                myClip.close();
                //snapshot = currentPlaying;
                randomPlay();
                break;
            case SINGLE_ROTATE:
                //snapshot = currentPlaying;
                restart();
                break;
            default:
                this.stop();
                break;
        }

    }

    /**
     * Play a random music on the list.
     */
    public void randomPlay()
    {
        int size = musicList.size();
        int playnext;
        if (!musicList.isEmpty())
        {
            playnext = Randomizer.randomInt(0, size - 1);
            musicTable.setRowSelectionInterval(playnext, playnext);
            this.play(playnext);

        } else
        {
            messageLabel.setText("Your musiclist is empty now.");
        }
    }

    /**
     * Automaticlly select the next member in the table. If the table have not
     * selected any member yet, it will select the first one.
     *
     */
    public void selectNext()
    {
        if (!musicList.isEmpty())
        {
            if (musicTable.getSelectedRow() == musicList.size() - 1)
            {
                musicTable.setRowSelectionInterval(0, 0);
            } else
            {
                musicTable.setRowSelectionInterval(musicTable.getSelectedRow() + 1, musicTable.getSelectedRow() + 1);
            }
        } else
        {
            messageLabel.setText("You don't have any music in your list yet! Why not add one now?");
        }

    }

    /**
     * Restart what the clip is playing.
     */
    public void restart()
    {
        try
        {
            if (myClip.isOpen())
            {
                myClip.setFramePosition(0);
                lyricReader.setCurrentPosition(0);
                refreshLyric();
                myClip.start();
                status = STATUS_PLAYING;
            }
        } catch (Exception e)
        {
            Warning warning = new Warning("Cannot play your music because of " + e.toString(),
                    "You might want to try to play another music." + Warning.DETAIL_LISTED, e);
        }

    }

    /**
     * This methods determins if a string contains Chinese character or not.
     *
     * @param string The string that needs to be inspected.
     * @return If that string contains Chinese character.
     */
    public boolean isChinese(String string)
    {
        int n;
        for (int i = 0; i < string.length(); i++)
        {
            n = string.charAt(i);
            if (!(19968 <= n && n < 40869))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Produce small sounds.
     *
     * @param soundName The name of the sound.
     */
    public void clickSound(String soundName)
    {
        try
        {
            Clip effectClip = AudioSystem.getClip();
            effectClip.open(AudioSystem.getAudioInputStream(getClass().getResource(soundName)));
            effectClip.start();
            effectClip.addLineListener((LineEvent e) ->
            {
                LineEvent.Type type = e.getType();
                if (type.equals(LineEvent.Type.STOP))
                {
                    effectClip.close();
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
        {
            Warning.createWarningDialog(ex);
            //Warning warning = new Warning("Cannot open sound  " + ex.toString(), "Please retry.", ex);
        }

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
     * Copy selected row from the table.
     */
    public void copyTableSelection()
    {
        int column = musicTable.getSelectedColumn();
        int row = musicTable.getSelectedRow();
        clipboardCopy(musicTable.getValueAt(row, column));
    }

    /**
     * Dispose this.
     */
    public void close()
    {
        this.dispose();
    }

    /**
     * Return the namefield that is displayed to the user.
     *
     * @return The namefield user used.
     */
    public JTextField getNameField()
    {
        return nameField;
    }

    /**
     * Get the url field displayed in this window.
     *
     * @return The urlField user used.
     */
    public JTextField getUrlField()
    {
        return urlField;
    }

    /**
     * Get the typebox displaed in this window.
     *
     * @return the combobox user used.
     */
    public JComboBox<String> getTypeBox()
    {
        return typeBox;
    }

    /**
     * Fired when user input by keyboard.
     *
     * @param e The event.
     */
    @Override
    public void insertUpdate(DocumentEvent e)
    {
        clickSound(SoundOracle.PHONE_TYPING_SOUND);
    }

    /**
     * When user delete something in a normal way, this invoked.
     *
     * @param e
     */
    @Override
    public void removeUpdate(DocumentEvent e)
    {
        clickSound(SoundOracle.PHONE_TYPING_SOUND);
    }

    /**
     * Invoked when user used other methods (such as copy paste) to change the
     * text in a document.
     *
     * @param e Doc event.
     */
    @Override
    public void changedUpdate(DocumentEvent e)
    {
        clickSound(SoundOracle.PHONE_TYPING_SOUND);
    }

}
