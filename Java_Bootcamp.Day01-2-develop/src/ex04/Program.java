package ex04;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {

        TransactionsService facade = new TransactionsService();
        System.out.println("___________________________________");
        System.out.println("Создаем 2 пользователей: ");
        User user1 = new User("Steve Jobs", 40_000_000);
        User user2 = new User("Bill Gates", 50_000_000);
        System.out.println(user1);
        System.out.println(user2);

        facade.addUser(user1);
        facade.addUser(user2);
        System.out.println("___________________________________");
        System.out.println("Проведем 3 операции и выведем баланс пользователей: ");
        facade.executeTransaction(user1.getId(), user2.getId(), 5_000_000);
        facade.executeTransaction(user1.getId(), user2.getId(), 500_000);
        facade.executeTransaction(user2.getId(), user1.getId(), 1_000_000);

        System.out.println("Balance " + user1.getName() + " = " + facade.getUserBalance(user1) + "$");
        System.out.println("Balance " + user2.getName() + " = " + facade.getUserBalance(user2.getId()) + "$");
        System.out.println("___________________________________");
        System.out.println("Выведем информацию о всех транзакциях пользователя " + user1.getName());
        Transaction[] arrayTransactionUser1 = facade.getTransactionList(user1.getId());
        for (int i = 0; i < arrayTransactionUser1.length; i++) {
            arrayTransactionUser1[i].printTransferInfo();
        }
        System.out.println("___________________________________");
        System.out.println("Удалим транзацию пользователя " + user1.getName());
        UUID idTr = arrayTransactionUser1[1].getIdTransaction();
        facade.removeTransactionById(idTr, user1.getId());
        arrayTransactionUser1 = facade.getTransactionList(user1.getId());
        for (int i = 0; i < arrayTransactionUser1.length; i++) {
            arrayTransactionUser1[i].printTransferInfo();
        }
        System.out.println("___________________________________");
        System.out.println("Выведем информацию о всех транзакциях пользователя " + user2.getName());
        Transaction[] arrayTransactionUser2 = facade.getTransactionList(user2.getId());
        for (int i = 0; i < arrayTransactionUser2.length; i++) {
            arrayTransactionUser2[i].printTransferInfo();
        }

        System.out.println("___________________________________");
        System.out.println("Выведем информацию непарных транзакциях");
        Transaction[] unpairedTransactions = facade.getInvalidTransaction();
        for (int i = 0; i < unpairedTransactions.length; i++) {
            unpairedTransactions[i].printTransferInfo();
        }

        System.out.println("___________________________________");
        System.out.println("Проверка срабатывания исключений:");
        try {
            facade.executeTransaction(user1.getId(), user2.getId(), 50_000_000);
        } catch (TransactionNotFoundException ex) {
            System.out.println(ex);
        }
        User noname = new User("NoName", 100);
        try {
            facade.getUserBalance(noname);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Конец программы");
    }
}
