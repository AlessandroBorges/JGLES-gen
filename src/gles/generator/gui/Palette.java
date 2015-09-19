package gles.generator.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class Palette extends JPanel {
    private JList<String> listAvail;
    private JList<String> listSelected;
    private JLabel lblTitle;
    private JLabel lblAvailable;
    private JLabel lblSelected;
    private JTextField tfFilter;
    
    private Vector<String> values = new Vector<String>();
    
    public Palette() {
        setLayout(new BorderLayout(0, 0));
        
        JPanel panelCenter = new JPanel();
        add(panelCenter, BorderLayout.CENTER);
        panelCenter.setLayout(new BorderLayout(0, 0));
        
        JPanel panelCenterTop = new JPanel();
        panelCenterTop.setPreferredSize(new Dimension(400, 36));
        panelCenterTop.setSize(new Dimension(400, 36));
        panelCenter.add(panelCenterTop, BorderLayout.NORTH);
        panelCenterTop.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        
        JLabel lblFilter = new JLabel("Filter: ");
        lblFilter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFilter.setPreferredSize(new Dimension(40, 20));
        panelCenterTop.add(lblFilter);
        
        tfFilter = new JTextField();
        tfFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                applyFilter();
            }
        });
        tfFilter.setToolTipText("Filter extension");
        tfFilter.setSize(new Dimension(40, 24));
        tfFilter.setPreferredSize(new Dimension(40, 24));
        panelCenterTop.add(tfFilter);
        tfFilter.setColumns(16);
        
        JButton btnClear = new JButton("clear");
        btnClear.setPreferredSize(new Dimension(63, 22));
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfFilter.setText("");
                applyFilter();
            }
        });
        panelCenterTop.add(btnClear);
        
        JPanel panelMain = new JPanel();
        panelCenter.add(panelMain, BorderLayout.CENTER);
        panelMain.setBorder(new TitledBorder(null, "Multi Selector", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.X_AXIS));
        
        JPanel panelAvail = new JPanel();
        panelAvail.setPreferredSize(new Dimension(160, 200));
        panelMain.add(panelAvail);
        panelAvail.setLayout(new BorderLayout(0, 0));
        DefaultListModel<String> model = new DefaultListModel<String>();
        listAvail = new JList<String>();
        listAvail.setModel(model);
        
        JScrollPane scrollPanel = new JScrollPane(listAvail, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setAutoscrolls(true);
       
        
        listAvail.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
       // listAvail.setPreferredSize(new Dimension(100, 200));
        panelAvail.add(scrollPanel);
        
        lblAvailable = new JLabel("Available");
        lblAvailable.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvailable.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblAvailable.setPreferredSize(new Dimension(43, 24));
        panelAvail.add(lblAvailable, BorderLayout.NORTH);
        
        JPanel panelButtons = new JPanel();
        panelMain.add(panelButtons);
        panelButtons.setSize(new Dimension(50, 200));
        panelButtons.setPreferredSize(new Dimension(50, 200));       
        
        Component verticalStrut_1 = Box.createVerticalStrut(20);
        verticalStrut_1.setPreferredSize(new Dimension(50, 40));
        verticalStrut_1.setSize(new Dimension(40, 40));
        panelButtons.add(verticalStrut_1);
        
        JButton btnGoOne = new JButton(">");        
        btnGoOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copy(listAvail, listSelected, false);
            }
        });               
        
        btnGoOne.setPreferredSize(new Dimension(50, 24));
        panelButtons.add(btnGoOne);
        
        JButton btGoAll = new JButton(">>");        
        btGoAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copy(listAvail, listSelected, true);
            }
        });
        btGoAll.setPreferredSize(new Dimension(50, 24));
        panelButtons.add(btGoAll);
        
        Component verticalStrut = Box.createVerticalStrut(20);
        verticalStrut.setPreferredSize(new Dimension(50, 20));
        panelButtons.add(verticalStrut);
        
        JButton btBackOne = new JButton("<");
        btBackOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copy( listSelected, listAvail, false);
            }
        });
        btBackOne.setPreferredSize(new Dimension(50, 24));
        panelButtons.add(btBackOne);
        
        JButton btBackAll = new JButton("<<");
        btBackAll.setPreferredSize(new Dimension(50, 24));
        btBackAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copy( listSelected, listAvail, true);
            }
        });
        panelButtons.add(btBackAll);
        
        JPanel panelSelected = new JPanel();
        panelSelected.setPreferredSize(new Dimension(160, 200));
        panelMain.add(panelSelected);
        panelSelected.setLayout(new BorderLayout(0, 0));
        
        model = new DefaultListModel<String>();
        listSelected = new JList<String>();        
        listSelected.setModel(model);
        
        JScrollPane scrollPane2 = new JScrollPane(listSelected, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setAutoscrolls(true);
        
        listSelected.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
      //  listSelected.setPreferredSize(new Dimension(100, 200));
        panelSelected.add(scrollPane2);
        
        lblSelected = new JLabel("Selected");
        lblSelected.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSelected.setHorizontalTextPosition(SwingConstants.CENTER);
        lblSelected.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelected.setPreferredSize(new Dimension(41, 24));
        panelSelected.add(lblSelected, BorderLayout.NORTH);
        
        JPanel panelTop = new JPanel();
        add(panelTop, BorderLayout.NORTH);
        
        lblTitle = new JLabel("Title");
        lblTitle.setHorizontalTextPosition(SwingConstants.CENTER);
        lblTitle.setPreferredSize(new Dimension(300, 30));
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        panelTop.add(lblTitle);

       // DefaultListModel<String> model = new DefaultListModel<String>();
       
        
        JPanel panelSouth = new JPanel();
        add(panelSouth, BorderLayout.SOUTH);
    }
   
    /**
     * Add options
     * @param options
     */
    public void setAvailableList(List<String> options ){
        this.values.addAll(options);
        DefaultListModel< String> avail = (DefaultListModel<String>) listAvail.getModel();
        for (String val : options) {
            avail.addElement(val);
        }
    }
    
    /**
     * Get a list of selected values
     * @return
     */
    public List<String> getSelected() {
        DefaultListModel<String> model = (DefaultListModel<String>)listSelected.getModel();
        int size = model.size();       
        List<String> list = new ArrayList<String>(size);
        for(int i=0; i<size; i++){
            list.add(model.get(i));
        }        
        return list;
    }
    
    /**
     * Set Title
     * @param title
     */
    public void setPaletteTitle(String title){
        lblTitle.setText(title);
    }
    
    /**
     * Set a caption for Available JList
     * @param availableTitle
     */
    public void setAvailableListTitle(String availableTitle){
        lblAvailable.setText(availableTitle);
    }
    
    
    /**
     * Set a caption for Selected JList
     * @param availableTitle
     */
    public void setSelectListTitle(String availableTitle){
        lblSelected.setText(availableTitle);
    }
    
    
    /**
     * Copy selected content from one list to another
     * @param src source list
     * @param dst dst list
     * @param all copy all
     */
    protected void copy(JList<String> src, JList<String> dst, boolean all) {
        DefaultListModel<String> dstModel = (DefaultListModel<String>) dst.getModel();
        DefaultListModel<String> srcModel = (DefaultListModel<String>) src.getModel();
        dst.setEnabled(false);
        src.setEnabled(false);
        
        if (all) {
            //copy all
            int size = srcModel.size();        
            
            for(int i=0; i<size; i++){
               String val = srcModel.getElementAt(i);
               dstModel.addElement(val);
            }
            srcModel.removeAllElements();

        } else {
            // one or more selected
            int pos = src.getSelectedIndex();
            @SuppressWarnings("deprecation")
            Object[] select = src.getSelectedValues();
            src.clearSelection();           
            if (select == null || select.length == 0){
                dst.setEnabled(true);
                src.setEnabled(true);
                return;
            }

            for (Object val : select) {
                dstModel.addElement((String) val);
                srcModel.removeElement(val);
            }
            
            if(srcModel.size() > pos){
                src.setSelectedIndex(pos);
            }
        }
        // sort DST
        Object[] arr = dstModel.toArray();
        Arrays.sort(arr);
        dstModel.removeAllElements();
        for (Object element : arr) {
            dstModel.addElement((String) element);
        }
        
        dst.setEnabled(true);
        src.setEnabled(true);
    }
    
    /**
     * Apply filter to available options
     */
    private void applyFilter(){
        String strFilter = tfFilter.getText().trim().toLowerCase();
        DefaultListModel<String> model = getModelInput();
        if(strFilter.length()==0 && model.size() < values.size()){
            model.clear();
            for (String val : values) {
                model.addElement(val);
            }
        }else{
            model.clear();
            for (String val : values) { 
                String test = val.trim().toLowerCase();
                if(test.contains(strFilter)){
                    model.addElement(val);
                }
            }            
        }
    }
    
    /**
     * Get available values
     * @return
     */
    private DefaultListModel<String> getModelInput(){
       return  (DefaultListModel<String>) listAvail.getModel();
    }
    
    
    
    public static void main(String[] args) {
        JFrame f = new JFrame("Multi");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Palette p = new Palette();
        f.getContentPane().add(p, BorderLayout.CENTER);        
        f.setSize(500, 600);
        
        String[] names = {"ABC", "BCD", "CDE","DEF","EFG","FHI"};
        
        p.setAvailableList(Arrays.asList(names));
        
        f.setVisible(true);
        
    }

}
