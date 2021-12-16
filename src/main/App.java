package main;

import cli.Cli;

public class App {
	public static void main(String[] args) {
		Cli cli = Cli.getCli();
		
		cli.run();
	}
}
