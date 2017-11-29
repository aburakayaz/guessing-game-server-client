package cs408.Server;

public class Client {
	private int id;
	private String username;

	Client(int id) {
		this.id = id;
		username = "";
	}

	/**
	 * If the user has an username (which they don't have at
	 * the very first step of their connection) this function
	 * will return the clientId instead of username.
	 */
	public String getRefName() {
		if (username == null) {
			return String.valueOf(id);
		}

		return username;
	}

	public boolean hasUsername() {
		return !username.equals("");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
