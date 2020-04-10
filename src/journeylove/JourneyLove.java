/*Editor: Johnson Gao
 * Date This Project Created: Apr.21 2019 
 * Description Of This Class: This is a welcome page that can give user surprices.
 * Art credit of icons: https://www.zhihu.com/question/39213350
https://image.baidu.com
 * 
 */
package journeylove;

//import lovelycolors.LovelyColors;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import static journeylove.Randomizer.randomInt;

/**
 * A place that nobody can steal our memories.
 * <br>This is a welcome page that can give user surprices.
 * @author Johnson Gao
 * @author Beautiful Princess Jenny Wang.
 * @since I love you
 * 
 */
public class JourneyLove extends JFrame implements ActionListener, MouseListener, ItemListener, KeyListener
{
//Main constructor have a background of blue.

    /**
     * The font that will show users live message.
     */
    final Font MESSAGE_FONT = new Font("Courier New", Font.PLAIN, 18);
    final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
    final int NW_SMALLICON_SCALE = 175;
    static final String[] MAIN_MENUS =
    {
        "--Hi, Welcome to Johnson & Jenny's digital secret garden!",
        "Our Memories",
        "My Musics",
        "My Images",
        "Music Converter",
        "Cipher Factory (Beta)",
        "MailBox Connection - External"
    };
    static final String[] MAJOR_ICON =
    {
        "LayingLove.jpg", "hideRain.jpg", "3tangyuans.jpg", "MSU9.jpg", "MissULoveUAccU.jpg",
        "on bed.jpg", "MSU7.jpg", "hug.png", "MSU4.jpg", "MSU1.jpg", "MSU4.jpg", "MSU6.jpg", "MSU5.jpg"
    };
    /**
     * THIS HOLDS THE STRING OF SOUND TRACKS.
     * <br>0.Tiny Button Push.wav
     * <br>
     * 1.Click Button 2.wav
     * <br>
     * 2.door unlock.wav
     * <br>
     * 3.deng.wav
     */
    static final String[] SOUND_TRACKS =
    {
        "Tiny Button Push.wav", "Click Button 2.wav", "Door Unlock.wav", "deng.wav"
    };
    /**
     * This holds the name of icons that may be louded on the North west LABEL.
     * <br>0.he is blbl<br>
     * 1.cute still<br>
     * 2.WOOW
     */
   public static final String[] NORTH_WEST_ICON =
    {
        "HeIsBLBL.jpeg", "cuteStill.jpg", "WOOW.jpg"
    };
    public static final String[] SAD_SOUND =
    {
        "Buzz",
        "Cat Scream",
        "Error Alert",
        "HUA-HUA-HUA-HUA",
        "Sad Male",
        "Computer Error2",
        "Crowd Boo 3",
        "haofanni",
        "beishangliugeiziji",
        "LiangLiangcli"
    };
    
    /**
     * Some addresses of happy music.
     */
    public static final String[] HAPPY_MUSIC =
    {
        "Kissing People", "CatMeow2", "Music Box", "Computer_Magic", "Tian Tian De - SELECTION", "XiaoXingXing-clip",
        "最美情侣clip1", "好想你clip1", "好想你小冰clip", "好想你纯音乐clip", "我想和你看电影clip", "等你下课clip"
    };
    /**
     * This stores some websites that have sweet video. !
     * 
     */
    public static final String[] SWEET_WEBSITES_MUSIC =
    {
        "https://www.bilibili.com/video/av1619843/",
        "https://www.bilibili.com/video/av14199287/",
        "https://www.youtube.com/watch?v=ulOb9gIGGd0"
    };

    /**
     * Here are some sweet love pick-ups.
     * <br>Oh don't blame me sometimes I will think of her when I'm programming.
     */
    public static final String[] SWEET_SENTENCES =
    {
        "Everyday at twilight, when the sun turns red in the sky, I think of you on that shoreline.",
        "You are pretty much the only thing that makes me want to get up in the morning.",
        "Again I'm falling for you, so you wrap your arms around mine.",
        "Being with you is like walking on a very clear morning.",
        "Within you I lose myself, without you I find myself wanting to be lost again.",
        "Love can touch us one time. And last for a lifetime.",
        "No matter the ending is perfect or not, you cannot disappear from my world.",
        "When I was dreaming about you baby, you were dreaming of me.",
        "Once we dreamt that we were strangers. We wake up to find that we were dear to each other.",
        "Don't ever say you're lonely. Just lay your problems on me.",
        "When the vision you have gets blurry, you don't have to worry. I'll be your eyes.",
        "So many people all around the world. Tell me where do I find someone like you ,Jenny!.",
        "You smiled and talked to me of nothing and I felt that for this I had been waiting long.",
        "When the words \"I love you\" were said by you for the first time, my world blossoms.",
        "Baby, you're the only thing that I really need.",
        "My universe will never be the same. I'm glad you came.",
        "I know I'm meant to be where I am. And I'm gonna be by your side.",
        //        "I love thee freely, as men strive for Right;I love thee purely, as they turn from Praise.",
        "Do you know? You know I love you so.",
        "This is me praying that this was the very first page, not where the story line ends.",
        "I'll spend forever wondering if you knew, I was enchanted to meet you too.",
        "What would I do without your smart mouth. Drawing me in, and you kicking me out.",
        "I can be your hero, baby. I can kiss away the pain.",
        //      "You hit me like the sky fell on me fell on me. And I decided you look well on me well on me.",
        "I really really really really really really like you Jenny.",
        "Love me 'till the summer's done. But keep me in your heart so I hold on.",
        //        "Go slow my dear, and feel no fear. You're not alone. Speak soft to me, and let me be your warmth in cold.",
        "We're collecting dust, but our love is enough.",
        "I'd hold you in my arms and never let go.",
        "Your beauty is just blinding me like sunbeams on a summer stream.",
        // "Will you still love me when I am no longer young and beautiful？",
        "My hands are tied, but not tied enough. You're the high that I can't give up.",
        "Time has brought your heart to me. I have loved you for a thousand years.",
        "Take me to your heart. Take me to your soul. Give me your hand before I'm old.",
        "All I want to do is live with you.",
        //       "If ever two were one, then surely we. If ever man were loved by wife, then thee.",//Standard length
        "Look at the stars, look how they shine for you.",
        "Nowhere else that I belong than here with you.",
        "Anywhere you are, I am near. Anywhere you go, I'll be there."
    };
    /**
     * The label for title.
     */
    private JLabel titleLabel;
    private JLabel westNorthLabel;
    private JLabel westSouthLabel;
    private JLabel messageLabel;
    private JLabel ramPicLabel;
    private JLabel eastNorthLabel;
    private JLabel majorIconLabel;
    private JLabel gotoLabel;
    private JButton gotoButton;
    private JComboBox<String> mainMenuComboBox;
    private ButtonGroup missOrNotButtonGroup;
    private JRadioButton missRadioButton;
    private JRadioButton noMissRadioButton;
    private Box missOrNotBox;
private UIManager.LookAndFeelInfo[] infos;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel centerPanel;
    //private LovelyColors lovelyColors = new LovelyColors();
    private Color bgColor = LovelyColors.LIGHT_HEART_BLUE;
    private int noMissTimesClick;
    private Clip soundEffectClip;
    private boolean sound;
    private JMenuBar myMenuBar;
    private JMenu feedbackMenu,linksMenu;
    private JMenuItem feedbackItem,mailBoxItem;
    private JButton setStyleButton;
    private JComboBox<String> stylesComboBox;
    
    final SecretGardenConnection SGC = new SecretGardenConnection();

    /**
     * A place that nobody can steal our memories.
 * <br>This is a welcome page that can give user surprises.
     */
    public JourneyLove()
    {
        /**
         * JFrame
         */
        super("Johnson & Jenny's secret Garden (4th release)");
        this.setBounds(400/*x align R*/, 150/*y align down*/, 1000/*X-WIDTH*/, 600/*Y-Width*/);
        this.getContentPane().setBackground(bgColor);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout(15, 15));
//        try
//        {            
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.frameBorderStyle = 
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
//            UIManager.put("RootPane.setupButtonVisible", false);
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//            //UIManager.installLookAndFeel(org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.getBeautyEyeLNFCrossPlatform().getName(),org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.getBeautyEyeLNFCrossPlatform().getClass().getName());
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }

//        JIntellitype.getInstance().registerHotKey(1, "F5");
//        JIntellitype.getInstance().addHotKeyListener(new HotkeyListener()
//        {
//            @Override
//            public void onHotKey(int identifier)
//            {
//                if(identifier == 1)
//                {
//                    System.out.println("f5 are pressed.");
//                }
//            }
//        });
        
        /**
         * JLabel
         */
        this.titleLabel = new JLabel("Johnson & Jenny's Secret Garden");
        titleLabel.setFont(TITLE_FONT);
        this.ramPicLabel = new JLabel();
        this.messageLabel = new JLabel(SWEET_SENTENCES[randomInt(0, SWEET_SENTENCES.length - 1)]);
        messageLabel.setFont(MESSAGE_FONT);
        messageLabel.addMouseListener(this);
        this.eastNorthLabel = new JLabel(openIcon("BuddingFlower.jpg", 170, 170));
        eastNorthLabel.addMouseListener(this);
        westNorthLabel = new JLabel(this.openIcon(NORTH_WEST_ICON[0], NW_SMALLICON_SCALE, NW_SMALLICON_SCALE));
        westNorthLabel.addMouseListener(this);
        //westNorthLabel.addMouseListener(this);
        this.majorIconLabel = new JLabel(this.openAutoScaledIcon(MAJOR_ICON[randomInt(0, MAJOR_ICON.length - 1)], 610, 400));
        this.majorIconLabel.addMouseListener(this);
        this.majorIconLabel.setOpaque(false);
        

        this.gotoLabel = new JLabel(openIcon("goto.png"));
        gotoLabel.addMouseListener(this);
        this.westSouthLabel = new JLabel(openIcon("LD" + randomInt(1, 9) + ".jpg", 162, 162));
        westSouthLabel.addMouseListener(this);
        /**
         * JButton.
         */
        setStyleButton = new JButton("Set Style");
        setStyleButton.addActionListener((e)->{setLookAndFeel(stylesComboBox.getSelectedIndex());});
        
        //JCombobox
        stylesComboBox = new JComboBox<>(getInstalledLookAndFeels());
        stylesComboBox.setOpaque(false);
        stylesComboBox.setBorder(new TitledBorder("Styles: "));
        stylesComboBox.addItemListener((e)->{clickSound(SoundOracle.WATER_PRESS_1);});
        /**
         * JMENU
         */
        feedbackItem = new JMenuItem("Feedback Message", openIcon("feedback.png"));
        feedbackItem.addActionListener(this);
        feedbackItem.setActionCommand("Message Feedback");
        mailBoxItem = new JMenuItem("Mail Registeration Address");
        mailBoxItem.addActionListener(this);
        feedbackMenu = new JMenu("Leave Feedback");
        feedbackMenu.add(feedbackItem);
        linksMenu = new JMenu("External Links");
        linksMenu.add(mailBoxItem);
        myMenuBar = new JMenuBar();
        myMenuBar.add(feedbackMenu);
        myMenuBar.setBackground(LovelyColors.BEWITCHED_TREE);
        myMenuBar.add(linksMenu);
        this.setJMenuBar(myMenuBar);
        
        
        /**
         * JRadioButton.
         */
        this.missRadioButton = new JRadioButton("Yes!");
        missRadioButton.addMouseListener(this);
        missRadioButton.setOpaque(false);
        missRadioButton.setToolTipText("Select this if you have missed me today.");
        this.noMissRadioButton = new JRadioButton("NO!");
        noMissRadioButton.addMouseListener(this);
        noMissRadioButton.setOpaque(false);
        noMissRadioButton.addActionListener(this);
        noMissRadioButton.setToolTipText("If you haven't missed me today, choose this.");
        this.missOrNotButtonGroup = new ButtonGroup();
        missOrNotButtonGroup.add(missRadioButton);
        missOrNotButtonGroup.add(noMissRadioButton);

        missOrNotBox = Box.createVerticalBox();
        missOrNotBox.setBorder(BorderFactory.createTitledBorder("Did you miss me today?"));
        missOrNotBox.add(missRadioButton);
        missOrNotBox.add(noMissRadioButton);

        /**
         * JComboBox.
         */
        mainMenuComboBox = new JComboBox<>(MAIN_MENUS);
        mainMenuComboBox.addItemListener(this);
        mainMenuComboBox.setToolTipText("Select where to go next.");
        
        Box setStyleBox = Box.createHorizontalBox();
        setStyleBox.add(stylesComboBox);
        setStyleBox.add(setStyleButton);
        setStyleBox.setOpaque(false);
        setStyleBox.setBorder(BorderFactory.createTitledBorder("Set Your Style"));
        
        /**
         * JPanel.
         */
        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel, SwingConstants.CENTER);
        northPanel.setOpaque(false);
        this.centerPanel = new JPanel(new BorderLayout(5, 3));
        centerPanel.add(mainMenuComboBox, BorderLayout.NORTH);
        centerPanel.add(gotoLabel, BorderLayout.CENTER, SwingConstants.CENTER);
        centerPanel.add(majorIconLabel, BorderLayout.SOUTH);
        centerPanel.setOpaque(false);
        this.southPanel = new JPanel(new FlowLayout());
        southPanel.add(messageLabel);
        southPanel.setOpaque(false);
        this.eastPanel = new JPanel(new BorderLayout(3, 3));
        eastPanel.add(eastNorthLabel, BorderLayout.NORTH);
        eastPanel.add(missOrNotBox, BorderLayout.CENTER);
        eastPanel.setOpaque(false);
        this.westPanel = new JPanel(new BorderLayout());
        westPanel.add(westNorthLabel, BorderLayout.NORTH);
        westPanel.add(setStyleBox,BorderLayout.CENTER);
        westPanel.add(westSouthLabel, BorderLayout.SOUTH);
        westPanel.setOpaque(false);

        /**
         * Adding.
         */
        this.add(northPanel, BorderLayout.NORTH, SwingConstants.CENTER);
        this.add(centerPanel, BorderLayout.CENTER, SwingConstants.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(westPanel, BorderLayout.WEST);

        /**
         * Set this frame truly visiable. Add-on methods @tail.
         */
        this.setVisible(true);
        this.validate();
        this.repaint();
        //this.addKeyListener(this);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                int confirmed = JOptionPane.showConfirmDialog(null, "别走嘛亲爱滴再多陪陪我 ~ ", "狠心离开确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, openIcon("Noo_WPS.jpg", 173, 153));
                if (confirmed == JOptionPane.YES_OPTION)
                {
                    clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
                    System.exit(0);
                } else
                {
                    clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
                }
            }
        });
        
        
        
        
    }

    /**
     * Action performed of this class.
     *
     * @param e Action event
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Do action command here
        String command = e.getActionCommand();
        //Object source = e.getSource();
        switch (command)
        {
            case "GoTo":
                System.out.println("YAY");
                this.clickSound(SOUND_TRACKS[2]);
                break;
            case "NO!":
                this.noMissBehavior(randomInt(1, 5));
                break;
            case "Message Feedback":
                clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
                browseWebsite("https://www.wjx.cn/hj/as2bmm7pnu6a1mv7mt54ka.aspx");
                break;
            case "Mail Registeration Address":
                selectMailBoxAddress();
                break;
            default:
                break;
        }
    }

    /**
     * When making adjustment to the menu.
     *
     * @param e
     */
    @Override
    public void itemStateChanged(ItemEvent e)
    {
        Object eventObj = e.getSource();
        if (eventObj.equals(this.mainMenuComboBox))
        {
            int index = this.mainMenuComboBox.getSelectedIndex();
            if (index == 0)
            {
                this.westNorthLabel.setIcon(openIcon(NORTH_WEST_ICON[0], NW_SMALLICON_SCALE, NW_SMALLICON_SCALE));
            } else if (index == 1)
            {
                this.westNorthLabel.setIcon(openIcon(NORTH_WEST_ICON[2], NW_SMALLICON_SCALE, NW_SMALLICON_SCALE));
            }
        }
    }

    /**
     * After user have choose where to go, guide user to where they want.
     *
     * @param index The index user selected.
     */
    public void switchFrame(int index)
    {
        switch (index)
        {
            case 0:
                this.messageLabel.setText("Please select one!");
                break;
            case 1:
                MemoriesRefresher memoriesRefresher = new MemoriesRefresher();
                break;
            case 2:
                BackgroundMusic backgroundMusic = new BackgroundMusic();
                backgroundMusic.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                //backgroundMusic.setVisible(true);
                break;
            case 3:
                ImageList il = new ImageList();
                il.getReturnButton().setVisible(false);
                il.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                il.setVisible(true);
                break;
            case 4:
                MusicConvertTool musicTool = new MusicConvertTool(new BackgroundMusic());
                break;
            case 5:
                CipherWindow cipherWindow = new CipherWindow();
                break;
            case 6:
                try
                {
                    String address = SGC.getMailBoxAddress();
                    if(address==null)
                    {
                        messageLabel.setText("You don't have an mailbox yet, please select one.");
                        selectMailBoxAddress();
                    }else
                    {
                        if(address.isEmpty())
                        {
                           int confirm= JOptionPane.showConfirmDialog(this, "You don't have an mailbox yet, do you want to connect?", "No MailBox ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                           if(confirm == JOptionPane.YES_OPTION)
                           {
                               selectMailBoxAddress();
                           }
                        }else
                        {
                            openApp(new File(SGC.getMailBoxAddress()));
                        }
                    }
                } catch (Exception e)
                {
            Warning warning = new Warning("An exception has occured when opening your mailbox","Please check the address and retry.",e);
            
            SGC.createErrorLog(e);
                }
                break;
            default:
                System.out.println("uncaught type = " + index);
                break;
        }
    }

    public void openApp(File file)
    {
        Desktop desktop = Desktop.getDesktop();
        
        try
        {
            desktop.open(file);
        } catch (IOException ex)
        {
            //Logger.getLogger(JourneyLove.class.getName()).log(Level.SEVERE, null, ex);
            new Warning(ex.toString(),"You may want to check your file, reset your mailbox or try again.",ex);
        }
    }
    
    /**
     * Select the address for the mailBox.
     */
    public void selectMailBoxAddress()
    {
        try
        {
            JFileChooser choose = new JFileChooser(SGC.getDefaultSavingPath());
            choose.setFileFilter(new FileNameExtensionFilter("Your mailBox as an Executable jar file (.jar)", "jar"));
            int confirm = choose.showOpenDialog(this);
            if(confirm == JFileChooser.APPROVE_OPTION)
            {
                SGC.updateMailBoxAddress(choose.getSelectedFile().getAbsolutePath());
                messageLabel.setText("You have a new mailbox, address updated.");
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(JourneyLove.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }
    
    /**
     * This event will be fired for a single click. No backaction are supported.
     *
     * @param e Action event.
     * @see public void mousePressed(MouseEvent e)
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        Object eventObj = e.getSource();
        if (eventObj.equals(this.eastNorthLabel))
        {
            this.messageLabel.setText("oh i am so shy");
            System.err.println("AHHHHHH YOU ARE HERE!");
            this.clickSound(SOUND_TRACKS[0]);
        } else if (eventObj.equals(this.missRadioButton))
        {
            int index = randomInt(1, 13);
            System.out.println(index + " are selected @ missUmethod.");
            missUbehavior(index);
        } else if (eventObj.equals(this.noMissRadioButton))
        {
            this.noMissTimesClick++;
            clickSound("Computer Error2.wav");
            if (noMissTimesClick >= 5)
            {
                if (noMissTimesClick % 7 == 0)
                {
                    clickSound(SAD_SOUND[randomInt(0, SAD_SOUND.length - 1)] + ".wav");
                }
                this.messageLabel.setText("STOP clicking this,I know you are not missing me! you said it" + noMissTimesClick + "times!!!");
            }
        } else if (eventObj.equals(this.majorIconLabel))
        {
            //clickSound(SOUND_TRACKS[0]);
            this.messageLabel.setText("Meow~~");

        } else if (eventObj.equals(this.westSouthLabel))
        {
            westSouthLabel.setIcon(openIcon("LD" + randomInt(1, 9) + ".jpg", 162, 162));
            clickSound(SOUND_TRACKS[0]);
        } else if (eventObj.equals(this.westNorthLabel))
        {
            clickSound("Pc_says_i_love_you.wav");
        } else if (eventObj.equals(this.messageLabel))
        {
            String currentCommand = this.messageLabel.getText();
            if (currentCommand.equals("Bao Bao!!!!!!  \\(^ 3 ^)/ "))
            {
                this.messageLabel.setText("Mua~ You know I'm waiting for you! Bao!");
            } else
            {
                messageLabel.setText("Bao bao~~ \\(^ 3 ^)/ ");
            }
        }
    }

    /**
     * This event will be fired as the mouse is pressed.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        Object eventObj = e.getSource();
        if (eventObj.equals(this.majorIconLabel))
        {
            //this.majorIconLabel.setIcon(this.openAutoScaledIcon(MAJOR_ICON[this.randomInt(0, MAJOR_ICON.length-1)], 610, 400));
            clickSound("Error Alert.wav");
        } else if (eventObj.equals(this.gotoLabel))
        {
            gotoLabel.setIcon(openIcon("goto2.png"));
            clickSound(SOUND_TRACKS[2]);

        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        Object eventObj = e.getSource();
        if (eventObj.equals(this.gotoLabel))
        {
            gotoLabel.setIcon(openIcon("goto.png"));
            switchFrame(mainMenuComboBox.getSelectedIndex());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        Object eventObj = e.getSource();

        if (eventObj.equals(this.missRadioButton))
        {
            this.missRadioButton.setForeground(LovelyColors.SPRING_GREEN);
        } else if (eventObj.equals(this.noMissRadioButton))
        {
            noMissRadioButton.setForeground(Color.RED);
        } else if (eventObj.equals(eastNorthLabel))
        {
            eastNorthLabel.setIcon(openIcon("cuteStill.jpg", 170, 170));
            clickSound("Tiny Button Push.wav");
        }
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        Object eventObj = e.getSource();

        if (eventObj.equals(this.missRadioButton))
        {
            this.missRadioButton.setForeground(Color.BLACK);
        } else if (eventObj.equals(this.noMissRadioButton))
        {
            noMissRadioButton.setForeground(Color.BLACK);
        } else if (eventObj.equals(gotoLabel))
        {
            gotoLabel.setIcon(openIcon("goto.png"));
        } else if (eventObj.equals(eastNorthLabel))
        {
            eastNorthLabel.setIcon(openIcon("BuddingFlower.jpg", 170, 170));
        }
    }

    /**
     * In the right panel when user clicked "yes"for missing cute Johnson, get
     * an random index and fire this medhod.
     *
     * @param behavior The behavior of the main frame.
     */
    private void missUbehavior(int behavior)
    {
        clickSound(HAPPY_MUSIC[randomInt(0, HAPPY_MUSIC.length - 1)] + ".wav");
        switch (behavior)
        {
            case 1:
                MessageFrame msg = new MessageFrame();
                msg.getCentralLabel().setIcon(openIcon("shootHeart.jpg", 450, 450, Image.SCALE_SMOOTH));
                msg.getTitleLabel().setText("I miss you too!");
                break;
            case 2:
                this.messageLabel.setText("mua! >3< love you toooooo I become sweeet!");
                //this.browseWebsite(SWEET_WEBSITES_MUSIC[randomInt(0, SWEET_WEBSITES_MUSIC.length-1)]);
                break;
            case 3:
                this.majorIconLabel.setIcon(openIcon("ZN1BEIZI.jpg"));
                this.messageLabel.setText("miao! zhu ren ai si ni le ");
                clickSound("CatMeow2.wav");
                break;
            case 4:
                JLabel picLabel = new JLabel((openIcon("picinic2.jpg", this.getWidth(), this.getHeight())));
                this.add(picLabel);
                this.messageLabel.setText("Do you like this background^-^");
                break;
            case 5:
                this.majorIconLabel.setIcon(openIcon("hideRain.jpg"));
                this.messageLabel.setText("I will forever stand by your side!");
                break;
            case 6:
                this.majorIconLabel.setIcon(openIcon("hug.png"));
                this.messageLabel.setText("Bao Bao!!!!!!  \\(^ 3 ^)/ ");
                break;
            case 7:
                MessageFrame mgf = new MessageFrame();
                mgf.getCentralLabel().setIcon(openIcon("showHeartToWZN.jpg", 360, 640));
                mgf.pack();
                this.messageLabel.setText(" //// > v < \\\\\\\\");
                break;
            case 8:
                JLabel picLabel2 = new JLabel(openIcon("PinkPicnic.jpg", this.getWidth(), this.getHeight()));
                this.add(picLabel2);
                break;
            case 9:
                JOptionPane.showMessageDialog(null, "我也超级想你ヾ(≧O≦)〃嗷~", "I miss u 2 you must know", JOptionPane.INFORMATION_MESSAGE, openIcon("MSU7.jpg"));
                break;
            case 10:
                JOptionPane.showMessageDialog(null, "Mua~隔着屏幕亲一个（づ￣3￣）づ╭❤～", "Mua Window", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 11:
                int miss = JOptionPane.showConfirmDialog(null, "我想你了，你想我了嘛？", "想你确认", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, openIcon("didUmisme.gif"));
                if (miss == JOptionPane.YES_OPTION)
                {
                    JOptionPane.showMessageDialog(null, "Mua!?(*╯3╰)~~~~~", "亲亲frame", JOptionPane.INFORMATION_MESSAGE, openIcon("LD2.jpg"));
                } else
                {
                    noMissBehavior(3);
                }
                break;
            case 12:
            case 13:
                JOptionPane.showMessageDialog(null, SWEET_SENTENCES[randomInt(0, SWEET_SENTENCES.length - 1)], "Look At Me, Darling", JOptionPane.INFORMATION_MESSAGE, openIcon(MAJOR_ICON[randomInt(0, MAJOR_ICON.length - 1)]));
                break;
            case 50:
                browseWebsite(SWEET_WEBSITES_MUSIC[randomInt(0, SWEET_WEBSITES_MUSIC.length - 1)]);
                messageLabel.setText("Watch a video with me!");
                break;

            default:
                Warning warn = new Warning("RUN OUT @ MISS U METHOD.");
                break;
        }
    }

    /**
     * If my little sweetie choose not to miss me, I don't know what is gonna
     * happen......
     *
     * @param behavior The behaviour of the main menu.
     */
    private void noMissBehavior(int behavior)
    {
        //produce a sound
        this.majorIconLabel.setIcon(openIcon("WhatIsWrong.jpg", 432, 270));
        int index = randomInt(0, SAD_SOUND.length - 1);
        System.out.println(index + " are selected.@ no miss behavior");
        this.clickSound(SAD_SOUND[index] + ".wav");
        switch (behavior)
        {
            case 1:
                this.noMissRadioButton.setIcon(openIcon("smile.png"));
                this.noMissRadioButton.setText("");
                //this.clickSound("Buzz.wav");
                break;
            case 2:
                this.noMissRadioButton.setText("NOT AN OPTION");
                this.noMissRadioButton.setEnabled(false);
                this.messageLabel.setText(":((((((((((");
                this.majorIconLabel.setIcon(openIcon("SoSad.jpg"));
                //clickSound("Cat Scream.wav");
                break;
            case 3:
                this.messageLabel.setText("WHATTTTT????????");
                this.clickSound("Sad Male.wav");
                //this.majorIconLabel.setIcon(openIcon("SoSad.jpg"));
                break;
            case 4:
                this.messageLabel.setText("heng~");
                this.majorIconLabel.setIcon(openIcon("smile.png", 256, 256));
                this.clickSound("haofanni.wav");
                break;
            case 5:
                Warning warning = new Warning("You should not do that!!!");
                warning.getContentPane().setBackground(new Color(102, 255, 102));
                break;
                        //NO
            default:
                Warning oobWarning = new Warning("Out of bounce: Unexpected input @ nomissb");
                throw new UnsupportedOperationException("Out of bounce: Unexpected input");
        }

    }
    
    

    /*Logic Safe Methods*/
     public String[] getInstalledLookAndFeels(){
        infos = javax.swing.UIManager.getInstalledLookAndFeels();
        String[] infoStrings = new String[infos.length];
        
        for (int i = 0; i < infoStrings.length; i++)
        {
            infoStrings[i] = infos[i].getName();
        }
        return infoStrings;
    }
     
     public void setLookAndFeel(int index)
     {
         try
        {
            // TODO add your handling code here:
            UIManager.setLookAndFeel(infos[index].getClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(SetLookAndFeel.class.getName()).log(Level.SEVERE, null, ex);
            messageLabel.setText(ex.toString());
            SGC.createErrorLog(ex);
        }
     }

    /**
     * Open an icon from the package.
     * @param addressURL The address of the imageicon.
     * @return The imageicon.
     * @deprecated Replaced by ImageManager.
     * @see ImageManager
     */
    public ImageIcon openIcon(String addressURL)
    {
        java.net.URL url = getClass().getResource(addressURL);
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(url).getImage());
        return imageIcon;
    }

    /**
     * Open and scale an icon using default scaling algrotism.
     * @param addressURL The address of the image.
     * @param width The width of the image.
     * @param height The height of the image.
     * @return The opened imageicon.
     * @deprecated Replaced by ImageManager.
     * @see journeylove.ImageManager
     */
    public ImageIcon openIcon(String addressURL, int width, int height)
    {
        return this.openIcon(addressURL, width, height, Image.SCALE_DEFAULT);
    }

    /**
     * Open an icon from the package using defined width, height and scaling algrotism.
     * @param addressURL The address of the image.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param imageScale The image scaling algrotism.
     * @return Opened image.
     * @deprecated Replaced by ImageManager.
     * @see ImageManager
     */
    public ImageIcon openIcon(String addressURL, int width, int height, int imageScale)
    {
        java.net.URL url = getClass().getResource(addressURL);
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(width, height, imageScale));
        return imageIcon;
    }

    /**
     * Open and autoscale an icon in the package, it will not damage the 
     * original ratio of the icon.
     * @param addressURL
     * @param maxWidth
     * @param maxHeight
     * @return The autoscaled icon.
     * @deprecated Replaced by imagemanager.
     * @see ImageManager
     */
    public ImageIcon openAutoScaledIcon(String addressURL, int maxWidth, int maxHeight)
    {
        java.net.URL url = getClass().getResource(addressURL);
        ImageIcon originIcon = new ImageIcon(new ImageIcon(url).getImage());
        int height = originIcon.getIconHeight();
        int width = originIcon.getIconWidth();

        if (height > maxHeight || width > maxWidth)
        {
            //feedbackLabel.setText("Image are scaled");
            ///Deal with large image
            while (height > maxHeight || width > maxWidth)
            {
                height /= 2;
                width /= 2;
            }
            ImageIcon icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(height, width, Image.SCALE_DEFAULT));
            return icon;
        } else
        {
            return originIcon;
        }
    }

    
   /**
    * Open an icon that is auto scaled to not greater than maximun but not less than minimun.
    * @deprecated
    * Using algrotism * both side by default ratio=2.
    * maximun cannot equal to minimun.
    * @param addressURL
    * @param maxWidth
    * @param maxHeight
    * @param minWidth
    * @param minHeight
    * @return The auto scaled icon.
    */
    public ImageIcon openAutoScaledIcon(String addressURL, int maxWidth, int maxHeight, int minWidth, int minHeight)
    {

        java.net.URL url = getClass().getResource(addressURL);
        ImageIcon originIcon = new ImageIcon(new ImageIcon(url).getImage());
        int height = originIcon.getIconHeight();
        int width = originIcon.getIconWidth();

        if(maxHeight != minHeight && maxWidth != minWidth)
        {
        if (height > maxHeight || width > maxWidth || height < minHeight || width < minWidth)
        {
            //feedbackLabel.setText("Image are scaled");
            ///Deal with large image

            while (height < minHeight || width < minWidth)
            {
                height *= 2;
                width *= 2;
            }
            while (height > maxHeight || width > maxWidth)
            {
                height /= 2;
                width /= 2;
            }
            ImageIcon icon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(height, width, Image.SCALE_DEFAULT));
            return icon;
        } else
        {
            return originIcon;
        }
        }else
        {
            
            throw new IllegalArgumentException("You cannot, just cannot make max = min." );
        }
    }

    /**
     * When clicked a little sound.
     *
     * @param soundName Name of the sound.
     */
    public void clickSound(String soundName)
    {
//        if(soundEffectClip.isActive())
//        {
//            this.closeMusic();
//        }
        this.openMusic(soundName);
        this.startMusic();
        soundEffectClip.addLineListener((LineEvent e)->{
            LineEvent.Type type = e.getType();
            if(type.equals(LineEvent.Type.STOP))
            {
                soundEffectClip.close();
            }
        });
    }

    /**
     * Open a music within this package
     *
     * @param fileName Name of the music.
     */
    public void openMusic(String fileName)
    {
        try
        {
            AudioInputStream myStream = AudioSystem.getAudioInputStream(getClass().getResource(fileName));
            soundEffectClip = AudioSystem.getClip();
            soundEffectClip.open(myStream);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e)
        {
            //Warning warning = new Warning("Error playing your music!(@open method)");
            //warning.setExceptionString(e.toString());
//            e.printStackTrace();
//            System.err.println("ERR YOUR MUSIC @OPENING");
            Warning warning = new Warning("ERR PLAYING SOUND" , e.toString(),e);
            warning.getContentPane().setBackground(Color.GREEN);
        }

    }

    /**
     * Start a beautiful music.
     */
    public void startMusic()
    {
        try
        {
            soundEffectClip.start();
        } catch (Exception e)
        {
            System.err.println("Error playing your music!(@start method)");
        }
    }

    /**
     * Stop the music and set the position to 0.
     */
    public void stopMusic()
    {
        try
        {
            soundEffectClip.stop();
            soundEffectClip.setFramePosition(0);
        } catch (Exception e)
        {
            System.err.println("Error playing your music!(@ STOPPING)");
        }
    }

    /**
     * Close the music.
     */
    public void closeMusic()
    {
        try
        {
            soundEffectClip.close();
        } catch (Exception e)
        {
            System.err.println("Error closing your music!");
        }
    }

    /**
     * Stop playing but will not set the position to anywhere else.
     */
    public void pauseMusic()
    {
        try
        {
            soundEffectClip.stop();
        } catch (Exception e)
        {
            System.err.println("Error pausing your music!");
            e.printStackTrace();
        }
    }

    /**
     * Browse a specific website.
     *
     * @param url the url of the website.
     */
    public void browseWebsite(String url)
    {
        try
        {
            Desktop desktop = Desktop.getDesktop();
            URI toUrl = new URI(url);
            desktop.browse(toUrl);
        } catch (IOException | URISyntaxException e)
        {
            Warning warning = new Warning("ERROR OPENING WEBSITE","PLEASE RETRY",e);
            //warning.setSuggestion(e.toString());

        }

    }

    /**
     * mute or not.
     *
     * @param audioAllowed true if allow audio, false otherwise.
     */
    public void setAudioAllowed(boolean audioAllowed)
    {
        this.sound = audioAllowed;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
////        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try
//        {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
//            {
//                if ("Nimbus".equals(info.getName()))
//                {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex)
//        {
//            java.util.logging.Logger.getLogger(ImagelistTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex)
//        {
//            java.util.logging.Logger.getLogger(ImagelistTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex)
//        {
//            java.util.logging.Logger.getLogger(ImagelistTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex)
//        {
//            java.util.logging.Logger.getLogger(ImagelistTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>
        
        JourneyLove journeyLove = new JourneyLove();
        journeyLove.addKeyListener(journeyLove);
        // TODO code application logic here
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        System.out.println("is typeded:  " + e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        System.err.println("is pressed" + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        System.out.println("Is released : " + e.getKeyLocation());
    }

}
