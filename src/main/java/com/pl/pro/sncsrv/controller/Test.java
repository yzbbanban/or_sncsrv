package com.pl.pro.sncsrv.controller;

public class Test {
	public static void main(String args[]) 
	{
		String regex="(0x[0-9A-Fa-f]{2}[\\s]{1})+(0x[0-9A-Fa-f]{2}){1}";
		String arr="0x0Aa 0x0B";
		System.out.println(arr.matches(regex));
	}
}
