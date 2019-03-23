package com.sojson.common.controller;

import com.alibaba.druid.sql.ast.SQLPartitionValue.Operator;

public class Mains {
public static void main(String[] args) {
	/*String a ="hello";
	String b="word";
	String c="helloword";
	String d="helloword";
	System.out.println(d.substring(0,5).equals(a));
	System.out.println((a+b)==c);
	System.out.println(d.substring(0 ,5)==a);
	System.out.println((a+b).equals(c));
	System.out.println(a.contains("eo"));
	System.out.println(d.contains("owo"));
	System.out.println(a.concat("word").equals(c));*/
	StringBuffer a=new StringBuffer("A");
	StringBuffer b=new StringBuffer("B");
	Operator(a,b);
	System.out.println(a+","+b);
}

private static void Operator(StringBuffer x, StringBuffer y) {
	x.append(y);
	y=x;
	
}
	
}
