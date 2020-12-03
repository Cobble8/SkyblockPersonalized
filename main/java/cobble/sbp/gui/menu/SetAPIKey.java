package cobble.sbp.gui.menu;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import cobble.sbp.utils.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class SetAPIKey extends GuiScreen {
	
	private GuiTextField inputNum;
	private GuiButton buttonCancel;
	
	@Override
	public void initGui() {
		this.buttonList.add(buttonCancel = new GuiButton(0, this.width/2-80, this.height-40, 160, 20, ChatFormatting.RED+"Cancel"));
	    this.inputNum = new GuiTextField(1, this.fontRendererObj, this.width/2-110, this.height/2, 220, 20);
	    this.inputNum.setText("");
		this.inputNum.setMaxStringLength(36);
		this.inputNum.setFocused(false);
		//this.inputNum.setText
		//this.buttonList.add(buttonClose = new GuiButton(0, xClose+160, yClose+20, 160, 20, "Hi :D"));
		super.initGui();
	}
	
	  protected void keyTyped(char par1, int par2) throws IOException
	    {
	        super.keyTyped(par1, par2);
	        this.inputNum.textboxKeyTyped(par1, par2);
	        if(!( par2== Keyboard.KEY_E  &&  this.inputNum.isFocused())) super.keyTyped(par1, par2);
	        if(par2==Keyboard.KEY_RETURN && this.inputNum.isFocused()) {
	        		
	        	//if(Utils.isNumeric(this.inputNum.getText())) {
	        		this.inputNum.setFocused(false);
	        		ConfigHandler.newObject("APIKey", this.inputNum.getText());
	        		Minecraft.getMinecraft().displayGuiScreen(new Main());
	        	//}
	        	
	        	
	        	
	        		//inputNum.newStringObject("temp", this.inputNum.getText());
	        }
	        
	        
	    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.inputNum.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.inputNum.drawTextBox();
		
		

		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"Please enter your new API key below:", this.width/2-90, this.height/2-12, 0x10, true);
		
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	 @Override
	    public boolean doesGuiPauseGame() {
	        return false;
	    }
	    
	    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
	        return false;
	    }
	    
	    @Override
	    public void actionPerformed(GuiButton button) throws IOException {
	    	if(button == buttonCancel) {
	    		Minecraft.getMinecraft().displayGuiScreen(new Main());
	    	}
	    }
}
