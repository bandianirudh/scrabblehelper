/*
 * TwoJLabelPanel.java
 *
 * Created on January 12, 2008, 9:35 PM
 */

package gui;

import java.awt.Dimension;

/**
 *
 * @author  Nick
 */
public class TwoJLabelPanel extends javax.swing.JPanel {
    
    
    /** Creates new form TwoJLabelPanel */
    public TwoJLabelPanel() {
        this("", "");
        setMinimumSize(new Dimension(0, 23));
    }
    
    public TwoJLabelPanel(String name, String value) {
        initComponents();
        setLabelName(name);
        setLabelValue(value);
        setOpaque(false);
    }
    
    public String getLabelName() {
        return labelName.getText();
    }
    
    public String getLabelValue() {
        return labelValue.getText();
    }
    
    public void setLabelName(String name) {
        labelName.setText(name);
    }
    
    public void setLabelValue(String value) {
        labelValue.setText(value);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelName = new javax.swing.JLabel();
        labelValue = new javax.swing.JLabel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(labelName, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelValue, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
            .addComponent(labelName, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelValue;
    // End of variables declaration//GEN-END:variables
    
}
