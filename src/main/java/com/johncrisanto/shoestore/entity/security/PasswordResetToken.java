package com.johncrisanto.shoestore.entity.security;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.johncrisanto.shoestore.entity.User;

@Entity
public class PasswordResetToken {
	
	private static final int EXPIRATIONOFTOKEN = 60 * 24; // One day in minutes
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long passwordResetTokenId;
	
	private String token;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable=false, name="user_id")
	private User user;
	
	private Date expiryDate;
	
	public PasswordResetToken() {
		
	}
	
	public PasswordResetToken(final String token, final User user) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate(EXPIRATIONOFTOKEN);
	}
	
	private Date calculateExpiryDate(final int expiringTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE,  expiringTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}
	
	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATIONOFTOKEN);
	}

	public Long getPasswordResetTokenId() {
		return passwordResetTokenId;
	}

	public void setPasswordResetTokenId(Long passwordResetTokenId) {
		this.passwordResetTokenId = passwordResetTokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static int getExpirationoftoken() {
		return EXPIRATIONOFTOKEN;
	}

	@Override
	public String toString() {
		return "PasswordResetToken [id=" + passwordResetTokenId + ", token=" + token + ", user=" + user + ", expiryDate=" + expiryDate
				+ "]";
	}
	
	
	
	

}
