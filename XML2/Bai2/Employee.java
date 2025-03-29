package XML2.Bai2;

public class Employee {
    private int id;
    private String name;
    private Contact contact;
    private Department department;

    public Employee() {
    }

    public Employee(int id, String name, Contact contact, Department department) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String toString() {
        return "ID: " + id + "Name: " + name + "Contact: " + contact + "Department: " + department;
    }
}
