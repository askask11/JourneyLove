/*Editor: Johnson Gao

 * Date This Class: June 2019.
 * Description Of This Class: To allow user to create their own playlist and initialize the list.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
//import static journeylove.ImageList.SGC;

/**
 * This class is to allow user to manager their lists.
 *
 * @author Johnson Gao
 */
public class ManagePlaylist extends JFrame implements ActionListener
{

    //Final:font
    /**
     * A constant decleared for the font on the top. 
     */
    final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
   
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
    private JPanel northPanel;
    private JPanel southPanel;
    private JTable listTable;
    private JPanel eastPanel,westPanel;
    private JPanel centralPanel;
    private JButton refreshButton;
    private JScrollPane tablePane;
    public static final String[] TABLE_HEADER = {"Name"};
    private JPopupMenu tableMenu;
    private JMenuItem deleteItem,addNewItem;
    /**
     * A JFrame visiable class allow user to mamager their lists.
     */
    public ManagePlaylist()
            
    {
        

        /**
         * JFrame.
         */
        super("My Frame");
        this.setBounds(100/*x align R*/, 100/*y align down*/, 500/*X-WIDTH*/, 500/*Y-Width*/);
        this.getContentPane().setBackground(Color.PINK);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        /**
         * JLabel.
         */
        this.titleLabel = new JLabel("Image Playlists");
        titleLabel.setFont(TITLE_FONT);
        
        /**
         * JButton.
         */
        this.returnButton = new JButton("return");
        returnButton.addActionListener(this);
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        /*
        JPopupMenu
        */
        deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(this);
        addNewItem = new JMenuItem("Create New Playlist");
        addNewItem.addActionListener(this);
        tableMenu = new JPopupMenu();
        tableMenu.add(addNewItem);
        tableMenu.add(deleteItem);
        
        /*
        JTable
        */
        listTable = new JTable(tableData(), TABLE_HEADER);
        listTable.getTableHeader().setBackground(LovelyColors.MOMO_PINK);
        listTable.setComponentPopupMenu(tableMenu);
        listTable.addMouseListener(new MouseAdapter()
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
        tablePane = new JScrollPane();
        tablePane.getViewport().add(listTable);
        tablePane.getViewport().setOpaque(false);
        tablePane.setOpaque(false);
        /**
         * JPanel.
         */
        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel,SwingConstants.CENTER);
        northPanel.setOpaque(false);
        this.southPanel = new JPanel(new FlowLayout());
        southPanel.add(returnButton);
        southPanel.add(refreshButton);
        southPanel.setOpaque(false);
        this.westPanel = new JPanel();
        westPanel.add(tablePane);
        westPanel.setOpaque(false);
        
        /**
         * Add components and finalize frame.
         */
        this.add(northPanel,BorderLayout.NORTH);
        this.add(southPanel,BorderLayout.SOUTH);
        this.add(westPanel,BorderLayout.WEST);
        this.setVisible(true);
    }

    /**
     * Get the info of the playlist.
     * @return The object case of the sublists.
     */
    public Object[][] tableData()
    {
        try(SecretGardenConnection database = SecretGardenConnection.getDefaultInstance())
        {
            String[] sublists = database.getImageSublists();
            Object [][] data = new Object[sublists.length][1];
            for(int i=0; i<sublists.length;i++)
            {
                data[i][0] = sublists[i];
            }
           return data; 
        } catch (SQLException|ClassNotFoundException ex)
        {
            Logger.getLogger(ManagePlaylist.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return null;          
    }
    /**
     * Reload the table with the up-to-date data.
     */
    public void refreshTable()
    {
        JTable soulTable = new JTable(tableData(), TABLE_HEADER);
        this.listTable.setModel(soulTable.getModel());
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if(source.equals(deleteItem))
        {
            int index = listTable.getSelectedRow();
            switch (index)
            {
                case 0:
                    {
                        Warning warning = new Warning("You cannot delete main list!");
                        warning.pack();
                        break;
                    }
                case -1:
                    {
                        Warning warning = new Warning("Please select one to edit!");
                        warning.pack();
                        break;
                    }
                default:
                    try(SecretGardenConnection database = SecretGardenConnection.getDefaultInstance())
                    {
                        database.dropImageSublist((String) listTable.getValueAt(index, 0));
                        clickSound(SoundOracle.UI_DINGDONG);
                        refreshTable();
                    } catch (SQLException|ClassNotFoundException ex)
                    {
                        Logger.getLogger(ManagePlaylist.class.getName()).log(Level.SEVERE, null, ex);
                        Warning warn = new Warning("Failed to delete your playlist because of " + ex.getMessage(),"Please refer to information above",ex,false);
                        
                    }   break;
            }
            
        }else if(source.equals(addNewItem))
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            CreatePlaylist cpl = new CreatePlaylist();
        }else if(source.equals(returnButton))
        {
            clickSound(SoundOracle.DOOR_UNLOCKED_SOUND);
            this.dispose();
        }else if(source.equals(refreshButton))
        {
            clickSound(SoundOracle.BUTTON_CLICKED_SOUND);
            refreshTable();
        }
        
    }
    /**
     * Test main method.
     * @param args Lines command argument.
     */
    public static void main(String[] args)
    {
       ManagePlaylist standardFrame = new ManagePlaylist();
    }
    /**
     * Produces a sound when user clicks.
     * @param soundName The name of the sound.
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
            Warning warning = new Warning("Cannot open sound  " + ex.toString(),"",ex,false);
        }
    }

}
