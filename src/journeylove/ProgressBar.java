/*Editor: Johnson Gao

 * Date This Class:
 * Description Of This Class: This is a frame come with a bar to show the progress.
 */
package journeylove;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This is a frame come with a bar to show the progress.
 *
 * @author Johnson Gao
 */
public class ProgressBar extends JFrame implements ActionListener,ChangeListener
{

    //Final:font
    /**
     * A constant decleared for the font on the top. 
     */
    final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
   final Font MESSAGE_FONT = new Font("Courier New", Font.PLAIN, 20);
    /**
     * Holds things for the title.
     */
    private JLabel titleLabel,progressLabel;
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
    private JProgressBar progressBar;
    private JLabel messageLabel;
    /**
     * This is a frame come with a bar to show the progress.
     */
    public ProgressBar()
    {
        /**
         * Constructing sequence: JFrame JLabel JButtom JPanel ADDING ?PACK?
         * Rule: When constructing, call"this",otherwise, no "this"unless necessary. 
         */

        /**
         * JFrame.
         */
        super("Processing...");
        this.setBounds(100/*x align R*/, 100/*y align down*/, 500/*X-WIDTH*/, 150/*Y-Width*/);
        this.getContentPane().setBackground(Color.PINK);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        /**
         * JLabel.
         */
        this.titleLabel = new JLabel("Processing ... ");
        titleLabel.setFont(TITLE_FONT);
        progressLabel = new JLabel("Completed: Not started yet");
        progressLabel.setFont(MESSAGE_FONT);
        messageLabel = new JLabel();
        
        /**
         * JButton.
         */
        this.returnButton = new JButton("return");
        returnButton.addActionListener(this);
        progressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        progressBar.setBackground(Color.green);
        progressBar.addChangeListener(this);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        /**
         * JPanel.
         */
        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel,SwingConstants.CENTER);
        northPanel.setOpaque(false);
        this.southPanel = new JPanel(new FlowLayout());
        southPanel.add(returnButton);
        this.centralPanel = new JPanel(new BorderLayout());
        centralPanel.setOpaque(false);
        centralPanel.add(progressLabel,BorderLayout.NORTH);
        centralPanel.add(progressBar,BorderLayout.CENTER);
        /**
         * Add components and finalize frame.
         */
        this.add(northPanel,BorderLayout.NORTH);
        this.add(southPanel,BorderLayout.SOUTH);
        add(centralPanel,BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.dispose();
    }
    public static void main(String[] args)
    {
       ProgressBar standardFrame = new ProgressBar();
       //standardFrame.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        progressLabel.setText("Completed : " + progressBar.getString());
    }
    
    public void setMessage(String msg)
    {
        messageLabel.setText(msg);
    }
    
    public void updateValue(int newVar)
    {
        progressBar.setValue(newVar);
    }
    /**
     * Set the minimun and maximun value of the bar.
     * @param min Minimun value of the bar.
     * @param max Max value of the bar.
     */
    public void setMin_N_Max (int min, int max)
    {
        this.progressBar.setMinimum(min);
        this.progressBar.setMaximum(max);
    }
        /**
         * Get the progress bar.
         * @return the progress bar.
         */
    public JProgressBar getProgressBar()
    {
        return progressBar;
    }

    
    

}
