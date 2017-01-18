package lattelib;

import java.util.Random;
import java.util.Scanner;

import lattelib.WebLatte;

/**
 * @author Hadley Benjamin
 *
 */
//LAB QUESTIONS:
//is it possible to make an array of images?

public class Foodomatic {
    public static void main(String[] args) {
	// declarations
	WebLatte frame = new WebLatte();
	int numberofoptions;
	String firstinitial;
	String lastinitial;
	Random gen = new Random();
	String[] name1 = { "Lil'", "Big", "Monster", "Baby", "Megga", "Tiny", "Kid", "DJ", "Homie", "MC" };
	String[] name2 = { "Funky", "Fresh", "Fabulous", "Spunky", "Ferocious", "Fierce", "Savage", "Cutthroat",
		"Gusty", "Temputous" };
	String[] name3 = { "Dominator", "Dictator", "President", "Doctor", "Scientist", "Soldier", "Dog walker",
		"Dentist", "Orthodonist", "Agent", "Computer Scientist", "Professor", "Chemist" };
	int n1 = gen.nextInt(name1.length);
	int n2 = gen.nextInt(name2.length);
	int n3 = gen.nextInt(name3.length);


	// Print instructions
	frame.println(
		"Wassup homie! How ya doin' daug? A rapper's identity is expressed through his name so I'll help you find yourself. Let's get this party started!!!");

	// Ask first initial
	frame.println("Type the letter that your first names starts with.");
	firstinitial = frame.nextLine().toString();

	// Ask last  initial
	frame.println("Type the letter that your last names starts with.");
	lastinitial = frame.nextLine().toString();

	// Ask how many name options
	frame.println(
		"Oh what's that? You don't trust me? Fine. How many options do ya want? *$999,999 for each additional option.");
	numberofoptions = frame.nextLine().toInt();

	// Print rapper name
	frame.println("Welcome to the family! FIST BUMP! Here are your rapper names:");
	for (int i = 0; i < numberofoptions; i++) {
	    if (i < numberofoptions - 1) {
	    }
	    frame.println(firstinitial + ". " + lastinitial +  ". " + name1[gen.nextInt(name1.length)] + " "+ name2[gen.nextInt(name2.length)] + " " + name3[gen.nextInt(name3.length)]);

	}

    }
}
