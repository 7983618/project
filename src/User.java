/**
 * Esta clase define el objeto Usuario. Junto a sus atributos y metodos que
 * premiten trabajar con él.
 * 
 * @author Santiago Quispe
 * @version 0.1.0
 */
public class User {
	/** Nombre de usuario (No real) */
	private String username;
	/** Nombre real */
	private String name;
	/** Número de identificación fiscal */
	private String nif;
	/** Correo electrónico */
	private String email;
	/** Domicilio */
	private String address;
	/** Fecha de nacimiento */
	private String birthdate;
	/** Rol en el programa */
	private String role;

	/** Constructor por defecto */
	public User() {
		super();
	}

	/**
	 * Constructor con todos los atributos.
	 * 
	 * @param username  Nombre de usuario
	 * @param name      Nombre completo
	 * @param nif       NIF
	 * @param email     Correo electrónico
	 * @param address   Dirección
	 * @param birthdate Fecha de nacimiento
	 * @param role      Rol
	 */
	public User(String username, String name, String nif, String email, String address, String birthdate, String role) {
		super();
		this.username = username;
		this.name = name;
		this.nif = nif;
		this.email = email;
		this.address = address;
		this.birthdate = birthdate;
		this.role = role;
	}

	/** @return Representación en cadena del usuario */
	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", nif=" + nif + ", email=" + email + ", address="
				+ address + ", birthdate=" + birthdate + ", role=" + role + "]";
	}

	/** @return Nombre de usuario */
	public String getUsername() {
		return username;
	}

	/** @param username Nuevo nombre de usuario */
	public void setUsername(String username) {
		this.username = username;
	}

	/** @return Nombre real*/
	public String getName() {
		return name;
	}

	/** @param name Nuevo nombre real del usuario */
	public void setName(String name) {
		this.name = name;
	}

	/** @return Número de identificacion Fiscal */
	public String getNif() {
		return nif;
	}

	/** @param nif Nuevo Número de identificación fiscal del usuario */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/** @return Dirección de correo electrónico */
	public String getEmail() {
		return email;
	}

	/** @param email Nuevo nombre de usuario */
	public void setEmail(String email) {
		this.email = email;
	}

	/** @return Domicilio del usuario*/
	public String getAddress() {
		return address;
	}

	/** @param address Nuevo domicilio del usuario */
	public void setAddress(String address) {
		this.address = address;
	}

	/** @return Fecha de nacimiento */
	public String getBirthdate() {
		return birthdate;
	}

	/** @param birthdate Nueva fecha de nacimiento del usuario */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	/** @return Rol del usuario en el programa */
	public String getRole() {
		return role;
	}

	/** @param role Nuevo rol de usuario */
	public void setRole(String role) {
		this.role = role;
	}

}
