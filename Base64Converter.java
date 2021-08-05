import java.util.*;
public class Base64Converter {

	public static void main(String[] args) {
		System.out.println("Do you want to encode from ascii to base 64 or decode from base 64 to ascii? (Enter 'A' or 'B' without the quotes.)");
		System.out.println("A. Encode");
		System.out.println("B. Decode");
		Scanner console = new Scanner(System.in);
		String choice = "";
                String line = "";
		if(console.hasNextLine()) {
                        line = console.nextLine();
                        if(line.trim().length() > 1) {
                            System.out.println("Invalid input.");
			    System.exit(1);
                        }
                        Scanner linereader = new Scanner(line);
			choice = linereader.next();
                        linereader.close();
		}
                if(!choice.trim().equalsIgnoreCase("A") && !choice.trim().equalsIgnoreCase("B")) {
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
		if(console.hasNextLine()) {
		    line2 = console.nextLine();
                    if(line2.trim().length() > 1) {
                        System.out.println("Invalid input.");
                        System.exit(1);
                    }
                    Scanner linereader2 = new Scanner(line2);
                    plus = linereader2.next();
                    linereader2.close();
		}
		System.out.println("What character are you using to represent the '/' character?");
                if(console.hasNextLine()) {
		    line3 = console.nextLine();
                    if(line3.trim().length() > 1) {
                        System.out.println("Invalid input.");
                        System.exit(1);
                    }
                    Scanner linereader3 = new Scanner(line3);
                    slash = linereader3.next();
                    linereader3.close();
                    if(slash.equalsIgnoreCase(plus)) {
		        System.out.println("Invalid input. Enter only one character that is different from the character for the '+' sign.");
			System.exit(1);
		    }
		}
		System.out.println("What character are you using to represent the '=' character?");
                if(console.hasNextLine()) {
	            line4 = console.nextLine();
                    if(line4.trim().length() > 1) {
                        System.out.println("Invalid input.");
                        System.exit(1);
                    }
                    Scanner linereader4 = new Scanner(line4);
                    equalsign = linereader4.next();
                    linereader4.close();
                    if(equalsign.equalsIgnoreCase(plus) || equalsign.equalsIgnoreCase(slash)) {
		        System.out.println("Invalid input. Enter only one character that is different from the characters for the '+' and '/' characters.");
			System.exit(1);
		    }
	       }
	       
	       if(choice.trim().equalsIgnoreCase("A")) {
                        System.out.println("Enter the string that you wish to encode:");
                        if(console.hasNextLine()) {
	                    line5 = console.nextLine();
                            if(line5.trim().contains(" ")) {
                                System.out.println("Invalid input.");
                                System.exit(1);
                            }
                            Scanner linereader5 = new Scanner(line5);
                            word = linereader5.next();
                            linereader5.close();
	                }
			String result = encodebase64(word, plus.charAt(0), slash.charAt(0), equalsign.charAt(0));
			System.out.println("The base 64 encoding of the string you entered is:");
			System.out.println(result);
	       } else if(choice.trim().equalsIgnoreCase("B")) {
			System.out.println("Enter the string that you wish to decode:");
			if(console.hasNextLine()) {
	                    line5 = console.nextLine();
                            if(line5.trim().contains(" ")) {
                                System.out.println("Invalid input.");
                                System.exit(1);
                            }
                            Scanner linereader5 = new Scanner(line5);
                            word = linereader5.next();
                            linereader5.close();
	                }
			String result = decodebase64(word, plus.charAt(0), slash.charAt(0), equalsign.charAt(0));
			System.out.println("The decoding of the string you entered is:");
			System.out.println(result);
                        System.out.println("In hex bytes this is:");
                        System.out.println(encodeHex(result));
		}
		console.close();
	}
	
	public static String encodeHex(String word) {
		String hexstring = "";
		for(int i = 0; i < word.length(); i++) {
			if(word.charAt(i) < 16) {
				hexstring += '0';
			}
			hexstring += Integer.toHexString((int)word.charAt(i));
		}
		return hexstring;
	}
	
	public static String encodebase64(String word, char plus, char slash, char equalsign) {
		int evensets = (int) Math.floor(word.length() / 3);
		int stragglers = word.length() - (evensets * 3);
		String result = "";
		if(evensets > 0) {
			int k = 0;
			for(int i = 0; i < evensets; i++) {
			    char one = word.charAt(k);
		            char two = word.charAt(k + 1);
			    char three = word.charAt(k + 2);
			    char w = indexForEncoding(getFirstSixBits(one));
			    char x = indexForEncoding((char)(unsignedLeftShift(getLastTwoBits(one), 4) | getFirstFourBits(two)));
			    char y = indexForEncoding((char) (unsignedLeftShift(getLastFourBits(two), 2) | getFirstTwoBits(three)));
			    char z = indexForEncoding(getLastSixBits(three));
			    result += w;
			    result += x;
			    result += y;
			    result += z;
			    k += 3;
			}
		}
		if(stragglers == 1) {
		    char one = word.charAt(word.length() - 1);
		    char w = indexForEncoding(getFirstSixBits(one));
		    char x = indexForEncoding(unsignedLeftShift(getLastTwoBits(one), 4));
		    char y = '=';
		    char z = '=';
		    result += w;
		    result += x;
		    result += y;
		    result += z;
		} else if(stragglers == 2) {
		    char one = word.charAt(word.length() - 2);
		    char two = word.charAt(word.length() - 1);
		    char w = indexForEncoding(getFirstSixBits(one));
		    char x = indexForEncoding((char)(unsignedLeftShift(getLastTwoBits(one), 4) | getFirstFourBits(two)));
		    char y = indexForEncoding(unsignedLeftShift(getLastFourBits(two), 2));
		    char z = '=';
		    result += w;
		    result += x;
		    result += y;
		    result += z;
		}
		String tempResult = result;
		for(int p = 0; p < tempResult.length(); p++) {
			if(result.charAt(p) == '+') {
				result = result.substring(0, p) + plus;
				if(p != tempResult.length() - 1) {
					result += tempResult.substring(p + 1, tempResult.length());
				}
			}
			if(result.charAt(p) == '/') {
				result = result.substring(0, p) + slash;
				if(p != tempResult.length() - 1) {
					result += tempResult.substring(p + 1, tempResult.length());
				}
			}
			if(result.charAt(p) == '=') {
                            result = result.substring(0, p) + equalsign;
			    if(p != tempResult.length() - 1) {
			        result += tempResult.substring(p + 1, tempResult.length());
			    }
			}
		}
		return result;
	}
	
	public static String decodebase64(String word, char plus, char slash, char equalsign) {
		if(word.length() % 4 != 0) {
			System.out.println("Invalid input. The word to be decoded must have a number of characters that is a multiple of 4.");
			System.exit(1);
		}
		int evensets = 0;
		int stragglers = 0;
		String result = "";
		if(word.charAt(word.length() - 1) == equalsign) {
			evensets = (word.length() - 4) / 4; //This is the number of sets of 3 characters there were in the plain text
			if(word.charAt(word.length() - 2) == equalsign) {
				stragglers = 1;
			} else {
				stragglers = 2;
			}
		} else {
			evensets = word.length() / 4;
		}
		for(int j = 0; j < word.length(); j++) {
			if(word.charAt(j) == plus) {
				String temp = word.substring(0, j) + '+';
				if(j != word.length() - 1) {
					temp += word.substring(j + 1, word.length());
				}
				word = temp;
			} else if(word.charAt(j) == slash) {
				String temp = word.substring(0, j) + '/';
				if(j != word.length() - 1) {
					temp += word.substring(j + 1, word.length());
				}
				word = temp;
			}
		}
		if(evensets > 0) {
			int k = 0;
			for(int i = 0; i < evensets; i++) {
			    char one = word.charAt(k);
			    char two = word.charAt(k + 1);
			    char three = word.charAt(k + 2);
			    char four = word.charAt(k + 3);
			    char w = (char) (unsignedLeftShift(getLastSixBits(indexForDecoding(one)), 2) | getFirstTwoBits(unsignedLeftShift(indexForDecoding(two), 2)));
			    char x = (char) (unsignedLeftShift(getLastFourBits(indexForDecoding(two)), 4) | getFirstFourBits(unsignedLeftShift(indexForDecoding(three), 2)));
			    char y = (char) (unsignedLeftShift(getLastTwoBits(indexForDecoding(three)), 6) | getFirstSixBits(unsignedLeftShift(indexForDecoding(four), 2)));
			    result += w;
			    result += x;
			    result += y;
			    k += 4;
			}
		}
		if(stragglers == 2) {
			char one = word.charAt(word.length() - 4);
			char two = word.charAt(word.length() - 3);
			char three = word.charAt(word.length() - 2);
			char w = (char) (unsignedLeftShift(getLastSixBits(indexForDecoding(one)), 2) | getFirstTwoBits(unsignedLeftShift(indexForDecoding(two), 2)));
			char x = (char) (unsignedLeftShift(getLastFourBits(indexForDecoding(two)), 4) | getFirstFourBits(unsignedLeftShift(indexForDecoding(three), 2)));
			result += w;
		    result += x;
		} else if(stragglers == 1) {
			char one = word.charAt(word.length() - 4);
			char two = word.charAt(word.length() - 3);
			char w = (char) (unsignedLeftShift(getLastSixBits(indexForDecoding(one)), 2) | getFirstTwoBits(unsignedLeftShift(indexForDecoding(two), 2)));
			result += w;
		}
		return result;
	}
	public static char getFirstSixBits(char a) {
		char b = (char) (a >>> 2);
		return b;
	}
	
	public static char getLastTwoBits(char a) {
		char b = (char) (unsignedLeftShift(a, 6));
		char c = (char) (b >>> 6);
		return c;
	}
	public static char getFirstTwoBits(char a) {
		char b = (char) (a >>> 6);
		return b;
	}
	public static char getFirstFourBits(char a) {
		char b = (char) (a >>> 4);
		return b;
	}
	public static char getLastFourBits(char a) {
		char b = (char) (unsignedLeftShift(a, 4));
		char c = (char) (b >>> 4);
		return c;
	}
	
	public static char getLastSixBits(char a) {
		char b = (char) (unsignedLeftShift(a, 2));
		char c = (char) (b >>> 2);
		return c;
	}
	
	public static char unsignedLeftShift(char a, int b) {
		char z = a;
		int lowestPowerDiscarded = 8 - b;
		for(int i = 7; i >= lowestPowerDiscarded; i--) {
			int power = (int)Math.pow(2, (double)i);
			if(z >= power) {
				z -= power;
			}
		}
		z *= (int)Math.pow(2, (double)b);
		return z;
	}
	
	public static char indexForEncoding(char letter) {
		if(letter >= 0 && letter <= 25) {
			return (char) (letter + 65);
		}
		if(letter >= 26 && letter <= 51) {
			return (char) (letter + 71);
		}
		if(letter >= 52 && letter <= 61) {
			return (char) (letter - 4);
		}
		if(letter == 62) {
			return 43;
		}
		if(letter == 63) {
		    return 47;	
		}
		return letter;
	}
	
	public static char indexForDecoding(char letter) {
        if(letter >= 65 && letter <= 90) {
	        return (char) (letter - 65);	
		}
		if(letter >= 97 && letter <= 122) {
		    return (char) (letter - 71);	
		}
		if(letter >= 48 && letter <= 57) {
			return (char) (letter + 4);	
		}
		if(letter == 43) {
			return 62;	
		}
		if(letter == 47) {
			return 63;
		}
		return 127;
	}

}