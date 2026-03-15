import service.BankingService;
import dao.TransactionDAO;

import java.util.Scanner;

public class BankingApp {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        BankingService service = new BankingService();
        TransactionDAO transactionDAO = new TransactionDAO();

        while(true){
            System.out.println();
            System.out.println("==============================");
            System.out.println("1 Create Customer Account");
            System.out.println("2 Deposit");
            System.out.println("3 Withdraw");
            System.out.println("4 Transaction History");
            System.out.println("5 Transfer Money");
            System.out.println("6 Exit");
            System.out.print("Enter your Choice : ");
            int choice=sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    System.out.print("Enter Name:");
                    String name = sc.nextLine();

                    System.out.print("Enter Phone:");
                    String phone = sc.nextLine();

                    System.out.print("Enter Email:");
                    String email = sc.nextLine();

                    System.out.print("Enter Address:");
                    String address = sc.nextLine();

                    System.out.print("Enter Initial Balance:");
                    double balance = sc.nextDouble();

                    service.createCustomerAccount(name,phone,email,address,balance);
                    break;

                case 2:
                    System.out.print("Account No:");
                    int acc1=sc.nextInt();

                    System.out.print("Amount:");
                    double amt1=sc.nextDouble();

                    service.deposit(acc1,amt1);
                    break;

                case 3:
                    System.out.print("Account No:");
                    int acc2=sc.nextInt();

                    System.out.print("Amount:");
                    double amt2=sc.nextDouble();

                    service.withdraw(acc2,amt2);
                    break;

                case 4:
                    System.out.print("Account No:");
                    int acc3=sc.nextInt();

                    transactionDAO.showTransactions(acc3);
                    break;

                case 5:

                    System.out.println("Sender Account:");
                    int fromAcc = sc.nextInt();

                    System.out.println("Receiver Account:");
                    int toAcc = sc.nextInt();

                    System.out.println("Amount:");
                    double amount = sc.nextDouble();

                    service.transferMoney(fromAcc, toAcc, amount);

                    break;

                case 6:
                    System.exit(0);
            }
        }
    }
}