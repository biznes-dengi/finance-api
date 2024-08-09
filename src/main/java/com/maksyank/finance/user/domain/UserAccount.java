package com.maksyank.finance.user.domain;

import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.user.domain.base.BaseUser;
import com.maksyank.finance.user.domain.enums.AppRole;
import com.maksyank.finance.user.domain.enums.UserGender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user_account")
public class UserAccount extends BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_account")
    private int id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AppRole role;

    @Column(name = "email")
    private String email;

    @Column(name = "pass")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private Collection<Saving> savings;

    public UserAccount() {
    }

    @Override
    public String toString() {
        return "UserAccount(id=" + this.getId() + ", role=" + this.getRole() +
                ", email=" + this.getEmail() + ", password=" + this.getPassword() +
                ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() +
                ", gender=" + this.getGender() + ", dateOfBirth=" + this.getDateOfBirth() +
                ", phoneNumber=" + this.getPhoneNumber() + ", createdOn=" + this.getCreatedOn() +
                ", lastLogin=" + this.getLastLogin() + ")";
    }

    public int getId() {
        return this.id;
    }

    public AppRole getRole() {
        return this.role;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public UserGender getGender() {
        return this.gender;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    public LocalDateTime getLastLogin() {
        return this.lastLogin;
    }

    public Collection<Saving> getSavings() {
        return this.savings;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(AppRole role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setSavings(Collection<Saving> savings) {
        this.savings = savings;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof final UserAccount other)) return false;
        if (!other.canEqual(this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if (!Objects.equals(this$role, other$role)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (!Objects.equals(this$email, other$email)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (!Objects.equals(this$password, other$password)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (!Objects.equals(this$firstName, other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (!Objects.equals(this$lastName, other$lastName)) return false;
        final Object this$gender = this.getGender();
        final Object other$gender = other.getGender();
        if (!Objects.equals(this$gender, other$gender)) return false;
        final Object this$dateOfBirth = this.getDateOfBirth();
        final Object other$dateOfBirth = other.getDateOfBirth();
        if (!Objects.equals(this$dateOfBirth, other$dateOfBirth))
            return false;
        final Object this$phoneNumber = this.getPhoneNumber();
        final Object other$phoneNumber = other.getPhoneNumber();
        if (!Objects.equals(this$phoneNumber, other$phoneNumber))
            return false;
        final Object this$createdOn = this.getCreatedOn();
        final Object other$createdOn = other.getCreatedOn();
        if (!Objects.equals(this$createdOn, other$createdOn)) return false;
        final Object this$lastLogin = this.getLastLogin();
        final Object other$lastLogin = other.getLastLogin();
        if (!Objects.equals(this$lastLogin, other$lastLogin)) return false;
        final Object this$savings = this.getSavings();
        final Object other$savings = other.getSavings();
        return Objects.equals(this$savings, other$savings);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserAccount;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $gender = this.getGender();
        result = result * PRIME + ($gender == null ? 43 : $gender.hashCode());
        final Object $dateOfBirth = this.getDateOfBirth();
        result = result * PRIME + ($dateOfBirth == null ? 43 : $dateOfBirth.hashCode());
        final Object $phoneNumber = this.getPhoneNumber();
        result = result * PRIME + ($phoneNumber == null ? 43 : $phoneNumber.hashCode());
        final Object $createdOn = this.getCreatedOn();
        result = result * PRIME + ($createdOn == null ? 43 : $createdOn.hashCode());
        final Object $lastLogin = this.getLastLogin();
        result = result * PRIME + ($lastLogin == null ? 43 : $lastLogin.hashCode());
        final Object $savings = this.getSavings();
        result = result * PRIME + ($savings == null ? 43 : $savings.hashCode());
        return result;
    }
}
