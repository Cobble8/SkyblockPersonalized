package cobble.sbp.utils;

import java.util.Arrays;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.Object;


public class GetFromAPI {
	static String[] profileUUIDs = new String[5];
	
	public static int getMostDamage(String file, String uuid, String profileuuid, String floor, String selectedClass) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		int temp = 0;
		String temp1 = dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs")+"";
		//String temp1 = dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsString();
		//Utils.print(temp1);
		if (temp1.contains("most_damage_"+selectedClass)) {
			Utils.print(floor+" | "+ selectedClass);
			temp = dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("most_damage_"+selectedClass).getAsJsonObject().get(floor).getAsInt();
		} else {
			temp = 1;
		}
		//temp = dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("most_damage_"+selectedClass).getAsJsonObject().get(floor).getAsInt();}
		return temp;
	}

	
	public static int getMostHealing(String file, String uuid, String profileuuid, String floor) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("most_healing").getAsJsonObject().get(floor).getAsInt();
		}
	
	public static int getFastestTime(String file, String uuid, String profileuuid, String floor) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {if(profileUUIDs[i] == profileuuid) {profileID = i;}}
		
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("fastest_time").getAsJsonObject().get(floor).getAsInt();
		}
	public static int getFastestSTime(String file, String uuid, String profileuuid, String floor) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {if(profileUUIDs[i] == profileuuid) {profileID = i;}}
		
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("fastest_time_s").getAsJsonObject().get(floor).getAsInt();
		}
	public static int getFastestSPlusTime(String file, String uuid, String profileuuid, String floor) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {if(profileUUIDs[i] == profileuuid) {profileID = i;}}
		
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("fastest_time_s_plus").getAsJsonObject().get(floor).getAsInt();
		}
	
	
	public static int getMostKills(String file, String uuid, String profileuuid, String floor) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("most_mobs_killed").getAsJsonObject().get(floor).getAsInt();
		}
	
	
	public static int getWatcherKills(String file, String uuid, String profileuuid, String floor) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("watcher_kills").getAsJsonObject().get(floor).getAsInt();
		}
	
	public static int getFloorAttempts(String file, String uuid, String profileuuid, String floor) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("times_played").getAsJsonObject().get(floor).getAsInt();
		}
	
	public static int getBestScore(String file, String uuid, String profileuuid, String floor) {
		JsonParser parser = new JsonParser();
		JsonElement bestScore = parser.parse(file.toString());
		int profileID = 0;
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		return bestScore.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("best_score").getAsJsonObject().get(floor).getAsInt();
	}
	
	public static String getSelectedDungeonClass(String file, String uuid, String profileuuid) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("selected_dungeon_class").getAsString();

		}
	
	
	
	public static int getFloorRuns(String file, String uuid, String profileuuid, String floor) {
		
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("tier_completions").getAsJsonObject().get(floor).getAsInt();
	}
	 
	public static int getCataXP(String file, String uuid, String profileuuid) {
		
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("experience").getAsInt();
		
	}
	
	public static int getClassXP(String file, String uuid, String profileuuid) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		String selectedClass = getSelectedDungeonClass(file, uuid, profileuuid).toString();
		return dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("player_classes").getAsJsonObject().get(selectedClass).getAsJsonObject().get("experience").getAsInt();
		

		}
	
	public static int getHighestFloor(String file, String uuid, String profileuuid) {
		
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		
		int temp = dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("highest_tier_completed").getAsInt();
		return temp;
	}
	public static String getCurrProfile(String file, String uuid) {
		String profileID = "";
		long[] profileRecents = new long[5];
		long[] profileNums = new long[5];
		int profileRecent = 0;
		if (file != null) {
            JsonObject obj = new JsonParser().parse(file).getAsJsonObject();
            if (obj.get("success").getAsBoolean()) {
                try {
                    JsonArray profileList = obj.getAsJsonArray("profiles");
                    //gets all of the last save values from all profiles of the person with this uuid
                    for(int i=0; i<profileList.size();i++) {
                    	profileRecents[i] = profileList.get(i).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("last_save").getAsLong();
                    	profileUUIDs[i] = profileList.get(i).getAsJsonObject().get("profile_id").getAsString();
                    	
                    }
                    //copies it into a new array so the order will be preserved
                    for(int i=0;i<profileRecents.length;i++) {
                    	//its abstract because sometimes the turn out negative for some reason
                    	profileNums[i] = profileRecents[i];
                    }
                    //gets highest value of all the last_save values
                    Arrays.sort(profileNums);
                    long highestProfile = profileNums[profileNums.length-1];
                    //checks it against all of the original array's values to see which ID the profile is (can be 0-4)
                    for(int i=0;i<profileRecents.length;i++) {
                    	if(profileRecents[i] == highestProfile) {
                    		profileRecent = i;
                    	}
                    }
                    //gets the profile uuid with the ID gotten before (will output a profile ID)
                    profileID = profileList.get(profileRecent).getAsJsonObject().get("profile_id").getAsString();
                    
                } catch (Exception e1) {e1.printStackTrace();}
            } else {
                return "NOT_FOUND";
            }
        }
		return profileID;
	}
	
	public static String getUUID(String file) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		String uuid = dungeonClass.getAsJsonObject().get("id").getAsString();
		if(uuid != null) {return uuid;}
		else return "Invalid Name";
	}
	
	
	public static int getAchiements(String file, String key) {
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		return dungeonClass.getAsJsonObject().get("player").getAsJsonObject().get("achievements").getAsJsonObject().get(key).getAsInt();
	}
	
	
	
}
