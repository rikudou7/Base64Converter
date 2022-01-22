import java.util.*;
public class Base64Converter {

		public static void main(String[] args) {
//		  System.out.println(encodeToBase64("abc"));
//        System.out.println(decodeBase64("YWJj"));
//        System.out.println(encodeToBase64("ABCD"));
//        System.out.println(decodeBase64("QUJDRA=="));
//        System.out.println(encodeToBase64("12345"));
//        System.out.println(decodeBase64("MTIzNDU="));
        
//		  System.out.println(urlEncode("ABCD", "+", "/", "~"));
//        System.out.println(urlDecode("QUJDRA==", "+", "/", "~"));
//        System.out.println(urlEncode("12345", "+", "/", "~"));
//        System.out.println(urlDecode("MTIzNDU=", "+", "/", "~"));
//		
//        System.out.println(urlEncode("ABCD", "+", "/", "omit"));
//        System.out.println(urlDecode("QUJDRA", "+", "/", "omit"));
//        System.out.println(urlEncode("12345", "+", "/", "omit"));
//        System.out.println(urlDecode("MTIzNDU", "+", "/", "omit"));
		
		System.out.println("Do you want to encode to base 64 or decode from base 64? (Enter 'A' or 'B' without the quotes.)");
		System.out.println("A. Encode");
		System.out.println("B. Decode");
		Scanner console = new Scanner(System.in);
		String choice = "";
		String line = "";
		if (console.hasNextLine()) {
			line = console.nextLine();
			if (line.trim().length() > 1) {
				System.out.println("Invalid input.");
				System.exit(1);
			}
			Scanner linereader = new Scanner(line);
			choice = linereader.next();
			linereader.close();
		}
		if (!choice.trim().equalsIgnoreCase("A") && !choice.trim().equalsIgnoreCase("B")) {
			System.out.println("Invalid input.");
			System.exit(1);
		}
		String plus = "";
		String slash = "";
		String equalsign = "";
		String word = "";
		String line2 = "";
		String line3 = "";
		String line4 = "";
		String line5 = "";
		System.out.println("What character are you using to represent the '+' character?");
		if (console.hasNextLine()) {
			line2 = console.nextLine();
			if (line2.trim().length() > 1) {
				System.out.println("Invalid input.");
				System.exit(1);
			}
			Scanner linereader2 = new Scanner(line2);
			plus = linereader2.next();
			linereader2.close();
		}
		System.out.println("What character are you using to represent the '/' character?");
		if (console.hasNextLine()) {
			line3 = console.nextLine();
			if (line3.trim().length() > 1) {
				System.out.println("Invalid input.");
				System.exit(1);
			}
			Scanner linereader3 = new Scanner(line3);
			slash = linereader3.next();
			linereader3.close();
			if (slash.equalsIgnoreCase(plus)) {
			    System.out.println("Invalid input. Enter only one character that is different from the character for the '+' sign.");
				System.exit(1);
			}
		}
		System.out.println("What character are you using to represent the '=' character? If you want to omit the '=' character due to url reasons, then enter 'omit'.");
		if (console.hasNextLine()) {
			line4 = console.nextLine();
			if (line4.trim().length() > 1 && !line4.trim().equalsIgnoreCase("omit")) {
				System.out.println("Invalid input.");
				System.exit(1);
			}
			Scanner linereader4 = new Scanner(line4);
			equalsign = linereader4.next();
			linereader4.close();
			if (equalsign.equalsIgnoreCase(plus) || equalsign.equalsIgnoreCase(slash)) {
				System.out.println("Invalid input. Enter only one character that is different from the characters for the '+' and '/' characters.");
				System.exit(1);
			}
		}

		if (choice.trim().equalsIgnoreCase("A")) {
			System.out.println("Enter the string that you wish to encode:");
			if (console.hasNextLine()) {
				line5 = console.nextLine();
				if (line5.trim().contains(" ")) {
					System.out.println("Invalid input.");
					System.exit(1);
				}
				Scanner linereader5 = new Scanner(line5);
				word = linereader5.next();
				linereader5.close();
			}
			String result = "";
			if(!plus.equals("+") || !slash.equals("/") || !equalsign.equals("=")) {
				result = urlEncode(word, plus, slash, equalsign);
			} else {
				result = encodeToBase64(word);
			}
			System.out.println("The base 64 encoding of the string you entered is:");
			System.out.println(result);
		} else if (choice.trim().equalsIgnoreCase("B")) {
			System.out.println("Enter the string that you wish to decode:");
			if (console.hasNextLine()) {
				line5 = console.nextLine();
				if (line5.trim().contains(" ")) {
					System.out.println("Invalid input.");
					System.exit(1);
				}
				Scanner linereader5 = new Scanner(line5);
				word = linereader5.next();
				linereader5.close();
			}
			String result = "";
			if(!plus.equals("+") || !slash.equals("/") || !equalsign.equals("=")) {
				result = urlDecode(word, plus, slash, equalsign);
			} else {
				result = decodeBase64(word);
			}
			System.out.println("The decoding of the string you entered is:");
			System.out.println(result);
			System.out.println("In hex bytes this is:");
			System.out.println(encodeHex(result));
		}
		console.close();
	}
	
	public static String encodeToBase64(String word) {
		int evensets = (int) Math.floor(word.length() / 3);
		int stragglers = word.length() - (evensets * 3);
		String result = "";
		if (evensets > 0) {
			int k = 0;
			for (int i = 0; i < evensets; i++) {
				char one = word.charAt(k);
				char two = word.charAt(k + 1);
				char three = word.charAt(k + 2);
				char w = indexForEncoding((char)getFirstSixBits(one));
				char x = indexForEncoding((char)appendBits(getLastTwoBits(one), getFirstFourBits(two), 4));
				char y = indexForEncoding((char)appendBits(getLastFourBits(two), getFirstTwoBits(three), 2));
				char z = indexForEncoding((char)getLastSixBits(three));
				result += w;
				result += x;
				result += y;
				result += z;
				k += 3;
			}
		}
		if (stragglers == 1) {
			char one = word.charAt(word.length() - 1);
			char w = indexForEncoding((char)getFirstSixBits(one));
			char x = indexForEncoding((char)(getLastTwoBits(one) * 16));
			char y = '=';
			char z = '=';
			result += w;
			result += x;
			result += y;
			result += z;
		} else if (stragglers == 2) {
			char one = word.charAt(word.length() - 2);
			char two = word.charAt(word.length() - 1);
			char w = indexForEncoding((char)getFirstSixBits(one));
			char x = indexForEncoding((char)appendBits(getLastTwoBits(one), getFirstFourBits(two), 4));
			char y = indexForEncoding((char)(getLastFourBits(two) * 4));
			char z = '=';
			result += w;
			result += x;
			result += y;
			result += z;
		}
		return result;
	}
	
	public static String decodeBase64(String word) {
		if (word.length() % 4 != 0) {
			System.out.println("Invalid input. The word to be decoded must have a number of characters that is a multiple of 4.");
			System.exit(1);
		}
		int evensets = 0;
		int stragglers = 0;
		String result = "";
		if (word.charAt(word.length() - 1) == '=') {
			evensets = (word.length() - 4) / 4;
			if (word.charAt(word.length() - 2) == '=') {
				stragglers = 1;
			} else {
				stragglers = 2;
			}
		} else {
			evensets = word.length() / 4;
		}
		if (evensets > 0) {
			int k = 0;
			for (int i = 0; i < evensets; i++) {
				char one = indexForDecoding(word.charAt(k));
				char two = indexForDecoding(word.charAt(k + 1));
				char three = indexForDecoding(word.charAt(k + 2));
				char four = indexForDecoding(word.charAt(k + 3));
				char w = (char)(appendBits(one, getFirstTwoBits((char)(two * 4)), 2));
				char x = (char)(appendBits(getLastFourBits(two), getFirstFourBits((char)(three * 4)), 4));
				char y = (char)(appendBits(getLastTwoBits(three), four, 6));
				result += w;
				result += x;
				result += y;
				k += 4;
			}
		}
		if (stragglers == 2) {
			char one = indexForDecoding(word.charAt(word.length() - 4));
			char two = indexForDecoding(word.charAt(word.length() - 3));
			char three = indexForDecoding(word.charAt(word.length() - 2));
			char w = (char)(appendBits(one, getFirstTwoBits((char)(two * 4)), 2));
			char x = (char)(appendBits(getLastFourBits(two), getFirstFourBits((char)(three * 4)), 4));
			result += w;
			result += x;
		} else if (stragglers == 1) {
			char one = indexForDecoding(word.charAt(word.length() - 4));
			char two = indexForDecoding(word.charAt(word.length() - 3));
			char w = (char)(appendBits(one, getFirstTwoBits((char)(two * 4)), 2));
			result += w;
		}
		
		return result;
	}
	public static String urlEncode(String word, String plus, String slash, String equalSign) {
		if(equalSign.trim().equalsIgnoreCase("omit")) {
			equalSign = "";
		}
		String result = encodeToBase64(word);
		for(int i = 0; i < result.length(); i++) {
			if(result.charAt(i) == '+') {
				String remainder = "";
				if(i != result.length() - 1) {
					remainder = result.substring(i + 1, result.length());
				}
				result = result.substring(0, i) + plus + remainder;
			}
		}
		result = result.replaceAll("/", slash);
		result = result.replaceAll("=", equalSign);
		return result;
	}
	public static String urlDecode(String word, String plus, String slash, String equalSign) {
		if(equalSign.trim().equalsIgnoreCase("omit") && word.length() % 4 != 0) {
			if((word.length() + 1) % 4 == 0) {
				word += "=";
			} else if((word.length() + 2) % 4 == 0) {
				word += "==";
			}
		} else {
			word = word.replaceAll(equalSign, "=");
		}
		for(int i = 0; i < word.length(); i++) {
			if(word.substring(i, i + 1).equals(plus)) {
				String remainder = "";
				if(i != word.length() - 1) {
					remainder = word.substring(i + 1, word.length());
				}
				word = word.substring(0, i) + "+" + remainder;
			}
		}
		word = word.replaceAll(slash, "/");
		return decodeBase64(word);
	}
	
	public static String encodeHex(String word) {
		String hexstring = "";
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) < 16) {
				hexstring += '0';
			}
			hexstring += Integer.toHexString((int) word.charAt(i));
		}
		return hexstring;
	}
	
	public static int appendBits(int a, int b, int bits2) {
		int c = (int) ((a * Math.pow(2, bits2)) + b);
		return c;
	}
	public static int getFirstSixBits(char a) {
		int b = (int)a;
		for(int i = 0; i < 2; i++) {
			if(b % 2 != 0) {
				b -= 1;
			}
			b = b / 2;
		}
		return b;
	}

	public static int getLastTwoBits(char a) {
		int b = (int)a;
		b -= (getFirstSixBits(a) * 4);
		return b;
	}

	public static int getFirstTwoBits(char a) {
		int b = (int)a;
		for(int i = 0; i < 6; i++) {
			if(b % 2 != 0) {
				b -= 1;
			}
			b = b / 2;
		}
		return b;
	}

	public static int getFirstFourBits(char a) {
		int b = (int)a;
		for(int i = 0; i < 4; i++) {
			if(b % 2 != 0) {
				b -= 1;
			}
			b = b / 2;
		}
		return b;
	}

	public static int getLastFourBits(char a) {
		int b = (int)a;
		b -= (getFirstFourBits(a) * 16);
		return b;
	}

	public static int getLastSixBits(char a) {
		int b = (int)a;
		b -= (getFirstTwoBits(a) * 64);
		return b;
	}
	
	public static char indexForEncoding(char letter) {
		if (letter >= 0 && letter <= 25) {
			return (char) (letter + 65);
		}
		if (letter >= 26 && letter <= 51) {
			return (char) (letter + 71);
		}
		if (letter >= 52 && letter <= 61) {
			return (char) (letter - 4);
		}
		if (letter == 62) {
			return 43;
		}
		if (letter == 63) {
			return 47;
		}
		return letter;
	}

	public static char indexForDecoding(char letter) {
		if (letter >= 65 && letter <= 90) {
			return (char) (letter - 65);
		}
		if (letter >= 97 && letter <= 122) {
			return (char) (letter - 71);
		}
		if (letter >= 48 && letter <= 57) {
			return (char) (letter + 4);
		}
		if (letter == 43) {
			return 62;
		}
		if (letter == 47) {
			return 63;
		}
		return 127;
	}

}
