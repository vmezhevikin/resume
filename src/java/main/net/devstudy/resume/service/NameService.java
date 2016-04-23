package net.devstudy.resume.service;

/**
 * @see http://devstudy.net
 */
public class NameService
{
	private static NameService instance = null;

	private NameService()
	{
	}

	public static NameService getInstance()
	{
		if (instance == null)
			instance = new NameService();
		return instance;
	}

	public String convertName(String name)
	{
		return name.toUpperCase();
	}
}
