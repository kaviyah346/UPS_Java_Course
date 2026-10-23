
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SupermarketApp extends JFrame {

    JPanel mainPanel;
    CardLayout cardLayout;
    double grandTotal=0;

    Map<String,String[]> products=new LinkedHashMap<>();
    Map<String,Double[]> prices=new LinkedHashMap<>();

    SupermarketApp() {
        setTitle("Supermarket Shopping");
        setSize(500,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addProducts();

        cardLayout=new CardLayout();
        mainPanel=new JPanel(cardLayout);
        mainPanel.add(createMainMenu(),"MAIN");

        add(mainPanel);
        setVisible(true);
    }

    void addProducts() {
        products.put("Fruits",new String[]{"Apple (500 g)","Banana (1 kg)","Orange (500 g)"});
        prices.put("Fruits",new Double[]{100.0,60.0,50.0});

        products.put("Vegetables",new String[]{"Tomato (1 kg)","Potato (1 kg)","Onion (1 kg)"});
        prices.put("Vegetables",new Double[]{40.0,35.0,45.0});

        products.put("Dairy Products",new String[]{"Milk (1 litre)","Curd (500 g)","Butter (100 g)"});
        prices.put("Dairy Products",new Double[]{60.0,35.0,55.0});

        products.put("Snacks",new String[]{"Chips","Biscuits","Chocolate"});
        prices.put("Snacks",new Double[]{20.0,30.0,40.0});

        products.put("Beverages",new String[]{"Juice (1 litre)","Soft Drink (750 ml)","Tea Powder (250 g)"});
        prices.put("Beverages",new Double[]{100.0,45.0,120.0});

        products.put("Rice and Grains",new String[]{"Rice (1 kg)","Wheat Flour (1 kg)","Oats (500 g)"});
        prices.put("Rice and Grains",new Double[]{70.0,50.0,90.0});

        products.put("Personal Care",new String[]{"Soap","Shampoo","Toothpaste"});
        prices.put("Personal Care",new Double[]{35.0,120.0,90.0});

        products.put("Household Items",new String[]{"Detergent","Dishwash Liquid","Tissue Paper"});
        prices.put("Household Items",new Double[]{110.0,85.0,50.0});
    }

    JPanel createMainMenu() {
        JPanel panel=new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel title=new JLabel("SUPERMARKET",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,22));
        panel.add(title,BorderLayout.NORTH);

        JPanel categoryPanel=new JPanel(new GridLayout(4,2,10,10));

        for(String category:products.keySet()) {
            JButton button=new JButton(category);
            button.setFont(new Font("Arial",Font.PLAIN,15));
            button.addActionListener(e->showItems(category));
            categoryPanel.add(button);
        }

        panel.add(categoryPanel,BorderLayout.CENTER);

        JButton exitButton=new JButton("EXIT AND GENERATE BILL");
        exitButton.addActionListener(e->{
            JOptionPane.showMessageDialog(this,
                    String.format("Grand Total: Rs. %.2f\nThank you for shopping!",grandTotal),
                    "Final Bill",JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(exitButton,BorderLayout.SOUTH);
        return panel;
    }

    void showItems(String category) {
        JPanel panel=new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel title=new JLabel(category,SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,22));
        panel.add(title,BorderLayout.NORTH);

        JPanel itemPanel=new JPanel(new GridLayout(0,1,8,8));

        String[] items=products.get(category);
        Double[] itemPrices=prices.get(category);

        for(int i=0;i<items.length;i++) {
            final int index=i;
            JButton itemButton=new JButton(items[i]+" - Rs. "+itemPrices[i]);

            itemButton.addActionListener(e->{
                String input=JOptionPane.showInputDialog(this,
                        "Enter quantity for "+items[index]+":");

                if(input==null) {
                    return;
                }

                try {
                    int quantity=Integer.parseInt(input.trim());

                    if(quantity<=0) {
                        JOptionPane.showMessageDialog(this, "Enter a quantity greater than zero.");
                        return;
                    }

                    grandTotal+=itemPrices[index]*quantity;
                    JOptionPane.showMessageDialog(this,"Item added successfully!");

                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid whole number.");
                }
            });

            itemPanel.add(itemButton);
        }

        panel.add(itemPanel,BorderLayout.CENTER);

        JButton backButton=new JButton("Back to Categories");
        backButton.addActionListener(e->cardLayout.show(mainPanel,"MAIN"));
        panel.add(backButton,BorderLayout.SOUTH);

        mainPanel.add(panel,category);
        cardLayout.show(mainPanel,category);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SupermarketApp::new);
    }
}
