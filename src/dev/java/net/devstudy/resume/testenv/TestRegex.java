package net.devstudy.resume.testenv;

import java.util.regex.Pattern;

public class TestRegex
{
	public static void main(String[] args)
	{
		System.out.println(Pattern.matches("^([+]{1})([0-9]{1,3})([0-9]{10})$", "+3805012345678"));
		System.out.println(Pattern.matches("^([+]{1})([0-9]{1,3})([0-9]{10})$", "3805012345678"));
		System.out.println(Pattern.matches("^([+]{1})([0-9]{1,3})([0-9]{10})$", "+3805012345678"));
	}
}
