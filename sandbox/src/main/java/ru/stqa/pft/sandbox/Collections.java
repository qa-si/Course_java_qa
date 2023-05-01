package ru.stqa.pft.sandbox;

import org.apache.tools.ant.taskdefs.Java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

	public static void main(String[] args) {
		String[] langs = {"Java", "C#", "Python", "PHP"};

		List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP");

		for (String l : languages) {
			System.out.println("Learn " + l);
		}
	}
}
