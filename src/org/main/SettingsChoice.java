package org.main;

public enum SettingsChoice {
	JAVA(".java"), TXT(".txt"), TEX(".tex"), SGML(".sgml"), CS(".cs");

	private final String extension;

	SettingsChoice(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}

}
