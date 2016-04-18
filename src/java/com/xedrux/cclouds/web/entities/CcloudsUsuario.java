package com.xedrux.cclouds.web.entities;

import com.xedrux.cclouds.web.exceptions.UnableToCreateEntityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Isidro Rodríguez Gamez
 */
public class CcloudsUsuario {

    private long idUser = -1;
    @NotNull
    private long idRol;
    @NotNull
    @Size(min = 1, max = 25)
    private String username;
    @Size(min = 1, max = 255)
    private String password;
    @Size(max = 255)
    private String passwordResetToken;
    @Size(max = 255)
    private String phoneNumber;
    @NotNull
    @Size(min=2, max = 255)
    private String userEmail;
    @Size(max = 255)
    private String firstName;
    @Size(max = 255)
    private String lastName;
    private String sex;
    @NotNull
    private Date dateBirth;
    @Size(min = 1, max = 50)
    private String dbHash;
    @Size(min = 1, max = 10)
    private String plainTextPassword;
    private Long parroquia;
    @NotNull
    private boolean enabled;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

    public CcloudsUsuario() {
        
    }

    public CcloudsUsuario(long idUser) {
        this.idUser = idUser;
    }

    public CcloudsUsuario(long idUser, long idRol, String username,
            String password, Date dateBirth, String dbHash,
            String plainTextPassword) {

        this.idUser = idUser;
        this.idRol = idRol;
        this.username = username;
        this.password = password;
        this.dateBirth = dateBirth;
        this.dbHash = dbHash;
        this.plainTextPassword = plainTextPassword;
    }

    private void checkEmailSyntax(String email) throws UnableToCreateEntityException {
        if (!email.matches("^[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z_+])*@([0-9a-zA-Z][-\\w]*"
                + "[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9}$")) {
            throw new UnableToCreateEntityException("Invalid email address");
        }
    }

    public CcloudsUsuario(long idUser, long idRol, String username,
            String password, String passwordResetToken, String phoneNumber,
            String userEmail, String firstName, String lastName, String sex,
            Date dateBirth, String dbHash, String plainTextPassword,
            Long parroquia, boolean enabled) throws UnableToCreateEntityException {
        checkEmailSyntax(userEmail);
        this.idUser = idUser;
        this.idRol = idRol;
        this.username = username;
        this.password = password;
        this.passwordResetToken = passwordResetToken;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.dateBirth = dateBirth;
        this.dbHash = dbHash;
        this.plainTextPassword = plainTextPassword;
        this.parroquia = parroquia;
        this.enabled = enabled;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdRol() {
        return idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }   

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) throws UnableToCreateEntityException {
        System.out.println("UserEmail en el set: "+userEmail);
        System.out.println("UserEmail en el del objeto: "+this.userEmail);
        System.out.println("This "+this);
        checkEmailSyntax(userEmail);
        this.userEmail = userEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateBirth() {
        return dateFormatter.format(dateBirth);
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getDbHash() {
        return dbHash;
    }

    public void setDbHash(String dbHash) {
        this.dbHash = dbHash;
    }

    public String getPlainTextPassword() {
        return plainTextPassword;
    }

    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
    }

    public void setParroquia(Long parroquia) {
        this.parroquia = parroquia;
    }

    public Long getParroquia() {
        return parroquia;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

//    @Override
//    public String toString() {
//        return "test.CcloudsUsuario[ idUser=" + idUser + " ]";
//    }

}
