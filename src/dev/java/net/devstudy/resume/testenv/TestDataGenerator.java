package net.devstudy.resume.testenv;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;

public class TestDataGenerator
{
	// private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://localhost/resume";
	private static final String USER = "resume";
	private static final String PASS = "password";

	private static final String PHOTO_DIR = "external/test-data/photos/";
	private static final String CERTIFICATES_DIR = "external/test-data/certificates/";
	private static final String MEDIA_DIR = "src/main/webapp/media";

	private static final String PASSWORD_HASH = "$2a$10$q7732w6Rj3kZGhfDYSIXI.wFp.uwTSi2inB2rYHvm1iDIAf1J1eVq";
	private static final String COUNTRY = "Ukraine";
	private static final String[] CITIES = { "Kharkiv", "Kyiv", "Odessa", "Poltava" };
	private static final String[] LANGUAGES = { "English", "German", "French" };
	private static final String[] LANGUAGE_TYPE = { "All", "Writing", "Speaking" };
	private static final String[] LANGUAGE_LEVEL = { "Beginner", "Elementary", "Pre-intrmediate", "Intermediate",
			"Upper-Intermediate", "Advanced", "Proficiency" };
	private static final String[] HOBBIES = { "Cycling", "Handball", "Football", "Basketball", "Bowling", "Boxing",
			"Volleyball", "Baseball", "Skating", "Skiing", "Table tennis", "Tennis", "Weightlifting", "Automobiles",
			"Book reading", "Cricket", "Photo", "Shopping", "Cooking", "Codding", "Animals", "Traveling", "Movie",
			"Painting", "Darts", "Fishing", "Kayak slalom", "Games of chance", "Ice hockey", "Roller skating",
			"Swimming", "Diving", "Golf", "Shooting", "Rowing", "Camping", "Archery", "Pubs", "Music", "Computer games",
			"Authorship", "Singing", "Foreign lang", "Billiards", "Skateboarding", "Collecting", "Badminton", "Disco" };
	private static final String[] SKILLS_CATEGORIES = { "Languages", "DBMS", "Web", "Java", "IDE", "CVS", "Web Servers",
			"Build system", "Cloud" };
	private static final String[] SKILLS_DESC = { "Java, SQL, PLSQL", "Mysql, Postgresql",
			"HTML, CSS, JS, Foundation, JQuery, Bootstrap",
			"Threads, IO, JAXB, GSON, Servlets, Logback, JSP, JSTL, JDBC, Apache Commons, Google+ Social API, Spring MVC, Spring Data JPA, Spring Security, Hibernate JPA, Facebook Social API",
			"Eclipse for JEE Developer", "Github", "Tomcat, Nginx", "Maven", "OpenShift, AWS" };
	private static final String TEXT = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et "
			+ "magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede "
			+ "justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. "
			+ "Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. "
			+ "Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi "
			+ "vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet "
			+ "adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero "
			+ "venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. "
			+ "Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, "
			+ "mollis sed, nonummy id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices "
			+ "posuere cubilia Curae; In ac dui quis mi consectetuer lacinia. Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Sed "
			+ "aliquam ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris. Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy "
			+ "metus. Vestibulum volutpat pretium libero. Cras id dui. Aenean ut";
	private static final String[] OBJECTIVES = { "Junior java trainee position", "Junior java developer position" };
	private static final String[] SUMMARIES = { "Java core course with developing one simple console application",
			"One Java professional course with developing web application blog",
			"Two Java professional courses with developing two web applications: blog and resume",
			"Three Java professional courses with developing one console application and two web applications: blog and resume" };
	private static final String[] COMPANIES = { "TeamDev", "INSART", "NIXSolutions", "DataArt", "EPAMSystems",
			"GlobalLogic" };
	private static final String[] POSITIONS = { "Junior java developer", "Java trainee" };
	private static final String[] UNIVERSITIES = { "National Technical University", "Karazin Kharkiv National University", "Kharkiv National University of Radioelectronics" };
	private static final String[] DEPARTMENTS = { "Computer-driven system and network", "Programming systems", "Intelligence system of problem-solving" };

	private static final Random rand = new Random();
	private static List<Profile> profiles;
	private static List<Certificate> certificates;
	private static List<String> sentences;

	private static void clearDestinationFolder() throws IOException
	{
		System.out.println("Clearing destination folder");

		Path path = Paths.get(MEDIA_DIR);
		if (Files.exists(path))
		{
			Files.walkFileTree(path, new SimpleFileVisitor<Path>()
			{
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
				{
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
				{
					return FileVisitResult.CONTINUE;
				}
			});
		}

		System.out.println("Destination folder cleared");
	}

	private static void clearDB(Connection connection) throws SQLException
	{
		System.out.println("Clearing DB");

		Statement statement = connection.createStatement();
		// cascade deleting rows in all tables
		statement.executeUpdate("delete from profile");
		statement.executeUpdate("delete from skill_category");
		// initializing sequences
		statement.executeQuery("select setval('certificate_seq', 1, false)");
		statement.executeQuery("select setval('course_seq', 1, false)");
		statement.executeQuery("select setval('education_seq', 1, false)");
		statement.executeQuery("select setval('experience_seq', 1, false)");
		statement.executeQuery("select setval('language_seq', 1, false)");
		statement.executeQuery("select setval('profile_seq', 1, false)");
		statement.executeQuery("select setval('skill_category_seq', 1, false)");
		statement.executeQuery("select setval('skill_seq', 1, false)");
		statement.executeQuery("select setval('hobby_seq', 1, false)");

		System.out.println("DB cleared");
	}

	private static void prepareData()
	{
		System.out.println("Preparing data");

		File[] photos = new File(PHOTO_DIR).listFiles();
		profiles = new ArrayList<>(photos.length);
		for (File file : photos)
		{
			Profile profile = new Profile();
			String[] names = file.getName().replace(".jpg", "").split(" ");
			profile.firstName = names[0];
			profile.lastName = names[1];
			profile.img = file.getAbsolutePath();

			profiles.add(profile);
		}

		photos = new File(CERTIFICATES_DIR).listFiles();
		certificates = new ArrayList<>(photos.length);
		for (File file : photos)
		{
			Certificate certificate = new Certificate();
			certificate.description = file.getName().replace(".jpg", "");
			certificate.img = file.getAbsolutePath();

			certificates.add(certificate);
		}

		sentences = Arrays.asList(TEXT.split(". "));
	}

	private static void insertData(Connection connection) throws SQLException, IOException
	{
		System.out.println("Inserting data");

		insertSkillCategory(connection);
		insertProfiles(connection);

		int profileNumber = profiles.size();
		for (int id = 1; id <= profileNumber; id++)
		{
			insertSkill(connection, id);
			insertHobby(connection, id);
			insertCertificate(connection, id);
			insertLanguage(connection, id);
			insertExperience(connection, id);
			insertEducation(connection, id);
			insertCourse(connection, id);
		}

		System.out.println("Data inserted");
	}

	private static void insertProfiles(Connection connection) throws SQLException, IOException
	{
		String sql = "insert into profile "
				+ "(id, uid, password, active, first_name, last_name, country, city, birthday, "
				+ "email, phone, skype, vkontakte, facebook, linkedin, github, stackoverflow, "
				+ "additional_info, objective, summary, photo, photo_small) "
				+ "values (nextval('profile_seq'),?,?,true,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		for (Profile profile : profiles)
		{
			statement.setString(1, (profile.firstName + "-" + profile.lastName).toLowerCase());
			statement.setString(2, PASSWORD_HASH);
			statement.setString(3, profile.firstName);
			statement.setString(4, profile.lastName);
			statement.setString(5, COUNTRY);
			statement.setString(6, CITIES[rand.nextInt(CITIES.length)]);
			statement.setDate(7, generateBirthday());
			statement.setString(8, (profile.firstName + "." + profile.lastName + "@gmail.com").toLowerCase());
			statement.setString(9, generatePhone());
			statement.setString(10, (profile.firstName + "." + profile.lastName).toLowerCase());

			if (rand.nextBoolean())
				statement.setString(11, ("vk.com/" + profile.lastName).toLowerCase());
			else
				statement.setNull(11, Types.VARCHAR);

			if (rand.nextBoolean())
				statement.setString(12, ("facebook.com/" + profile.lastName).toLowerCase());
			else
				statement.setNull(12, Types.VARCHAR);

			if (rand.nextBoolean())
				statement.setString(13, ("linkedin.com/" + profile.lastName).toLowerCase());
			else
				statement.setNull(13, Types.VARCHAR);

			if (rand.nextBoolean())
				statement.setString(14, ("github.com/" + profile.lastName).toLowerCase());
			else
				statement.setNull(14, Types.VARCHAR);

			if (rand.nextBoolean())
				statement.setString(15, ("stackoverflow.com/" + profile.lastName).toLowerCase());
			else
				statement.setNull(15, Types.VARCHAR);

			if (rand.nextBoolean())
				statement.setString(16, sentences.get(rand.nextInt(sentences.size())));
			else
				statement.setNull(16, Types.VARCHAR);

			statement.setString(17, OBJECTIVES[rand.nextInt(OBJECTIVES.length)]);
			statement.setString(18, SUMMARIES[rand.nextInt(SUMMARIES.length)]);

			String uuid = UUID.randomUUID().toString() + ".jpg";
			File photo = new File(MEDIA_DIR + "/avatar/" + uuid);
			if (!photo.getParentFile().exists())
				photo.getParentFile().mkdirs();

			Files.copy(Paths.get(profile.img), Paths.get(photo.getAbsolutePath()));

			statement.setString(19, "/media/avatar/" + uuid);

			String smallUuid = uuid.replace(".jpg", "-sm.jpg");
			Thumbnails.of(photo).size(100, 100).toFile(new File(MEDIA_DIR + "/avatar/" + smallUuid));

			statement.setString(20, "/media/avatar/" + smallUuid);

			// System.out.println(statement);
			statement.addBatch();
		}

		statement.executeBatch();
		statement.close();
	}

	private static Date generateBirthday()
	{
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.DAY_OF_MONTH, rand.nextInt(30));
		cl.set(Calendar.MONTH, rand.nextInt(12));
		int year = cl.get(Calendar.YEAR) - 30;
		cl.set(Calendar.YEAR, year + rand.nextInt(10));
		return new Date(cl.getTimeInMillis());
	}

	private static String generatePhone()
	{
		StringBuilder phone = new StringBuilder("+38050");
		for (int i = 0; i < 7; i++)
		{
			int code = '0' + rand.nextInt(10);
			phone.append(((char) code));
		}
		return phone.toString();
	}

	private static void insertSkillCategory(Connection connection) throws SQLException
	{
		String sql = "insert into skill_category (id, name) values (nextval('skill_category_seq'),?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		for (int i = 0; i < SKILLS_CATEGORIES.length; i++)
		{
			statement.setString(1, SKILLS_CATEGORIES[i]);
			// System.out.println(statement);
			statement.addBatch();
		}

		statement.executeBatch();
		statement.close();
	}

	private static void insertSkill(Connection connection, int id) throws SQLException
	{
		String sql = "insert into skill (id, id_profile, category, description) values (nextval('skill_seq'),?,?,?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		for (int i = 0; i < SKILLS_CATEGORIES.length; i++)
		{
			statement.setLong(1, id);
			statement.setString(2, SKILLS_CATEGORIES[i]);
			statement.setString(3, SKILLS_DESC[i]);
			// System.out.println(statement);
			statement.addBatch();
		}

		statement.executeBatch();
		statement.close();
	}

	private static void insertExperience(Connection connection, int id) throws SQLException
	{
		String sql = "insert into experience (id, id_profile, company, position, starting_date, completion_date, responsibility, demo, code) "
				+ "values (nextval('experience_seq'),?,?,?,?,?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		int count = rand.nextInt(3);
		for (int i = 0; i < count; i++)
		{
			statement.setLong(1, id);
			statement.setString(2, COMPANIES[rand.nextInt(COMPANIES.length)]);
			statement.setString(3, POSITIONS[rand.nextInt(POSITIONS.length)]);
			statement.setDate(4, generateDate());
			if (rand.nextBoolean())
				statement.setDate(5, generateDate());
			else
				statement.setNull(5, Types.DATE);
			statement.setString(6, sentences.get(rand.nextInt(sentences.size())));
			statement.setString(7, "http://LINK_TO_DEMO_SITE");
			statement.setString(8, "http://github.com/TODO");
			// System.out.println(statement);
			statement.addBatch();
		}

		statement.executeBatch();
		statement.close();
	}

	private static Date generateDate()
	{
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.DAY_OF_MONTH, rand.nextInt(30));
		cl.set(Calendar.MONTH, rand.nextInt(12));
		int year = cl.get(Calendar.YEAR);
		cl.set(Calendar.YEAR, year - rand.nextInt(10));
		return new Date(cl.getTimeInMillis());
	}
	
	private static int generateYear()
	{
		Calendar cl = Calendar.getInstance();
		int year = cl.get(Calendar.YEAR);
		return year - rand.nextInt(10) - 3;
	}

	private static void insertCertificate(Connection connection, int id) throws SQLException, IOException
	{
		String sql = "insert into certificate " + "(id, id_profile, description, img, img_small) "
				+ "values (nextval('certificate_seq'),?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		for (Certificate certificate : certificates)
		{
			if (rand.nextBoolean())
			{
				statement.setLong(1, id);
				statement.setString(2, certificate.description);

				String uuid = UUID.randomUUID().toString() + ".jpg";
				File photo = new File(MEDIA_DIR + "/certificate/" + uuid);
				if (!photo.getParentFile().exists())
					photo.getParentFile().mkdirs();

				Files.copy(Paths.get(certificate.img), Paths.get(photo.getAbsolutePath()));
				statement.setString(3, "/media/certificate/" + uuid);

				String smallUuid = uuid.replace(".jpg", "-sm.jpg");
				Thumbnails.of(photo).size(100, 100).toFile(new File(MEDIA_DIR + "/certificate/" + smallUuid));

				statement.setString(4, "/media/certificate/" + smallUuid);

				// System.out.println(statement);
				statement.addBatch();
			}
		}

		statement.executeBatch();
		statement.close();
	}

	private static void insertCourse(Connection connection, int id) throws SQLException
	{
		String sql = "insert into course (id, id_profile, description, school, completion_date) "
				+ "values (nextval('course_seq'),?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setLong(1, id);
		statement.setString(2, "Java basic");
		statement.setString(3, "SourceIt");
		if (rand.nextBoolean())
			statement.setDate(4, generateDate());
		else
			statement.setNull(4, Types.DATE);
		System.out.println(statement);

		statement.execute();
		statement.close();
	}

	private static void insertEducation(Connection connection, int id) throws SQLException
	{
		String sql = "insert into education (id, id_profile, university, department, starting_year, completion_year) "
				+ "values (nextval('education_seq'),?,?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setLong(1, id);
		statement.setString(2, UNIVERSITIES[rand.nextInt(UNIVERSITIES.length)]);
		statement.setString(3, DEPARTMENTS[rand.nextInt(DEPARTMENTS.length)]);
		int year = generateYear();
		statement.setInt(4, year);
		if (rand.nextBoolean())
			statement.setInt(5, year + 3);
		else
			statement.setNull(5, Types.INTEGER);
		//System.out.println(statement);

		statement.execute();
		statement.close();
	}

	private static void insertHobby(Connection connection, int id) throws SQLException
	{
		String sql = "insert into hobby (id, id_profile, description) values (nextval('hobby_seq'),?,?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		int count = rand.nextInt(5);
		for (int i = 0; i < count; i++)
		{
			statement.setLong(1, id);
			statement.setString(2, HOBBIES[rand.nextInt(HOBBIES.length)]);
			// System.out.println(statement);
			statement.addBatch();
		}

		statement.executeBatch();
		statement.close();
	}

	private static void insertLanguage(Connection connection, int id) throws SQLException
	{
		String sql = "insert into language (id, id_profile, name, type, level) values (nextval('language_seq'),?,?,?,?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		int count = rand.nextInt(3);
		for (int i = 0; i < count; i++)
		{
			statement.setLong(1, id);
			statement.setString(2, LANGUAGES[rand.nextInt(LANGUAGES.length)]);
			statement.setString(3, LANGUAGE_TYPE[rand.nextInt(LANGUAGE_TYPE.length)]);
			statement.setString(4, LANGUAGE_LEVEL[rand.nextInt(LANGUAGE_LEVEL.length)]);
			// System.out.println(statement);
			statement.addBatch();
		}

		statement.executeBatch();
		statement.close();
	}

	private static class Profile
	{
		private String firstName;
		private String lastName;
		private String img;
	}

	private static class Certificate
	{
		private String description;
		private String img;
	}

	public static void main(String[] args)
	{
		System.out.println("Start generating");

		Connection connection = null;

		try
		{
			clearDestinationFolder();
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			connection.setAutoCommit(false);
			clearDB(connection);
			prepareData();
			insertData(connection);
			connection.commit();
			System.out.println("Data generated");
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (connection != null)
				try
				{
					connection.close();
					System.out.println("Connection closed");
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
		}
	}
}