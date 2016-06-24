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

import org.joda.time.LocalDate;

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
	private static final String[] LANGUAGE_LEVEL = { "Beginner", "Elementary", "Pre-intrmediate", "Intermediate", "Upper-Intermediate", "Advanced",
			"Proficiency" };
	private static final String[] HOBBIES = { "Cycling", "Handball", "Football", "Basketball", "Bowling", "Boxing", "Volleyball", "Baseball",
			"Skating", "Skiing", "Table tennis", "Tennis", "Weightlifting", "Automobiles", "Book reading", "Cricket", "Photo", "Shopping", "Cooking",
			"Codding", "Animals", "Traveling", "Movie", "Painting", "Darts", "Fishing", "Kayak slalom", "Games of chance", "Ice hockey",
			"Roller skating", "Swimming", "Diving", "Golf", "Shooting", "Rowing", "Camping", "Archery", "Pubs", "Music", "Computer games",
			"Authorship", "Singing", "Foreign lang", "Billiards", "Skateboarding", "Collecting", "Badminton", "Disco" };
	private static final String[] SKILLS_CATEGORIES = { "Languages", "DBMS", "Web", "Java", "IDE", "CVS", "Web Servers", "Build system", "Cloud" };
	private static final String[] SKILLS_DESC = { "Java, SQL, PLSQL", "Mysql, Postgresql", "HTML, CSS, JS, Foundation, JQuery, Bootstrap",
			"Threads, IO, JAXB, GSON, Servlets, Logback, JSP, JSTL, JDBC, Apache Commons, Google+ Social API, Spring MVC, Spring Data JPA, Spring Security, Hibernate JPA, Facebook Social API",
			"Eclipse for JEE Developer", "Github", "Tomcat, Nginx", "Maven", "OpenShift, AWS" };
	private static final String DUMMY_TEXT = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et "
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
			+ "metus. Vestibulum volutpat pretium libero. Cras id dui. Aenean ut.";
	private static final String[] OBJECTIVES = { "Junior java trainee position", "Junior java developer position" };
	private static final String[] SUMMARIES = { "Java core course with developing one simple console application",
			"One Java professional course with developing web application blog",
			"Two Java professional courses with developing two web applications: blog and resume",
			"Three Java professional courses with developing one console application and two web applications: blog and resume" };
	private static final String[] COMPANIES = { "Lorem ipsum", "Aenean commodo", "Etiam rhoncus", "Duis arcu tortor", "Praesent adipiscing", "Aenean vulputate" };
	private static final String[] POSITIONS = { "Junior java developer", "Java trainee" };
	private static final String[] UNIVERSITIES = { "National Technical University", "Karazin Kharkiv National University",
			"Kharkiv National University of Radioelectronics" };
	private static final String[] DEPARTMENTS = { "Computer-driven system and network", "Programming systems",
			"Intelligence system of problem-solving" };
	private static final String[] HOBBY_ICONS = { "<i class='fa fa-paw' aria-hidden='true'></i>", "<i class='fa fa-bullseye' aria-hidden='true'></i>",
			"<i class='fa fa-pencil-square-o' aria-hidden='true'></i>", "<i class='fa fa-car' aria-hidden='true'></i>",
			"<i class='fa fa-futbol-o' aria-hidden='true'></i>", "<i class='fa fa-futbol-o' aria-hidden='true'></i>",
			"<i class='fa fa-futbol-o' aria-hidden='true'></i>", "<i class='fa fa-futbol-o' aria-hidden='true'></i>",
			"<i class='fa fa-book' aria-hidden='true'></i>", "<i class='fa fa-futbol-o' aria-hidden='true'></i>",
			"<i class='fa fa-futbol-o' aria-hidden='true'></i>", "<i class='fa fa-leaf' aria-hidden='true'></i>",
			"<i class='fa fa-code' aria-hidden='true'></i>", "<i class='fa fa-star' aria-hidden='true'></i>",
			"<i class='fa fa-gamepad' aria-hidden='true'></i>", "<i class='fa fa-cutlery' aria-hidden='true'></i>",
			"<i class='fa fa-futbol-o' aria-hidden='true'></i>", "<i class='fa fa-bicycle' aria-hidden='true'></i>",
			"<i class='fa fa-bullseye' aria-hidden='true'></i>", "<i class='fa fa-users' aria-hidden='true'></i>",
			"<i class='fa fa-tint' aria-hidden='true'></i>", "<i class='fa fa-life-ring' aria-hidden='true'></i>",
			"<i class='fa fa-futbol-o' aria-hidden='true'></i>", "<i class='fa fa-language' aria-hidden='true'></i>",
			"<i class='fa fa-list-alt' aria-hidden='true'></i>", "<i class='fa fa-futbol-o' aria-hidden='true'></i>",
			"<i class='fa fa-futbol-o' aria-hidden='true'></i>", "<i class='fa fa-futbol-o' aria-hidden='true'></i>",
			"<i class='fa fa-tint' aria-hidden='true'></i>", "<i class='fa fa-film' aria-hidden='true'></i>",
			"<i class='fa fa-music' aria-hidden='true'></i>", "<i class='fa fa-paint-brush' aria-hidden='true'></i>",
			"<i class='fa fa-camera' aria-hidden='true'></i>", "<i class='fa fa-beer' aria-hidden='true'></i>",
			"<i class='fa fa-star' aria-hidden='true'></i>", "<i class='fa fa-futbol-o' aria-hidden='true'></i>",
			"<i class='fa fa-crosshairs' aria-hidden='true'></i>", "<i class='fa fa-shopping-basket' aria-hidden='true'></i>",
			"<i class='fa fa-microphone' aria-hidden='true'></i>", "<i class='fa fa-star' aria-hidden='true'></i>",
			"<i class='fa fa-star' aria-hidden='true'></i>", "<i class='fa fa-star' aria-hidden='true'></i>",
			"<i class='fa fa-tint' aria-hidden='true'></i>", "<i class='fa fa-futbol-o' aria-hidden='true'></i>",
			"<i class='fa fa-futbol-o' aria-hidden='true'></i>", "<i class='fa fa-map' aria-hidden='true'></i>",
			"<i class='fa fa-futbol-o' aria-hidden='true'></i>", "<i class='fa fa-star' aria-hidden='true'></i>" };
	private static final String[] HOBBY_NAMES = { "Animals", "Archery", "Authorship", "Automobiles", "Badminton", "Baseball", "Basketball",
			"Billiards", "Book reading", "Bowling", "Boxing", "Camping", "Coding", "Collecting", "Computer games", "Cooking", "Cricket", "Cycling",
			"Darts", "Disco", "Diving", "Fishing", "Football", "Foreign language", "Games of chance", "Golf", "Handball", "Ice hockey",
			"Kayak slalom", "Movie", "Music", "Painting", "Photo", "Pubs", "Roller skating", "Rowing", "Shooting", "Shopping", "Singing",
			"Skateboarding", "Skating", "Skiing", "Swimming", "Table tennis", "Tennis", "Traveling", "Volleyball", "Weightlifting" };

	private static final Random rand = new Random();
	private static List<ProfilePhotoImage> profilePhotoImages;
	private static List<CertificateImage> certificatesImages;
	private static List<String> dummySentences = Arrays.asList(DUMMY_TEXT.split("[.][^,]"));

	private static void clearDestinationFolder() throws IOException {
		System.out.println("Clearing destination folder");
		Path path = Paths.get(MEDIA_DIR);
		if (Files.exists(path)) {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}
			});
		}
		System.out.println("Destination folder hase been cleared");
	}

	private static void clearDB(Connection connection) throws SQLException {
		System.out.println("Clearing DB");
		Statement statement = connection.createStatement();		
		
		statement.executeUpdate("delete from profile");
		statement.executeUpdate("delete from skill_category");
		statement.executeUpdate("delete from hobby_name");
		
		statement.executeQuery("select setval('certificate_seq', 1, false)");
		statement.executeQuery("select setval('course_seq', 1, false)");
		statement.executeQuery("select setval('education_seq', 1, false)");
		statement.executeQuery("select setval('experience_seq', 1, false)");
		statement.executeQuery("select setval('language_seq', 1, false)");
		statement.executeQuery("select setval('profile_seq', 1, false)");
		statement.executeQuery("select setval('skill_category_seq', 1, false)");
		statement.executeQuery("select setval('skill_seq', 1, false)");
		statement.executeQuery("select setval('hobby_seq', 1, false)");
		statement.executeQuery("select setval('hobby_name_seq', 1, false)");
		System.out.println("DB has been cleared");
	}

	private static void prepareData() {
		System.out.println("Preparing data");
		File[] files = new File(PHOTO_DIR).listFiles();
		profilePhotoImages = new ArrayList<>(files.length);
		for (File file : files) {
			ProfilePhotoImage profile = new ProfilePhotoImage();
			String[] names = file.getName().replace(".jpg", "").split(" ");
			profile.firstName = names[0];
			profile.lastName = names[1];
			profile.img = file;
			profilePhotoImages.add(profile);
		}
		files = new File(CERTIFICATES_DIR).listFiles();
		certificatesImages = new ArrayList<>(files.length);
		for (File file : files) {
			CertificateImage certificate = new CertificateImage();
			certificate.description = file.getName().replace(".jpg", "");
			certificate.img = file;
			certificatesImages.add(certificate);
		}
	}

	private static void insertData(Connection connection) throws SQLException, IOException {
		System.out.println("Inserting data");
		insertSkillCategory(connection);
		insertHobbyName(connection);
		insertProfiles(connection);
		for (int id = 1; id <= profilePhotoImages.size(); id++) {
			insertSkill(connection, id);
			insertHobby(connection, id);
			insertCertificate(connection, id);
			insertLanguage(connection, id);
			insertExperience(connection, id);
			insertEducation(connection, id);
			insertCourse(connection, id);
		}
		System.out.println("Data have been inserted");
	}

	private static void insertProfiles(Connection connection) throws SQLException, IOException {		
		String sql = "insert into profile " + "(id, uid, password, active, first_name, last_name, country, city, birthday, "
				+ "email, phone, skype, vkontakte, facebook, linkedin, github, stackoverflow, "
				+ "additional_info, objective, summary, photo, photo_small) "
				+ "values (nextval('profile_seq'),?,?,true,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);

		for (ProfilePhotoImage profile : profilePhotoImages)
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

			if (rand.nextBoolean()) {
				statement.setString(11, ("http://vk.com/" + profile.lastName).toLowerCase());
			}
			else {
				statement.setNull(11, Types.VARCHAR);
			}
			if (rand.nextBoolean()) {
				statement.setString(12, ("http://facebook.com/" + profile.lastName).toLowerCase());
			}
			else {
				statement.setNull(12, Types.VARCHAR);
			}
			if (rand.nextBoolean()) {
				statement.setString(13, ("http://linkedin.com/" + profile.lastName).toLowerCase());
			}
			else {
				statement.setNull(13, Types.VARCHAR);
			}
			if (rand.nextBoolean()) {
				statement.setString(14, ("http://github.com/" + profile.lastName).toLowerCase());
			}
			else {
				statement.setNull(14, Types.VARCHAR);
			}
			if (rand.nextBoolean()) {
				statement.setString(15, ("http://stackoverflow.com/" + profile.lastName).toLowerCase());
			}
			else {
				statement.setNull(15, Types.VARCHAR);
			}
			if (rand.nextBoolean()) {
				statement.setString(16, dummySentences.get(rand.nextInt(dummySentences.size())));
			}
			else {
				statement.setNull(16, Types.VARCHAR);
			}
			statement.setString(17, OBJECTIVES[rand.nextInt(OBJECTIVES.length)]);
			statement.setString(18, SUMMARIES[rand.nextInt(SUMMARIES.length)]);
			ImageLinks links = processImage(MEDIA_DIR + "/avatar/", profile.img);
			statement.setString(19, "/media/avatar/" + links.uuid);
			statement.setString(20, "/media/avatar/" + links.smallUuid);
			// System.out.println(statement);
			statement.addBatch();
		}
		statement.executeBatch();
		statement.close();
	}
	
	private static ImageLinks processImage(String folder, File sourceFile) throws IOException {
		String uuid = UUID.randomUUID().toString() + ".jpg";
		String smallUuid = uuid.replace(".jpg", "-sm.jpg");		
		File largeImage = new File(folder + uuid);
		File smallImage = new File(folder + smallUuid);
		if (!largeImage.getParentFile().exists())
			largeImage.getParentFile().mkdirs();
		Thumbnails.of(sourceFile).size(400, 400).toFile(largeImage);
		Thumbnails.of(sourceFile).size(100, 100).toFile(smallImage);
		ImageLinks links = new ImageLinks();
		links.uuid = uuid;
		links.smallUuid = smallUuid;
		return links;
	}

	private static Date generateBirthday() {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.DAY_OF_MONTH, rand.nextInt(30));
		cl.set(Calendar.MONTH, rand.nextInt(12));
		int year = cl.get(Calendar.YEAR) - 30;
		cl.set(Calendar.YEAR, year + rand.nextInt(10));
		return new Date(cl.getTimeInMillis());
	}

	private static String generatePhone() {
		StringBuilder phone = new StringBuilder("+38");
		for (int i = 0; i < 10; i++) {
			int code = '0' + rand.nextInt(10);
			phone.append(((char) code));
		}
		return phone.toString();
	}

	private static void insertSkillCategory(Connection connection) throws SQLException {
		String sql = "insert into skill_category (id, name) values (nextval('skill_category_seq'),?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		for (int i = 0; i < SKILLS_CATEGORIES.length; i++) {
			statement.setString(1, SKILLS_CATEGORIES[i]);
			// System.out.println(statement);
			statement.addBatch();
		}
		statement.executeBatch();
		statement.close();
	}

	private static void insertHobbyName(Connection connection) throws SQLException {
		String sql = "insert into hobby_name (id, icon, name) values (nextval('hobby_name_seq'),?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		for (int i = 0; i < HOBBY_ICONS.length; i++) {
			statement.setString(1, HOBBY_ICONS[i]);
			statement.setString(2, HOBBY_NAMES[i]);
			//System.out.println(statement);
			statement.addBatch();
		}
		statement.executeBatch();
		statement.close();
	}

	private static void insertSkill(Connection connection, int id) throws SQLException {		
		String sql = "insert into skill (id, id_profile, category, description) values (nextval('skill_seq'),?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		for (int i = 0; i < SKILLS_CATEGORIES.length; i++) {
			statement.setLong(1, id);
			statement.setString(2, SKILLS_CATEGORIES[i]);
			statement.setString(3, SKILLS_DESC[i]);
			// System.out.println(statement);
			statement.addBatch();
		}
		statement.executeBatch();
		statement.close();
	}

	private static void insertExperience(Connection connection, int id) throws SQLException {
		String sql = "insert into experience (id, id_profile, company, position, starting_date, completion_date, responsibility, demo, code) "
				+ "values (nextval('experience_seq'),?,?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		int count = rand.nextInt(3);
		for (int i = 0; i < count; i++) {
			statement.setLong(1, id);
			statement.setString(2, COMPANIES[rand.nextInt(COMPANIES.length)]);
			statement.setString(3, POSITIONS[rand.nextInt(POSITIONS.length)]);
			Date completionDate = generateDate();
			statement.setDate(4, generateDateBefore(completionDate));
			if (rand.nextBoolean()) {
				statement.setDate(5, completionDate);
			}
			else {
				statement.setNull(5, Types.DATE);
			}
			statement.setString(6, dummySentences.get(rand.nextInt(dummySentences.size())));
			statement.setString(7, "http://LINK_TO_DEMO_SITE");
			statement.setString(8, "http://github.com/TODO");
			// System.out.println(statement);
			statement.addBatch();
		}
		statement.executeBatch();
		statement.close();
	}

	private static Date generateDate() {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.DAY_OF_MONTH, rand.nextInt(30));
		cl.set(Calendar.MONTH, rand.nextInt(12));
		int year = cl.get(Calendar.YEAR);
		cl.set(Calendar.YEAR, year - rand.nextInt(5));
		return new Date(cl.getTimeInMillis());
	}

	private static Date generateDateBefore(Date laterDate) {
		LocalDate date = new LocalDate(laterDate.getTime());
		date.minusMonths(rand.nextInt(48) + 12);
		return new Date(date.toDate().getTime());
	}

	private static int generateYear() {
		Calendar cl = Calendar.getInstance();
		int year = cl.get(Calendar.YEAR);
		return year - rand.nextInt(10) - 3;
	}

	private static void insertCertificate(Connection connection, int id) throws SQLException, IOException {
		String sql = "insert into certificate " + "(id, id_profile, description, img, img_small) " + "values (nextval('certificate_seq'),?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		for (CertificateImage certificate : certificatesImages) {
			if (rand.nextBoolean()) {
				statement.setLong(1, id);
				statement.setString(2, certificate.description);
				ImageLinks links = processImage(MEDIA_DIR + "/certificate/", certificate.img);
				statement.setString(3, "/media/certificate/" + links.uuid);
				statement.setString(4, "/media/certificate/" + links.smallUuid);
				// System.out.println(statement);
				statement.addBatch();
			}
		}
		statement.executeBatch();
		statement.close();
	}

	private static void insertCourse(Connection connection, int id) throws SQLException {
		String sql = "insert into course (id, id_profile, description, school, completion_date) " + "values (nextval('course_seq'),?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		statement.setString(2, "Java basic");
		statement.setString(3, "Vivamus");
		if (rand.nextBoolean()) {
			statement.setDate(4, generateDate());
		}
		else {
			statement.setNull(4, Types.DATE);
		}
		// System.out.println(statement);
		statement.execute();
		statement.close();
	}

	private static void insertEducation(Connection connection, int id) throws SQLException {
		String sql = "insert into education (id, id_profile, speciality, university, department, starting_year, completion_year) "
				+ "values (nextval('education_seq'),?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		statement.setString(2, "Specialist of computer systems");
		statement.setString(3, UNIVERSITIES[rand.nextInt(UNIVERSITIES.length)]);
		statement.setString(4, DEPARTMENTS[rand.nextInt(DEPARTMENTS.length)]);
		int year = generateYear();
		statement.setInt(5, year);
		if (rand.nextBoolean()) {
			statement.setInt(6, year + rand.nextInt(3) + 1);
		}
		else
			statement.setNull(6, Types.INTEGER);
		// System.out.println(statement);
		statement.execute();
		statement.close();
	}

	private static void insertHobby(Connection connection, int id) throws SQLException {
		String sql = "insert into hobby (id, id_profile, description) values (nextval('hobby_seq'),?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		int count = rand.nextInt(5);
		for (int i = 0; i < count; i++) {
			statement.setLong(1, id);
			statement.setString(2, HOBBIES[rand.nextInt(HOBBIES.length)]);
			// System.out.println(statement);
			statement.addBatch();
		}
		statement.executeBatch();
		statement.close();
	}

	private static void insertLanguage(Connection connection, int id) throws SQLException {
		String sql = "insert into language (id, id_profile, name, type, level) values (nextval('language_seq'),?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		int count = rand.nextInt(3);
		for (int i = 0; i < count; i++) {
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

	private static class ProfilePhotoImage {
		private String firstName;
		private String lastName;
		private File img;
	}

	private static class CertificateImage {
		private String description;
		private File img;
	}
	
	private static class ImageLinks {
		private String uuid;
		private String smallUuid;
	}

	public static void main(String[] args) {
		System.out.println("Start generating");
		Connection connection = null;
		try {
			clearDestinationFolder();
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			connection.setAutoCommit(false);
			clearDB(connection);
			prepareData();
			insertData(connection);
			connection.commit();
			System.out.println("Data have been generated");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
					System.out.println("Connection has been closed");
				} catch (SQLException e) {
					System.out.println("Connection has not been closed");
					e.printStackTrace();
				}
			}
		}
	}
}
