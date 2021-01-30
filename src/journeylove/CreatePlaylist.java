/*Editor: Johnson Gao
 * Date This Class: June 2019
 * Description Of This Class: This allows users to create their own playlist and first initalize it.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import static journeylove.ImageList.TABLE_HEADERS_STRINGS;
//import static journeylove.ImageList.SGC;
import static journeylove.ImageList.IM;

/**
 * This allows users to create their own playlist and first initalize it.
 *
 * @author Johnson Gao
 */
public final class CreatePlaylist extends JFrame implements ActionListener, DocumentListener
{

    private static final long serialVersionUID = 1L;
    final Font MESSAGE_FONT = new Font("Courier New", Font.PLAIN, 20);
    //Final:font
    /**
     * A constant decleared for the font on the top.
     */
    final static Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);

    /**
     * Holds things for the title.
     */
    private JLabel titleLabel;
    /**
     * This button disposes the frame.
     */
    private JButton returnButton;
    //Panels declearing seq: N - S - W - E
    /**
     * Mother panel on the north.
     */
    private JPanel titlePanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel swapPanel;
    private JPanel centralPanel;
    private JLabel nameLabel, messageLabel, swapLabel1, swapLabel2;
    private JLabel toSelectFromLabel, downLabel;
    private JComboBox<String> fromListComboBox, typeComboBox;
    private JTextField nameField, urlField, listNameField, descriptionField;
    private JTable fromTable, toTable;
    private JScrollPane fromTablePane, toTablePane;
    //private ImageList imageList;
    private ArrayList<DisplayImage> imageArrayList, newDisplayImagesList;
    private JRadioButton addRadioButton, removeRadioButton, updateRadioButton, swapRadioButton;
    private ButtonGroup operationGroup;
    private Box buttonBox;
    private JButton saveButton, applyButton, chooseFileButton, swapButton1, swapButton2;
    //private JSplitPane fromSplitPane, toSplitPane;
    private JPopupMenu listMenu;
    private JMenuItem manageItem;
    private JPanel centralSelectionPanel;
    private Box urlBox;
    private int swapIndex1 = -1, swapIndex2 = -1;

    /**
     * Allow people to create new playlist.
     */
    public CreatePlaylist()
    {
        /**
         * JFrame.
         */
        super("Create PlayList");
        this.setBounds(100/*x align R*/, 100/*y align down*/, 900/*X-WIDTH*/, 750/*Y-Width*/);
        this.getContentPane().setBackground(Color.PINK);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        // this.imageList = imageList;
        newDisplayImagesList = new ArrayList<>();

        /**
         * JLabel.
         */
        this.titleLabel = new JLabel("Create New Playlist");
        titleLabel.setFont(TITLE_FONT);
        nameLabel = new JLabel("Name:");
        toSelectFromLabel = new JLabel("To Select From");
        downLabel = new JLabel(IM.openIcon("DownDarkBlue.png"));
        downLabel.setHorizontalAlignment(JLabel.CENTER);
        downLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        downLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                downLabel.setIcon(IM.openIcon("DownBlue.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                downLabel.setIcon(IM.openIcon("DownDarkBlue.png"));//here take final action.
                copyDownImage();
                clickSound(SoundOracle.WATER_PRESS_3);
            }
        });
        messageLabel = new JLabel("Welcome to use play list maker!");
        messageLabel.setFont(MESSAGE_FONT);
        swapLabel1 = new JLabel("--> #");
        swapLabel1.setFont(MESSAGE_FONT);
        swapLabel2 = new JLabel("--> #");
        swapLabel2.setFont(MESSAGE_FONT);
        /**
         * JButton.
         */
        this.returnButton = new JButton("return");
        returnButton.addActionListener(this);
        applyButton = new JButton("Apply");
        applyButton.addActionListener(this);
        saveButton = new JButton("Save", IM.openIcon("V.png", 16, 16));
        saveButton.addActionListener(this);
        chooseFileButton = new JButton("Select Your File", IM.openIcon("newFile.png", 16, 16));
        chooseFileButton.addActionListener(this);
        swapButton1 = new JButton("A");
        swapButton1.addActionListener(this);
        swapButton2 = new JButton("B");
        swapButton2.addActionListener(this);

        /*
        JRadioButtons
         */
        //<editor-fold defaultstate="collapsed" desc="JRadioButton">
        addRadioButton = new JRadioButton("Add");
        addRadioButton.addActionListener(this);
        addRadioButton.setOpaque(false);
        removeRadioButton = new JRadioButton("Remove");
        removeRadioButton.setOpaque(false);
        removeRadioButton.addActionListener(this);
        updateRadioButton = new JRadioButton("Update");
        updateRadioButton.setOpaque(false);
        updateRadioButton.addActionListener(this);
        swapRadioButton = new JRadioButton("Swap");
        swapRadioButton.setOpaque(false);
        swapRadioButton.addActionListener(this);
        operationGroup = new ButtonGroup();
        operationGroup.add(addRadioButton);
        operationGroup.add(removeRadioButton);
        operationGroup.add(updateRadioButton);
        operationGroup.add(swapRadioButton);
        //</editor-fold>

        /*
        JPopupMenu
         */
        manageItem = new JMenuItem("Manage Your Lists");
        manageItem.addActionListener(this);
        listMenu = new JPopupMenu();
        listMenu.add(manageItem);
        /*
        jtextfield
         */
        listNameField = new JTextField(30);
        listNameField.setOpaque(false);
        listNameField.setBorder(BorderFactory.createTitledBorder("Name Of Your New List: "));
        listNameField.getDocument().addDocumentListener(this);
        nameField = new JTextField(20);
        nameField.setOpaque(false);
        nameField.setBorder(BorderFactory.createTitledBorder("Name"));
        nameField.getDocument().addDocumentListener(this);

        urlField = new JTextField(50);
        urlField.setOpaque(false);
        urlField.setBorder(BorderFactory.createTitledBorder("URL"));
        urlField.getDocument().addDocumentListener(this);

        descriptionField = new JTextField(50);
        descriptionField.setOpaque(false);
        descriptionField.setBorder(BorderFactory.createTitledBorder("Something To Say"));
        descriptionField.getDocument().addDocumentListener(this);

        /*
        JComboBox
         */
        try
        {
            loadLists();
            typeComboBox = new JComboBox<>(ImageList.TYPE_STRING);
            typeComboBox.setBorder(BorderFactory.createTitledBorder("Type: "));
            typeComboBox.setOpaque(false);
            typeComboBox.addActionListener((ActionEvent e) ->
            {
                clickSound(SoundOracle.WATER_PRESS_4);
            });
            typeComboBox.addItemListener((ItemEvent e) ->
            {
                chooseFileButton.setVisible(typeComboBox.getSelectedIndex() + 2 == DisplayImage.TYPE_LOCAL);
            });
        } catch (SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(CreatePlaylist.class.getName()).log(Level.SEVERE, null, ex);
            Warning warning = new Warning("Cannot connect to db!", "", ex);
        }

        /*
        JTabel
         */
        fromTable = new JTable(transferToTable(), TABLE_HEADERS_STRINGS);
        fromTable.getTableHeader().setBackground(LovelyColors.MOMO_PINK);
        fromTable.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                clickSound(SoundOracle.TINY_BUTTON_SOUND);
            }
        });
        toTable = new JTable(transferToTable(newDisplayImagesList), TABLE_HEADERS_STRINGS);
        toTable.getTableHeader().setBackground(LovelyColors.MOMO_PINK);
        toTable.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                System.out.println(".keyPressed()");
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_BACK_SPACE)
                {
                    System.out.println("Selected index = " + toTable.getSelectedRow());
                    remove();
                    clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
                }
            }
        });
        toTable.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                clickSound(SoundOracle.TINY_BUTTON_SOUND);
            }
        });

        /*
        JScrollPane
         */
        fromTablePane = new JScrollPane();
        fromTablePane.getViewport().add(fromTable);
        fromTablePane.getViewport().setOpaque(false);
        fromTablePane.setOpaque(false);
        toTablePane = new JScrollPane();
        toTablePane.getViewport().add(toTable);
        toTablePane.getViewport().setOpaque(false);
        toTablePane.setOpaque(false);

        /**
         * JPanel.
         */
        //Box listNameBox = Box.createHorizontalBox();
        Box toSelectFromBox = Box.createHorizontalBox();
        Box northBox = Box.createVerticalBox();
        Box operationBox = Box.createHorizontalBox();
        swapPanel = new JPanel(new GridLayout(1, 4));
        urlBox = Box.createHorizontalBox();
        centralSelectionPanel = new JPanel(new GridLayout(3, 1));
        buttonBox = Box.createHorizontalBox();
//        listNameBox.add(nameLabel);
//        listNameBox.add(listNameField);
//        listNameBox.setOpaque(false);
        urlBox.add(urlField);
        urlBox.add(chooseFileButton);
        urlBox.setOpaque(false);
        toSelectFromBox.add(toSelectFromLabel);
        toSelectFromBox.add(fromListComboBox);
        toSelectFromBox.setOpaque(false);
        northBox.add(listNameField);
        northBox.add(toSelectFromBox);
        northBox.setOpaque(false);
        centralSelectionPanel.add(fromTablePane);
        centralSelectionPanel.add(downLabel);
        centralSelectionPanel.add(toTablePane);
        centralSelectionPanel.setOpaque(false);
        swapPanel.add(swapButton1);
        swapPanel.add(swapLabel1);
        swapPanel.add(swapButton2);
        swapPanel.add(swapLabel2);
        swapPanel.setOpaque(false);
        //swapPanel.setVisible(rootPaneCheckingEnabled);
        operationBox.add(addRadioButton);
        operationBox.add(removeRadioButton);
        operationBox.add(updateRadioButton);
        operationBox.add(swapRadioButton);

        operationBox.setBorder(BorderFactory.createTitledBorder("To Edit Your New List:"));
        operationBox.setOpaque(false);
        //operationBox.setBorder(BorderFactory.createTitledBorder("To Edit Sublist:"));
        buttonBox.add(saveButton);
        buttonBox.add(returnButton);
        buttonBox.setOpaque(false);

//        fromSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, northBox, fromTablePane);
//        toSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, toTab, northPanel)
        //entryPanel = new JPanel(new GridLayout(5, 1));
        Box entryBox = Box.createVerticalBox();
        entryBox.add(nameField);
        entryBox.add(urlBox);
        entryBox.add(descriptionField);
        entryBox.add(typeComboBox);
        entryBox.add(applyButton);
        entryBox.add(swapPanel);
        entryBox.setOpaque(false);

        titlePanel = new JPanel();
        titlePanel.add(titleLabel, SwingConstants.CENTER);
        titlePanel.setOpaque(false);
        this.northPanel = new JPanel(new BorderLayout());
        northPanel.add(titlePanel, BorderLayout.NORTH);
        northPanel.add(northBox, BorderLayout.CENTER);
        northPanel.setComponentPopupMenu(listMenu);
        northPanel.setOpaque(false);
        this.centralPanel = new JPanel(new BorderLayout());
        //centralPanel.add(fromTablePane,BorderLayout.NORTH);
        centralPanel.add(centralSelectionPanel, BorderLayout.CENTER);
        centralPanel.add(messageLabel, BorderLayout.SOUTH);
        centralPanel.setOpaque(false);
        this.southPanel = new JPanel(new BorderLayout());
        southPanel.add(operationBox, BorderLayout.NORTH);
        southPanel.add(entryBox, BorderLayout.CENTER);
        southPanel.add(buttonBox, BorderLayout.SOUTH, SwingConstants.CENTER);
        southPanel.setOpaque(false);
        //southPanel.add(returnButton);
        /**
         * Add components and finalize frame.
         */
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        add(centralPanel, BorderLayout.CENTER);
        this.setVisible(true);
        //this.add(listMenu);
//        downLabel.setVisible(false);
//        fromTablePane.setVisible(false);
//        fromListComboBox.setVisible(false);
        envalidateOperation();

    }

    /**
     * Get the imagelist of the current selected list.
     *
     * @return The arraylist of selected list.
     */
    public ArrayList<DisplayImage> getImages()
    {
        int index = fromListComboBox.getSelectedIndex();
        try (SecretGardenConnection database = SecretGardenConnection.getDefaultInstance())
        {
            if (index == 0)
            {
                this.imageArrayList = database.getDisplayImage();
            } else
            {
                this.imageArrayList = database.getDisplayImage(SecretGardenConnection.IMAGES_SUBLIST_PREFIX + fromListComboBox.getItemAt(index));
            }

        } catch (SQLException | ClassNotFoundException e)
        {
            Warning warning = new Warning(e.toString(), "", e);
            System.out.println("journeylove.ImageList.getImages()");

        }
        return imageArrayList;
    }

    /**
     * Invoked when user click "edit"buttom.
     */
    public void applyEdit()
    {
        if (addRadioButton.isSelected())
        {
            System.out.println("journeylove.CreatePlaylist.applyEdit() : Add");
            add();
        } else if (removeRadioButton.isSelected())
        {
            remove();
        } else if (updateRadioButton.isSelected())
        {
            update();
        } else if (swapRadioButton.isSelected())
        {
            swap(swapIndex1, swapIndex2);

        } else if (updateRadioButton.isSelected())
        {
            update();
        }
    }

    /**
     * Load the list.
     *
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public final void loadLists() throws SQLException, ClassNotFoundException
    {
        SecretGardenConnection SGC = SecretGardenConnection.getDefaultInstance();
        fromListComboBox = new JComboBox<>(SGC.getImageSublists());
        // fromListComboBox.setBorder(BorderFactory.createTitledBorder("From: "));
        fromListComboBox.setOpaque(false);
        fromListComboBox.addItemListener((ItemEvent e) ->
        {
            refreshTable(getImages(), fromTable);
        });
        fromListComboBox.addActionListener((ActionEvent e) ->
        {
            clickSound(SoundOracle.WATER_PRESS_4);
        });
        fromListComboBox.setComponentPopupMenu(listMenu);
    }

    /**
     * Clear all the field of the frame.
     */
    public void clearAllFields()
    {
        nameField.setText("");
        urlField.setText("");
        descriptionField.setText("");
    }

    /**
     * This transfer the array data to an 2d array for the table.
     *
     * @return
     */
    public Object[][] transferToTable()
    {
        return transferToTable(getImages());
    }

    /**
     * This transfer the array data to an 2d array for the table.
     *
     * @param displayImages
     * @return
     */
    public Object[][] transferToTable(ArrayList<DisplayImage> displayImages)
    {
        Object[][] tableData = new Object[displayImages.size()][TABLE_HEADERS_STRINGS.length];
        for (int i = 0; i < displayImages.size(); i++)
        {
            tableData[i][0] = i + 1;
            tableData[i][1] = displayImages.get(i).getName();
            tableData[i][2] = displayImages.get(i).getTypeAsString();
            tableData[i][3] = displayImages.get(i).getDescription();
            tableData[i][4] = displayImages.get(i).getUrlAsString();
        }
        return tableData;
    }

    /**
     * Add a new member into the list.
     */
    public void add()
    {
        String name = nameField.getText();
        String url = urlField.getText();
        String description = descriptionField.getText();
        int type = typeComboBox.getSelectedIndex() + 2;

        if (!url.isEmpty())
        {
            if (name.isEmpty())
            {
                if (type == DisplayImage.TYPE_LOCAL)
                {
                    name = url.substring(url.lastIndexOf("\\"), url.lastIndexOf("."));
                } else
                {
                    name = "online image";
                }
            }
            newDisplayImagesList.add(new DisplayImage(name, url, type, Randomizer.randomInt(1000, 9999), description));
            refreshTable(newDisplayImagesList, toTable);
            messageLabel.setText("Added into your list!");
            clickSound(SoundOracle.UI_DINGDONG);
            clearAllFields();

        } else
        {
            messageLabel.setText("Please Enter URL!");
        }
    }

    /**
     * Remove a member from selected list.
     */
    public void remove()
    {
        int index = toTable.getSelectedRow();
        if (index != -1)
        {
            newDisplayImagesList.remove(index);
            refreshTable(newDisplayImagesList, toTable);
            messageLabel.setText("Removed!");
            clearAllFields();
        } else
        {
            this.messageLabel.setText("Please select one to remove from downside table.");
        }
    }

    /**
     * Update the table underneeth. This won't contact database.
     */
    public void update()
    {
        String name = nameField.getText();
        String url = urlField.getText();
        String description = descriptionField.getText();
        int type = typeComboBox.getSelectedIndex() + 2;
        int index = toTable.getSelectedRow();

        if (name.isEmpty())
        {
            name = newDisplayImagesList.get(index).getName();
        }
        if (url.isEmpty())
        {
            url = newDisplayImagesList.get(index).getUrlAsString();
        }
        if (description.isEmpty())
        {
            description = newDisplayImagesList.get(index).getDescription();
        }

        DisplayImage displayImage = new DisplayImage(name, url, type, Randomizer.randomInt(1000, 9999), description);

        if (index != -1)
        {
            newDisplayImagesList.remove(index);
            newDisplayImagesList.add(index, displayImage);
            refreshTable(newDisplayImagesList, toTable);
            clickSound(SoundOracle.UI_DINGDONG);
            clearAllFields();
        } else
        {
            messageLabel.setText("Please select a row from the table underneath.");
        }

    }

    /**
     * Switch the position of two members in the new list.
     *
     * @param index1 Member 1.
     * @param index2 Member 2.
     */
    public void swap(int index1, int index2)
    {
        if (index1 != -1 && index2 != -1 && index2 != index1)
        {
            int capacity = newDisplayImagesList.size();
//            if(index2 > index1)
//            {
//                index2--;
//            }else 
//            {
//                index1--;
//            }
//            DisplayImage toImage2 = newDisplayImagesList.remove(index2);
//            DisplayImage toImage1 = newDisplayImagesList.remove(index1);
//            System.out.println("Index 1 = " + index1 + " Index 2 = " + index2);
//            System.out.println("Capacity of arraylist = " +  capacity);
//            newDisplayImagesList.ensureCapacity(capacity);
//            newDisplayImagesList.add(index2, toImage1);
//            newDisplayImagesList.add(index1, toImage2);
            Collections.swap(newDisplayImagesList, index1, index2);

            messageLabel.setText("Swamped!");

            this.refreshTable(newDisplayImagesList, toTable);

            swapLabel1.setText("--> #");
            swapLabel2.setText("--> #");
            swapIndex1 = swapIndex2 = -1;
            clickSound(SoundOracle.UI_DINGDONG);
        } else
        {
            messageLabel.setText("Please select 2index!");
        }

    }

    /**
     * Invoked when user click the down arrow. This copies the selected index
     * from the table above to the table underneeth.
     */
    public void copyDownImage()
    {
        int index = fromTable.getSelectedRow();
        if (index != -1)
        {
            try
            {
                DisplayImage displayImage = imageArrayList.get(index);
                newDisplayImagesList.add(displayImage);
                messageLabel.setText("One member has been added to your list.");
                refreshTable(newDisplayImagesList, toTable);
            } catch (Exception e)
            {
                messageLabel.setText("Failed to add!");
            }
        } else
        {
            messageLabel.setText("Please select a member!");
        }

    }

    /**
     * This refresh the table.
     *
     * @param displayImages The data need to be put in the table.
     * @param oldTable The previous table needs to be refreshed.
     */
    public void refreshTable(ArrayList<DisplayImage> displayImages, JTable oldTable)
    {
        JTable soulTable = new JTable(transferToTable(displayImages), TABLE_HEADERS_STRINGS);
        oldTable.setModel(soulTable.getModel());
    }

    /**
     * Refresh the buttom control panel.
     */
    final public void envalidateOperation()
    {
        boolean addUpdateGroup = addRadioButton.isSelected() || updateRadioButton.isSelected();
        urlBox.setVisible(addUpdateGroup);
        nameField.setVisible(addUpdateGroup);
        descriptionField.setVisible(addUpdateGroup);
        typeComboBox.setVisible(addUpdateGroup);
        swapPanel.setVisible(swapRadioButton.isSelected());
    }

    /**
     * Invoked when an action is performed.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source.equals(returnButton))
        {
            this.dispose();
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
        } else if (source.equals(addRadioButton))
        {
            envalidateOperation();
            messageLabel.setText("Add new image into your new list.");
            clearAllFields();
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
        } else if (source.equals(removeRadioButton))
        {
            envalidateOperation();
            messageLabel.setText("Remove from a list.");
            clearAllFields();
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
        } else if (source.equals(updateRadioButton))
        {
            envalidateOperation();
            messageLabel.setText("Update a certain member in a list");
            clearAllFields();
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
        } else if (source.equals(swapRadioButton))
        {
            clickSound(SoundOracle.TINY_BUTTON_SOUND);
            clearAllFields();
            messageLabel.setText("Swap two images in a list in sequence");
            envalidateOperation();
        } else if (source.equals(applyButton))
        {
            applyEdit();
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
        } else if (source.equals(swapButton1))
        {
            swapIndex1 = toTable.getSelectedRow();
            if (swapIndex1 != -1)
            {
                swapLabel1.setText("--> #" + (swapIndex1 + 1));
            } else
            {
                messageLabel.setText("Please select a member!");
            }
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
        } else if (source.equals(swapButton2))
        {
            swapIndex2 = toTable.getSelectedRow();
            if (swapIndex2 != -1)
            {
                swapLabel2.setText("--> #" + (swapIndex2 + 1));
            } else
            {
                messageLabel.setText("Please select a member!");

            }
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
        } else if (source.equals(chooseFileButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            JFileChooser chooser = new JFileChooser("C:\\Users\\app\\Pictures\\");
            int confirmed = chooser.showOpenDialog(this);
            if (confirmed == JFileChooser.APPROVE_OPTION)
            {
                urlField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        } else if (source.equals(saveButton))
        {
            save();
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
        } else if (source.equals(manageItem))
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            ManagePlaylist managePlaylist = new ManagePlaylist();
        }

    }

    /**
     * Invoke when user press "save" button. An confirm dialog will pop up and
     * show confirmation message.
     *
     * @see saveList()
     */
    public void save()
    {
        int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to save?", "Save Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirmed == JOptionPane.YES_OPTION)
        {
            saveList();
        }
    }

    /**
     * Create a new list and insert all data in table underneeth into the new
     * list. Data are got from user entered.
     */
    public void saveList()
    {
        String name = listNameField.getText().replace(" ", "_");
        try (SecretGardenConnection SGC = SecretGardenConnection.getDefaultInstance())
        {
            SGC.createImageSublist(name);

            for (DisplayImage newDisplayImagesList1 : newDisplayImagesList)
            {
                SGC.insertIntoImageSublist(newDisplayImagesList1, name);
            }
            messageLabel.setText("Your new list are saved!");
            newDisplayImagesList.removeAll(newDisplayImagesList);
            clickSound(SoundOracle.UI_DINGDONG);
            refreshTable(newDisplayImagesList, toTable);
            listNameField.setText("");
            refreshComboBox();
            // loadLists();
        } catch (SQLException | ClassNotFoundException ex)
        {
//            Logger.getLogger(CreatePlaylist.class.getName()).log(Level.SEVERE, "cannot save list", ex);
            Warning warning = new Warning("Failed to save your sublist because of " + ex.getMessage() + Warning.SQL_EXCEPTION_DESCTIPTION, "Resave" + Warning.SQL_EXCEPTION_SUGGESTION, ex);
//            try
//            {
//                //SGC.dropImageSublist(name);
//                fromListComboBox.setModel(new JComboBox<>(SGC.getImageSublists()).getModel());
//            } catch (SQLException ex1)
//            {
//                Logger.getLogger(CreatePlaylist.class.getName()).log(Level.SEVERE, null, ex1);
//                Warning www = new Warning(ex1.getMessage());
//                www.setSuggestion("Please check for illegal characters or spaces ,existing name, and try again!");
//            }
        }

    }

    /**
     * Test main method.
     *
     * @param args Lines command argument.
     */
    public static void main(String[] args)
    {
//        for(int i = 0;i<args.length;i++)
//       {
//           System.out.println("Arg "+ i + " is: "+ args[i]);
//       }

        // System.out.println(Arrays.toString(args);
        ImageList list = new ImageList();
        list.setDefaultCloseOperation(EXIT_ON_CLOSE);
        list.setVisible(true);
        CreatePlaylist standardFrame = new CreatePlaylist();
        standardFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    /**
     * Reload the table selection combo box with new data from database.
     *
     * @throws SQLException Failed to connect database or cannot get data.
     * @throws java.lang.ClassNotFoundException
     */
    public void refreshComboBox() throws SQLException, ClassNotFoundException
    {
        SecretGardenConnection db = SecretGardenConnection.getDefaultInstance();
        typeComboBox.setModel(new DefaultComboBoxModel<>(db.getImageSublists()));
    }

    /**
     * Produce a little click sound.
     *
     * @param soundName The soundname.
     * @see SoundOracle
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
            Warning warning = new Warning("Cannot open sound  ", ex.toString(), ex);
        }
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
//        Document doc =e.getDocument();
//        if(doc.equals(listNameField.getDocument()))
//        {
//            try
//            {
//                int lastIndex = listNameField.getText().length()-1;
//                char lastchar = listNameField.getText().charAt(lastIndex);
//                if(lastchar == ' ' || lastchar == ':')
//                {
////                    listNameField.setOpaque(true);
////                    listNameField.setBackground(Color.RED);
//                    messageLabel.setText("Name illegal: cannot contain \" \"/ \":\"");
//                    //listNameField.setText(/*listNameField.getText().replace(" ", "").replace(":", "")*/"");
//                }
//                
//            } catch (Exception ex)
//            {
//                Logger.getLogger(CreatePlaylist.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

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
        clickSound(SoundOracle.PHONE_TYPING_SOUND);
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
