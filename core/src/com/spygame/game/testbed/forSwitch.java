package com.spygame.game.testbed;

import java.util.ArrayList;

public class forSwitch {
	public static void main(String[] args) {
		ArrayList<Integer> integer = new ArrayList<Integer>();
		int a = 2;
		
		integer.add(0);
		integer.add(0);
		integer.add(0);
		integer.add(1);
		integer.add(2);
		
		switch(a) {
		case 2: for (int i : integer) {
					if(i != 0) {
						break;
					}
					System.out.println(i);
				}
			break;
		default:
			break;
		}
	}
}
