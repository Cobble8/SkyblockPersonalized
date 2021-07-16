package com.cobble.sbp.handlers;

import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class RecieveCrystalMapHandler {

    public RecieveCrystalMapHandler(String message) {
        try {

            String key = message.substring(message.indexOf("sbpMap")+6);
            int vers = Integer.parseInt(key.substring(0,1));
            key = key.substring(1);
            ArrayList<Integer> loadLocs = new ArrayList<>();
            char[] chars = key.toCharArray();
                int lastNum = 0;
                boolean skip = false; //if should skip next
                boolean let = false; //if letter is next

            for(int i=0;i<chars.length;i++) {
                try {


                    if(skip) { // if we need to skip current spot
                        skip=false;
                        continue;
                    }

                    String currLet = chars[i]+""; //current char
                    int currNum = chars[i]-50;
                    if(let) { //if the previous spot, determined this spot was a location
                        let=false;
                        for(int j=0;j<lastNum;j++) {
                            loadLocs.add(letToNum(currLet)); //adds the current letter lastNum amt of times
                        }
                    } else { //if it didn't do the previous if statement
                        String nextLet = chars[i+1]+""; //next char
                        if("fhklmn".contains(nextLet)) { //check if next char is a location
                            lastNum=currNum;
                            let=true;
                        } else {//if next char is NOT a location, add blank spaces
                            for(int j=0;j<currNum;j++) {
                                loadLocs.add(-1);
                            }
                        }
                    }
                } catch(Exception e) {e.printStackTrace();}
            }

            int[][] newLocs = new int[50][50];
            for(int x=0;x<50;x++) {
                for(int y=0;y<50;y++) {
                    int curr = (x*50)+y;
                    if(vers != 1) {
                        curr = (y*50)+x;
                    }
                    newLocs[x][y]= loadLocs.get(curr);
                }
            }

            for(int i=0;i<50;i++) {
                Utils.print(Arrays.toString(newLocs[i]));
            }

            //CrystalHollowsMap.resetLocs();

            /*
            boolean skip = false;
            for(int i=0;i<chars.length;i++) {
                if(skip) {
                    skip=false;
                    continue;
                }

                try {
                    int curr = (int) chars[i];
                    //int next = chars[i+1];
                    //Utils.print(chars[i] +" -> "+chars[i+1]+"   :   "+((int)chars[i])+" -> "+((int)chars[i+1]));
                    Utils.print("fhklmn".contains(chars[i+1]+""));
                    if(curr > 45) {
                        if("fhklmn".contains(chars[i+1]+"")) {
                            Utils.print("Found - "+i);
                            for(int j=0;j<curr;j++) {
                                //Utils.print("FOUND!");
                                loadLocs.add(letToNum((chars[i+1]-50)+""));
                                //Utils.print(loadLocs.get(loadLocs.size()-2));
                                Utils.print((char) (chars[i+1]-50));
                                //Utils.print((char) (chars[i+1]-50)+" : "+letToNum(((chars[i+1] -50)+"")));
                                //Utils.print("ADDCHAR : "+chars[i+1]);
                            }
                            skip=true;
                        } else {
                            for(int j=0;j<curr;j++) {
                                loadLocs.add(-2);
                            }

                        }
                    }
                } catch(Exception e) {
                    loadLocs.add(-3);
                }

            }

            //Utils.print(loadLocs.size());
            //Utils.print(loadLocs);

            //Utils.print("ARRAYS");
            if(vers == 1) {
                for(int i=0;i<50;i++) {
                    for(int j=0;j<50;j++) {
                        newLocs[i][j] = loadLocs.get((i * 50) + j);
                    }
                }
            } else {
                for(int i=0;i<50;i++) {
                    for(int j=0;j<50;j++) {
                        newLocs[j][i]=loadLocs.get((i*50)+j);
                    }
                   //Utils.print(Arrays.toString(newLocs[i]));
                }
            }
            Utils.print("ANYTHING");
            for(int i=0;i<50;i++) {

                //Utils.print(Arrays.toString(newLocs[i]));
            }

            //CrystalHollowsMap.locs=newLocs;
            */

            //for each char
            //check if above 45,
            //yes -> check if next is above 45
            //  yes -> add prev num blanks
            //  no -> add num of next locs
            //no -> should be skipped


        } catch(Exception ignored) {}
    }

    public static int letToNum(String let) {
        int output = -1;


        String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        for(int i=0;i<letters.length;i++) {
            if(let.equals(letters[i])) {
                output = i;
                break;
            }
        }

        return output;
    }


}
