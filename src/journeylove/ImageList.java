/*Editor: Johnson Gao

 * Date This Class: May 2019
 * Description Of This Class:This is the playlist of users. Users will be able to edit their own playlist from here.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 * This is the playlist of users. Users will be able to edit their own playlist
 * from here.
 *
 * @author Johnson Gao
 */
public class ImageList extends JFrame implements ActionListener, DocumentListener
{

    //Final:font
    /**
     * A constant decleared for the font on the top.
     */
    static final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);

    /**
     * Default serial version UID.
     */
    public static final long serialVersionUID = 1L;
    final Font MESSAGE_FONT = new Font("Courier New", Font.PLAIN, 20);
    //final LovelyColors LC = new LovelyColors();
    final static SecretGardenConnection SGC = new SecretGardenConnection();
    /**
     * Holds things for the title.
     */
    private JLabel titleLabel;
    /**
     * This button disposes the frame.
     */
    private JButton returnButton, applyButton,chooseFilesButton;
    //Panels declearing seq: N - S - W - E
    /**
     * Mother panel on the north.
     */
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel eastPanel;
    private JPanel centralPanel;
    private SecretGardenConnection conn;
    private JTable imageTable;
    private JScrollPane tablePane;
    private JRadioButton addRadioButton, removeRadioButton, changeRadioButton, swapRadioButton,addBatchRadioButton;
    final static String[] TABLE_HEADERS_STRINGS =
    {
        "#", "Name", "Type", "Description", "Address"
    };
    private JTextField nameField, addressField;
    private JComboBox<String> typeBox, listComboBox;
    final static String[] TYPE_STRING =
    {
        "Local", "Online"
    };
    private JPanel editPanel;
    private ArrayList<DisplayImage> imageList = new ArrayList<>(20);
    private Box operationOptionBox;
    private ButtonGroup optionGroup;
    private JPanel buttonPanel;
    private JLabel feedbackLabel;
    private int swapId1 = 0, swapId2 = 0;
    private JPanel swapPanel;
    private JButton swapButton1, swapButton2, importFileButton;
    private JLabel swapLabel1, swapLabel2;
    private Clip myClip;
    private JButton toolButton;
    private JFileChooser chooser;
    private JTextField descriptionField;
    private JPopupMenu listComboBoxMenu;
    private JMenuItem listComboBoxEditItem, createItem;
    
    final static ImageManager IM = new ImageManager();

    public ImageList()
    {
        /**
         * Constructing sequence: JFrame JLabel JButtom JPanel ADDING ?PACK?
         * Rule: When constructing, call"this",otherwise, no "this"unless
         * necessary.
         */

        /**
         * JFrame.
         */
        super("Image Display List");
        this.setBounds(100/*x align R*/, 100/*y align down*/, 800/*X-WIDTH*/, 750/*Y-Width*/);
        this.getContentPane().setBackground(LovelyColors.GLASS_GALL);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        try
        {
            conn = new SecretGardenConnection();
        } catch (Exception e)
        {
            Warning warning = new Warning("Cannot connect to database, do NOT open two applacations @the same time",
                    "This will be closed soon.");
            this.dispose();
        }

        /**
         * JMenu
         */
        listComboBoxEditItem = new JMenuItem("Manage My Sublists");
        listComboBoxEditItem.addActionListener(this);
        createItem = new JMenuItem("Create New Playlist");
        createItem.addActionListener(this);
        listComboBoxMenu = new JPopupMenu();
        listComboBoxMenu.add(listComboBoxEditItem);
        listComboBoxMenu.add(createItem);
        /**
         * JLabel.
         */
        this.titleLabel = new JLabel("My Playlist");
        titleLabel.setFont(TITLE_FONT);
        feedbackLabel = new JLabel("Here is your play list, you can edit them above.");
        feedbackLabel.setFont(MESSAGE_FONT.deriveFont(10));
        swapLabel1 = new JLabel("--> # ");
        swapLabel1.setFont(MESSAGE_FONT);
        swapLabel2 = new JLabel("--> # ");
        swapLabel2.setFont(MESSAGE_FONT);
        /**
         * JButton.
         */
        this.returnButton = new JButton("return");
        returnButton.addActionListener(this);
        applyButton = new JButton("Confirm");
        applyButton.addActionListener(this);
        applyButton.setOpaque(false);
        applyButton.setToolTipText("Apply any changes to the list.");
        chooseFilesButton = new JButton("Choose Files", IM.openIcon("newFile.png",16,16));
        chooseFilesButton.addActionListener(this);
        chooseFilesButton.setToolTipText("Choose files here.");
        chooseFilesButton.setVisible(false);
        swapButton1 = new JButton("A");
        swapButton1.addActionListener(this);
        swapButton1.setBackground(LovelyColors.MOMO_PINK);
        swapButton1.setForeground(LovelyColors.MOMO_FONTCOLOR);
        swapButton1.setToolTipText("Choose the selected image as one for exchanging.");
        swapButton2 = new JButton("B");
        swapButton2.addActionListener(this);
        swapButton2.setBackground(LovelyColors.MOMO_PINK);
        swapButton2.setForeground(LovelyColors.MOMO_FONTCOLOR);
        swapButton2.setToolTipText("Choose the selected image as another one for exchanging.");
        toolButton = new JButton("Imagelist Tool");
        toolButton.addActionListener(this);
        toolButton.setToolTipText("A tool to back-up , import or export your imagelist.");
        importFileButton = new JButton("Open Your File", IM.openIcon("newFile.png", 16, 16));
        importFileButton.addActionListener(this);
        importFileButton.setToolTipText("Choose a file from your computer.");

        //JTextfield
        nameField = new JTextField();
        nameField.setOpaque(false);
        nameField.setBorder(BorderFactory.createTitledBorder("Name: "));
        nameField.getDocument().addDocumentListener(this);
        nameField.setToolTipText("A name to identify your image.");
        addressField = new JTextField();
        addressField.setOpaque(false);
        addressField.setBorder(BorderFactory.createTitledBorder("Address: "));
        //addressField.setToolTipText("Please select a currect type.");
        addressField.getDocument().addDocumentListener(this);
        addressField.setToolTipText("The address where your image are stored.");
        Box addressBox = Box.createHorizontalBox();
        addressBox.add(addressField);
        addressBox.add(importFileButton);
        descriptionField = new JTextField();
        descriptionField.setOpaque(false);
        descriptionField.setBorder(BorderFactory.createTitledBorder("Something to say: "));
        descriptionField.setToolTipText("Description that will appear on your displayer.");
        descriptionField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ENTER)
                {
                    descriptionField.setText(descriptionField.getText() + "\\n");
                }
            }
        });
        descriptionField.getDocument().addDocumentListener(this);

        //jcombobox
        typeBox = new JComboBox<>(TYPE_STRING);
        typeBox.setOpaque(false);
        typeBox.setBorder(BorderFactory.createTitledBorder("Address Type (Online/Offline) : "));
        typeBox.setBackground(Color.PINK);
        typeBox.setToolTipText("Select the type of your address.");
        typeBox.addItemListener((ItemEvent e) ->
        {
            int index = typeBox.getSelectedIndex();
            importFileButton.setVisible(index == 0);
            clickSound(SoundOracle.WATER_PRESS_3);
        });
        try
        {
            listComboBox = new JComboBox<>(SGC.getImageSublists());
            //listComboBox.setBorder(BorderFactory.createTitledBorder("Choose Your List:"));
            listComboBox.setOpaque(false);
            listComboBox.setToolTipText("<html>Select your playlist."
                    + "<br>Right click to manage</html>");
            listComboBox.setComponentPopupMenu(listComboBoxMenu);            
            listComboBox.addItemListener((ItemEvent e) ->
            {
                refreshTable();
                clickSound(SoundOracle.WATER_PRESS_3);
            });
        } catch (SQLException ex)
        {
            Logger.getLogger(ImageList.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }

        //JTable
        imageTable = new JTable(transferToTable(), TABLE_HEADERS_STRINGS);
        imageTable.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                clickSound(SoundOracle.TINY_BUTTON_SOUND);
            }
            
        });
        formattingTable();
        //JScrollpane
        tablePane = new JScrollPane();
        tablePane.getViewport().add(imageTable);
        tablePane.getViewport().setOpaque(false);
        tablePane.setOpaque(false);

        //JRS
        addRadioButton = new JRadioButton("Add");
        addRadioButton.setOpaque(false);
        addRadioButton.addActionListener(this);
        addRadioButton.setToolTipText("Add a new image into your list.");
        addBatchRadioButton = new JRadioButton("Add In Batch");
        addBatchRadioButton.setOpaque(false);
        addBatchRadioButton.addActionListener(this);
        addBatchRadioButton.setToolTipText("Add images in batch in your list.");
        removeRadioButton = new JRadioButton("Remove");
        removeRadioButton.setOpaque(false);
        removeRadioButton.addActionListener(this);
        removeRadioButton.setToolTipText("Remove selected image from your list.");
        changeRadioButton = new JRadioButton("Change");
        changeRadioButton.setOpaque(false);
        changeRadioButton.addActionListener(this);
        changeRadioButton.setToolTipText("Update selected image in your list with newly provided information.");
        swapRadioButton = new JRadioButton("Swap");
        swapRadioButton.setOpaque(false);
        swapRadioButton.addActionListener(this);
        swapRadioButton.setToolTipText("Exchange the positions of two images in the list.");
        //swapButton1.setToolTipText("Choose the currently selected image in your list");

        operationOptionBox = Box.createHorizontalBox();
        operationOptionBox.setBorder(BorderFactory.createTitledBorder("Editing Options"));
        operationOptionBox.setOpaque(false);
        operationOptionBox.add(addRadioButton);
        operationOptionBox.add(addBatchRadioButton);
        operationOptionBox.add(removeRadioButton);
        operationOptionBox.add(changeRadioButton);
        operationOptionBox.add(swapRadioButton);
        
        optionGroup = new ButtonGroup();
        optionGroup.add(addRadioButton);
        optionGroup.add(addBatchRadioButton);
        optionGroup.add(removeRadioButton);
        optionGroup.add(changeRadioButton);
        optionGroup.add(swapRadioButton);

        /**
         * JPanel.
         */
        this.editPanel = new JPanel(new GridLayout(6, 1, 4, 0));
        editPanel.add(operationOptionBox);
        editPanel.add(nameField);
        editPanel.add(addressBox);
        editPanel.add(descriptionField);
        editPanel.add(typeBox);
        //editPanel.add(chooseFilesButton);
        editPanel.setOpaque(false);
        
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(applyButton);
        buttonPanel.add(chooseFilesButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(toolButton);
        buttonPanel.setOpaque(false);
        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(listComboBox, BorderLayout.NORTH);
        northPanel.add(titleLabel, SwingConstants.CENTER);
        northPanel.setComponentPopupMenu(listComboBoxMenu);
        northPanel.setOpaque(false);
        this.centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(tablePane, BorderLayout.CENTER);
        centralPanel.setOpaque(false);
        this.southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.add(feedbackLabel, BorderLayout.NORTH);
        southPanel.add(editPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        swapPanel = new JPanel(new GridBagLayout());
        swapPanel.add(swapButton1);
        swapPanel.add(swapLabel1);
        swapPanel.add(swapButton2);
        swapPanel.add(swapLabel2);
        swapPanel.setOpaque(false);
        swapPanel.setVisible(false);
        editPanel.add(swapPanel);

        /**
         * Add components and finalize frame.
         */
        this.add(northPanel, BorderLayout.NORTH);
        this.add(centralPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
        //this.setVisible(true);
        chooser = new JFileChooser();
        envalidateOperation();
        //singlUseEntry();
        
    }

    /**
     * This method will be attempting to read data from the DB.
     *
     * @return The stored data in ImageList.
     */
    public ArrayList<DisplayImage> getImages()
    {
        int index = listComboBox.getSelectedIndex();
        try
        {
            if (index == 0)
            {
                this.imageList = conn.getDisplayImage();
            } else
            {
                this.imageList = conn.getDisplayImage(SecretGardenConnection.IMAGES_SUBLIST_PREFIX + listComboBox.getItemAt(index));
            }
            
        } catch (Exception e)
        {
            Warning warning = new Warning(e.toString());
            System.out.println("journeylove.ImageList.getImages()");
        }
        return imageList;
    }

    /**
     * This transfer the array data to an 2d array for the table.
     *
     * @return
     */
    public Object[][] transferToTable()
    {
        Object[][] tableData = new Object[getImages().size()][TABLE_HEADERS_STRINGS.length];
        for (int i = 0; i < imageList.size(); i++)
        {
            tableData[i][0] = i + 1;
            tableData[i][1] = imageList.get(i).getName();
            tableData[i][2] = imageList.get(i).getTypeAsString();
            tableData[i][3] = imageList.get(i).getDescription();
            tableData[i][4] = imageList.get(i).getUrlAsString();
        }
        return tableData;
    }

    /**
     * This formats the table. Every time user refresh it will be formatted
     * again.
     */
    public void formattingTable()
    {
        imageTable.getTableHeader().setBackground(LovelyColors.BEWITCHED_TREE);
        imageTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        //imageTable.getColumnModel().getColumn(1).setPreferredWidth(70);
    }

    /**
     * The action ferformed for this class.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source.equals(returnButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            this.dispose();
        } else if (source.equals(addRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            envalidateOperation();
            feedbackLabel.setText("Enter information of the new image, click \"Apply\"");
            clearField();
        }else if(source.equals(addBatchRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            envalidateOperation();
            feedbackLabel.setText("Please click \"choose files\",  press \"ctrl\" to select multiple files.");
        }
        else if (source.equals(removeRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            envalidateOperation();
            feedbackLabel.setText("Select what you want to remove, click \"apply\"");
            clearField();
        } else if (source.equals(swapRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            envalidateOperation();
            clearField();
        } else if (source.equals(changeRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            envalidateOperation();
            feedbackLabel.setText("Select what you want to change, enter alternative info");
            clearField();
        } else if (source.equals(applyButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            if (addRadioButton.isSelected())
            {
                addIntoImageList();
            } else if (removeRadioButton.isSelected())
            {
                removeFromImageList();
            } else if (changeRadioButton.isSelected())
            {
                updateImageList();
            } else if (swapRadioButton.isSelected())
            {
                swapImageList();
            }
            
        } else if (source.equals(swapButton1))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            try
            {
                this.swapId1 = imageList.get(imageTable.getSelectedRow()).getId();
                swapLabel1.setText("--> # " + imageTable.getValueAt(imageTable.getSelectedRow(), 0));
            } catch (ArrayIndexOutOfBoundsException ex)
            {
                feedbackLabel.setText("Please select a member.");
            }
        } else if (source.equals(swapButton2))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            try
            {
                swapId2 = imageList.get(imageTable.getSelectedRow()).getId();
                swapLabel2.setText("--> # " + imageTable.getValueAt(imageTable.getSelectedRow(), 0));
            } catch (ArrayIndexOutOfBoundsException aioobe)
            {
                feedbackLabel.setText("Please select a member.");
            }
        } else if (source.equals(toolButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            ImagelistTool imagelistTool = new ImagelistTool(this);
        } else if (source.equals(importFileButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            try
            {
                int confirmed = chooser.showOpenDialog(null);
                if (confirmed == JFileChooser.APPROVE_OPTION)
                {
                    addressField.setText(chooser.getSelectedFile().getAbsolutePath());
                    //applyButton.dispatchEvent(new ActionEvent(applyButton, WIDTH, "apply"));
                }
            } catch (Exception ex)
            {
                feedbackLabel.setText("Cannot open your file");
            }
            
        } else if (source.equals(listComboBoxEditItem))
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            ManagePlaylist managePlaylist = new ManagePlaylist();
        } else if (source.equals(createItem))
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            CreatePlaylist cpl = new CreatePlaylist();
        }else if(source.equals(chooseFilesButton))
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            addBatch();
        }
        
    }

    /**
     * Clear all the fields.
     */
    public void clearField()
    {
        nameField.setText("");
        addressField.setText("");
        descriptionField.setText("");
    }

    /**
     * Automatically updates the editing panel what should be visiable and what
     * should not.
     */
    public void envalidateOperation()
    {
        boolean addChangeGroup = addRadioButton.isSelected() || changeRadioButton.isSelected();
        nameField.setVisible(addChangeGroup);
        addressField.setVisible(addChangeGroup);
        typeBox.setVisible(addChangeGroup);
        descriptionField.setVisible(addChangeGroup);
        swapPanel.setVisible(swapRadioButton.isSelected());
        importFileButton.setVisible(addChangeGroup && typeBox.getSelectedIndex() == 0);
        chooseFilesButton.setVisible(addBatchRadioButton.isSelected());
        applyButton.setVisible(!addBatchRadioButton.isSelected());
    }

    /**
     * get the jtable of the image list.
     *
     * @return The table of the user.
     */
    public JTable getImageTable()
    {
        return imageTable;
    }

    /**
     * Set the table.
     *
     * @param imageTable
     */
    public void setImageTable(JTable imageTable)
    {
        this.imageTable = imageTable;
    }

    /**
     * Refresh the table with new data.
     */
    public void refreshTable()
    {
        //JTable soulTable = new JTable(transferToTable(), TABLE_HEADERS_STRINGS);
        DefaultTableModel model = new DefaultTableModel(transferToTable(), TABLE_HEADERS_STRINGS);
        imageTable.setModel(model);
        formattingTable();
    }

    /**
     * Received text from the field and update them into tabel.
     */
    public void addIntoImageList()
    {
        String name = nameField.getText();
        String url = addressField.getText().replace("\\", "/").replace("\"", "");//Replace illeagl chars.
        int type = typeBox.getSelectedIndex() + 2;//Purse type
        String description = descriptionField.getText();
        if (!url.isEmpty())
        {
            if (name.isEmpty())
            {
                name = getFileName(addressField.getText());
            }
            try
            {
                if (listComboBox.getSelectedIndex() == 0)
                {
                    SGC.insertImageIntoList(new DisplayImage(name, url, type, Randomizer.randomInt(1000, 9999), description));
                } else
                {
                    SGC.insertIntoImageSublist(new DisplayImage(name, url, type, Randomizer.randomInt(1000, 9999), description), listComboBox.getItemAt(listComboBox.getSelectedIndex()));
                }
                
                clearField();
                clickSound(SoundOracle.UI_DINGDONG);
            } catch (Exception e)
            {
                Warning warning = new Warning("Sorry we cannot add new image in because of " + e.toString());
            }
        } else
        {
            feedbackLabel.setText("Address is an required field.");
        }
        refreshTable();
        
    }

    /**
     * Read from user selection and fields, remove from the imagelist.
     */
    public void removeFromImageList()
    {
        try
        {
            int id = imageList.get(imageTable.getSelectedRow()).getId();
            
            SGC.removeFromImageDisplayList(id, getSelectedList());
            refreshTable();
            clickSound(SoundOracle.UI_DINGDONG);
        } catch (SQLException e)
        {
            Warning warning = new Warning("Failed to connect to DB. Detail: " + e.toString());
        } catch (ArrayIndexOutOfBoundsException aioobe)
        {
            feedbackLabel.setText("Select one to remove please");
        }
    }

    /**
     * Read user selection and updates the imagelisr.
     */
    public void updateImageList()
    {
        int id;
        String newName = nameField.getText();
        String newAddress = addressField.getText().replace("\\", "/").replace("\"", "");
        String description = descriptionField.getText();
        int newType = typeBox.getSelectedIndex() + 2;
        DisplayImage imageChosen = imageList.get(imageTable.getSelectedRow());
        if (newName.isEmpty())
        {
            newName = imageChosen.getName();
        }
        if (newAddress.isEmpty())
        {
            newAddress = imageChosen.getUrlAsString();
        }
        if (description.isEmpty())
        {
            description = imageChosen.getDescription();
        }
        
        try
        {
            id = imageList.get(imageTable.getSelectedRow()).getId();
            SGC.updateImageDisplayList(id, new DisplayImage(newName, newAddress, newType, id, description), getSelectedList());
            refreshTable();
            clearField();
            clickSound(SoundOracle.UI_DINGDONG);
        } catch (SQLException e)
        {
            Warning warning = new Warning("Sorry cannot connect to DB " + e.toString());
        } catch (ArrayIndexOutOfBoundsException aioobe)
        {
            feedbackLabel.setText("Please select a member!!!!!");
        }
        
    }

    /**
     * @deprecated Replaced by ImagelistTool
     */
    public void singlUseEntry()
    {
        for (int i = 0; i < ImageURLList.TEXT_QZONE1.length; i++)
        {
            this.nameField.setText("5.20de回忆@欢乐海岸 " + (i + 1));
            addressField.setText(ImageURLList.TEXT_QZONE1[i]);
            typeBox.setSelectedIndex(1);
            addIntoImageList();
        }
    }

    /**
     * Read from user selection and exchange two members in the imagelist.
     */
    public void swapImageList()
    {
        if (swapId1 != 0 && swapId2 != 0 && swapId1 != swapId2)
        {
            try
            {
                SGC.swapImageDisplayList(swapId1, swapId2, getSelectedList());
                swapId1 = 0;
                swapId2 = 0;
                swapLabel1.setText("--> # ");
                swapLabel2.setText("--> # ");
                refreshTable();
                clickSound(SoundOracle.UI_DINGDONG);
            } catch (Exception e)
            {
                Warning warning = new Warning("Ho nooo exception occurs " + e.toString());
            }
        } else
        {
            feedbackLabel.setText("Please select for A and B.");
        }
    }

    public String getSelectedList()
    {
        String table;
        int index = listComboBox.getSelectedIndex();
        if (index == 0)
        {
            table = SecretGardenConnection.ALL_IMAGES_TABLE_NAME;
        } else
        {
            table = SecretGardenConnection.IMAGES_SUBLIST_PREFIX + listComboBox.getItemAt(index);
        }
        return table;
    }

    public void addBatch()
    {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\app\\Pictures");
        fileChooser.setDialogTitle("Add Images In Batch");
        fileChooser.setMultiSelectionEnabled(true);
        int confirmed = fileChooser.showOpenDialog(this);
        if (confirmed == JFileChooser.APPROVE_OPTION)
        {
            addBatch(fileChooser.getSelectedFiles());
        }
        
    }
    
    public void addBatch(File[] files)
    {
        DisplayImage[] displayImages = new DisplayImage[files.length];
        
        try
        {
            for (int i = 0; i < files.length; i++)
            {
                DisplayImage tempImg = new DisplayImage(files[i].getName(), files[i].getAbsolutePath(), DisplayImage.TYPE_LOCAL, Randomizer.randomInt(1000, 9999));
                tempImg.setDescription("");
                displayImages[i] = tempImg;
            }
            conn.insertImagesInBatch(displayImages, getSelectedList());
        } catch (Exception ex)
        {
            //Logger.getLogger(ImageList.class.getName()).log(Level.SEVERE, null, ex);
            feedbackLabel.setText("Cannot add images since" + ex.getMessage());
        }
        
        feedbackLabel.setText("Images have successfully inserted in your list.");
        refreshTable();
    
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
            
            myClip.addLineListener((LineEvent e) ->
            {
                LineEvent.Type type = e.getType();
                if (type.equals(LineEvent.Type.STOP))
                {
                    myClip.close();
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex)
        {
            Warning warning = new Warning("Cannot open sound  " + ex.toString());
        }
        myClip.start();
        
    }

    /**
     * Get the name of the file from the address user input.
     *
     * @param address The local address of the file.
     * @return The readed name.
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
                name = "Online Image";
            }
            
        } catch (Exception e)
        {
            feedbackLabel.setText("Sorry, we cannot assign name to the file you provided.");
        }
        return name;
    }

    /**
     * See if this list is empty.
     *
     * @return true of this table is empty, false otherwise.
     */
    public boolean isEmpty()
    {
        return imageList.isEmpty();
    }

    /**
     * Get the return button of this class and make adjustment to it.
     *
     * @return The return button of this class.
     */
    public JButton getReturnButton()
    {
        return returnButton;
    }

    /**
     * Test main method.
     *
     * @param args Argument
     */
    public static void main(String[] args)
    {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(ImagelistTool.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        java.awt.EventQueue.invokeLater(() ->
        {
            ImageList standardFrame = new ImageList();
            standardFrame.setVisible(true);
            standardFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        });
        
    }

    /**
     * Gives notification that there was an insert into the document. The range
     * given by the DocumentEvent bounds the freshly inserted region.
     *
     * @param e the document event
     */
    @Override
    public void insertUpdate(DocumentEvent e)
    {
        clickSound(SoundOracle.PHONE_TYPING_SOUND);
    }

    /**
     * Gives notification that a portion of the document has been removed. The
     * range is given in terms of what the view last saw (that is, before
     * updating sticky positions).
     *
     * @param e the document event
     */
    @Override
    public void removeUpdate(DocumentEvent e)
    {
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     *
     * @param e the document event
     */
    @Override
    public void changedUpdate(DocumentEvent e)
    {
    }
    
}
