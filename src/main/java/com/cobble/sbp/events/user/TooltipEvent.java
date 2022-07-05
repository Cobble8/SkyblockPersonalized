package com.cobble.sbp.events.user;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.handlers.GuiChestHandler;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.TextUtils;
import com.cobble.sbp.utils.Utils;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TooltipEvent {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onItemTooltip(ItemTooltipEvent event) {
        if(GuiChestHandler.menuName.equals("calendar_and_events") && DataGetter.findBool("misc.eventTimes.toggle")) {

            List<String> desc = event.toolTip;
            for(String line : desc) {
                String curr = TextUtils.unformatAllText(line);
                if(curr.startsWith("Starts in: ")) {

                    String parse = curr.substring(11);
                    String[] times = parse.split(" ");
                    long totalTime = 0;
                    int s;
                    int m;
                    int h;
                    int d = 0;
                    for(String time : times) {
                        try {
                            String tmp = time;
                            if(tmp.contains("s")) {
                                tmp = tmp.replace("s","");
                                s = Integer.parseInt(tmp);
                                totalTime+= s * 1000L;
                            } else if(tmp.contains("m")) {
                                tmp = tmp.replace("m","");
                                m = Integer.parseInt(tmp);
                                totalTime+= m * 60000L;
                            } else if(tmp.contains("h")) {
                                tmp = tmp.replace("h","");
                                h = Integer.parseInt(tmp);
                                totalTime+= h * 3600000L;
                            } else if(tmp.contains("d")) {
                                tmp = tmp.replace("d","");
                                d = Integer.parseInt(tmp);
                                totalTime+= d * 86400000L;
                            }
                        } catch(Exception e) {
                            Utils.printDev(e);
                        }

                    }
                    long t = totalTime+System.currentTimeMillis();

                    boolean military = DataGetter.findBool("misc.eventTimes.military");
                    String day;
                    if(d == 0) {day = "Today";}
                    else if(d == 1) {day = "Tomorrow";}
                    else { day = new SimpleDateFormat("EEEE").format(t); }

                    String time;
                    if(military) {
                        time = (new SimpleDateFormat("HH:mm")).format(t);
                    } else {
                        time = (new SimpleDateFormat("hh:mm aa")).format(t);
                    }
                    if(time.startsWith("0")) {
                        time = time.substring(1);
                    }
                    String prefix = "";
                    String suffix = "";
                    if(d/7 > 1 ) {
                        prefix = (d/7)+" ";
                        suffix = "s away";
                    }
                    else if(d/7 > 0) {
                        prefix = "Next ";
                    }
                    String fin = Colors.GREEN+prefix+day+suffix+" at "+time;
                    List<String> newToolTip = new ArrayList<>();
                    for(String tip : event.toolTip) {
                        newToolTip.add(tip);
                        String str = TextUtils.unformatAllText(tip);
                        if(str.startsWith("Starts in: ")) {
                            newToolTip.add(fin);
                        }
                    }
                    event.toolTip.clear();
                    event.toolTip.addAll(newToolTip);
                    break;
                }
            }
        }
    }

}
