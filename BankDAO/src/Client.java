public class Client {
	private int id;
	private String name;
	private String cnp;

	public Client(int id, String name, String cnp) {
		this.id = id;
		this.name = name;
		this.cnp = cnp;
	}

	public Client() {
	}

	public final void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public String getCnp() {
		return cnp;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		Client other = (Client) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "| ID:" + id + "| Name:" + name + "| CNP:" + cnp+" |";
	}

}
