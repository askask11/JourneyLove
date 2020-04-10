/*Editor: Johnson Gao

 * Date This Project Created:
 * Description Of This Class: This is bulit to test the function of setting look and feel.
 */
package journeylove;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author app
 */
public class SetLookAndFeel extends javax.swing.JFrame
{

    /**
     * Creates new form SetLookAndFeel
     */
    public SetLookAndFeel()
    {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        applyButton = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        messageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+6f));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Skin Setting");

        applyButton.setText("Confirm");
        applyButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                applyButtonActionPerformed(evt);
            }
        });

        jComboBox1.setModel(comboBoxModel);
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder("Installed Skins"));

        messageLabel.setText("Select your favourite skin and click \"confirm\"");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(messageLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(applyButton)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(messageLabel)
                .addGap(18, 18, 18)
                .addComponent(applyButton))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_applyButtonActionPerformed
    {//GEN-HEADEREND:event_applyButtonActionPerformed
        try
        {
            // TODO add your handling code here:
            UIManager.setLookAndFeel(infos[jComboBox1.getSelectedIndex()].getClassName());
            repaint();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(SetLookAndFeel.class.getName()).log(Level.SEVERE, null, ex);
            messageLabel.setText(ex.toString());
        }
    }//GEN-LAST:event_applyButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
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
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(SetLookAndFeel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(SetLookAndFeel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(SetLookAndFeel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(SetLookAndFeel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() ->
        {
            
            SetLookAndFeel slaf = new SetLookAndFeel();
            slaf.getContentPane().setBackground(Color.pink);
            slaf.setVisible(true);
            JourneyLove journeyLove = new JourneyLove();
        });
    }

    public String[] getInstalledLookAndFeels(){
        infos = javax.swing.UIManager.getInstalledLookAndFeels();
        String[] infoStrings = new String[infos.length];
        
        for (int i = 0; i < infoStrings.length; i++)
        {
            infoStrings[i] = infos[i].getName();
        }
        return infoStrings;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyButton;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel messageLabel;
    // End of variables declaration//GEN-END:variables
    private DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(getInstalledLookAndFeels());
    private UIManager.LookAndFeelInfo[] infos;
    //This is the real end.
}
