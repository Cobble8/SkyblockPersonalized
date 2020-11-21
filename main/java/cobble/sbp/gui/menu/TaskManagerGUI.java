package cobble.sbp.gui.menu;

import java.io.IOException;
import java.util.ArrayList;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.handlers.ConfigHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class TaskManagerGUI extends GuiScreen {

	private GuiButton ButtonClose;
	private GuiButton ButtonNew;
	private float bgColorID = 0;
	int line;
	boolean firstTime = false;
	ArrayList<String> wordArray = new ArrayList<String>();
	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(ButtonNew = new GuiButton(15, this.width / 2 - 100, this.height - (this.height / 4) - 20, 200, 20, ChatFormatting.YELLOW+"New Task"));
		this.buttonList.add(ButtonClose = new GuiButton(0, this.width / 2 - 100, this.height - (this.height / 4) + 10, ChatFormatting.RED+"Close"));
		for(int i=1;i<10;i++) {
		try { ConfigHandler.newObject(/*"task"+i+"Text", i+" | "+i*/"task1Text", "This is a sentence that is WAY to long for sure"); } catch (IOException e) { e.printStackTrace(); }
		}
		line=0;
		firstTime=true;
		drawTasks();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	public void drawTasks() {
		String line1 = "";
		String line2 = "";
		String line3 = "";
		String line4 = "";
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				
				line1="";
				line2="";
				line3="";
				line4="";
				line=0;
				String wordString = (String) DataGetter.find("task"+(i+1)*(j+1)+"Text");
				String[] tempWordArray = wordString.split(" ");
				
				if(firstTime) {
					Utils.print("firstTime");
					for(int k=0;k<tempWordArray.length;k++)	{
						wordArray.add(tempWordArray[k]);
					}
					firstTime=false;
				} else {
					Utils.print(tempWordArray.length +" | "+ wordString);
					Utils.print(wordArray.size() +" | "+ wordArray);
					for(int p=0;p<tempWordArray.length;p++) {
						wordArray.set(p, "");
					}
					
					for(int k=0;k<tempWordArray.length;k++) {
						wordArray.set(k, tempWordArray[k]);
					}
				}
				for(int l=0;l<wordArray.size();l++) {
				
					if(wordArray.size()-1 > l) {
						if(wordArray.get(l).length()+wordArray.get(l+1).length() < 15) {
							wordArray.set(l, wordArray.get(l)+" "+wordArray.get(l+1));
							wordArray.remove(l+1);
						} else {
						}
					}
				}
				if(wordArray.size() >=1) line1=wordArray.get(0);
				if(wordArray.size() >=2) line2=wordArray.get(1);
				if(wordArray.size() >=3) line3=wordArray.get(2);
				if(wordArray.size() >=4) line4=wordArray.get(3);
				
				this.mc.getTextureManager().bindTexture(taskBG);
        		this.drawModalRectWithCustomSizedTexture((width/2-192)+(130*i), 20+50*j, 0f, bgColorID*96, 128, 48, 128, 144);
				mc.fontRendererObj.drawString(ChatFormatting.WHITE+line1, (width/2-186)+(130*i), 26+50*j, 0, true);
        		mc.fontRendererObj.drawString(ChatFormatting.WHITE+line2, (width/2-186)+(130*i), 36+50*j, 0, true);
        		mc.fontRendererObj.drawString(ChatFormatting.WHITE+line3, (width/2-186)+(130*i), 46+50*j, 0, true);
        		mc.fontRendererObj.drawString(ChatFormatting.WHITE+line4, (width/2-186)+(130*i), 56+50*j, 0, true);
			}
		}
	}
	
	
	
	ResourceLocation taskBG = new ResourceLocation("sbp:textures/gui/taskBG.png");
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		
		this.drawDefaultBackground();
        drawTasks();
        
        super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	
	
	protected void actionPerformed(GuiButton button) {
		if(button == ButtonClose) {
            mc.thePlayer.closeScreen();
        } else if(button == ButtonNew) {
        	bgColorID++;
        	if(bgColorID == 3) bgColorID=0;
        }
	}
	
	@Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return false;
    }
}
