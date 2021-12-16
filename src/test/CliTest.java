package test;

import cli.Cli;

public class CliTest {
	public static void main(String[] args) {
		Cli cli = Cli.getCli();
		
		cli.run();
	}
}
