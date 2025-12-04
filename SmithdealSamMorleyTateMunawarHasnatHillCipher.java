import java.util.regex.*;
import java.io.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Example Java code to use regular expression
 * 
 * @author Xunhua Wang (wangxx@jmu.edu). All rights reserved.
 * @author Sam Smithdeal (smithdsc@dukes.jmu.edu)
 * @author Tate Morley (morleytp@dukes.jmu.edu)
 * @author Hasnat Munawar (munawahx@dukes.jmu.edu)
 * @date 11/24/2019; further revised on 12/01/2019, 12/04/2019, 12/06/2022; completed on 12/08/2025
 */
public class SmithdealSamMorleyTateMunawarHasnatHillCipher {

	//
	// 1. The local-part of an email address must not exceed 64 characters
	// 2. The domain part of an email address must not exceed 255 characters
	// 3. The total length of an email address must not exceed 254 characters
	//
	public boolean isLengthValid(String inEmailAddress) {
		int pos = inEmailAddress.indexOf('@');
		if (pos < 0)
			return false;

		String localuserName = inEmailAddress.substring(0, pos);
		String domainPart = inEmailAddress.substring(pos + 1);
		int len1 = localuserName.length();
		int len2 = domainPart.length();
		if (len1 > 64)
			return false;
		if (len2 > 255)
			return false;
		if (len1 + len2 > 254)
			return false;
		return true;
	}

	public void testIsLengthValid() {
		String emailAddress1 = "wangxx@jmu.edu";
		boolean result1 = isLengthValid(emailAddress1);

		String emailAddress2 = "786762D781A7FF4FAC9060892B4044880360B6E00F@CLNTINET08";
		boolean result2 = isLengthValid(emailAddress2);
		System.out.println("Is " + emailAddress1 + " valid? " + result1);
		System.out.println("Is " + emailAddress2 + " valid? " + result2);
	}

	/**
	 * Search for email addresses in the input text using the given pattern
	 * @param inPatternString
	 * @param inText
	 * @return
	 */
	public HashMap<String, Integer> search(String inPatternString, String inText) {
		Pattern r = Pattern.compile(inPatternString, Pattern.CASE_INSENSITIVE);
		Matcher m = r.matcher(inText);
		HashMap<String, Integer> emailMap = new HashMap<>();

		while (m.find()) { // while
			String str0 = m.group(0);

			if (isLengthValid(str0)) {
				String email = str0.toLowerCase();
				emailMap.put(email, emailMap.getOrDefault(email, 0) + 1);
			}
		}

		return emailMap;
	}

	/**
	 * Sort the email map by frequency and print the top 10 email addresses
	 * @param emailMap
	 * @return the sorted list of email entries
	 */
	public List<Entry<String, Integer>> sortAndPrint(HashMap<String, Integer> emailMap) {
		// Turn the map to a list to sort
		List<Entry<String, Integer>> list = new LinkedList<>(emailMap.entrySet());

		// Sort that list by the frequency count in descending order
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		int totalUnique = emailMap.size();
		int totalCount = emailMap.values().stream().mapToInt(Integer::intValue).sum();
		System.out.println("Task 2: Total email addresses = " + totalCount);
		System.out.println("Task 3: Total unique email addresses = " + totalUnique);

		System.out.println("Task 4a: Top 10 email addresses are:");
		int count = 0;
		for (Entry<String, Integer> entry : list) {
			System.out.println("\t" + entry.getKey() + "\t" + entry.getValue());
			count++;
			if (count >= 10) {
				break;
			}
		}

		return list;
	}

	/**
	 * Write all email addresses to a file
	 * @param list
	 * @param outFilename
	 * @throws Exception
	 */
	public void writeAllEmailsToFile(List<Entry<String, Integer>> list, String outFilename) throws Exception {
		FileWriter writer = new FileWriter(outFilename);
		writer.write("EXAMPLE" + System.lineSeparator() + "local@domain.xyz=frequency" + System.lineSeparator());
		for (Map.Entry<String, Integer> entry : list) {
			writer.write(entry + System.lineSeparator());
		}
		writer.close();
		System.out.println("Task 4b: All sorted email addresses are written to file " + outFilename + "(all team members eIDs)");
	}

	/**
	 * Load a text file and return the content as a string
	 * @param inFilename
	 * @return
	 * @throws Exception
	 */
	public String loadFile(String inFilename) throws Exception {
		StringBuffer sb = new StringBuffer(); // hillary-clinton-emails-august-31-release_djvu_copy.txt

		File file = new File(inFilename);
		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		while ((st = br.readLine()) != null) {
			// System.out.println(st);
			sb.append(st.toLowerCase());
			sb.append(System.getProperty("line.separator"));
		}

		br.close();

		return sb.toString();
	}

	public static void main(String args[]) {
		try {
			SmithdealSamMorleyTateMunawarHasnatHillCipher djre = new SmithdealSamMorleyTateMunawarHasnatHillCipher();
			// put the regular expression for your local part here
			String comment = "(\\(\\w*\\))*";
			String noQuoteReg = comment + "(?!\\.)([\\w!#$%&*+\\-/=?^_`{|}~]|\\.(?!\\.))+(?<!\\.)" + comment;
			String quotedReg = comment + "\"([\\w!#$%&*+\\-/=?^_`{|}~. )(,:;<\\[\\]>@]|(?<=\\\\)\"|\\\\\\\\)+\""
					+ comment;
			String LOCAL_PART = "(" + noQuoteReg + ")|(" + quotedReg + ")";

			String domainLabel = "[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?";
			String standardDomain = "(?:" + domainLabel + "\\.)+" + domainLabel;
			String ipAddress = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

			String DOMAIN = "(?:" + standardDomain + "|" + ipAddress + ")";

			String pattern4_localuser_variant = LOCAL_PART;
			String pattern4_domain = DOMAIN;
			String pattern5 = "(?:" + pattern4_localuser_variant + ")@" + "(?:" + pattern4_domain + ")";

			if (args.length == 0) {
				System.out.println("Use java DoeJohnRegEx FILE");
				return;
			}

			String text2 = djre.loadFile(args[0]);

			System.out.println("Task 1: Our regular expression works as follows:");
			System.out.println("\tLOCAL_PART: Either unquoted (comments allowed, special chars allowed, dots can't be consecutive) OR quoted strings");
			System.out.println("\tDOMAIN: Either standard domain (labels with alphanumerics/hyphens, no leading/trailing hyphens) OR IP in brackets");

			HashMap<String,Integer> emailMap = djre.search(pattern5, text2); // pattern5
			List<Entry<String, Integer>> emailList = djre.sortAndPrint(emailMap);
			djre.writeAllEmailsToFile(emailList, "SmithdealSamMorleyTateMunawarHasnat_all_email_addresses.txt");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
