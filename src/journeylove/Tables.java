/*Editor: Johnson Gao

 * Date This Class:
 * Description Of This Class: See how many tables do you have.
Warning: This class are nolonger used.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 * See how many tables do you have.
 *
 * @author Johnson Gao
 */
public class Tables extends JFrame implements ActionListener
{

    //Final:font
    /**
     * A constant decleared for the font on the top. 
     */
    final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
   private SecretGardenConnection src = new SecretGardenConnection();
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
    private JPanel eastPanel;
    private JPanel centralPanel;
    private JTable displayTable;
    private String[] headerModel = src.COLLECTORHEADER;
    private JScrollPane tableScrollPane;
    private JComboBox<String> tablesSelectionBox;
    private String[] tables ; //SecretBridge.DB_GARDEN_CONNECTION.getDisplayTableInfo(SecretBridge.DB_GARDEN_CONNECTION.getData("Tables", headerModel))[0].toString();
    private JButton gotoButton;
    
    public Tables()
    {
        /**
         * Constructing sequence: JFrame JLabel JButtom JPanel ADDING ?PACK?
         * Rule: When constructing, call"this",otherwise, no "this"unless necessary. 
         */

        /**
         * JFrame.
         */
        super("My Frame");
        this.setBounds(100/*x align R*/, 100/*y align down*/, 900/*X-WIDTH*/, 700/*Y-Width*/);
        this.getContentPane().setBackground(Color.PINK);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        /**
         * JLabel.
         */
        this.titleLabel = new JLabel("Secret Garden Manager");
        titleLabel.setFont(TITLE_FONT);
        
        /**
         * JButton.
         */
        this.returnButton = new JButton("return");
        returnButton.addActionListener(this);
        gotoButton=new JButton("Go to");
        gotoButton.addActionListener(this);
        /**
         * JTable.
         */
        
        this.displayTable = new JTable(src.getTablesInfo(src.getTablesData("Tables",headerModel)), headerModel);
//        for (int i = 0; i < displayTable.getColumnCount(); i++)
//        {
//            displayTable.getColumnModel().getColumn(i).setPreferredWidth(50);
//        }
        displayTable.getTableHeader().setBackground(new LovelyColors().BEWITCHED_TREE);
        displayTable.setBackground(new LovelyColors().GLASS_GALL);
        /**
         * JScrollPane
         */
        this.tableScrollPane = new JScrollPane();
        tableScrollPane.getViewport().add(displayTable);
        tableScrollPane.setOpaque(false);
        tableScrollPane.getViewport().setOpaque(false);
        
        /*
        jcombobox
        */
        
        System.out.println("journeylove.SecretGardenManager.<init>()");
        this.updateTablesNames();
        tablesSelectionBox = new JComboBox<>(tables);
        
        /**
         * JPanel.
         */
        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel,SwingConstants.CENTER);
        northPanel.setOpaque(false);
        this.southPanel = new JPanel(new FlowLayout());
        southPanel.add(returnButton);
        southPanel.setOpaque(false);
        this.eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(tablesSelectionBox,BorderLayout.NORTH);
        eastPanel.setOpaque(false);
        eastPanel.add(gotoButton,BorderLayout.SOUTH);
        /*this.centralPanel = new JPanel(new FlowLayout());
        centralPanel.add(tableScrollPane);
        centralPanel.setOpaque(false);*/
        
        /**
         * Add components and finalize frame.
         */
        this.add(northPanel,BorderLayout.NORTH);
        this.add(southPanel,BorderLayout.SOUTH);
        this.add(eastPanel,BorderLayout.EAST);
        this.add(tableScrollPane,BorderLayout.CENTER);
        //this.add(tableScrollPane);
        this.setVisible(true);
    }
    
    /**
     *
     */
    public void updateTablesNames()
    {
        Object [][] data = src.getTablesInfo(src.getTablesData("Tables", headerModel));
        tables = new String[data.length];
        for (int i = 0; i < data.length; i++)
        {
            tables[i] = data[i][0].toString();
        }
    }

    public void validateTable()
    {
        
        //this.displayTable.setModel(SecretBridge.DB_GARDEN_CONNECTION.getDisplayTableModel(this.tablesSelectionBox.getItemAt(tablesSelectionBox.getSelectedIndex()), newHeaders));
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object commandObj = e.getSource();
        if(commandObj.equals(returnButton))
        {
            this.dispose();
        }else if(commandObj.equals(gotoButton))
        {
            String selection = tablesSelectionBox.getItemAt(tablesSelectionBox.getSelectedIndex());
        }
        
    }
    public static void main(String[] args)
    {
       Tables standardFrame = new Tables();
    }

}
