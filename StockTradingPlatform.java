import java.util.ArrayList;
import java.util.Scanner;

class Stock {
    String symbol;
    String companyName;
    double price;

    Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }
}

class Transaction {
    String type;
    String stockSymbol;
    int quantity;
    double price;
    double amount;

    Transaction(String type, String stockSymbol, int quantity, double price) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.amount = quantity * price;
    }
    void display() {
        System.out.printf(
            "%-8s %-10s %-10d ₹%-10.2f ₹%-10.2f%n",
            type, stockSymbol, quantity, price, amount
        );
    }
}

class Holding {
    Stock stock;
    int quantity;
    double buyPrice;

    Holding(Stock stock, int quantity, double buyPrice) {
        this.stock = stock;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
    }

    double currentValue() {
        return quantity * stock.price;
    }

    double investmentValue() {
        return quantity * buyPrice;
    }

    double profitLoss() {
        return currentValue() - investmentValue();
    }
}
// ================= USER CLASS =================
class User {
    String name;
    double balance;

    ArrayList<Holding> portfolio = new ArrayList<>();
    ArrayList<Transaction> transactions = new ArrayList<>();

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    // Buy stock
    void buyStock(Stock stock, int quantity) {

        double totalCost = stock.price * quantity;

        if (quantity <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }

        if (balance < totalCost) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance -= totalCost;
        Holding existing = null;
        for (Holding h : portfolio) {
            if (h.stock.symbol.equals(stock.symbol)) {
                existing = h;
                break;
            }
        }
        if (existing != null) {

            int oldQuantity = existing.quantity;
            double oldInvestment = existing.buyPrice * oldQuantity;
            existing.quantity += quantity;
            existing.buyPrice =
                (oldInvestment + totalCost) / existing.quantity;
        } else {
            portfolio.add(
                new Holding(stock, quantity, stock.price)
            );
        }
        transactions.add(new Transaction("BUY", stock.symbol, quantity, stock.price));
        System.out.println("\nStock purchased successfully!");
        System.out.printf("Amount Paid: ₹%.2f%n", totalCost);
        System.out.printf("Remaining Balance: ₹%.2f%n", balance);
    }
    // Sell stock
    void sellStock(Stock stock, int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }
        Holding existing = null;
        for (Holding h : portfolio) {
            if (h.stock.symbol.equals(stock.symbol)) {
                existing = h;
                break;
            }
        }
        if (existing == null) {
            System.out.println("You don't own this stock!");
            return;
        }
        if (existing.quantity < quantity) {
            System.out.println("You don't have enough shares!");
            return;
        }

        double amount = stock.price * quantity;
        existing.quantity -= quantity;
        balance += amount;
        transactions.add(
            new Transaction("SELL", stock.symbol, quantity, stock.price)
        );
        if (existing.quantity == 0) {
            portfolio.remove(existing);
        }
        System.out.println("\nStock sold successfully!");
        System.out.printf("Amount Received: ₹%.2f%n", amount);
        System.out.printf("Current Balance: ₹%.2f%n", balance);
    }

    // Display portfolio
    void displayPortfolio() {

        if (portfolio.isEmpty()) {
            System.out.println("\nYour portfolio is empty.");
            return;
        }
        System.out.println(   "\n================ MY PORTFOLIO ================");
        System.out.printf("%-10s %-10s %-12s %-12s %-12s%n","Stock", "Quantity", "Buy Price","Current", "P/L");
        System.out.println("------------------------------------------------------------");
        double totalInvestment = 0;
        double totalCurrentValue = 0;
        for (Holding h : portfolio) {

            System.out.printf(
                "%-10s %-10d ₹%-11.2f ₹%-11.2f ₹%-11.2f%n",
                h.stock.symbol,
                h.quantity,
                h.buyPrice,
                h.stock.price,
                h.profitLoss()
            );
            totalInvestment += h.investmentValue();
            totalCurrentValue += h.currentValue();
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf( "Total Investment : ₹%.2f%n",totalInvestment);
        System.out.printf("Current Value    : ₹%.2f%n",totalCurrentValue);
        System.out.printf("Total Profit/Loss: ₹%.2f%n",totalCurrentValue - totalInvestment);
    }
    // Display transactions
    void displayTransactions() {

        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions yet.");
            return;
        }
        System.out.println( "\n================ TRANSACTION HISTORY ================");
        System.out.printf( "%-8s %-10s %-10s %-12s %-12s%n","Type", "Stock", "Quantity", "Price", "Amount");
        System.out.println("------------------------------------------------------------");
        for (Transaction t : transactions) {
            t.display();
        }
    }
}
// ================= MAIN CLASS =================
public class StockTradingPlatform {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Stock> market = new ArrayList<>();

    static User user;
    // Find stock by symbol
    static Stock findStock(String symbol) {
        for (Stock stock : market) {
            if (stock.symbol.equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }
    // Display market data
    static void displayMarket() {
        System.out.println(
            "\n================ MARKET DATA ================"
        );
        System.out.printf( "%-10s %-25s %-15s%n","Symbol", "Company", "Price" );
        System.out.println( "------------------------------------------------");
        for (Stock stock : market) {

            System.out.printf(
                "%-10s %-25s ₹%-14.2f%n",
                stock.symbol,
                stock.companyName,
                stock.price
            );
        }
    }

    static void buyStock() {

        displayMarket();

        System.out.print("\nEnter stock symbol: ");
        String symbol = sc.next();
        Stock stock = findStock(symbol);
        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        user.buyStock(stock, quantity);
    }

    // Sell operation
    static void sellStock() {
        if (user.portfolio.isEmpty()) {
            System.out.println("\nYour portfolio is empty.");
            return;
        }

        user.displayPortfolio();

        System.out.print("\nEnter stock symbol: ");
        String symbol = sc.next();

        Stock stock = findStock(symbol);

        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        user.sellStock(stock, quantity);
    }


    // Add money
    static void addMoney() {

        System.out.print("\nEnter amount to add: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        user.balance += amount;

        System.out.printf(
            "₹%.2f added successfully!%n",
            amount
        );

        System.out.printf(
            "Current Balance: ₹%.2f%n",
            user.balance
        );
    }

    // Main method
    public static void main(String[] args) {
        // Sample market data
        market.add(new Stock("TCS", "Tata Consultancy Services", 3500));
        market.add(new Stock("INFY", "Infosys", 1800));
        market.add(new Stock("RELIANCE", "Reliance Industries", 2900));
        market.add(new Stock("HDFC", "HDFC Bank", 1700));
        market.add( new Stock("WIPRO", "Wipro", 550));
        // Create user with initial balance
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        user = new User(name, 100000);
        int choice;
        do {
            System.out.println( "\n==============================================" );
            System.out.println( "STOCK TRADING PLATFORM");
            System.out.println("==============================================");
            System.out.println("Welcome, " + user.name);
            System.out.printf( "Balance: ₹%.2f%n",user.balance);
            System.out.println("----------------------------------------------");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Performance");
            System.out.println("6. View Transactions");
            System.out.println("7. Add Money");
            System.out.println("8. Exit");

            System.out.println("==============================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();


            switch (choice) {

                case 1:
                    displayMarket();
                    break;

                case 2:
                    buyStock();
                    break;

                case 3:
                    sellStock();
                    break;

                case 4:
                    user.displayPortfolio();
                    break;

                case 5:
                    user.displayPortfolio();
                    break;

                case 6:
                    user.displayTransactions();
                    break;

                case 7:
                    addMoney();
                    break;

                case 8:
                    System.out.println("\nThank you for using Stock Trading Platform!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 8);
        sc.close();
    }
}