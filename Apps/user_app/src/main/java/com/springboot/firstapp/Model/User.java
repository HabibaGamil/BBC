package com.springboot.firstapp.Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.internal.util.type.PrimitiveWrapperHelper.IntegerDescriptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString
@Entity
@Table(name = "user")
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "register", procedureName = "Signup",
								parameters = {
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "username", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "userpassword", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "useremail", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "usercountry", type = String.class),
										@StoredProcedureParameter(mode = ParameterMode.IN, name = "user_date_of_birth", type = Date.class),
										@StoredProcedureParameter(mode = ParameterMode.OUT, name = "validRegister", type = Integer.class)
								}),
				@NamedStoredProcedureQuery(name = "findByEmail", procedureName = "findByEmail",
				parameters = {
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "email", type = String.class)
				}),
				@NamedStoredProcedureQuery(name = "UpdatePassword", procedureName = "UpdatePassword",
				parameters = {
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "userid", type = Integer.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "new_password", type = String.class)
				})
})
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "user_name", nullable = false)
	private String user_name;
	
	@Column(name = "user_password", nullable = false)
	//@JsonIgnore
	private String password;
	
	@Column(name = "user_email", nullable = false)
	private String email;
	
	@Column(name = "user_country", nullable = false)
	private String country;
	
	@Column(name = "user_date_of_birth", nullable = false)
	private LocalDate date;
	
	@Column(name = "is_admin")
	private boolean isAdmin;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "preferences" ,
				joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name = "preference_id", referencedColumnName = "id"))
	private Set<Category> preferences;

	
	
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}



	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	
	
	

}
