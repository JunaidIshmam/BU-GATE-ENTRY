import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.net.URL;

public class MyBrowser extends JPanel
{
    public static void main(String[] args)
    {
        JFrame pannel = new JFrame("LOOSER BROWSER");
        MyBrowser content = new MyBrowser();
        pannel.setContentPane(content);
        pannel.setSize(700,700);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        pannel.setLocation( (screenSize.width - pannel.getWidth())/2,
                           (screenSize.height - pannel.getHeight())/2 );
        pannel.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        pannel.setVisible(true);
    }
    
    private JEditorPane editPane;
    private JTextField locationInput;
       
    private class LinkListener implements HyperlinkListener
    {
        public void hyperlinkUpdate(HyperlinkEvent evt)
        {
            if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            {
                loadURL(evt.getURL());
            }
        }
    }
    
    private class GoListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            URL url;
            try
            {
                String location = locationInput.getText().trim();
                if (location.length() == 0)
                    throw new Exception();
                if (! location.contains("://"))
                    location = "http://" + location;
                url = new URL(location);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(MyBrowser.this, 
                                              "WRONG INPUT!!");
                return;
            }
            loadURL(url);
            locationInput.selectAll();
            locationInput.requestFocus();
        }
    }
    
    
    public MyBrowser()
    {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout(1,1));
        setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        
        editPane = new JEditorPane();
        editPane.setEditable(false);
        editPane.addHyperlinkListener(new LinkListener());
        add(new JScrollPane(editPane),BorderLayout.CENTER);
        
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        add(toolbar,BorderLayout.NORTH);
        ActionListener goListener = new GoListener();
        locationInput = new JTextField("enter url", 40);
        locationInput.addActionListener(goListener);
        JButton goButton = new JButton(" HELL YEAH ");
        goButton.addActionListener(goListener);
        toolbar.add( new JLabel(" Address: "));
        toolbar.add(locationInput);
        toolbar.addSeparator(new Dimension(5,30));
        toolbar.add(goButton);
    }
    
    
    private void loadURL(URL url)
    {
        try
        {
            editPane.setPage(url);
        }
        catch (Exception e)
        {
            editPane.setContentType("TEXT");
            editPane.setText( "HAHA!! LOOSER!!"
                                 +"WON'T SHOW YOU.\n\nError:" + e);
        }
    }
    
}