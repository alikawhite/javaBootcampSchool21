package ex04;

public class UserArrayList implements UserList {
    private int arraySize = 10;
    private User[] users = new User[arraySize];
    private int count = 0;

    @Override
    public void addUser(User newUser) {
        if (this.count == this.arraySize) {
            User[] users = new User[arraySize * 2];
            if (this.arraySize >= 0) System.arraycopy(this.users, 0, users, 0, this.arraySize);
            this.arraySize = this.arraySize * 2;
            this.users = users;
        }
        this.users[count++] = newUser;
    }

    @Override
    public User getUserById(int id) {
        for (int i = 0; i < this.count; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User getUserByIndex(int index) {
        if (index <= this.count && index >= 0) {
            return users[index];
        }
        throw new UserNotFoundException("User with index " + index + " not found");
    }

    @Override
    public int getUserCount() {
        return this.count;
    }

    public void printInfo(){
        for (int i = 0; i < this.count; i++){
            System.out.print(i + "\tName: " + users[i].getName() + "  balance: " + users[i].getBalance());
            System.out.println("\tid: " + users[i].getId());
        }
    }
}
