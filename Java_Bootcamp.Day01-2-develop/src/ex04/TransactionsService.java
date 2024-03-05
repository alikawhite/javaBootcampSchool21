package ex04;

import java.util.UUID;

public class TransactionsService {
    TransactionsList transactionsList = new TransactionsLinkedList();
    UserList userList = new UserArrayList();
    private TransactionsLinkedList invalidTransactionList = new TransactionsLinkedList();

    public void addUser(User user) {
        this.userList.addUser(user);
    }

    public int getUserBalance(int id) {
        return userList.getUserById(id).getBalance();
    }

    public int getUserBalance(User user) {
        int idUser = user.getId();
        for (int i = 0; i < userList.getUserCount(); i++) {
            if (idUser == userList.getUserByIndex(i).getId()) {
                return user.getBalance();
            }
        }
        throw new UserNotFoundException("User not found");
    }

    public void executeTransaction(int senderId, int recipientId, int amount) {
        User sender = userList.getUserById(senderId);
        User recipient = userList.getUserById(recipientId);

        if (senderId == recipientId || amount < 0 || sender.getBalance() < amount) {
            throw new TransactionNotFoundException("Illegal Transaction.");
        }

        Transaction credit = new Transaction(sender.getName(), recipient.getName(), -amount, TransferCategory.OUTCOME);
        Transaction debit = new Transaction(sender.getName(), recipient.getName(), amount, TransferCategory.INCOME);

        debit.setIdentifier(credit.getIdTransaction());

        recipient.getTransactionsList().addTransaction(debit);
        sender.getTransactionsList().addTransaction(credit);

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
    }

    public Transaction[] getTransactionList(int userId) {
        return userList.getUserById(userId).getTransactionsList().toArray();
    }

    public void removeTransactionById(UUID transactionId, int userId) {
        userList.getUserById(userId).getTransactionsList().deleteTransaction(transactionId);
    }

    public Transaction[] getInvalidTransaction() {
        TransactionsLinkedList listTransOfAllUsers = getAllTransactionList();
        TransactionsLinkedList result = new TransactionsLinkedList();
        Transaction[] arrayFirst = listTransOfAllUsers.toArray();
        if (arrayFirst != null) {
            for (Transaction value : arrayFirst) {
                int count = 0;
                for (Transaction transaction : arrayFirst) {
                    if (value.getIdTransaction().equals(transaction.getIdTransaction())) count++;
                }
                if (count != 2) result.addTransaction(value);
            }
        }
        return result.toArray();
    }

    private TransactionsLinkedList getAllTransactionList() {
        TransactionsLinkedList listTransOfAllUsers = new TransactionsLinkedList();

        for (int i = 0; i < userList.getUserCount(); i++) {
            User user = userList.getUserByIndex(i);
            if (user != null) {
                int size = user.getTransactionsList().getSize();
                for (int j = 0; j < size; j++) {
                    listTransOfAllUsers.addTransaction(user.getTransactionsList().toArray()[j]);
                }
            }
        }
        return listTransOfAllUsers;
    }
}
