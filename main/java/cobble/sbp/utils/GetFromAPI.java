package cobble.sbp.utils;

import java.util.Arrays;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cobble.sbp.threads.DungeonsFloorThread;

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

		if (temp1.contains("most_damage_"+selectedClass)) {
			temp = dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("most_damage_"+selectedClass).getAsJsonObject().get(floor).getAsInt();
		} else {
			temp = 1;
		}
		return temp;
	}
	
	public static long getHighestFloorDamage(String file, String uuid, String profileuuid, String floor) {
		int profileID = 0;
		
		for(int i=0;i<profileUUIDs.length;i++) {
			if(profileUUIDs[i] == profileuuid) {
				profileID = i;
			}
		}
		Boolean mageDamageToggle;
		Boolean healerDamageToggle;
		Boolean tankDamageToggle;
		Boolean berserkDamageToggle;
		Boolean archerDamageToggle;
		
		long mDmg;
		long hDmg;
		long tDmg;
		long bDmg;
		long aDmg;
		
		JsonParser parser = new JsonParser();
		JsonElement dungeonClass = parser.parse(file.toString());
		String findDamage = dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs")+"";
		JsonObject findClassFloors = dungeonClass.getAsJsonObject().get("profiles").getAsJsonArray().get(profileID).getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject().get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject();
		
		if(findDamage.contains("most_damage_mage")) { 
			if((findClassFloors.get("most_damage_mage")+"").contains("\""+floor+"\"")) {
				mageDamageToggle = true; 
			} else {mageDamageToggle = false;}
		} else {mageDamageToggle = false;}
		
		if(findDamage.contains("most_damage_healer")) { 
			if((findClassFloors.get("most_damage_healer")+"").contains("\""+floor+"\"")) {
				healerDamageToggle = true; 
			} else {healerDamageToggle = false;}
		} else {healerDamageToggle = false;}
		
		if(findDamage.contains("most_damage_tank")) { 
			if((findClassFloors.get("most_damage_tank")+"").contains("\""+floor+"\"")) {
				tankDamageToggle = true; 
			} else {tankDamageToggle = false;}
		} else {tankDamageToggle = false;}
		
		if(findDamage.contains("most_damage_berserk")) { 
			if((findClassFloors.get("most_damage_berserk")+"").contains("\""+floor+"\"")) {
				berserkDamageToggle = true; 
			} else {berserkDamageToggle = false;}
		} else {berserkDamageToggle = false;}
		
		if(findDamage.contains("most_damage_archer")) { 
			if((findClassFloors.get("most_damage_archer")+"").contains("\""+floor+"\"")) {
				archerDamageToggle = true; 
			} else {archerDamageToggle = false;}
		} else {archerDamageToggle = false;}
		
		if(mageDamageToggle) {mDmg = getMostDamage(file, uuid, profileuuid, floor, "mage");} else {mDmg = 1;}
		if(healerDamageToggle) {hDmg = getMostDamage(file, uuid, profileuuid, floor, "healer");} else {hDmg = 1;}
		if(tankDamageToggle) {tDmg = getMostDamage(file, uuid, profileuuid, floor, "tank");} else {tDmg = 1;}
		if(berserkDamageToggle) {bDmg = getMostDamage(file, uuid, profileuuid, floor, "berserk");} else {bDmg = 1;}
		if(archerDamageToggle) {aDmg = getMostDamage(file, uuid, profileuuid, floor, "archer");} else {aDmg = 1;}
		
		long[] compareDamage = {mDmg, hDmg, tDmg, bDmg, aDmg};
		Arrays.sort(compareDamage);
		long mostDamage = compareDamage[compareDamage.length-1];
		
		if(mostDamage == mDmg) {DungeonsFloorThread.highestClass = "Mage";}
		else if(mostDamage == hDmg) {DungeonsFloorThread.highestClass = "Healer";}
		else if(mostDamage == tDmg) {DungeonsFloorThread.highestClass = "Tank";}
		else if(mostDamage == bDmg) {DungeonsFloorThread.highestClass = "Berserk";}
		else if(mostDamage == aDmg) {DungeonsFloorThread.highestClass = "Archer";}
		
		return mostDamage;
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
