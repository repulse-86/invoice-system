package main;

import cli.CLI;
import events.EventDispatcher;

public class Main {
	public static void main(String[] args) {
		EventDispatcher dispatcher = new EventDispatcher();
		CLI cli = new CLI(dispatcher);
		cli.start();
	}
}