package net.devstudy.resume.util;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.WordUtils;
import org.joda.time.LocalDate;

import net.devstudy.resume.form.SignUpForm;

public class DataUtil
{
	private static final String UID_DELIMITER = "-";

	public static String normailizeName(String name)
	{
		return name.trim().toLowerCase();
	}

	public static String capitailizeName(String name)
	{
		return WordUtils.capitalize(normailizeName(name));
	}

	public static String generateProfileUid(SignUpForm form)
	{
		return normailizeName(form.getFirstName()) + UID_DELIMITER + normailizeName(form.getLastName());
	}

	public static String regenerateUidWithRandomSuffix(String baseUid, String alphabet, int letterCount)
	{
		return baseUid + UID_DELIMITER + generateRandomSuffix(alphabet, letterCount);
	}

	private static String generateRandomSuffix(String alphabet, int count)
	{
		Random random = new Random();
		StringBuilder suffix = new StringBuilder();
		for (int i = 0; i < count; i++)
		{
			char randomChar = alphabet.charAt(random.nextInt(alphabet.length()));
			suffix.append(randomChar);
		}

		return suffix.toString();
	}

	public static Date generateDateFromString(String dateStr)
	{
		String[] part = dateStr.split("-");
		int year = Integer.parseInt(part[0]);
		int month = Integer.parseInt(part[1]);
		int day = Integer.parseInt(part[2]);

		return new LocalDate(year, month, day).toDate();
	}
}