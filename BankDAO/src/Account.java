public class Account {
	private int id;
	private String cnp;
	private double sumOfMoney;

	public Account(int id, String cnp, double sumOfMoney) {
		this.id = id;
		this.cnp = cnp;
		this.sumOfMoney = sumOfMoney;
	}
	
	public Account(){
		
	}
	
	public int getId() {
		return id;
	}

	public double getSumOfMoney() {
		return sumOfMoney;
	}

	public void setSumOfMoney(double sumOfMoney) {
		this.sumOfMoney = sumOfMoney;
	}

	public String getCnp() {
		return cnp;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnp == null) ? 0 : cnp.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		Account other = (Account) obj;
		if (cnp == null) {
			if (other.cnp != null)
				return false;
		} else if (!cnp.equals(other.cnp))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account ID:" + id + "\n " + "CNP:=" + cnp + ", Sum:"
				+ sumOfMoney;
	}

}
