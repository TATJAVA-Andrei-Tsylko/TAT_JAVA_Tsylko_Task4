package com.epam.tsylko.andrei.entity;


import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable{
    private static final long serialVersionUID = 186040757636720271L;

    private int id;
    private String login;
    private String password;
    private boolean status;

    private String userName;
    private String userSurname;
    private Date birthday;
    private Address address;
    private String email;
    private String phone;
    private Role role;


    public User() {

    }

    public User(int id, String login, String password, String userName, String userSurname, Date birthday, Address address, String email, String phone) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.userSurname = userSurname;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.phone = phone;

    }

    public User(int id, String login, String password, boolean status, String userName, String userSurname, Date birthday, Address address, String email, String phone) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.status = status;
        this.userName = userName;
        this.userSurname = userSurname;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public User(int id, Role role) {
        this.id = id;
        this.role = role;
    }

    public User(int id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (status != user.status) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (userSurname != null ? !userSurname.equals(user.userSurname) : user.userSurname != null) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userSurname != null ? userSurname.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", status=").append(status);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userSurname='").append(userSurname).append('\'');
        sb.append(", birthday=").append(birthday);
        sb.append(", address=").append(address);
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
