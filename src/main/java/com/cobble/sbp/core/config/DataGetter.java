package com.cobble.sbp.core.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class DataGetter
{			


	/** Returns a boolean at the specified config value
	 * Returns false if fails
	 */
	public static boolean findBool(String boolName) {
		Object value;
		try { value = ConfigHandler.config.get(boolName);
		} catch(Exception e) { value = ConfigHandler.getDefaultValue(boolName); }
		if(value instanceof Boolean) {
			return (Boolean) value;
		} else { return false; }
	}

	/** Returns a double at the specified config value
	 * Returns -69.0d if fails
	 */
	public static double findDoub(String doubName) {
		Object value;
		try { value = ConfigHandler.config.get(doubName);
		} catch(Exception e) { value = ConfigHandler.getDefaultValue(doubName); }
		if(value instanceof Double) {
			return (Double) value;
		} else { return -69.0d; }
	}

	/** Returns a long at the specified config value
	 * Returns -69.0 if fails
	 */
	public static long findLong(String longName) {
		Object value;
		try { value = ConfigHandler.config.get(longName);
		} catch(Exception e) { value = ConfigHandler.getDefaultValue(longName); }
		if(value instanceof Long) {
			return (Long) value;
		} else { return (long) -69.0; }
	}

	/** Returns a float at the specified config value
	 * Returns -69.0f if fails
	 */
	public static float findFloat(String floatName) {
		Object value;
		try { value = ConfigHandler.config.get(floatName);
		} catch(Exception e) { value = ConfigHandler.getDefaultValue(floatName); }
		if(value instanceof Float) {
			return (Float) value;
		} else { return -69.0f; }
	}

	/** Returns a string at the specified config value
	 * Returns "null" if fails
	 */
	public static String findStr(String strName) {
		Object value;
		try { value = ConfigHandler.config.get(strName);
		} catch(Exception e) { value = ConfigHandler.getDefaultValue(strName); }
		if(value instanceof String) {
			return value+"";
		} else { return "null"; }
	}

	/** Returns an int at the specified config value
	 * Returns -69 if fails
	 */
	public static int findInt(String intName) {
		Object value;
		try { value = ConfigHandler.config.get(intName);
		} catch(Exception e) { value = ConfigHandler.getDefaultValue(intName); }

		if(value instanceof Integer) {
			return (Integer) value;
		} else if(value instanceof Long) {
			return Math.toIntExact((Long) value);
		}

		/*try { return (int) value;
		} catch(Exception e) { System.out.println("Failed to find int value: '"+intName+"' -> '"+value+"'\n"+e.getMessage()); return -69; }*/

		return -69;
	}
	
	public static Object lazyFind(String jsonKey, String jsonInfo) {
		try {
			char[] jsonKeyChars = jsonKey.toCharArray();
			//Make a system for lazyFind that works with arrays aswell.

			ArrayList<String> objectList = new ArrayList<>();
			int lastSpot = 0;
			for(int i=0;i<jsonKeyChars.length;i++) {
				String currChar = jsonKeyChars[i]+"";
				//Normal Object
				if(currChar.replace(".", "/").equals("/")) {
					String nextObj = jsonKey.substring(lastSpot, i);
					lastSpot=i+1;
					objectList.add("o-"+nextObj);
				}
				//Array Object
				else if(currChar.equals(";")) {
					String nextObj = jsonKey.substring(lastSpot, i);
					lastSpot=i+1;
					objectList.add("a-"+nextObj);
				}
			}

			String currSearch = jsonInfo;
			JsonParser parser = new JsonParser();
			for(int i=0;i<objectList.size();i++) {
				if(objectList.get(i).startsWith("o-")) {
					objectList.set(i, objectList.get(i).substring(2));
					JsonElement searching = parser.parse(currSearch);
					currSearch = searching.getAsJsonObject().get(objectList.get(i))+"";

				} else if(objectList.get(i).startsWith("a-")) {
					objectList.set(i, objectList.get(i).substring(2));
					JsonElement searching = parser.parse(currSearch);
					int getID;
					try {
						getID = Integer.parseInt(objectList.get(i));
					} catch(Exception e) {
						ArrayList<Object> findInArray= new ArrayList<>();
						JsonArray JSONfindInArray = searching.getAsJsonArray();
						for(int j=0;j<JSONfindInArray.size();j++) { findInArray.add(JSONfindInArray.get(j)); }
						getID=0;

						if(objectList.get(i).contains("|")) {
							int spot = objectList.get(i).indexOf("|");
							String objName = objectList.get(i).substring(0, spot);
							String resName = objectList.get(i).substring(spot + 1);

							for(int j=0;j<findInArray.size();j++) {
								try {
									if(resName.equals(JSONfindInArray.get(j).getAsJsonObject().get(objName).getAsString())) {
										getID=j;
										break;
									}
								} catch(Exception ignored) { }
							}


						} else {
							for (int j = 0; j < findInArray.size(); j++) {
								if (objectList.get(i).equals(findInArray.get(j).toString())) {
									getID = j;
									break;
								}
							}
						}
					}
					currSearch = searching.getAsJsonArray().get(getID).toString();
				}
			}
			currSearch = parser.parse(currSearch).getAsString().replace("\"","");
			return currSearch;
		} catch(Exception e) {

			e.printStackTrace();
			return "N/A";}
	}

	
	
}
