import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame{
    static JFrame frame;
    static JPanel controlPanel;
    static JPanel itemPanel;

    static String typeChooser = "Button";
    static java.util.List<String> listElemets = new ArrayList<String>();

    static JTextArea textHelper = new JTextArea();
    static JButton buttonHelper = new JButton("Add");

    static JTextField xCoordinate = new JTextField("", 3);
    static JTextField yCoordinate = new JTextField("", 3);


    static java.util.List<JComponent> lastComponenetAdded = new ArrayList<JComponent>();

    public static void main(String[] args) {
        frame = new JFrame();

        frame.setSize(1000, 1000);
        controlPanel = new JPanel();
        itemPanel = new JPanel();

        String[] items = {"Button", "ComboBox", "Label", "TextArea"};
        final JComboBox chooser = new JComboBox(items);

        chooser.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
               typeChooser = (String) chooser.getSelectedItem();
               if(typeChooser.equals("Button") || typeChooser.equals("Label")){
                   controlPanel.remove(buttonHelper);
                   controlPanel.remove(textHelper);
                   controlPanel.add(textHelper);
                   textHelper.setText("");
                   frame.validate();
                   frame.repaint();
               }else if(typeChooser.equals("ComboBox")){
                   controlPanel.remove(buttonHelper);
                   controlPanel.remove(textHelper);
                   controlPanel.add(textHelper);
                   controlPanel.add(buttonHelper);
                   textHelper.setText("");
                   frame.validate();
                   frame.repaint();
               }else if(typeChooser.equals("TextArea")){
                   controlPanel.remove(buttonHelper);
                   controlPanel.remove(textHelper);
                   frame.validate();
                   frame.repaint();
               }
            }
        });

        JButton createButton = new JButton("Create");
        JButton undoButton = new JButton("Undo");

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(!xCoordinate.getText().equals("") && !yCoordinate.getText().equals("")) {

                    int x = Integer.parseInt(xCoordinate.getText());
                    int y = Integer.parseInt(yCoordinate.getText());

                    String item = "javax.swing.J" + chooser.getSelectedItem().toString();

                    try{
                        Class itemObject = Class.forName(item);
                        JComponent component = (JComponent) itemObject.getConstructor().newInstance();


                        if(typeChooser.equals("Button")) {
                            Method setText = JButton.class.getMethod("setText", String.class);
                            setText.invoke(component, textHelper.getText());
                        } else if(typeChooser.equals("Label")) {
                            Method setText = JLabel.class.getMethod("setText", String.class);
                            setText.invoke(component, textHelper.getText());
                        }
                          else if(typeChooser.equals("ComboBox")) {
                            Method setItems = JComboBox.class.getMethod("addItem", Object.class);
                            for(String text : listElemets){
                                setItems.invoke(component, text);
                            }
                        } else if(typeChooser.equals("TextArea")) {
                            Method setItems = JTextArea.class.getMethod("setColumns", int.class);
                            setItems.invoke(component, 10);

                        }

                        lastComponenetAdded.add(component);
                        component.setBounds(x, y, component.getPreferredSize().width,component.getPreferredSize().height);
                        itemPanel.add(component);
                        frame.validate();
                        frame.repaint();

                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (InstantiationException ex) {
                        ex.printStackTrace();
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    } catch (NoSuchMethodException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }

                }

                textHelper.setText("");
                xCoordinate.setText("");
                yCoordinate.setText("");
                listElemets.clear();
            }
        });

        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(lastComponenetAdded.size() > 0) {
                    itemPanel.remove(lastComponenetAdded.get(lastComponenetAdded.size() - 1));
                    lastComponenetAdded.remove(lastComponenetAdded.get(lastComponenetAdded.size() - 1));
                }
                frame.validate();
                frame.repaint();
            }
        });

        buttonHelper.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!textHelper.getText().equals("")){
                    listElemets.add(textHelper.getText());
                    textHelper.setText("");
                }
            }
        });

        textHelper.setColumns(20);

        itemPanel.setLayout(null);
        controlPanel.add(xCoordinate);
        controlPanel.add(yCoordinate);



        controlPanel.add(chooser);
        controlPanel.add(createButton);
        controlPanel.add(undoButton);
        controlPanel.add(textHelper);



        itemPanel.setBackground(Color.LIGHT_GRAY);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(itemPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}


