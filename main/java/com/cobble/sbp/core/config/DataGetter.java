package com.cobble.sbp.core.config;

import java.io.FileReader;
import java.util.ArrayList;

import com.cobble.sbp.SBP;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class DataGetter
{			
	/** Returns a boolean at the specified config value
	 * Returns false if fails
	 */
	public static Boolean findBool(String boolName) {
		try {
			/*for(String var : ConfigHandler.forceEnabled) {
				if(boolName.equals(var)) {
					ConfigHandler.newObject(boolName, true);
					return true;
				}
			}
			for(String var : ConfigHandler.forceDisabled) {
				if(boolName.equals(var)) {
					ConfigHandler.newObject(boolName, false);
					return false;
				}
			}*/
			//Utils.print("------------------------------------");
			//Utils.print(boolName+" <-001-> "+find(boolName));
		return Boolean.parseBoolean(find(boolName)+"");
		} catch (Exception e) {
			try {
				//Utils.print(boolName+" <-002-> "+ConfigHandler.getDefaultValue(boolName));
			return (Boolean) ConfigHandler.getDefaultValue(boolName);
			} catch(Exception e2) {
				//Utils.print(boolName+" <-003-> null");
				return null;
			}
		}
	}

	public static double findDoub(String doubName) {
		try {
			return Double.parseDouble(find(doubName)+"");
		} catch(Exception e) {
			try {
				return Double.parseDouble(ConfigHandler.getDefaultValue(doubName)+"");
			} catch(Exception e2) {
				return -69.0;
			}
		}
	}

	public static float findFloat(String floatName) {
		try {
			return Float.parseFloat(find(floatName)+"");
		} catch(Exception e) {
			try {
				return Float.parseFloat(ConfigHandler.getDefaultValue(floatName)+"");
			} catch(Exception e2) {
				return -69.0F;
			}
		}
	}
	
	/** Returns a string at the specified config value
	 * Returns "false" if fails
	 */
	public static String findStr(String strName) {
		try {
		return find(strName)+"";
		} catch (Exception e) {
			try {
				return ConfigHandler.getDefaultValue(strName)+"";
				} catch(Exception e2) {
					return null;
				}
		}
	}
	
	/** Returns an int at the specified config value
	 * Returns -1 if fails
	 */
	public static int findInt(String intName) {
		try {
			//Utils.print(intName+": "+find(intName));
		return Integer.parseInt(find(intName)+"");
		} catch (Exception e) {
			try {
				return Integer.parseInt(ConfigHandler.getDefaultValue(intName)+"");
				} catch(Exception e2) {
					return -69;
				}
		}
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
			//Utils.sendMessage(jsonKey);
			//Utils.sendMessage(objectList);
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



								//}
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
		} catch(Exception e) { /*e.printStackTrace();*/ return "N/A";}

	}
	
	public static Object find(String objectName) {

		if(ConfigHandler.configObj == null) { updateConfig("main"); }
		try {
		JSONObject data = (JSONObject) ConfigHandler.configObj;

		return data.get(objectName)+"";
		} catch(Exception e) {
			//e.printStackTrace();
			Utils.print("Failed to find: "+objectName);
			return null;
		}
	}
	
	public static Object find(String fileName, String objectName) {
		if(ConfigHandler.configObj == null) {
			updateConfig(fileName);
		}
		try {
			JSONObject data = (JSONObject) ConfigHandler.configObj;
			return data.get(objectName);
		} catch(NullPointerException ignored) {
			
		} catch(Exception e) {
			return "failed";
		}
		return "failed";
	}
	
	public static void updateConfig(String fileName) {
		JSONParser parser = new JSONParser();
		try {
			ConfigHandler.configObj = parser.parse(new FileReader("config/"+Reference.MODID+"/"+fileName+".cfg"));
		} catch (Exception e) {
			SBP.firstLaunch=true;
		}
	}
	
	
}
