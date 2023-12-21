import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

class Product {
    private String id;
    private String pname;
    private int qty;
    private double price;
    private double totalPrice;

    public Product(String id, String pname, int qty, double price) {
        this.id = id;
        this.pname = pname;
        this.qty = qty;
        this.price = price;
        this.totalPrice = qty * price;
    }

    public String getId() {
        return id;
    }

    public String getPname() {
        return pname;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public static void displayFormat() {
        System.out.println("---------------------------------------------------------------------------");
        System.out.print("Product ID \t\tName\t\tQuantity\t\tRate \t\t\t\tTotal Price\n");
        System.out.println("---------------------------------------------------------------------------");
    }

    public void display() {
        System.out.format("   %-9s             %-9s      %5d               %9.2f                       %14.2f\n", id,
                pname, qty, price, totalPrice);
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class BillItem {
    private List<Product> products;

    public BillItem() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}

class DateFormatter {
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

        return "Date: " + formatter.format(date) + "  " + days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }
}

class InvoiceGenerator {
    public static void generateInvoice(BillItem billItem) {
        Product.displayFormat();
        double overAllPrice = 0.0;

        for (Product p : billItem.getProducts()) {
            p.display();
            overAllPrice += p.getTotalPrice();
        }

        double discount = overAllPrice * 0.02;
        double subtotal = overAllPrice - discount;
        double sgst = overAllPrice * 0.06; // Changed SGST rate to 6%
        double cgst = overAllPrice * 0.06; // Changed CGST rate to 6%
        double totalAmount = subtotal + cgst + sgst;

        System.out.println("\n\t\t\t\t\t\t\t\t\t\tTotal Amount (Rs.) " + overAllPrice);
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t    Discount (Rs.) " + discount);
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t          Subtotal " + subtotal);
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t          SGST (%) " + sgst);
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t          CGST (%) " + cgst);
        System.out.println("\n\t\t\t\t\t\t\t\t\t\t     Invoice Total " + totalAmount);
    }
}

class LaundryShop {
    private static final double SGST_RATE = 0.12;
    private static final double CGST_RATE = 0.12;

    public void processShoppingBill(Scanner scan, Customer customer, BillItem billItem) {
        char choice;
        do {
            System.out.println("Enter the product details: ");
            System.out.print("Jenis Pakaian: ");
            String id = scan.nextLine();
            System.out.print("Bahan Pakaian: ");
            String productName = scan.nextLine();
            System.out.print("Jumlah (pcs): ");
            int quantity = scan.nextInt();
            System.out.print("Price (per unit): ");
            double price = scan.nextDouble();

            Product product = new Product(id, productName, quantity, price);
            billItem.addProduct(product);

            System.out.print("Want to add more items? (y or n): ");
            choice = scan.next().charAt(0);
            scan.nextLine(); // consume the newline character
        } while (choice == 'y' || choice == 'Y');
    }

    public void generateBill(Scanner scan) {
        double overAllPrice = 0.0;

        System.out.println("\t\t\t\t--------------------Selamat Mencuci-----------------");
        System.out.println("\t\t\t\t\t " + "    " + " Laundry 3S Salwa, Sidik, Sabriin");

        System.out.println(DateFormatter.getCurrentDate());
        System.out.print("Enter Customer Name: ");
        Customer customer = new Customer(scan.nextLine());

        BillItem billItem = new BillItem();
        processShoppingBill(scan, customer, billItem);

        InvoiceGenerator.generateInvoice(billItem);

        System.out.println("\t\t\t\t----------------Terima kasih telah mencuci di Laundry kami!!-----------------");
        System.out.println("\t\t\t\t                           Silahkan berkunjung kembali");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        LaundryShop laundryShop = new LaundryShop();

        laundryShop.generateBill(scan);

        scan.close();
    }
}
