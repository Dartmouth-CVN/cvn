package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.Contact;
import model.ContactElement;

public class RandomGenerator {

	static String[] firstNames = { "AARON", "ABDUL", "ABE", "ABEL", "ABRAHAM", "ABRAM", "ADALBERTO", "ADAM", "BARRY",
			"BART", "BARTON", "BASIL", "BEAU", "BEN", "BENEDICT", "BRANDEN", "BRANDON", "BRANT", "BRENDAN", "CEDRIC",
			"CEDRICK", "CESAR", "CHAD", "CHADWICK", "CORTEZ", "CORY", "DEREK", "DERICK", "DONNIE", "DOUGLASS", "DOYLE",
			"FRITZ", "GABRIEL", "GAIL", "GENARO", "GENE", "GEOFFREY", "GEORGE", "Henry", "HERB", "HERBERT", "HERIBERTO",
			"HERMAN", "HERSCHEL", "IRVING", "IRWIN", "ISAAC", "ISAIAH", "JEAN", "JED", "JEFFEREY", "JEFFERSON",
			"JEFFERY", "JESS", "JESS", "MOHAMED", "NED", "NEIL", "NOEL", "NOLAN", "NORBERT", "NORBERTO", "NORMAN",
			"NORMAND", "NORRIS", "ODELL", "ODIS", "OLIN", "OLIVER", "ORVILLE", "OSCAR", "OSVALDO", "OSWALDO", "RANDELL",
			"RANDOLPH", "RANDY", "RAPHAEL", "RASHAD", "WYATT", "XAVIER", "YONG", "YOUNG", "ZACHARIAH", "ZACHARY",
			"ZACHERY", "ZACK", "ZACKARY", "ZANE" };

	static String[] lastNames = { "SMITH", "JOHNSON", "MARTINEZ", "THOMAS", "JACKSON", "LEE", "WALKER", "PEREZ", "HALL",
			"YOUNG", "ALLEN", "SANCHEZ", "WRIGHT", "KING", "SCOTT", "GREEN", "BAKER", "CAMPBELL", "MITCHELL", "ROBERTS",
			"CARTER", "PHILLIPS", "EVANS", "TURNER", "WARD", "COX", "DIAZ", "RICHARDSON" };

	static String[] phoneNumbers = { "(741) 951-5271", "(561) 742-3921", "(784) 287-1076", "(838) 727-6573",
			"(500) 244-7083", "(943) 570-2414", "(874) 381-4941", "(367) 226-2040", "(815) 825-6662" };

	static String[] emails = { "isi@tidur.org", "johu@codovo.org", "uj@orkimfah.com", "fapet@go.edu", "me@udaga.net",
			"kenna@vecbu.com", "gunob@moahu.net", "nashulu@it.gov", "opwankiw@seiteevo.io", "tifinpim@za.io",
			"nupedu@ta.edu", "juh@antof.com", "mewso@necuwnuk.io", "fuivif@daunen.gov", "hohzavi@paw.edu",
			"motjeni@muvu.io", "setosuf@oti.org", "lad@kupez.gov", "uggako@filse.net", "pivop@wewjur.com" };

	static Random randomNumber = getRandomNumber();

	public static Random getRandomNumber() {
		return new Random(System.currentTimeMillis());
	}

	public static String getRandomFirstName() {
		return firstNames[randomNumber.nextInt(firstNames.length)];
	}

	public static String getRandomLastName() {
		return lastNames[randomNumber.nextInt(lastNames.length)];
	}

	public static String getRandomPhoneNumber() {
		return phoneNumbers[randomNumber.nextInt(phoneNumbers.length)];
	}

	public static String getRandomEmail() {
		return emails[randomNumber.nextInt(emails.length)];
	}

	public static String getRandomAddress() {
		return "100 Institute Rd\nWorcester MA, 01609\n";
	}

	public static String createUsername(String firstname, String lastname) {
		return lastname.toLowerCase() + firstname.toLowerCase().charAt(0);
	}

	public static String getRandomUsername() {
		return lastNames[randomNumber.nextInt(lastNames.length)].toLowerCase()
				+ firstNames[randomNumber.nextInt(firstNames.length)].toLowerCase().charAt(0);
	}

	public static Date getRandomBirthday() {
//		String input = String.format("%4d-%02d-%02d", (randomNumber.nextInt(80) + 1930), randomNumber.nextInt(12) + 1,
//				randomNumber.nextInt(27) + 1);
//		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		return new Date((randomNumber.nextInt(80) + 1930), randomNumber.nextInt(12) + 1,  randomNumber.nextInt(27) + 1);
//		return Date.parse(input, formatter);
	}

	public static String getRandomRoom() {
		return "Room " + (randomNumber.nextInt(20) + 1);
	}

	public static Contact getRandomContactInfo() {
		String[] types = { "personal", "work", "home" };
		List<ContactElement> phoneNumbers = new LinkedList<ContactElement>();
		List<ContactElement> emails = new LinkedList<ContactElement>();
		List<ContactElement> addresses = new LinkedList<ContactElement>();

		// add phone numbers
		for (String type : types) {
			phoneNumbers.add(new ContactElement(getRandomPhoneNumber(), type));
		}

		// add email addresses
		for (String type : types) {
			emails.add(new ContactElement(getRandomEmail(), type));
		}

		// add addresses
		for (String type : types) {
			addresses.add(new ContactElement(getRandomAddress(), type));
		}

		return new Contact(phoneNumbers, emails, addresses);

	}

}
