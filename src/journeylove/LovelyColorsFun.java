/*Editor: Johnson Gao

 * Date This Project Created: Mar.30 2019
 * Description Of This Class:To test the class " LovelyColors" all the colors before actually use it.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *To test the class " LovelyColors" all the colors before actually use it.Also allow users to have fun!
 * @author Johnson Gao
 * @since Journey1.0
 */
public class LovelyColorsFun extends JFrame implements ActionListener
{

    final LovelyColors LOVELY_COLORS = new LovelyColors();
    final Font TITLE_FONT = new Font("方正有猫在简体", Font.BOLD | Font.ITALIC, 20);
    final Font MESSAGE_FONT = new Font("方正有猫在简体", Font.PLAIN, 15);
    final Font FEEDBACK_FONT = new Font("Courier New", Font.PLAIN, 20);
    private JLabel targetFrameName;
    /**
     * @param COLORS point to the colors that defined as constants.
     */
    final String[] CONSTANT_COLOR =
    {
        "--OR select an constant color you would like to use--",
        "MERRY_CRANESBILL",
        "TRUE_BLUSH",
        "MAGIC_POWDER",
        "MUSTARD_ADDICTED",
        "BRAIN_SAND",
        "SILLY_FIZZ",
        "GLASS_GALL",
        "LIGHT_HEART_BLUE",
        "MYSTICAL_GREEN",
        "BEWITCHED_TREE",
        "SPRING_GREEN",
        "HALF_N_HALF_CHOCOLATE",
        "SAKURA_PINK",
        "SAKURA_FONTCOLOR",
        "JINZAMOMI_PINK",
        "JINZAMOMI_FONTCOLOR",
        "MOMO_PINK",
        "MOMO_FONTCOLOR",
        "KOHBAI_PINK",
        "KOHBAI_FONTCOLOR",
        "NAE_GREEN",
        "NAE_FONTCOLOR"
    };
    /**
     * @deprecated No longer apply to this frame itself.
     */
    final String[] CHANGE_WHAT =
    {
        "My self", "Background", "North Panel", "South Panel", "West Panel", "East Panel",
        "Perferred Group Editor(Panel)", "Origin selector", "Constant Color Selector", "FeedBack-Font", "Apply Button", "ExitButton",
        "Menu Bar",
        "Menu",
        "Menu Item"
    };
    private String appliedTo = "Editing applied to :";
    private JLabel titleLabel, feedbackLabel, groupNumberLabel, sequenceNumberLabel;
    private JButton returnButton, applyButton;
    private JRadioButton usePerferredGroup, useConstantColor;
    private ButtonGroup directionSelectionGroup;
    private JTextField groupNumber, sequenceNumber;
    private JComboBox<String> constantColorSelector, changeWhat;
    private JPanel northPanel, westPanel, eastPanel, southPanel, testGroupPanel;
    private JFrame gFrame;
    private JMenuBar myMenuBar;
    private JMenu myMenu;
    private JMenuItem myMenuItem;
    private Color defaultColor = new LovelyColors().LIGHT_HEART_BLUE;
    private boolean trueEnter = true;
    private Color targetColor;
    Box selectionBox = Box.createHorizontalBox();
    private static final long serialVersionUID = 1L;

    /**
     * Main constructor of this frame.
     * @param itemList options for components to edit for the target frame.
     * @param lFrame the target frame.
     */
    public LovelyColorsFun(String[] itemList,JFrame lFrame)
    {
        //Main Frame.
        super("Lovely Colors Fun");
        this.setBounds(100, 100, 700, 500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(defaultColor);
        this.setLayout(new BorderLayout());
        gFrame = lFrame;
        //JLabel.
        titleLabel = new JLabel("Frames DIY: You are editing " + lFrame.getTitle());
        titleLabel.setFont(TITLE_FONT);
        feedbackLabel = new JLabel("Please Select Color to make change!");
        feedbackLabel.setFont(FEEDBACK_FONT);
        feedbackLabel.setOpaque(false);
        groupNumberLabel = new JLabel("Group #");
        sequenceNumberLabel = new JLabel("Sequence #");
        
        //JButtons.
        returnButton = new JButton("Return");
        returnButton.setBackground(LOVELY_COLORS.MERRY_CRANESBILL);
        returnButton.addActionListener(this);
        applyButton = new JButton("Apply To Frame");
        applyButton.setBackground(LOVELY_COLORS.BEWITCHED_TREE);
        applyButton.addActionListener(this);
        //JRadioButtons.
        usePerferredGroup = new JRadioButton("Use Perferred Group Color");
        usePerferredGroup.setOpaque(false);
        useConstantColor = new JRadioButton("Use constant colors.");
        useConstantColor.setOpaque(false);
        //Box
        selectionBox.setOpaque(true);
        selectionBox.add(usePerferredGroup);
        selectionBox.add(useConstantColor);
        selectionBox.setBackground(defaultColor);
        //Group radio buttons.
        directionSelectionGroup = new ButtonGroup();
        directionSelectionGroup.add(usePerferredGroup);
        directionSelectionGroup.add(useConstantColor);
        //textfields
        groupNumber = new JTextField();
        sequenceNumber = new JTextField();
        //Combo Box 
        this.constantColorSelector = new JComboBox<>(CONSTANT_COLOR);
        constantColorSelector.setBorder(BorderFactory.createTitledBorder("Constant color."));
        constantColorSelector.setBackground(defaultColor);
        this.changeWhat = new JComboBox<>(itemList);
        changeWhat.setBorder(BorderFactory.createTitledBorder("Which component to change?"));
        changeWhat.setBackground(defaultColor);
        //Menus
        myMenuBar = new JMenuBar();
        myMenuBar.setBackground(defaultColor);
        myMenu = new JMenu("Help");
        myMenu.setOpaque(true);
        myMenu.setBackground(defaultColor);
        myMenuItem = new JMenuItem("Help?");
        myMenuBar.add(myMenu);
        myMenu.add(myMenuItem);

        //JPanel
        this.testGroupPanel = new JPanel(new GridLayout(2, 2));
        testGroupPanel.setBorder(BorderFactory.createTitledBorder("Perferred Group Method"));
        testGroupPanel.add(groupNumberLabel);
        testGroupPanel.add(sequenceNumberLabel);
        testGroupPanel.add(groupNumber);
        testGroupPanel.add(sequenceNumber);
        testGroupPanel.setBackground(defaultColor);
        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel);
        northPanel.setBackground(defaultColor);
        this.eastPanel = new JPanel(new GridLayout(3/*ROWS*/, 0/*Single column*/, 0, 4));
        eastPanel.setBorder(BorderFactory.createTitledBorder("Which part of \"Lovely Colors\" to use?"));
        eastPanel.add(testGroupPanel);
        eastPanel.add(constantColorSelector);
        eastPanel.add(selectionBox);
        eastPanel.setBackground(defaultColor);
        this.westPanel = new JPanel(new BorderLayout());
        westPanel.add(changeWhat, BorderLayout.NORTH);
        westPanel.setBackground(defaultColor);

        this.southPanel = new JPanel(new FlowLayout());
        southPanel.add(applyButton);
        southPanel.add(returnButton);
        southPanel.setBackground(defaultColor);

        //Add components to the frame.
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(westPanel, BorderLayout.WEST);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(feedbackLabel, BorderLayout.CENTER);
        this.setJMenuBar(myMenuBar);
        //Set the frame truly visiable.
        this.setVisible(true);
        //RESET BOUND
        this.pack();
    }
    /**
     * Action perform method.
     * @param e action event.
     */

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("Apply"))
        {
            this.applyEdit();
        } else
        {
            this.dispose();
        }

    }

    /**
     * Apply edit to the frame.
     */
    private void applyEdit()
    {
        //reset the value of "trueenter"
        this.trueEnter = true;
        if (usePerferredGroup.isSelected())
        {

            this.getPerferredGroupColor();//at the same time check user's enter.
            //check if user enter correctly;
            if (this.trueEnter)
            {
                this.editTo(targetColor);
            } else
            {
                //Warning warn = new Warning("Not entered correctly, please enter your editing request again.");
            }
        } else if (useConstantColor.isSelected())
        {
            this.getConstantGroupColor();
            if(this.trueEnter == true)
            {
                this.editTo(targetColor);
            }
            else
            {
                //Since warning window already pop up, do nothing here.
            }
        } else
        {
            //Warning noSelWarning = new Warning("Plesae Select an group to use!!!");
        }
    }

    /**
     * When user select the "group colors", this method will be called.
     */
    private void getPerferredGroupColor()
    {
        int group;
        int sequence;

        try
        {
            group = Integer.parseInt(this.groupNumber.getText());
            sequence = Integer.parseInt(this.sequenceNumber.getText());
            this.targetColor = new LovelyColors().perferredColorGroup(group, sequence);
        } catch (NumberFormatException nfe)
        {
            //Warning warn = new Warning("Enter integer @ group & sequence # field!");
            this.trueEnter = false;
        } catch (Error error)
        {
           // Warning warn = new Warning("PLEASE ENTER AN EXISTING NUMBER");
            this.trueEnter = false;
        }

    }

    /**
     * When user select the "constant color", this method will be called.
     */
    private void getConstantGroupColor()
    {
        String color = this.constantColorSelector.getSelectedItem().toString();
        switch (color)
        {
            case "MERRY_CRANESBILL":
                targetColor = LOVELY_COLORS.MERRY_CRANESBILL;
                break;
            case "TRUE_BLUSH":
                targetColor = LOVELY_COLORS.TRUE_BLUSH;
                break;
            case "MAGIC_POWDER":
                targetColor = LOVELY_COLORS.MAGIC_POWDER;
                break;
            case "MUSTARD_ADDICTED":
                targetColor = LOVELY_COLORS.MUSTARD_ADDICTED;
                break;
            case "BRAIN_SAND":
                targetColor = LOVELY_COLORS.BRAIN_SAND;
                break;
            case "SILLY_FIZZ":
                targetColor = LOVELY_COLORS.SILLY_FIZZ;
                break;
            case "GLASS_GALL":
                targetColor = LOVELY_COLORS.GLASS_GALL;
                break;
            case "LIGHT_HEART_BLUE":
                targetColor = LOVELY_COLORS.LIGHT_HEART_BLUE;
                break;
            case "MYSTICAL_GREEN":
                targetColor = LOVELY_COLORS.MYSTICAL_GREEN;
                break;
            case "BEWITCHED_TREE":
                targetColor = LOVELY_COLORS.BEWITCHED_TREE;
                break;
            case "SPRING_GREEN":
                targetColor = LOVELY_COLORS.SPRING_GREEN;
                break;
            case "HALF_N_HALF_CHOCOLATE":
                targetColor = LOVELY_COLORS.HALF_N_HALF_CHOCOLATE;
                break;
            case "SAKURA_PINK":
                targetColor = LOVELY_COLORS.SAKURA_PINK;
                break;
            case "SAKURA_FONTCOLOR":
                targetColor = LOVELY_COLORS.SAKURA_FONTCOLOR;
                break;
            case "JINZAMOMI_PINK":
                targetColor = LOVELY_COLORS.JINZAMOMI_PINK;
                break;
            case "JINZAMOMI_FONTCOLOR":
                targetColor = LOVELY_COLORS.JINZAMOMI_FONTCOLOR;
                break;
            case "MOMO_PINK":
                targetColor = LOVELY_COLORS.MOMO_PINK;
                break;
            case "MOMO_FONTCOLOR":
                targetColor = LOVELY_COLORS.MOMO_FONTCOLOR;
                break;
            case "KOHBAI_PINK":
                targetColor = LOVELY_COLORS.KOHBAI_PINK;
                break;
            case "KOHBAI_FONTCOLOR":
                targetColor = LOVELY_COLORS.KOHBAI_FONTCOLOR;
                break;
            case "NAE_GREEN":
                targetColor = LOVELY_COLORS.NAE_GREEN;
                break;
            case "NAE_FONTCOLOR":
                targetColor = LOVELY_COLORS.NAE_FONTCOLOR;
                break;
            default:
               // Warning nowarn = new Warning("NOT AN OPTION @ CONSTANT COLOR!!!");
                this.trueEnter = false;

                break;

        }
    }
    public void editTo (Color applyColor)
    {
        gFrame.setBackground(applyColor);
        
    }
    
    /**
     * @deprecated The editing no longer apply to this editor.
     * Apply the edit to anywhere in the frame, direct the color.
     * 
     * @param applyColor The color to edit, like the magintude.
     * 
     */
    public void editingDirection(Color applyColor)
    {
        String command = this.changeWhat.getSelectedItem().toString();
        switch (command)
        {
            case "My self":
                this.changeWhat.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "Direction Selector Editior!");
                break;
            case "Background":
                this.getContentPane().setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "Background!");
                break;
            case "North Panel":
                this.northPanel.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "North Panel");
                break;
            case "South Panel":
                this.southPanel.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "South Panel");
                break;
            case "West Panel":
                this.westPanel.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "West Panel");
                break;
            case "East Panel":
                this.eastPanel.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "East Panel");
                break;
            case "Perferred Group Editor(Panel)":
                this.testGroupPanel.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "Perferred Group Editor(Panel)");
                break;
            case "Constant Color Selector":
                this.constantColorSelector.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "Default Group-ComboBox");
                break;
            case "Origin selector":
                this.selectionBox.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "Origin selecror(The box on the right)");
                break;
            case "FeedBack-Font":
                this.feedbackLabel.setForeground(applyColor);
                this.feedbackLabel.setText("I am applied;");
                break;
            case "Apply Button":

                this.applyButton.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "Apply Button");
                break;
            case "ExitButton":
                this.returnButton.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "ExitButton");
                break;
            case "Menu Bar":
                this.myMenuBar.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "Menu Bar");
                break;
            case "Menu":
                this.myMenu.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "Menu");
                break;
            case "Menu Item":
                this.myMenuItem.setBackground(applyColor);
                this.feedbackLabel.setText(appliedTo + "Meny Item");
                break;
            default:
                //Warning warn = new Warning("ERROR @ DIRECTION SELECTOR!!");
                break;
        }
    }

    
    /**
     * Test main method.
     * Main TesT COLOR.
     * @param args arguments.
     */
    public static void main(String[] args)
    {
        //LovelyColorsFun testLovelyColors = new LovelyColorsFun();
    }
}
