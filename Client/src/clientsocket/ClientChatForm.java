/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author EISHON
 */
public class ClientChatForm extends javax.swing.JFrame {

    /**
     * Creates new form ClientChatForm
     */
    
    public String user = "";
    
    public ClientChatForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        onlineUserList = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        rcvMsgTxtArea = new javax.swing.JTextArea();
        sendMsgTxtFld = new javax.swing.JTextField();
        sendBtn = new javax.swing.JButton();
        chatFormLabel = new javax.swing.JLabel();
        fileSendBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        onlineUserList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        onlineUserList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                onlineUserListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(onlineUserList);

        rcvMsgTxtArea.setColumns(20);
        rcvMsgTxtArea.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        rcvMsgTxtArea.setRows(5);
        jScrollPane2.setViewportView(rcvMsgTxtArea);

        sendMsgTxtFld.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sendMsgTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMsgTxtFldActionPerformed(evt);
            }
        });

        sendBtn.setText("send");
        sendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        chatFormLabel.setText("  ");

        fileSendBtn.setText("File Send");
        fileSendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSendBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addComponent(sendMsgTxtFld)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chatFormLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileSendBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendBtn)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sendMsgTxtFld, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sendBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fileSendBtn))
                    .addComponent(chatFormLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendMsgTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMsgTxtFldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendMsgTxtFldActionPerformed

    private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBtnActionPerformed
        try {
            Client.sendMsg();
        } catch (IOException ex) {
            Logger.getLogger(ClientChatForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sendBtnActionPerformed

    private void onlineUserListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_onlineUserListValueChanged
        if (!evt.getValueIsAdjusting()) {
            if(onlineUserList.getSelectedValue() != null)
                user = onlineUserList.getSelectedValue();
            System.out.println(user+" Selected");
        }
    }//GEN-LAST:event_onlineUserListValueChanged

    private void fileSendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSendBtnActionPerformed
       if( !user.isEmpty()){
            JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView());
            jFileChooser.showSaveDialog(null);

            File file = jFileChooser.getSelectedFile();
            System.out.println(file.getName());
            try {
                Client.sendFileMsg(file);
            } catch (IOException ex) {
                Logger.getLogger(ClientChatForm.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }//GEN-LAST:event_fileSendBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientChatForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientChatForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientChatForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientChatForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientChatForm().setVisible(true);
            }
        });
    }
    
    public javax.swing.JTextField getSendMsgTxtFld() {
        return sendMsgTxtFld;
    }
    
    public void setRcvMsgTxtAreaTxt(String msg) {
        String tmpMsg = rcvMsgTxtArea.getText()+ "\n" + msg;
        rcvMsgTxtArea.setText(tmpMsg);
    }
    
    public void setOnlineUsersList(String[] users){
        onlineUserList.setListData(users);
    }
    
    public String getSelectedUser(){
        return user;
    }
    
    public void setChatFormMsg(String msg){
        chatFormLabel.setText(msg);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chatFormLabel;
    private javax.swing.JButton fileSendBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> onlineUserList;
    private javax.swing.JTextArea rcvMsgTxtArea;
    private javax.swing.JButton sendBtn;
    private javax.swing.JTextField sendMsgTxtFld;
    // End of variables declaration//GEN-END:variables
}
