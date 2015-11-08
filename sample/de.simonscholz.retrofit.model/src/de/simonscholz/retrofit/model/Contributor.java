package de.simonscholz.retrofit.model;

public class Contributor {
	public final String login;
	public final int contributions;

	public Contributor(String login, int contributions) {
		this.login = login;
		this.contributions = contributions;
	}
}